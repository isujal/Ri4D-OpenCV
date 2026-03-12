package org.firstinspires.ftc.teamcode.commandbase.InstandCommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.function.BooleanSupplier;

/**
 * A versatile command that waits for a sensor condition to be met.
 * It includes options for timeouts, debouncing (settle time), and edge detection.
 */
public class WaitForSensorCommand extends CommandBase {

    private final BooleanSupplier condition;
    private final long timeoutMs;
    private final long debounceMs;
    private final boolean waitForFalse;
    private final boolean triggerOnChange;

    private boolean lastState;
    private long startTime = 0;
    private final ElapsedTime debounceTimer;

    /**
     * Waits until the condition is true. No timeout.
     * Example: new WaitForSensorCommand(() -> myLimitSwitch.isPressed())
     */
    public WaitForSensorCommand(BooleanSupplier condition) {
        this(condition, 0, 0, false, false);
    }
    /**
     * Waits until the condition is true, with a timeout.
     * @param condition The boolean condition to check.
     * @param timeoutMs The maximum time to wait in milliseconds.
     */
    public WaitForSensorCommand(BooleanSupplier condition, long timeoutMs) {
        this(condition, timeoutMs, 0, false, false);
    }

    /**
     * Waits until the condition is true for a continuous duration (debounce), with a timeout.
     * This is useful for noisy sensors or for ensuring a state is stable.
     * @param condition   The boolean condition to check.
     * @param timeoutMs   The maximum time to wait in milliseconds.
     * @param debounceMs  The time in milliseconds the condition must remain true before finishing.
     */
    public WaitForSensorCommand(BooleanSupplier condition, long timeoutMs, long debounceMs) {
        this(condition, timeoutMs, debounceMs, false, false);
    }

    /**
     * The full constructor with all options.
     * @param condition        The boolean condition to check.
     * @param timeoutMs        The maximum time to wait in milliseconds (0 for no timeout).
     * @param debounceMs       The time in milliseconds the condition must remain stable.
     * @param waitForFalse     If true, waits for the condition to become false instead of true.
     * @param triggerOnChange  If true, finishes the instant the condition's state changes.
     */
    public WaitForSensorCommand(BooleanSupplier condition, long timeoutMs, long debounceMs,
                                boolean waitForFalse, boolean triggerOnChange) {
        this.condition = condition;
        this.timeoutMs = timeoutMs;
        this.debounceMs = debounceMs;
        this.waitForFalse = waitForFalse;
        this.triggerOnChange = triggerOnChange;
        this.debounceTimer = new ElapsedTime();
    }

    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
        debounceTimer.reset();
        try {
            lastState = condition.getAsBoolean();
        } catch (Exception e) {
            // Gracefully handle sensor initialization errors
            lastState = false;
        }
    }

    @Override
    public boolean isFinished() {
        // Timeout check is always first to prevent getting stuck
        if (timeoutMs > 0 && (System.currentTimeMillis() - startTime) >= timeoutMs) {
            return true;
        }

        boolean sensorValue;
        try {
            sensorValue = condition.getAsBoolean();
        } catch (Exception e) {
            // If a sensor disconnects mid-run, treat its value as false and don't crash
            sensorValue = false;
        }

        // Handle edge detection (trigger on any change)
        if (triggerOnChange) {
            if (sensorValue != lastState) {
                return true;
            }
        }

        // Determine the desired state (true, or false if inverted)
        boolean desiredStateMet = waitForFalse ? !sensorValue : sensorValue;

        // Handle debouncing logic
        if (desiredStateMet) {
            // The condition is met. Check if it has been stable for the required duration.
            if (debounceTimer.milliseconds() >= debounceMs) {
                return true; // Condition is stable and met. We are finished.
            }
        } else {
            // The condition is not met, so reset the debounce timer.
            debounceTimer.reset();
        }

        // If none of the finish conditions are met, continue waiting.
        return false;
    }
}





