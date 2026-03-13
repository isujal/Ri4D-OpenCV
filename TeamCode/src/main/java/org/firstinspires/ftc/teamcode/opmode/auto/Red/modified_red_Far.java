package org.firstinspires.ftc.teamcode.opmode.auto.Red; /// WORKING CODE ///



import static org.firstinspires.ftc.teamcode.commandbase.Trajectorycommands.KPathCommand2.finished;
import static org.firstinspires.ftc.teamcode.hardware.Globals.A_hoodFar;
import static org.firstinspires.ftc.teamcode.hardware.Globals.A_hoodNear;
import static org.firstinspires.ftc.teamcode.hardware.Globals.sliderIn;
import static org.firstinspires.ftc.teamcode.hardware.Globals.targetflyVelocity;
import static org.firstinspires.ftc.teamcode.hardware.Globals.turretFarAutoRed;
import static org.firstinspires.ftc.teamcode.hardware.RobotHardware.flywheelVelocity;
import static org.firstinspires.ftc.teamcode.sequences.sequence.shootingSeq;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.WaitForSensorCommand;
import org.firstinspires.ftc.teamcode.commandbase.Trajectorycommands.KPathCommand2;
import org.firstinspires.ftc.teamcode.commandbase.VisionRegionCommand;
import org.firstinspires.ftc.teamcode.hardware.Globals;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.paths.FAR.RedFarPath_Final;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.sequences.sequence;
import org.firstinspires.ftc.teamcode.subsystem.endgamesubsystem;
import org.firstinspires.ftc.teamcode.subsystem.intaksubsystem;
import org.firstinspires.ftc.teamcode.subsystem.shootersubsystem;
import org.firstinspires.ftc.teamcode.terravision.Vision2;

import java.util.List;


//@Disabled
@Autonomous(name = "red modified Far", group = "RedAuto")
public class modified_red_Far extends LinearOpMode {

    public Follower follower;

    private PIDFController pidf;
    public static double flykP = Globals.flykP;
    public static double flykI = Globals.flykI;
    public static double flykD = Globals.flykD;
    public static double flykF = Globals.flykF;
    public Pose startPose;


    intaksubsystem intake;
    shootersubsystem shooter;
    endgamesubsystem endgame;


    Vision2 vision = new Vision2();
    VisionRegionCommand detectCommand = new VisionRegionCommand(vision);

    private final RobotHardware robot = RobotHardware.getInstance();

    private RedFarPath_Final farPath; // Paths defined in the Paths class

    //BULK READ
    List<LynxModule> allHubs = null;

    public SequentialCommandGroup autoSeq;

    public  SequentialCommandGroup waitForShoot(){
        return  new SequentialCommandGroup(

                new WaitCommand(700)
                /*new WaitForSensorCommand(
                        ()-> robot.R3.getState() && robot.R2.getState() && robot.R1.getState() && robot.F3.getState() && robot.F2.getState() && robot.F1.getState(),5000
                )*/
        );//                new WaitForSensorCommand(
    }

    public SequentialCommandGroup waitForVision(){
        return new SequentialCommandGroup(

                detectCommand,

                new ConditionalCommand(
                        new KPathCommand2(follower,farPath.RIGHT),
                        new ConditionalCommand(
                                new KPathCommand2(follower, farPath.CENTRE),
                                new KPathCommand2(follower, farPath.LEFT),
                                () -> detectCommand.getRegion() == 1
                        ),
                        () -> detectCommand.getRegion() == 0
                )
        );
    }



