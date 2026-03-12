package org.firstinspires.ftc.teamcode.commandbase.InstandCommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.control.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.function.BooleanSupplier;

/**
 * A versatile command that waits for a sensor condition to be met.
 * It includes options for timeouts, debouncing (settle time), and edge detection.
 */
public class shooterpidrun extends CommandBase {


    private  PIDFController shootCtrl;

    /**
     * Waits until the condition is true. No timeout.
     * Example: new WaitForSensorCommand(() -> myLimitSwitch.isPressed())
     */
    public shooterpidrun(BooleanSupplier condition) {
        this(condition, 0, 0, false, false);
    }
    /**
     * Waits until the condition is true, with a timeout.
     * @param condition The boolean condition to check.
     * @param timeoutMs The maximum time to wait in milliseconds.
     */
    public shooterpidrun(BooleanSupplier condition, long timeoutMs) {
        this(condition, timeoutMs, 0, false, false);
    }

    /**
     * Waits until the condition is true for a continuous duration (debounce), with a timeout.
     * This is useful for noisy sensors or for ensuring a state is stable.
     * @param condition   The boolean condition to check.
     * @param timeoutMs   The maximum time to wait in milliseconds.
     * @param debounceMs  The time in milliseconds the condition must remain true before finishing.
     */


    /**
     * The full constructor with all options.
     * @param condition        The boolean condition to check.
     * @param timeoutMs        The maximum time to wait in milliseconds (0 for no timeout).
     * @param debounceMs       The time in milliseconds the condition must remain stable.
     * @param waitForFalse     If true, waits for the condition to become false instead of true.
     * @param triggerOnChange  If true, finishes the instant the condition's state changes.
     */
    public shooterpidrun(BooleanSupplier condition, long timeoutMs, long debounceMs,
                         boolean waitForFalse, boolean triggerOnChange) {

    }

    @Override
    public void initialize() {

    }

    @Override
    public boolean isFinished() {

        return false;
    }
}





