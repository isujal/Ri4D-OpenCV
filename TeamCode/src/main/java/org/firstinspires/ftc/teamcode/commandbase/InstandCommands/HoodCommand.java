package org.firstinspires.ftc.teamcode.commandbase.InstandCommands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.subsystem.shootersubsystem;


public class HoodCommand extends InstantCommand {
    public HoodCommand(shootersubsystem shooter, shootersubsystem.HOOD_STATES state) {
        super(() -> shooter.updateState(state));
    }
}
