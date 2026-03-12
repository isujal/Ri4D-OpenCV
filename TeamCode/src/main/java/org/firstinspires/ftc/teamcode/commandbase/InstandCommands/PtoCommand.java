package org.firstinspires.ftc.teamcode.commandbase.InstandCommands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.subsystem.endgamesubsystem;


public class PtoCommand extends InstantCommand {
    public PtoCommand(endgamesubsystem endgame, endgamesubsystem.PTO_STATES state) {
        super(() -> endgame.updateState(state));
    }
}
