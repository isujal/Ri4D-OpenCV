package org.firstinspires.ftc.teamcode.commandbase.InstandCommands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.subsystem.intaksubsystem;


public class GateCommand extends InstantCommand {
    public GateCommand(intaksubsystem intake, intaksubsystem.GATE_OPENER_STATES state) {
        super(() -> intake.updateState(state));
    }
}
