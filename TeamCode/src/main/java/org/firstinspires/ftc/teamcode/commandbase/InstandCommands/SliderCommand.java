package org.firstinspires.ftc.teamcode.commandbase.InstandCommands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.subsystem.shootersubsystem;


public class SliderCommand extends InstantCommand {
    public SliderCommand(shootersubsystem shooter, shootersubsystem.SLIDER_STATES state) {
        super(() -> shooter.updateState(state));
    }
}
