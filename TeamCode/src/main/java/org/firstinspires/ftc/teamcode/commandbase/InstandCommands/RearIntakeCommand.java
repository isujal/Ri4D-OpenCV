package org.firstinspires.ftc.teamcode.commandbase.InstandCommands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.subsystem.intaksubsystem;


public class RearIntakeCommand extends InstantCommand {
    public RearIntakeCommand(intaksubsystem intake, intaksubsystem.REAR_INTAKE_STATES state) {
        super(() -> intake.updateState(state));
    }
}
