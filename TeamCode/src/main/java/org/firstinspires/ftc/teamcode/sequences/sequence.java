package org.firstinspires.ftc.teamcode.sequences;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.DeployerCommand;
import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.FrontIntakeCommand;
import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.GateCommand;
import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.HoodCommand;
import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.RearIntakeCommand;
import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.ShooterCommand;
import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.SliderCommand;
import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.TurretCommand;
import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.WaitForSensorCommand;
import org.firstinspires.ftc.teamcode.commandbase.Trajectorycommands.KPathCommand2;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystem.intaksubsystem;
import org.firstinspires.ftc.teamcode.subsystem.shootersubsystem;

public class sequence {

    public static SequentialCommandGroup preload_shoot_Near(intaksubsystem intake,
                                                            shootersubsystem shooter,
                                                            Follower follower,
                                                            PathChain preload,
                                                            int tpreload, String side){

        if(side == "red"){
        return ///  RED
                new SequentialCommandGroup(
                        new KPathCommand2(follower, preload, tpreload).alongWith(
                                new SequentialCommandGroup(
                                        new TurretCommand(shooter, shootersubsystem.TURRET_STATES.NEAR_AutoRed),
                                        new HoodCommand(shooter, shootersubsystem.HOOD_STATES.A_NEAR),
                                        new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.FRONT_INTAKE),
                                        new WaitCommand(300),
                                        new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.A_NEAR)

                                )
                        )

                );
        }
        else { ///  BLUE
            return
                    new SequentialCommandGroup(
                            new KPathCommand2(follower, preload, tpreload).alongWith(
                                    new SequentialCommandGroup(
                                            new TurretCommand(shooter, shootersubsystem.TURRET_STATES.NEAR_AutoBlue),
                                            new HoodCommand(shooter, shootersubsystem.HOOD_STATES.A_NEAR),
                                            new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.FRONT_INTAKE),
                                            new WaitCommand(300),
                                            new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.A_NEAR)

                                    )
                            )

                    );}

    }

    public static SequentialCommandGroup noGateCycle(intaksubsystem intake,
                                                     shootersubsystem shooter,
                                                     Follower follower,
                                                     RobotHardware robot,
                                                     PathChain pick,
                                                     PathChain shoot,
                                                     double tPick,
                                                     double tShoot,
                                                     String side){


        if(side == "red"){
            return  /// RED
                    new SequentialCommandGroup(
                    // 1st 3 pick
                    new KPathCommand2(follower, pick, tPick) .raceWith(
                                    new WaitForSensorCommand(()->(!robot.R1.getState() && !robot.F1.getState() && (!robot.F3.getState() || !robot.R3.getState())),2400)
                            )
                            .alongWith(

                                    new SequentialCommandGroup(
                                            new ParallelCommandGroup(

                                                    new GateCommand(intake, intaksubsystem.GATE_OPENER_STATES.Gate_OPEN),

                                                    new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.FRONT_INTAKE),
//                                                new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.REAR_INTAKE),
                                                    new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
                                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.IN)
                                            ),
                                            new WaitCommand(200),
                                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.REVERSE)
//                                        new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.REVERSE)

                                    )

                                            .andThen(
                                                    new WaitForSensorCommand(() -> !robot.R1.getState(), (int)tPick *1000).andThen(
                                                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF)
//                                                        new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF)
                                                    ))
                            ),

                    new WaitForSensorCommand(() -> (!robot.R1.getState() && !robot.F1.getState() && (!robot.F3.getState() || !robot.R3.getState())), 2000),

                    // 2- 3 shoot pose
                    /// no gate opened
                    new KPathCommand2(follower, shoot, tShoot).alongWith(
                            new SequentialCommandGroup(
                                    new GateCommand(intake, intaksubsystem.GATE_OPENER_STATES.GAte_Close),
                                    new WaitCommand(200),
                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF),
//                                new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF),
                                    new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
//                                    new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.A_NEAR),
                                    new HoodCommand(shooter, shootersubsystem.HOOD_STATES.A_NEAR),

                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.IN),
                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.IN),
                                    new WaitCommand(300),
                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF),
                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF)

                            )
                    )
            );
        }
        else{ ///  BLUE
            return  new SequentialCommandGroup(
                    // 1st 3 pick
                    new KPathCommand2(follower, pick, tPick) .raceWith(
                                    new WaitForSensorCommand(()->(!robot.R1.getState() && !robot.F1.getState() && (!robot.F3.getState() || !robot.R3.getState())),2400)
                            )
                            .alongWith(

                                    new SequentialCommandGroup(
                                            new ParallelCommandGroup(

                                                    new GateCommand(intake, intaksubsystem.GATE_OPENER_STATES.Gate_OPEN),

//                                                    new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.FRONT_INTAKE),
                                                new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.REAR_INTAKE),
                                                    new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
//                                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.IN)
                                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.IN)
                                            ),
                                            new WaitCommand(200),
//                                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.REVERSE)
                                        new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.REVERSE)

                                    )

                                            .andThen(
                                                    new WaitForSensorCommand(() -> !robot.F1.getState(), (int)tPick *1000).andThen(
//                                                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF)
                                                        new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF)
                                                    ))
                            ),

                    new WaitForSensorCommand(() -> (!robot.R1.getState() && !robot.F1.getState() && (!robot.F3.getState() || !robot.R3.getState())), 2000),

                    // 2- 3 shoot pose
                    /// no gate opened
                    new KPathCommand2(follower, shoot, tShoot).alongWith(
                            new SequentialCommandGroup(
                                    new GateCommand(intake, intaksubsystem.GATE_OPENER_STATES.GAte_Close),
                                    new WaitCommand(200),
//                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF),
                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF),
                                    new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
//                                    new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.A_NEAR),
                                    new HoodCommand(shooter, shootersubsystem.HOOD_STATES.A_NEAR),

                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.IN),
                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.IN),
                                    new WaitCommand(300),
                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF),
                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF)

                            )
                    )
            );
        }
    }



    public static SequentialCommandGroup blindShot(intaksubsystem intake,
                                                     shootersubsystem shooter,
                                                     Follower follower,
                                                     RobotHardware robot,
                                                     PathChain pick,
                                                     PathChain shoot,
                                                     double tPick,
                                                     double tShoot,
                                                     String side){


        if(side == "red"){
            return  /// RED
                    new SequentialCommandGroup(
                            // 1st 3 pick
                            new KPathCommand2(follower, pick, tPick)
                                    .alongWith(

                                            new SequentialCommandGroup(
                                                    new ParallelCommandGroup(

                                                            new GateCommand(intake, intaksubsystem.GATE_OPENER_STATES.Gate_OPEN),

                                                            new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.FRONT_INTAKE),
//                                                new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.REAR_INTAKE),
                                                            new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
                                                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.IN)
                                                    ),
                                                    new WaitCommand(200),
                                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.REVERSE)
//                                        new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.REVERSE)

                                            )

                                                    .andThen(
                                                            new WaitForSensorCommand(() -> !robot.R1.getState(), (int)tPick *1000).andThen(
                                                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF)
//                                                        new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF)
                                                            ))
                                    ),

