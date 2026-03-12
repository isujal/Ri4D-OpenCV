//package org.firstinspires.ftc.teamcode.Auto.NEAR.Red;
//
//import com.arcrobotics.ftclib.command.*;
//import com.pedropathing.follower.Follower;
//import com.pedropathing.geometry.Pose;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.util.ElapsedTime;
//import org.firstinspires.ftc.teamcode.drivecommand.KPathCommand2;
//import org.firstinspires.ftc.teamcode.drivecommand.WaitForSensorCommand;
//import org.firstinspires.ftc.teamcode.hardware.Global;
//import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
//import org.firstinspires.ftc.teamcode.paths.NEAR.RedNearPath_Final;
//import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
//import org.firstinspires.ftc.teamcode.subsystems.gate_endgamesubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.intakesubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.shootersubsystem;
//
//import static org.firstinspires.ftc.teamcode.hardware.Global.*;
//import static org.firstinspires.ftc.teamcode.sequences.Intake.*;
//import static org.firstinspires.ftc.teamcode.sequences.Shooting.nearShootingAuto;
//import static org.firstinspires.ftc.teamcode.sequences.back_feed.back_feedAUTO;
//import static org.firstinspires.ftc.teamcode.sequences.initSequence.initSequence;
//
//
////TODO Gate Auto 21 cycle
//@Autonomous(name = "RED Near Final", group = "RED NEAR")
//public class RedNearFinalV2 extends LinearOpMode {
//
//    private final RobotHardware robot = RobotHardware.getInstance();
//    intakesubsystem intakess;
//    shootersubsystem shooterss;
//    gate_endgamesubsystem gatess;
//
//    public Follower follower;
//
//    private RedNearPath_Final nearPath; // Paths defined in the Paths class
//
//    private ElapsedTime endTimer = new ElapsedTime();
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
//        nearPath = new RedNearPath_Final(follower); // Initialize paths
//
//        follower.setStartingPose(nearPath.startPose);
//
//        robot.shooter1.setVelocityPIDFCoefficients(Global.P, Global.I, Global.D, Global.F);
//        robot.shooter2.setVelocityPIDFCoefficients(Global.P, Global.I, Global.D, Global.F);
//
//
//        SequentialCommandGroup path = new SequentialCommandGroup(
//
//                //TODO ==================== Preload Shoot ====================
//                new KPathCommand2(follower, nearPath.SHOOT0).
//                        alongWith(new ParallelCommandGroup(
//                                        nearShootingAuto(shooterss),
//                                        shooterss.turretCommand(shootersubsystem.TURRET.REDAUTONEAR),
//                                        intakess.feederCommands(intakesubsystem.FEEDER.FEED),
//                                        gatess.endgame1Command(gate_endgamesubsystem.ENDGAME1.UP)
//                                )
//                        ),
//
//                new WaitCommand(Global.PreShootTimer + 20),
//                back_feedAUTO(intakess),
//                new WaitCommand(Global.ShootTimer),
//
//                //TODO ====================== Pick 2 =========================
//                new KPathCommand2(follower, nearPath.SPIKE2).alongWith(
//                        new SequentialCommandGroup(
//                                new WaitCommand(350),
//                                intakeFrontAuto(intakess, shooterss),
//                                new WaitForSensorCommand(() -> (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()), 1400, 50).andThen(
//                                        intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF)
//                                )
//                        )
//                )
//                ,
//                ///TODO ==================== Shoot 2 ====================
//                new KPathCommand2(follower, nearPath.SHOOT2).alongWith(
//                        new ParallelCommandGroup(
//                                nearShootingAuto(shooterss),
//                                new WaitForSensorCommand(() -> (!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState()), 800, 50).
//                                        andThen(
//                                                new SequentialCommandGroup(
//                                                        new ParallelCommandGroup(
//                                                                intakess.deployFCommands(intakesubsystem.DEPLOYF.UP),
//                                                                intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF)
//                                                        ),
//                                                        new WaitCommand(300),
//                                                        intakess.feederCommands(intakesubsystem.FEEDER.FEED)
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
//                new KPathCommand2(follower, nearPath.GateIntake10,2.7).alongWith(
//                        new SequentialCommandGroup(
//                                new WaitCommand(350),
//                                intakeBackAutoGate(intakess, shooterss, gatess)
//                        )
//                ),
//                new KPathCommand2(follower, nearPath.GateIntake1,2.5)
//                        .alongWith(
//                                new ParallelCommandGroup(
//                                        intakeFrontAutoGate(intakess, shooterss, gatess),
//                                        new WaitForSensorCommand(() -> (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()), backTimer, 25).andThen(
//                                                intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF)
//                                        ),
//                                        new WaitForSensorCommand(() -> ((!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()) && !robot.midBeam.getState() && (!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState())), frontTimer, 25).andThen(
//                                                intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF),
//                                                intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF),
//                                                gatess.gate1Command(gate_endgamesubsystem.GATE1.CLOSE),
//                                                gatess.gate2Command(gate_endgamesubsystem.GATE2.CLOSE)
//                                        )
//                                )
//                        )
//                ,
//
//                ///TODO ==================== Gate Shoot 1 ====================
////                new InstantCommand(() -> Constants.pathConstraints.setTimeoutConstraint(100)),
//                new KPathCommand2(follower, nearPath.GateShoo2).alongWith(
//                        new SequentialCommandGroup(
//                                nearShootingAuto(shooterss),
//                                new WaitCommand(350),
//                                intakess.feederCommands(intakesubsystem.FEEDER.FEED)
//                        )
//                ),
//
//                new WaitCommand(Global.PreShootTimer),
//                back_feedAUTO(intakess),
//                new WaitCommand(Global.ShootTimer),
//
//                //TODO ====================== GatePick 2 =========================
//                new KPathCommand2(follower, nearPath.GateIntake10,2.75).alongWith(
//                        new SequentialCommandGroup(
//                                new WaitCommand(250),
//                                intakeBackAutoGate(intakess, shooterss, gatess)
//                        )
//                ),
//                new KPathCommand2(follower, nearPath.GateIntake1,2.5)
//                        .alongWith(
//                                new ParallelCommandGroup(
//                                        intakeFrontAutoGate(intakess, shooterss, gatess),
//                                        new WaitForSensorCommand(() -> (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()), backTimer, 25).andThen(
//                                                intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF)
//                                        ),
//                                        new WaitForSensorCommand(() -> ((!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()) && !robot.midBeam.getState() && (!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState())), frontTimer, 25).andThen(
//                                                intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF),
//                                                intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF),
//                                                gatess.gate1Command(gate_endgamesubsystem.GATE1.CLOSE),
//                                                gatess.gate2Command(gate_endgamesubsystem.GATE2.CLOSE)
//                                        )
////                                new InstantCommand(() -> holdGatePose())
//                                )
//                        ),
//
//                ///TODO ==================== Gate Shoot 2 ====================
//                new KPathCommand2(follower, nearPath.GateShoo1).alongWith(
//                        new ParallelCommandGroup(
//                                nearShootingAuto(shooterss),
//                                new WaitCommand(250),
//                                intakess.feederCommands(intakesubsystem.FEEDER.FEED)
//                        )
//                ),
//
//                new WaitCommand(Global.PreShootTimer),
//                back_feedAUTO(intakess),
//                new WaitCommand(Global.ShootTimer),
//
//                //TODO ====================== GatePick 3 =========================
//                new KPathCommand2(follower, nearPath.GateIntake10, 2.75).alongWith(
//                        new SequentialCommandGroup(
//                                new WaitCommand(350),
//                                intakeBackAutoGate(intakess, shooterss, gatess)
//                        )
//                ),
//                new KPathCommand2(follower, nearPath.GateIntake1, 2.5)
//                        .alongWith(
//                                new ParallelCommandGroup(
//                                        intakeFrontAutoGate(intakess, shooterss, gatess),
//                                        new WaitForSensorCommand(() -> (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()), backTimer, 25).andThen(
//                                                intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF)
//                                        ),
//                                        new WaitForSensorCommand(() -> ((!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()) && !robot.midBeam.getState() && (!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState())), frontTimer, 25).andThen(
//                                                intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF),
//                                                intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF),
//                                                gatess.gate1Command(gate_endgamesubsystem.GATE1.CLOSE),
//                                                gatess.gate2Command(gate_endgamesubsystem.GATE2.CLOSE)
//                                        )
////                                new InstantCommand(() -> holdGatePose())
//                                )
//                        )
//                ,
//                ///TODO ==================== Gate Shoot 3 ====================
//                new KPathCommand2(follower, nearPath.GateShoo1).alongWith(
//                        new ParallelCommandGroup(
//                                nearShootingAuto(shooterss),
//                                new WaitCommand(250),
//                                intakess.feederCommands(intakesubsystem.FEEDER.FEED)
//                        )
//                ),
//
//                new WaitCommand(Global.PreShootTimer),
//                back_feedAUTO(intakess),
//                new WaitCommand(Global.ShootTimer),
//
//                //TODO ====================== GatePick 4 =========================
//                new KPathCommand2(follower, nearPath.GateIntake10, 2.5).alongWith(
//                        new SequentialCommandGroup(
//                                new WaitCommand(350),
//                                intakeBackAutoGate(intakess, shooterss, gatess)
//                        )
//                ),
//                new KPathCommand2(follower, nearPath.GateIntake1, 2.5)
//                        .alongWith(
//                                new ParallelCommandGroup(
//                                        intakeFrontAutoGate(intakess, shooterss, gatess),
//                                        new WaitForSensorCommand(() -> (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()), backTimer, 25).andThen(
//                                                intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF)
//                                        ),
//                                        new WaitForSensorCommand(() -> ((!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()) && !robot.midBeam.getState() && (!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState())), frontTimer, 25).andThen(
//                                                intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF),
//                                                intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF),
//                                                gatess.gate1Command(gate_endgamesubsystem.GATE1.CLOSE),
//                                                gatess.gate2Command(gate_endgamesubsystem.GATE2.CLOSE)
//                                        )
////                                new InstantCommand(() -> holdGatePose())
//                                )
//                        )
//                ,
//                ///TODO ==================== Gate Shoot 4 ====================
//                new KPathCommand2(follower, nearPath.GateShoo1).alongWith(
//                        new ParallelCommandGroup(
//                                nearShootingAuto(shooterss),
//                                new WaitCommand(250),
//                                intakess.feederCommands(intakesubsystem.FEEDER.FEED)
////                                new InstantCommand(() -> holdGatePose())
//                        )
//                ),
//
//                new WaitCommand(Global.PreShootTimer),
//                back_feedAUTO(intakess),
//                new WaitCommand(Global.ShootTimer),
//
//
//                //TODO ===================== Pick 1 ====================
//                new KPathCommand2(follower, nearPath.SPIKE1).alongWith(
//                        new SequentialCommandGroup(
//                                new WaitCommand(350),
//                                intakeFrontAuto(intakess, shooterss),
//                                new WaitForSensorCommand(() -> (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()), 1100, 50).andThen(
//                                        intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF)
//                                )
//                        )
//                )
//                ,
//                ///TODO ==================== Shoot 1 =====================
//                new KPathCommand2(follower, nearPath.SHOOT1).alongWith(
//                        new ParallelCommandGroup(
//                                nearShootingAuto(shooterss),
//                                new WaitForSensorCommand(() -> (!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState()), 800, 50).andThen(
//                                        new SequentialCommandGroup(
//                                                new ParallelCommandGroup(
//                                                        intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF),
//                                                        intakess.deployFCommands(intakesubsystem.DEPLOYF.UP)
//                                                ),
//                                                new WaitCommand(300),
//                                                intakess.feederCommands(intakesubsystem.FEEDER.FEED)
//                                        )
//                                )
////                                new InstantCommand(() -> holdShootPose())
//                        )
//                ),
//                new WaitCommand(Global.PreShootTimer),
//                new KPathCommand2(follower, nearPath.SHOOT1).alongWith(back_feedAUTO(intakess)),
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
//        while (opModeInInit()) {
//            endTrajectory = true;
//            new SequentialCommandGroup(
//
//                    initSequence(intakess, shooterss, gatess)
//            );
//        }
//
//        waitForStart();
//
//        endTimer.reset();
//
//        while (opModeIsActive()) {
//
//            if (endTimer.seconds() > 29.5 && endTrajectory) {
//                endTrajectory = false;
//                CommandScheduler.getInstance().cancelAll();
//
//                CommandScheduler.getInstance().schedule(leavePath());
//            }
//            /// Run the sequence
//            CommandScheduler.getInstance().run();
//            follower.update();
//            Pose pose = follower.getPose();
//            Global.startPoseTry = pose;
//            Global.redSide = true;
//
////            telemetry.addData("X", pose.getX());
////            telemetry.addData("X", follower.isBusy());
////            telemetry.addData("Y", pose.getY());
////            telemetry.addData("Heading", pose.getHeading());
//            telemetry.update();
//
//
//        }
//    }
//
//    private Command leavePath() {
//        return new KPathCommand2(follower, nearPath.LEAVE).alongWith(
//                new ParallelCommandGroup(
//                        initSequence(intakess, shooterss, gatess)
//                )
//        );
//    }
//
//}
