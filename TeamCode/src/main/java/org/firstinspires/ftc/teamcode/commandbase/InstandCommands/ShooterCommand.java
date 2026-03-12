package org.firstinspires.ftc.teamcode.commandbase.InstandCommands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.subsystem.shootersubsystem;


public class ShooterCommand extends InstantCommand {
    public ShooterCommand(shootersubsystem shooter, shootersubsystem.SHOOTER_STATES state) {
        super(() -> shooter.updateState(state));
    }
}