//                            new WaitForSensorCommand(() -> (!robot.R1.getState() && !robot.F1.getState() && (!robot.F3.getState() || !robot.R3.getState())), 2000),

                            // 2- 3 shoot pose
                            /// no gate opened
                            new KPathCommand2(follower, shoot, tShoot).alongWith(
                                    new SequentialCommandGroup(
                                            new GateCommand(intake, intaksubsystem.GATE_OPENER_STATES.GAte_Close),
                                            new WaitCommand(200),
                                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF),
//                                new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF),
                                            new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
//                                    new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.A_NEAR),
                                            new HoodCommand(shooter, shootersubsystem.HOOD_STATES.A_NEAR),

                                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.IN),
                                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.IN),
                                            new WaitCommand(300),
                                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF),
                                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF)

                                    )
                            )
                    );
        }
        else{ ///  BLUE
            return  new SequentialCommandGroup(
                    // 1st 3 pick
                    new KPathCommand2(follower, pick, tPick)
                            .alongWith(

                                    new SequentialCommandGroup(
                                            new ParallelCommandGroup(

                                                    new GateCommand(intake, intaksubsystem.GATE_OPENER_STATES.Gate_OPEN),

//                                                    new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.FRONT_INTAKE),
                                                    new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.REAR_INTAKE),
                                                    new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
//                                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.IN)
                                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.IN)
                                            ),
                                            new WaitCommand(200),
//                                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.REVERSE)
                                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.REVERSE)

                                    )

                                            .andThen(
                                                    new WaitForSensorCommand(() -> !robot.F1.getState(), (int)tPick *1000).andThen(
//                                                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF)
                                                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF)
                                                    ))
                            ),

