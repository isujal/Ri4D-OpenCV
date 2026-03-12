package org.firstinspires.ftc.teamcode.commandbase.InstandCommands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.subsystem.intaksubsystem;


public class DeployerCommand extends InstantCommand {
    public DeployerCommand(intaksubsystem intake, intaksubsystem.DEPLOYER_STATES state) {
        super(() -> intake.updateState(state));
    }
}