    public SequentialCommandGroup AutoSeq(){
        return
                new SequentialCommandGroup(

                        /// preload shoot
                        sequence.preload_shoot_Far(intake,
                                shooter,
                                follower,
                                farPath.SHOOT0,
                                2,"red"),
//                        new WaitForSensorCommand(()->finished,2000),
                        /// preload shoot
                        shootingSeq(intake,shooter),
                        /// check ramp is empty
                        waitForShoot(),
                        /// wait for third shoot
                        waitForVision()


//
//                        /// 2nd stack Next cycle
//                        sequence.noGateCycle(intake,shooter,
//                                follower,
//                                robot,
//                                farPath.SPIKE3,
//                                farPath.SHOOT3,
//                                2.5,
//                                2.5,
//                                "red"),
//                        /// 1-123 shoot
//                        new WaitCommand(10),
//                        new WaitForSensorCommand(()->finished,2000),
//                        shootingSeq(intake,shooter),
//                        /// check is ramp empty or not
//                        waitForShoot(),
//                        /// wait for 3rd shoot
//
//
//                        /// 2nd stack Next cycle
//                        sequence.cornerpick(intake,shooter,
//                                follower,
//                                robot,
//                                farPath.First2,
//                                farPath.Corner,
//                                farPath.CornerShoot,
//                                2.5,
//                                1.8,
//                                2.5,
//                                "red"),
//                        /// 1-123 shoot
//                        new WaitCommand(10),
//                        new WaitForSensorCommand(()->finished,2000),
//                        shootingSeq(intake,shooter),
//                        /// check is ramp empty or not
//                        waitForShoot(),
//                        /// wait for 3rd shoot
//
//
//                        /// Blind shot
//                        sequence.blindShot(intake,shooter,
//                                follower,
//                                robot,
//                                farPath.Random_StraightCorner,
//                                farPath.Random_StraightCorner_toshoot,
//                                2.5,
//                                2.5,
//                                "red"),
//                        /// 1-123 shoot
//                        new WaitCommand(10),
//                        new WaitForSensorCommand(()->finished,2000),
//                        shootingSeq(intake,shooter),
//                        /// check is ramp empty or not
//                        waitForShoot(),
//                        /// wait for 3rd shoot
//
//
//                        /// Blind shot 2
//                        sequence.blindShot(intake,shooter,
//                                follower,
//                                robot,
//                                farPath.Random_StraightCorner_extra_push,
//                                farPath.Random_StraightCorner_extra_push_toshoot,
//                                2.5,
//                                2.5,
//                                "red"),
//                        /// 1-123 shoot
//                        new WaitCommand(10),
//                        new WaitForSensorCommand(()->finished,2000),
//                        shootingSeq(intake,shooter),
//                        /// check is ramp empty or not
//                        waitForShoot(),
//                        /// wait for 3rd shoot
//
//
//                        /// Blind shot 3
//                        sequence.blindShot(intake,shooter,
//                                follower,
//                                robot,
//                                farPath.Random_near_spike,
//                                farPath.Random_near_spike_toshoot,
//                                2.5,
//                                2.5,
//                                "red"),
//                        /// 1-123 shoot
//                        new WaitCommand(10),
//                        new WaitForSensorCommand(()->finished,2000),
//                        shootingSeq(intake,shooter),
//                        /// check is ramp empty or not
//                        waitForShoot(),
//                        /// wait for 3rd shoot
//                        new WaitCommand(10),
//
//                        new KPathCommand2(follower, farPath.LEAVE, 2)
//
                        );
    }


    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap,new Pose2d(0,0,0));
        intake = new intaksubsystem(robot);
        shooter = new shootersubsystem(robot);
        endgame = new endgamesubsystem(robot);

        robot.enabled = true;
        allHubs = hardwareMap.getAll(LynxModule.class);

        for(LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }


        follower = Constants.createFollower(hardwareMap);
        farPath = new RedFarPath_Final(follower); // Initialize paths



        follower.setStartingPose(farPath.startPose);



        robot.turret.setPosition(robot.hardangle(turretFarAutoRed));
        robot.hood.setPosition(A_hoodFar);
        robot.slider.setPosition(sliderIn);
        pidf = new PIDFController(flykP, flykI, flykD, flykF);

        vision.init(hardwareMap);
        vision.setAutoMode(true);

        waitForStart();
        autoSeq = AutoSeq();



        if (isStopRequested()) return;
        CommandScheduler.getInstance().schedule(autoSeq);


        while (opModeIsActive()){

            CommandScheduler.getInstance().run();
            for (LynxModule hub : allHubs) {
                hub.clearBulkCache();
            }

            flywheelVelocity(pidf,
                    robot.shootLeft,
                    robot.shootRight,
                    (int)robot.shootLeft.getVelocity(),
                    targetflyVelocity,
                    20);


            telemetry.addData("finished", finished);
            telemetry.addData("left velo", robot.shootLeft.getVelocity());
            telemetry.addData("pidf", pidf.getCoefficients());
            telemetry.addData("detectCommand", detectCommand.getRegion());

            telemetry.update();
            follower.update();
            Pose pose = follower.getPose();



        }

    }
}


