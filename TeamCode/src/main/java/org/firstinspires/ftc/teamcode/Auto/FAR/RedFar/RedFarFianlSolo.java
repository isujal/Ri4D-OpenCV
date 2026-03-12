//package org.firstinspires.ftc.teamcode.Auto.FAR.RedFar;
//
//import com.arcrobotics.ftclib.command.CommandScheduler;
//import com.arcrobotics.ftclib.command.ParallelCommandGroup;
//import com.arcrobotics.ftclib.command.SequentialCommandGroup;
//import com.arcrobotics.ftclib.command.WaitCommand;
//import com.pedropathing.follower.Follower;
//import com.pedropathing.geometry.Pose;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import org.firstinspires.ftc.teamcode.drivecommand.KPathCommand3;
//import org.firstinspires.ftc.teamcode.drivecommand.WaitForSensorCommand;
//import org.firstinspires.ftc.teamcode.hardware.Global;
//import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
//import org.firstinspires.ftc.teamcode.paths.FAR.RedFarPath_Final;
//import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
//import org.firstinspires.ftc.teamcode.subsystems.gate_endgamesubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.intakesubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.shootersubsystem;
//
//import static org.firstinspires.ftc.teamcode.sequences.Intake.intakeFrontAuto;
//import static org.firstinspires.ftc.teamcode.sequences.Intake.intakeFrontAutoGate;
//import static org.firstinspires.ftc.teamcode.sequences.Shooting.farShootingAUTO;
//import static org.firstinspires.ftc.teamcode.sequences.back_feed.back_feedAUTO;
//import static org.firstinspires.ftc.teamcode.sequences.initSequence.initSequence;
//
//@Autonomous(name = "RED Far SOLO Final ", group = "RED 🔴")
//public class RedFarFianlSolo extends LinearOpMode {
//
//    private final RobotHardware robot = RobotHardware.getInstance();
//    intakesubsystem intakess;
//    shootersubsystem shooterss;
//    gate_endgamesubsystem gatess;
//
//    public Follower follower;
//
//    private RedFarPath_Final farPath; // Paths defined in the Paths class
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
//        farPath = new RedFarPath_Final(follower); // Initialize paths
//
//        follower.setStartingPose(farPath.startPose);
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
//                new KPathCommand3(follower, farPath.SHOOT0).alongWith(
//                        new ParallelCommandGroup(
//                                shooterss.shooter1Command(shootersubsystem.SHOOTER1.AUTOFAR),
//                                shooterss.shooter2Command(shootersubsystem.SHOOTER2.AUTOFAR),
//                                shooterss.hoodCommand(shootersubsystem.HOOD.FARAUTO),
//                                shooterss.turretCommand(shootersubsystem.TURRET.REDAUTO),
//                                intakess.feederCommands(intakesubsystem.FEEDER.FEED)
//                        )),
//                new WaitCommand(Global.PreShootTimerFAR + 300),
//                back_feedAUTO(intakess),
//                new WaitCommand(Global.ShootTimerFAR),
//
//                //TODO ====================  Pick 4 ====================
//                new KPathCommand3(follower, farPath.SPIKE4).alongWith(intakeFrontAuto(intakess, shooterss)),
//                new KPathCommand3(follower, farPath.SPIKE41, 3.5)
//                        .alongWith(
//                                new WaitForSensorCommand(() -> (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()), 2000, 50).andThen(
//                                        intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF)
//                                )
//                        )
//                ,
//                ///TODO ==================== Shoot 4 ====================
//                new KPathCommand3(follower, farPath.SHOOT4).alongWith(
//                        new ParallelCommandGroup(
//                                farShootingAUTO(shooterss),
//                                new WaitForSensorCommand(() -> (!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState()), 500, 50).andThen(
//                                        new ParallelCommandGroup(
//                                                intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF),
//                                                new WaitCommand(300),
//                                                intakess.feederCommands(intakesubsystem.FEEDER.FEED)
//                                        )
//                                )
//                        )
//                ),
//                new WaitCommand(Global.PreShootTimerFAR),
//                back_feedAUTO(intakess),
//                new WaitCommand(Global.ShootTimerFAR),
//
//                //TODO ====================  Pick 2 ====================
//                new KPathCommand3(follower, farPath.SPIKE2).alongWith(
//                        new ParallelCommandGroup(
//                                gatess.gate1Command(gate_endgamesubsystem.GATE1.OPEN),
//                                intakeFrontAuto(intakess, shooterss),
//                                new WaitForSensorCommand(() -> (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()), 1800, 50).andThen(
//                                        intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF),
//                                        intakess.deployFCommands(intakesubsystem.DEPLOYF.UP)
//
//                                )
//                        )
//                )
//                ,
//
//                new WaitCommand(700),
//                ///TODO ==================== Shoot 2 ====================
//                new KPathCommand3(follower, farPath.SHOOT2).alongWith(
//                        new ParallelCommandGroup(
//                                farShootingAUTO(shooterss),
//                                new WaitForSensorCommand(() -> (!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState()), 500, 50).andThen(
//                                        new ParallelCommandGroup(
//                                                gatess.gate1Command(gate_endgamesubsystem.GATE1.CLOSE),
//                                                intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF),
//                                                new WaitCommand(300),
//                                                intakess.feederCommands(intakesubsystem.FEEDER.FEED)
//                                        )
//                                )
//                        )
//                ),
//                new WaitCommand(Global.PreShootTimerFAR),
//                back_feedAUTO(intakess),
//                new WaitCommand(Global.ShootTimerFAR),
//
//                //TODO ====================  Pick 3 ====================
//                new KPathCommand3(follower, farPath.SPIKE3).alongWith(
//                        new ParallelCommandGroup(
//                                intakeFrontAuto(intakess, shooterss),
//                                new WaitForSensorCommand(() -> (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()), 1300, 50).andThen(
//                                        intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF)
//                                )
//                        )
//                )
//                ,
//                ///TODO ==================== Shoot 3 ====================
//                new KPathCommand3(follower, farPath.SHOOT3).alongWith(
//                        new ParallelCommandGroup(
//                                farShootingAUTO(shooterss),
//                                new WaitForSensorCommand(() -> (!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState()), 500, 50).andThen(
//                                        new ParallelCommandGroup(
//                                                intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF),
//                                                new WaitCommand(300),
//                                                intakess.feederCommands(intakesubsystem.FEEDER.FEED)
//                                        )
//                                )
//                        )
//                ),
//                new WaitCommand(Global.PreShootTimerFAR),
//                back_feedAUTO(intakess),
//                new WaitCommand(Global.ShootTimerFAR),
//
//                //TODO ====================  Random Pick 1 ====================
////                new KPathCommand3(follower, farPath.RANDOM3).alongWith(intakeFrontAuto(intakess, shooterss)),
////                new KPathCommand3(follower, farPath.RANDOM32).alongWith(
////                        new WaitForSensorCommand(() -> (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()), 2000, 50).andThen(
////                                intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF)
////                        )
////                ),
////                new KPathCommand3(follower, farPath.SPIKE4).alongWith(intakeFrontAuto(intakess, shooterss)),
//
//                new KPathCommand3(follower, farPath.RANDOM33, 3.5)
//                        .alongWith(
//                                new ParallelCommandGroup(
//                                        intakeFrontAuto(intakess, shooterss),
//                                        new WaitForSensorCommand(() -> (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()), 2000, 50).andThen(
//                                                intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF)
//                                        )
//                                )
//                        )
//                ,
//
//
//                ///TODO ==================== Random Shoot 1 ====================
//                new KPathCommand3(follower, farPath.RSHOOT3).alongWith(
//                        new ParallelCommandGroup(
//                                farShootingAUTO(shooterss),
//                                new WaitForSensorCommand(() -> (!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState()), 500, 50).andThen(
//                                        new ParallelCommandGroup(
//                                                intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF),
//                                                new WaitCommand(300),
//                                                intakess.feederCommands(intakesubsystem.FEEDER.FEED)
//                                        )
//                                )
//                        )
//                ),
//                new WaitCommand(Global.PreShootTimerFAR),
//                back_feedAUTO(intakess),
//                new WaitCommand(Global.ShootTimerFAR),
//
//                //TODO ====================== GatePick 1 =========================
////                new InstantCommand(() -> Constants.pathConstraints.setTimeoutConstraint(Global.timeOut)),
//                new KPathCommand3(follower, farPath.GATEINTAKE1, 0.6, "RED")
//                        .alongWith(
//                                new ParallelCommandGroup(
//                                        intakeFrontAutoGate(intakess, gatess),
//                                        new WaitForSensorCommand(() -> (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()), 3000, 25).andThen(
//                                                intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF)
//                                        ),
//                                        new WaitForSensorCommand(() -> ((!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()) && !robot.midBeam.getState() && (!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState())), 3500, 25).andThen(
//                                                new ParallelCommandGroup(
//                                                        intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF),
//                                                        intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF),
//                                                        gatess.gate1Command(gate_endgamesubsystem.GATE1.CLOSE),
//                                                        gatess.gate2Command(gate_endgamesubsystem.GATE2.CLOSE)
//                                                )
//                                        )
//                                )
//                        )
//                ,
//                ///TODO ==================== Gate Shoot 1 ====================
////                new InstantCommand(() -> Constants.pathConstraints.setTimeoutConstraint(100)),
//                new KPathCommand3(follower, farPath.GATESHOOT1).alongWith(
//                        new ParallelCommandGroup(
//                                farShootingAUTO(shooterss),
//                                intakess.feederCommands(intakesubsystem.FEEDER.FEED)
//                        )
//                ),
//
//                new WaitCommand(Global.PreShootTimer),
//                back_feedAUTO(intakess),
//                new WaitCommand(Global.ShootTimer),
//
//                //TODO ====================  Random Pick 2 ====================
////                new KPathCommand3(follower, farPath.RANDOM3).alongWith(intakeFrontAuto(intakess, shooterss)),
////                new KPathCommand3(follower, farPath.RANDOM32).alongWith(
////                        new WaitForSensorCommand(() -> (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()), 2000, 50).andThen(
////                                intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF)
////                        )
////                ),
//
//                new KPathCommand3(follower, farPath.RANDOM34, 3.5)
//                        .alongWith(
//                                new ParallelCommandGroup(
//                                        intakeFrontAuto(intakess, shooterss),
//                                        new WaitForSensorCommand(() -> (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()), 2000, 50).andThen(
//                                                intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF)
//                                        )
//                                )
//                        )
//                ,
//                ///TODO ==================== Random Shoot 2 ====================
//                new KPathCommand3(follower, farPath.RSHOOT3).alongWith(
//                        new ParallelCommandGroup(
//                                farShootingAUTO(shooterss),
//                                new WaitForSensorCommand(() -> (!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState()), 500, 50).andThen(
//                                        new ParallelCommandGroup(
//                                                intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF),
//                                                new WaitCommand(300),
//                                                intakess.feederCommands(intakesubsystem.FEEDER.FEED)
//                                        )
//                                )
//                        )
//                ),
//                new WaitCommand(Global.PreShootTimerFAR),
//                back_feedAUTO(intakess),
//                new WaitCommand(Global.ShootTimerFAR),
//
//                //TODO ====================  Pick 4 ====================
////                new KPathCommand3(follower, farPath.SPIKE4).alongWith(intakeFrontAuto(intakess, shooterss)),
////                new KPathCommand3(follower, farPath.SPIKE41)
////                        .alongWith(
////                                new WaitForSensorCommand(() -> (!robot.BfeederBeam.getState() || !robot.BintakeBeam.getState()), 2000, 50).andThen(
////                                        intakess.IntakeBCommands(intakesubsystem.INTAKEB.OFF)
////                                )
////                        )
////                ,
////                ///TODO ==================== Shoot 4 ====================
////                new KPathCommand3(follower, farPath.SHOOT4).alongWith(
////                        new ParallelCommandGroup(
////                                farShootingAUTO(shooterss),
////                                new WaitForSensorCommand(() -> (!robot.FfeederBeam.getState() || !robot.FintakeBeam.getState()), 500, 50).andThen(
////                                        new ParallelCommandGroup(
////                                                intakess.IntakeFCommands(intakesubsystem.INTAKEF.OFF),
////                                                new WaitCommand(300),
////                                                intakess.feederCommands(intakesubsystem.FEEDER.FEED)
////                                        )
////                                )
////                        )
////                ),
////                new WaitCommand(Global.PreShootTimerFAR),
////                back_feedAUTO(intakess),
////                new WaitCommand(Global.ShootTimerFAR),
//
//                ///TODO ==================== Parking ====================
//                new KPathCommand3(follower, farPath.LEAVE).alongWith(
//                        new ParallelCommandGroup(
//                                shooterss.shooter1Command(shootersubsystem.SHOOTER1.OFF),
//                                shooterss.shooter2Command(shootersubsystem.SHOOTER2.OFF),
//                                shooterss.hoodCommand(shootersubsystem.HOOD.FARAUTO),
//                                shooterss.turretCommand(shootersubsystem.TURRET.REDAUTO),
//                                intakess.feederCommands(intakesubsystem.FEEDER.STOP)
//                        )
//                )
////                        .andThen(
////                        new SequentialCommandGroup(
////                                new InstantCommand(() -> Global.startPoseTry = new Pose(follower.getPose().getX(), follower.getPose().getY(), follower.getPose().getHeading()))
////                        )
////                )
//        );
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
//
////            telemetry.addData("X", pose.getX());
////            telemetry.addData("Y", pose.getY());
////            telemetry.addData("Heading", pose.getHeading());
//
//        }
//    }
//}