//                    new WaitForSensorCommand(() -> (!robot.R1.getState() && !robot.F1.getState() && (!robot.F3.getState() || !robot.R3.getState())), 2000),

                    // 2- 3 shoot pose
                    /// no gate opened
                    new KPathCommand2(follower, shoot, tShoot).alongWith(
                            new SequentialCommandGroup(
                                    new GateCommand(intake, intaksubsystem.GATE_OPENER_STATES.GAte_Close),
                                    new WaitCommand(200),
//                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF),
                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF),
                                    new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
//                                    new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.A_NEAR),
                                    new HoodCommand(shooter, shootersubsystem.HOOD_STATES.A_NEAR),

                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.IN),
                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.IN),
                                    new WaitCommand(300),
                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF),
                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF)

                            )
                    )
            );
        }
    }


    public static SequentialCommandGroup CycleForGateIntakeNopenalty(intaksubsystem intake,
                                                                     shootersubsystem shooter,
                                                                     Follower follower,
                                                                     RobotHardware robot,
                                                                     PathChain touch,
                                                                     PathChain gofar,
                                                                     PathChain collect,
                                                                     PathChain shootpose,
                                                                     double ttouch,
                                                                     double tgofar,
                                                                     double tcollect,
                                                                     double tshoot,
                                                                     String side){


        if(side == "red"){
        return  new SequentialCommandGroup(

                new KPathCommand2(follower, touch, ttouch).alongWith(
                        new GateCommand(intake, intaksubsystem.GATE_OPENER_STATES.Gate_OPEN),
                        new SequentialCommandGroup(
                                new ParallelCommandGroup(

                                        new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.REAR_INTAKE),

                                        new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
                                        new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.IN)
                                ),
//                                new WaitCommand(),
                                new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF)
//                                        new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.REVERSE)

                        )
                ),
                new WaitCommand(400 )
                        .andThen(
                                new KPathCommand2(follower, gofar, tgofar).alongWith(
                                        new SequentialCommandGroup(
                                                new WaitCommand(120),
                                                new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.FRONT_INTAKE)
                                        )
                                )),


                new KPathCommand2(follower, collect, tcollect).raceWith(
                                new WaitForSensorCommand(()->(!robot.R1.getState() && !robot.F1.getState() && (!robot.F3.getState() || !robot.R3.getState())),1800)
                        )
                        .alongWith(

                                new SequentialCommandGroup(

                                        new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.REVERSE)
//                                        new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.REVERSE)

                                )

                                        .andThen(
                                                new WaitForSensorCommand(() -> !robot.R1.getState(), 1500).andThen(
                                                        new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF)
//                                                        new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF)
                                                ))
                        ),

//                new WaitForSensorCommand(() -> (!robot.R1.getState() && !robot.F1.getState() && (!robot.F3.getState() || !robot.R3.getState())), 500,50),

                // 2- 3 shoot pose
                /// no gate opened
                new KPathCommand2(follower, shootpose, tshoot).alongWith(
                        new SequentialCommandGroup(
                                new GateCommand(intake, intaksubsystem.GATE_OPENER_STATES.GAte_Close),
                                new WaitCommand(200),
                                new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF),
//                                new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF),
                                new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
//                                new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.A_NEAR),
                                new HoodCommand(shooter, shootersubsystem.HOOD_STATES.A_NEAR),

                                new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.IN),
                                new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.IN),
                                new WaitCommand(300),
                                new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF),
                                new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF)
                        )
                )
        );}
        else{
            ///  BLUE
            return  new SequentialCommandGroup(

                    new KPathCommand2(follower, touch, ttouch).alongWith(
                            new GateCommand(intake, intaksubsystem.GATE_OPENER_STATES.Gate_OPEN),
                            new SequentialCommandGroup(
                                    new ParallelCommandGroup(

//                                            new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.REAR_INTAKE),
                                            new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.FRONT_INTAKE),

                                            new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
//                                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.IN)
                                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.IN)
                                    ),
//                                new WaitCommand(),
//                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF)
                                        new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF)

                            )
                    ),
                    new WaitCommand(300 )
                            .andThen(
                                    new KPathCommand2(follower, gofar, tgofar).alongWith(
                                            new SequentialCommandGroup(
                                                    new WaitCommand(120),
//                                                    new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.FRONT_INTAKE)
                                                    new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.REAR_INTAKE)
                                            )
                                    )),


                    new KPathCommand2(follower, collect, tcollect).raceWith(
                                    new WaitForSensorCommand(()->(!robot.R1.getState() && !robot.F1.getState() && (!robot.F3.getState() || !robot.R3.getState())),1800)
                            )
                            .alongWith(

                                    new SequentialCommandGroup(

//                                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.REVERSE)
                                        new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.REVERSE)

                                    )

                                            .andThen(
                                                    new WaitForSensorCommand(() -> !robot.F1.getState(), 1500).andThen(
//                                                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF)
                                                        new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF)
                                                    ))
                            ),

