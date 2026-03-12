package org.firstinspires.ftc.teamcode.commandbase.InstandCommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import java.util.function.BooleanSupplier;

/**
 * Optimized WaitForSensorCommand
 * Prevents system hangs by enforcing a default safety timeout and 
 * reducing CPU load during sensor failures.
 */
public class checksensor extends CommandBase {

    private final BooleanSupplier condition;
    private final long timeoutMs;
    private final long debounceMs;
    private final boolean waitForFalse;
    private final boolean triggerOnChange;

    private boolean lastState;
    private final ElapsedTime timer;
    private final ElapsedTime debounceTimer;
    private boolean sensorErrorLogged = false;

    /**
     * Safety Default: If no timeout is provided, it defaults to 5 seconds 
     * to prevent the robot from "hanging" and restarting if the sensor fails.
     */
    public checksensor(BooleanSupplier condition) {
        this(condition, 5000, 0, false, false);
    }

    public checksensor(BooleanSupplier condition, long timeoutMs) {
        this(condition, timeoutMs, 0, false, false);
    }

    public checksensor(BooleanSupplier condition, long timeoutMs, long debounceMs,
                       boolean waitForFalse, boolean triggerOnChange) {
        this.condition = condition;
        this.timeoutMs = timeoutMs;
        this.debounceMs = debounceMs;
        this.waitForFalse = waitForFalse;
        this.triggerOnChange = triggerOnChange;
        this.timer = new ElapsedTime();
        this.debounceTimer = new ElapsedTime();
    }

    @Override
    public void initialize() {
        timer.reset();
        debounceTimer.reset();
        sensorErrorLogged = false;
        
        // Initial state check
        lastState = safeGetCondition();
    }

    @Override
    public boolean isFinished() {
        // 1. EMERGENCY TIMEOUT (Prevents Robot Restart/Hang)
        if (timeoutMs > 0 && timer.milliseconds() >= timeoutMs) {
//            RobotLog.wwarning("WaitForSensorCommand: TIMEOUT REACHED - Ending command to prevent hang.");
            return true;
        }

        boolean sensorValue = safeGetCondition();

        // 2. Edge Detection Logic
        if (triggerOnChange && (sensorValue != lastState)) {
            return true;
        }

        // 3. Condition Logic (Wait for True or Wait for False)
        boolean desiredStateMet = waitForFalse ? !sensorValue : sensorValue;

        if (desiredStateMet) {
            if (debounceTimer.milliseconds() >= debounceMs) {
                return true; 
            }
        } else {
            debounceTimer.reset();
        }

        return false;
    }

    /**
     * Helper to read the sensor without crashing the CPU.
     * If the sensor is null or throws an error, it returns false and logs once.
     */
    private boolean safeGetCondition() {
        try {
            if (condition == null) return false;
            return condition.getAsBoolean();
        } catch (Exception e) {
            if (!sensorErrorLogged) {
                RobotLog.e("WaitForSensorCommand: Sensor read failed! Check hardware map.");
                sensorErrorLogged = true;
            }
            return false;
        }
    }
}