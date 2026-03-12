package org.firstinspires.ftc.teamcode.commandbase.InstandCommands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.subsystem.shootersubsystem;


public class TurretCommand extends InstantCommand {
    public TurretCommand(shootersubsystem shooter, shootersubsystem.TURRET_STATES state) {
        super(() -> shooter.updateState(state));
    }
}