//                new WaitForSensorCommand(() -> (!robot.R1.getState() && !robot.F1.getState() && (!robot.F3.getState() || !robot.R3.getState())), 500,50),

                    // 2- 3 shoot pose
                    /// no gate opened
                    new KPathCommand2(follower, shootpose, tshoot).alongWith(
                            new SequentialCommandGroup(
                                    new GateCommand(intake, intaksubsystem.GATE_OPENER_STATES.GAte_Close),
                                    new WaitCommand(200),
//                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF),
                                new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF),
                                    new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
//                                    new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.A_NEAR),
                                    new HoodCommand(shooter, shootersubsystem.HOOD_STATES.A_NEAR),

                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.IN),
                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.IN),
                                    new WaitCommand(300),
                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF),
                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF)
                            )
                    )
            );
        }
    }

    public static SequentialCommandGroup shootingSeq (intaksubsystem intake,shootersubsystem shooter){
        return

                    new SequentialCommandGroup(
                            new ParallelCommandGroup(
                                    new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.OUT),
                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.PUSH),
                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.PUSH)
                            ),
                            new WaitCommand(100),
//                            new WaitForSensorCommand(()-> robot.F1.getState() && robot.R1.getState()), //100
                            new ParallelCommandGroup(
                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.REVERSE),
                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.PUSH)
                            ),
                            new WaitCommand(120), //100
//                            new InstantCommand(()-> robot.hood.setPosition(hoo_far_shoot_mid)),
                            new ParallelCommandGroup(
                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.PUSH),
                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.PUSH)
                            )

                    );

         /// shooter sequence end
    }



    public static SequentialCommandGroup preload_shoot_Far(intaksubsystem intake,
                                                            shootersubsystem shooter,
                                                            Follower follower,
                                                            PathChain preload,
                                                            int tpreload, String side){

        if(side == "red"){
            return ///  RED
                    new SequentialCommandGroup(
                            new KPathCommand2(follower, preload, tpreload).alongWith(
                                    new SequentialCommandGroup(
                                            new TurretCommand(shooter, shootersubsystem.TURRET_STATES.A_FAR_RED),
                                            new HoodCommand(shooter, shootersubsystem.HOOD_STATES.A_FAR),
                                            new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.FRONT_INTAKE),
                                            new WaitCommand(300),
                                            new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.A_FAR)

                                    )
                            )

                    );
        }
        else { ///  BLUE
            return
                    new SequentialCommandGroup(
                            new KPathCommand2(follower, preload, tpreload).alongWith(
                                    new SequentialCommandGroup(
                                            new TurretCommand(shooter, shootersubsystem.TURRET_STATES.A_FAR_BLUE),
                                            new HoodCommand(shooter, shootersubsystem.HOOD_STATES.A_FAR),
                                            new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.FRONT_INTAKE),
                                            new WaitCommand(300),
                                            new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.A_FAR)

                                    )
                            )

                    );}

    }




        ///
        public static SequentialCommandGroup cornerpick(intaksubsystem intake,
                                                        shootersubsystem shooter,
                                                        Follower follower,
                                                        RobotHardware robot,
                                                        PathChain first2,
                                                        PathChain cornerone,
                                                        PathChain shootpose,
                                                        double tfirst2,
                                                        double tcornerOne,
                                                        double tshoot,
                                                        String side)
        {

            if(side == "red"){
            return new SequentialCommandGroup(
                    // 1st 3 pick
                    new KPathCommand2(follower, first2, tfirst2)
                            .raceWith(
                                    new WaitForSensorCommand(()->(!robot.F1.getState() || (!robot.F3.getState() || !robot.R3.getState())), (int)tfirst2 * 1000 )
                            )
                            .alongWith(
                                    new SequentialCommandGroup(
                                            new ParallelCommandGroup(

                                                    new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.FRONT_INTAKE),
//                                                    new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.REAR_INTAKE),
                                                    new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
                                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.IN)
//                                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.IN)
                                            ),
                                            new WaitCommand(300),
                                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.REVERSE)
//                                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.REVERSE)

                                    )

                                            .andThen(
                                                    new WaitForSensorCommand(() -> !robot.R1.getState(), ((int)tfirst2* 1000)).andThen(
                                                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF)
//                                                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF)
                                                    ))
                            ),

                    new WaitForSensorCommand(() -> ( !robot.R1.getState() || (!robot.F3.getState() || !robot.R3.getState())), 1500),

                    // 2- 3 shoot pose
                    /// no gate opened
                    new KPathCommand2(follower, cornerone, tcornerOne).raceWith(
                                    new WaitForSensorCommand(()->(!robot.R1.getState() && !robot.F1.getState() && (!robot.F3.getState() || !robot.R3.getState())), 2000)
                            )
                            .alongWith(
                                    new SequentialCommandGroup(
//                                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.REVERSE))
                                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.REVERSE))
                                            .andThen(
                                                    new WaitForSensorCommand(() -> !robot.R1.getState(), 1000).andThen(
//                                                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF)
                                                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF)
                                                    ))
                            ),



                    /// return to shoot

                    new WaitForSensorCommand(() -> ( !robot.R1.getState() &&!robot.F1.getState() && (!robot.F3.getState() || !robot.R3.getState())), 2000),

                    // 2- 3 shoot pose


                    /// return to shoot
