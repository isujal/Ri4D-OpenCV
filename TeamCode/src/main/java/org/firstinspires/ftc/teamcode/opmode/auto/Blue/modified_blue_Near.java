/// WORKING CODE ///


package org.firstinspires.ftc.teamcode.opmode.auto.Blue;

import static org.firstinspires.ftc.teamcode.commandbase.Trajectorycommands.KPathCommand2.finished;
import static org.firstinspires.ftc.teamcode.hardware.Globals.A_hoodNear;
import static org.firstinspires.ftc.teamcode.hardware.Globals.sliderIn;
import static org.firstinspires.ftc.teamcode.hardware.Globals.targetflyVelocity;
import static org.firstinspires.ftc.teamcode.hardware.Globals.turretNearAutoBlue;
import static org.firstinspires.ftc.teamcode.hardware.RobotHardware.flywheelVelocity;
import static org.firstinspires.ftc.teamcode.sequences.sequence.shootingSeq;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.GateCommand;
import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.SliderCommand;
import org.firstinspires.ftc.teamcode.commandbase.Trajectorycommands.KPathCommand2;
import org.firstinspires.ftc.teamcode.hardware.Globals;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.paths.NEAR.BlueNearPath_Final;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.sequences.sequence;
import org.firstinspires.ftc.teamcode.subsystem.endgamesubsystem;
import org.firstinspires.ftc.teamcode.subsystem.intaksubsystem;
import org.firstinspires.ftc.teamcode.subsystem.shootersubsystem;

import java.util.List;


//@Disabled
@Autonomous(name = "blue modified near", group = "RedAuto")
public class modified_blue_Near extends LinearOpMode {

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



    private final RobotHardware robot = RobotHardware.getInstance();

    private BlueNearPath_Final nearPath; // Paths defined in the Paths class

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




    public SequentialCommandGroup AutoSeq(){
        return
                new SequentialCommandGroup(

                        /// preload shoot
                        sequence.preload_shoot_Near(intake,
                                        shooter,
                                        follower,
                                        nearPath.SHOOT0,
                                        2,"blue"),
                                        /// preload shoot
                                        shootingSeq(intake,shooter),
                                        /// check ramp is empty
                                        waitForShoot(),
                                        /// wait for third shoot
                                        new WaitCommand(10),



                                        /// 2nd stack Next cycle
                                        sequence.noGateCycle(intake,shooter,
                                                follower,
                                                robot,
                                                nearPath.SPIKE2,
                                                nearPath.SHOOT2,
                                                2.5,
                                                2.5,
                                                "blue"),
                                        /// 1-123 shoot

                                        new WaitCommand(10),
                                        shootingSeq(intake,shooter),
                                        /// check is ramp empty or not
                                        waitForShoot(),
                                        /// wait for 3rd shoot
                                        new WaitCommand(10),





                                        /// Next Gate intake
                                        sequence.CycleForGateIntakeNopenalty(
                                                intake,
                                                shooter,
                                                follower,
                                                robot,
                                                nearPath.Gate_Touch,
                                                nearPath.Go_far,
                                                nearPath.Collect,
                                                nearPath.GateTouchToShoot,
                                                2.5,
                                                2.5,
                                                2.5,
                                                2,
                                                "blue"),


                                        /// 1-123 shoot
                                        new WaitCommand(10),

                                        shootingSeq(intake,shooter),
                                        /// check is ramp empty or not
                                        waitForShoot(),
                                        /// wait for 3rd shoot
                                        new WaitCommand(10),

                                        /// 3rd stack Next cycle
                                        sequence.noGateCycle(intake,shooter,
                                                follower,
                                                robot,
                                                nearPath.SPIKE3,
                                                nearPath.SHOOT3,
                                                3.5,
                                                3.5,
                                                "blue"),
                                        /// 1-123 shoot

                                        new WaitCommand(10),
                                        shootingSeq(intake,shooter),
                                        /// check is ramp empty or not
                                        waitForShoot(),
                                        /// wait for 3rd shoot
                                        new WaitCommand(10),

/*

                                        /// Next Gate intake 2
                                        /// Next Gate intake
                                        sequence.CycleForGateIntakeNopenalty(
                                                intake,
                                                shooter,
                                                follower,
                                                robot,
                                                nearPath.Gate_Touch,
                                                nearPath.Go_far,
                                                nearPath.Collect,
                                                nearPath.GateTouchToShoot,
                                                2.5,
                                                2.5,
                                                2.5,
                                                2,
                                                "blue"
                                         ),

                                            /// 1-123 shoot
                                            new WaitCommand(10),

                                            shootingSeq(intake,shooter),
                                            /// check is ramp empty or not
                                            waitForShoot(),
                                            /// wait for 3rd shoot
                                            new WaitCommand(10),
*/






                                        new GateCommand(intake, intaksubsystem.GATE_OPENER_STATES.GAte_Close),




                                        /// Next cycle First 3 pick
                                        /// 2nd stack Next cycle
                                            sequence.noGateCycle(intake,
                                                    shooter,
                                                    follower,
                                                    robot,
                                                    nearPath.SPIKE1,
                                                    nearPath.SHOOT1,
                                                    2.5,
                                                    2.5,
                                                    "blue"),
                                                            /// 1-123 shoot
                                                            new WaitCommand(10),
                                                            shootingSeq(intake,shooter),                        /// check is ramp empty or not
                                                            waitForShoot(),
                                                            /// wait for 3rd shoot
                                                            new WaitCommand(10),


                                        new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
                                                new KPathCommand2(follower, nearPath.SPIKE1, 2)


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
        nearPath = new BlueNearPath_Final(follower); // Initialize paths



        follower.setStartingPose(nearPath.startPose);



        robot.turret.setPosition(robot.hardangle(turretNearAutoBlue));
        robot.hood.setPosition(A_hoodNear);
        robot.slider.setPosition(sliderIn);
        pidf = new PIDFController(flykP, flykI, flykD, flykF);



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

            telemetry.update();
            follower.update();
            Pose pose = follower.getPose();



        }

    }
}


