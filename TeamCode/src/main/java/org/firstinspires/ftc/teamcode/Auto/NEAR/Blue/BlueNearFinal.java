//package org.firstinspires.ftc.teamcode.Auto.NEAR.Blue;
//
//import com.arcrobotics.ftclib.command.*;
//import com.pedropathing.follower.Follower;
//import com.pedropathing.geometry.Pose;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.commandbase.Trajectorycommands.KPathCommand2;
//import org.firstinspires.ftc.teamcode.drivecommand.KPathCommand2;
//import org.firstinspires.ftc.teamcode.drivecommand.WaitForSensorCommand;
//import org.firstinspires.ftc.teamcode.hardware.Global;
//import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
//import org.firstinspires.ftc.teamcode.paths.NEAR.BlueNearPath_Final;
//import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
//import org.firstinspires.ftc.teamcode.subsystems.gate_endgamesubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.intakesubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.shootersubsystem;
//
//import static org.firstinspires.ftc.teamcode.sequences.Shooting.nearShootingAuto;
//import static org.firstinspires.ftc.teamcode.sequences.back_feed.back_feedAUTO;
//import static org.firstinspires.ftc.teamcode.sequences.initSequence.initSequence;
//import static org.firstinspires.ftc.teamcode.sequences.Intake.*;
//
////TODO Safe Gate Intake for 1st Cycle
//@Autonomous(name = "BLUE Near SAFE", group = "BLUE NEAR")
//public class BlueNearFinal extends LinearOpMode {
//
//    private final RobotHardware robot = RobotHardware.getInstance();
//    intakesubsystem intakess;
//    shootersubsystem shooterss;
//    gate_endgamesubsystem gatess;
//
//    public Follower follower;
//
//    private BlueNearPath_Final nearPath; // Paths defined in the Paths class
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//
//        robot.init(hardwareMap);
//        intakess = new intakesubsystem(robot);
//        shooterss = new shootersubsystem(robot);
//        gatess = new gate_endgamesubsystem(robot);
//
//        follower = Constants.createFollower(hardwareMap);
//        nearPath = new BlueNearPath_Final(follower); // Initialize paths
//
//        follower.setStartingPose(nearPath.startPose);
//
//        new SequentialCommandGroup(
//                initSequence(intakess, shooterss, gatess)
//        );
//
//        robot.shooter1.setVelocityPIDFCoefficients(Global.P, Global.I, Global.D, Global.F);
//        robot.shooter2.setVelocityPIDFCoefficients(Global.P, Global.I, Global.D, Global.F);
//
//        waitForStart();
//
//        SequentialCommandGroup path = new SequentialCommandGroup(
//
//                //TODO ==================== Preload Shoot ====================
//                new KPathCommand2(follower, nearPath.SHOOT0).
//                        alongWith(new ParallelCommandGroup(
//                                nearShootingAuto(shooterss),
//                                shooterss.turretCommand(shootersubsystem.TURRET.BLUEAUTONEAR),
//                                intakess.feederCommands(intakesubsystem.FEEDER.FEED)
////                                new InstantCommand(() -> holdShootPose())
//                        )),
//                new WaitCommand(Global.PreShootTimer + 60),
//                back_feedAUTO(intakess),
//                new WaitCommand(Global.ShootTimer),
//
//                //TODO ====================== Pick 2 =========================
//                new KPathCommand2(follower, nearPath.SPIKE2).alongWith(
//                        new ParallelCommandGroup(
//                                intakeBackAuto(intakess, shooterss),
//                                new WaitForSensorCommand(() -> (!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState()), 1600, 50).andThen(
//                                        intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF)
//                                )
//                        )
//                )
//                ,
//                ///TODO ==================== Shoot 2 ====================
//                new KPathCommand2(follower, nearPath.SHOOT2).alongWith(
//                        new ParallelCommandGroup(
//                                nearShootingAuto(shooterss),
//                                new WaitForSensorCommand(() -> (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()), 800, 50).
//                                        andThen(
//                                                new SequentialCommandGroup(
//                                                        new ParallelCommandGroup(
//                                                                intakess.deployBCommands(intakesubsystem.DEPLOYB.UP),
//                                                                intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF)
//                                                        ),
//                                                        new WaitCommand(300),
//                                                        intakess.feederCommands(intakesubsystem.FEEDER.FEED)
//
//                                                )
//                                        )
//                        )
//                ),
//
//                new WaitCommand(Global.PreShootTimer),
//                back_feedAUTO(intakess),
//                new WaitCommand(Global.ShootTimer),
//
//                //TODO ====================== GatePick 1 =========================
//                new KPathCommand2(follower, nearPath.Gate_Touch, 0.7, "holdBlueStraight").alongWith(
//                        new ParallelCommandGroup(
//                                intakess.feederCommands(intakesubsystem.FEEDER.STOP),
////                                gatess.gate1Command(gate_endgamesubsystem.GATE1.OPEN),
//                                gatess.gate2Command(gate_endgamesubsystem.GATE2.OPEN)
//                        )
//                ),
//
//                new WaitCommand(700),
//                new KPathCommand2(follower, nearPath.GoFar).alongWith(
//                        new SequentialCommandGroup(
//                                gatess.gate1Command(gate_endgamesubsystem.GATE1.CLOSE),
//                                gatess.gate2Command(gate_endgamesubsystem.GATE2.CLOSE),
//                                new WaitCommand(250),
//                                intakeBackAuto(intakess, shooterss),
//                                new WaitForSensorCommand(() -> (!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState()), 200, 25).andThen(
//                                        intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF)
//                                ),
//                                new WaitForSensorCommand(() -> ((!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState()) && !robot.midBeam.getState() && (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState())), 2500, 25).andThen(
//                                        new ParallelCommandGroup(
//                                                intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF),
//                                                intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF),
//                                                gatess.gate1Command(gate_endgamesubsystem.GATE1.CLOSE),
//                                                gatess.gate2Command(gate_endgamesubsystem.GATE2.CLOSE)
//                                        )
//                                )
//                        )
//                ),
//
//                ///TODO ==================== Gate Shoot 1 ====================
////                new InstantCommand(()->Constants.pathConstraints.setTimeoutConstraint(100)),
//                new KPathCommand2(follower, nearPath.GateShoo1).alongWith(
//                        new ParallelCommandGroup(
//                                nearShootingAuto(shooterss),
//                                intakess.feederCommands(intakesubsystem.FEEDER.FEED)
//                        )
//                ),
//
//                new WaitCommand(Global.PreShootTimer),
//                back_feedAUTO(intakess),
//                new WaitCommand(Global.ShootTimer),
//
//                //TODO ====================== GatePick 2 =========================
//
////                new KPathCommand2(follower, nearPath.GateIntake1, 0.65, "BLUE").alongWith(
////                        new ParallelCommandGroup(
////                                intakeBackAutoGate(intakess, shooterss, gatess),
////                                new WaitForSensorCommand(() -> (!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState()), 3000, 25).andThen(
////                                        intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF)
////                                ),
////                                new WaitForSensorCommand(() -> ((!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState()) && !robot.midBeam.getState() && (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState())), 3500, 25).andThen(
////                                        new ParallelCommandGroup(
////                                                intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF),
////                                                intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF),
////                                                gatess.gate1Command(gate_endgamesubsystem.GATE1.CLOSE),
////                                                gatess.gate2Command(gate_endgamesubsystem.GATE2.CLOSE)
////                                        )
////                                )
//////                                new InstantCommand(() -> holdGatePose())
////                        )
////                ),
//
//                ///TODO ==================== Gate Shoot 2 ====================
////                new InstantCommand(()->Constants.pathConstraints.setTimeoutConstraint(100)),
//                new KPathCommand2(follower, nearPath.GateShoo1).alongWith(
//                        new ParallelCommandGroup(
//                                nearShootingAuto(shooterss),
//                                intakess.feederCommands(intakesubsystem.FEEDER.FEED)
////                                new InstantCommand(() -> holdShootPose())
//                        )
//                ),
//
//                new WaitCommand(Global.PreShootTimer),
//                back_feedAUTO(intakess),
//                new WaitCommand(Global.ShootTimer),
//
//                //TODO ====================== GatePick 3 =========================
////                new InstantCommand(()->Constants.pathConstraints.setTimeoutConstraint(Global.timeOut)),
//                new KPathCommand2(follower, nearPath.GateIntake1, 0.65, "BLUE").alongWith(
//                        new ParallelCommandGroup(
//                                intakeBackAutoGate(intakess, shooterss, gatess),
//                                new WaitForSensorCommand(() -> (!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState()), 3000, 25).andThen(
//                                        intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF)
//                                ),
//                                new WaitForSensorCommand(() -> ((!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState()) && !robot.midBeam.getState() && (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState())), 4000, 25).andThen(
//                                        new ParallelCommandGroup(
//                                                intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF),
//                                                intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF),
//                                                gatess.gate1Command(gate_endgamesubsystem.GATE1.CLOSE),
//                                                gatess.gate2Command(gate_endgamesubsystem.GATE2.CLOSE)
//                                        )
//                                )
////                                new InstantCommand(() -> holdGatePose())
//                        )
//                ),
//
//                ///TODO ==================== Gate Shoot 3 ====================
//                new InstantCommand(() -> Constants.pathConstraints.setTimeoutConstraint(100)),
//                new KPathCommand2(follower, nearPath.GateShoo1).alongWith(
//                        new ParallelCommandGroup(
//                                nearShootingAuto(shooterss),
//                                intakess.feederCommands(intakesubsystem.FEEDER.FEED)
////                                new InstantCommand(() -> holdShootPose())
//                        )
//                ),
//
//                new WaitCommand(Global.PreShootTimer),
//                back_feedAUTO(intakess),
//                new WaitCommand(Global.ShootTimer),
//
//                //TODO ===================== Pick 1 ====================
//                new KPathCommand2(follower, nearPath.SPIKE1).alongWith(
//                        new ParallelCommandGroup(
//                                intakeBackAuto(intakess, shooterss),
//                                new WaitForSensorCommand(() -> (!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState()), 2000, 50).andThen(
//                                        intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF)
//                                )
//                        )
//                )
//                ,
//                ///TODO ==================== Shoot 1 =====================
//                new KPathCommand2(follower, nearPath.SHOOT1).alongWith(
//                        new ParallelCommandGroup(
//                                nearShootingAuto(shooterss),
//                                new WaitForSensorCommand(() -> (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()), 800, 50).andThen(
//                                        new SequentialCommandGroup(
//                                                new ParallelCommandGroup(
//                                                        intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF),
//                                                        intakess.deployBCommands(intakesubsystem.DEPLOYB.UP)
//                                                ),
//                                                new WaitCommand(300),
//                                                intakess.feederCommands(intakesubsystem.FEEDER.FEED)
//                                        )
//                                )
//                        )
//                ),
//                new WaitCommand(Global.PreShootTimer),
//                back_feedAUTO(intakess),
//                new WaitCommand(Global.ShootTimer),
//
//                ///TODO ==================== Parking ====================
//                new KPathCommand2(follower, nearPath.LEAVE).alongWith(
//                        new ParallelCommandGroup(
//                                initSequence(intakess, shooterss, gatess)
//                        )
//                )
//        );
//
//        while (opModeInInit()) {
//            CommandScheduler.getInstance().reset();
//        }
//
//
//        if (isStopRequested()) return;
//
//        CommandScheduler.getInstance().schedule(path);
//
//
//        while (opModeIsActive()) {
//            /// Run the sequence
//            CommandScheduler.getInstance().run();
//            follower.update();
//            Pose pose = follower.getPose();
//            Global.startPoseTry = pose;
//            Global.redSide = false;
//
////            telemetry.addData("X", pose.getX());
////            telemetry.addData("Y", pose.getY());
////            telemetry.addData("Heading", pose.getHeading());
//
//
//        }
//    }
//
//}