//                        new holyinject(drivetrain, robot.localizer, shoot,shoot.calculateLength(), shootProfile, robot.getVoltage(), tShoot,1,1,17,1,200).alongWith(
                    new KPathCommand2(follower, shootpose, tshoot).alongWith(

//                        new holyinject(drivetrain, robot.localizer, shoot,shoot.calculateLength(), shootProfile, robot.getVoltage(), tShoot,1,1,24,2,200).alongWith(
                            new SequentialCommandGroup(
                                    new WaitCommand(200),
                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF),
//                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF),
                                    new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
                                    new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.A_FAR),
                                    new HoodCommand(shooter, shootersubsystem.HOOD_STATES.A_FAR)
                            )
                    )


            );}
            else { ///  Blue
                return new SequentialCommandGroup(
                        // 1st 3 pick
                        new KPathCommand2(follower, first2, tfirst2)
                                .raceWith(
                                        new WaitForSensorCommand(()->(!robot.R1.getState() || (!robot.F3.getState() || !robot.R3.getState())), (int)tfirst2 * 1000 )
                                )
                                .alongWith(
                                        new SequentialCommandGroup(
                                                new ParallelCommandGroup(

//                                                        new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.FRONT_INTAKE),
                                                    new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.REAR_INTAKE),
                                                        new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
//                                                        new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.IN)
                                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.IN)
                                                ),
                                                new WaitCommand(300),
//                                                new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.REVERSE)
                                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.REVERSE)

                                        )

                                                .andThen(
                                                        new WaitForSensorCommand(() -> !robot.R1.getState(), ((int)tfirst2* 1000)).andThen(
//                                                                new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF)
                                                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF)
                                                        ))
                                ),

                        new WaitForSensorCommand(() -> ( !robot.F1.getState() || (!robot.F3.getState() || !robot.R3.getState())), 1500),

                        // 2- 3 shoot pose
                        /// no gate opened
                        new KPathCommand2(follower, cornerone, tcornerOne).raceWith(
                                        new WaitForSensorCommand(()->(!robot.R1.getState() && !robot.F1.getState() && (!robot.F3.getState() || !robot.R3.getState())), 2000)
                                )
                                .alongWith(
                                        new SequentialCommandGroup(
                                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.REVERSE))
//                                                new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.REVERSE))
                                                .andThen(
                                                        new WaitForSensorCommand(() -> !robot.F1.getState(), 1000).andThen(
                                                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF)
//                                                                new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF)
                                                        ))
                                ),



                        /// return to shoot

                        new WaitForSensorCommand(() -> ( !robot.R1.getState() &&!robot.F1.getState() && (!robot.F3.getState() || !robot.R3.getState())), 2000),

                        // 2- 3 shoot pose


                        /// return to shoot
//                        new holyinject(drivetrain, robot.localizer, shoot,shoot.calculateLength(), shootProfile, robot.getVoltage(), tShoot,1,1,17,1,200).alongWith(
                        new KPathCommand2(follower, shootpose, tshoot).alongWith(

//                        new holyinject(drivetrain, robot.localizer, shoot,shoot.calculateLength(), shootProfile, robot.getVoltage(), tShoot,1,1,24,2,200).alongWith(
                                new SequentialCommandGroup(
                                        new WaitCommand(200),
//                                        new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF),
                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF),
                                        new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
                                        new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.A_FAR),
                                        new HoodCommand(shooter, shootersubsystem.HOOD_STATES.A_FAR)
                                )
                        )


                );
            }
        }


}
