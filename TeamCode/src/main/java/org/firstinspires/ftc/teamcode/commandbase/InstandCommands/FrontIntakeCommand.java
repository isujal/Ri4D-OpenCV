package org.firstinspires.ftc.teamcode.commandbase.InstandCommands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.subsystem.intaksubsystem;


public class FrontIntakeCommand extends InstantCommand {
    public FrontIntakeCommand(intaksubsystem intake, intaksubsystem.FRONT_INTAKE_STATES state) {
        super(() -> intake.updateState(state));
    }
}
