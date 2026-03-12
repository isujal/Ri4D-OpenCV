package org.firstinspires.ftc.teamcode.opmode.teleop;


import static org.firstinspires.ftc.teamcode.hardware.Globals.shooter_D;
import static org.firstinspires.ftc.teamcode.hardware.Globals.shooter_F;
import static org.firstinspires.ftc.teamcode.hardware.Globals.shooter_I;
import static org.firstinspires.ftc.teamcode.hardware.Globals.shooter_P;
import static org.firstinspires.ftc.teamcode.hardware.Globals.sliderIn;
import static org.firstinspires.ftc.teamcode.hardware.Globals.targetflyVelocity;
import static org.firstinspires.ftc.teamcode.hardware.RobotHardware.flywheelVelocity;
import static org.firstinspires.ftc.teamcode.pedroPathing.Constants.driveConstants;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.drivetrains.Mecanum;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.DeployerCommand;
import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.FrontIntakeCommand;
import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.GateCommand;
import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.HoodCommand;
import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.RearIntakeCommand;
import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.ShooterCommand;
import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.SliderCommand;
import org.firstinspires.ftc.teamcode.commandbase.InstandCommands.TurretCommand;
import org.firstinspires.ftc.teamcode.commandbase.Trajectorycommands.KPathCommand2;
import org.firstinspires.ftc.teamcode.commandbase.VisionAlignCommand;
import org.firstinspires.ftc.teamcode.hardware.Globals;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystem.endgamesubsystem;
import org.firstinspires.ftc.teamcode.subsystem.intaksubsystem;
import org.firstinspires.ftc.teamcode.subsystem.shootersubsystem;

import java.util.List;

import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.openftc.easyopencv.*;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.ArtifactPipeline;
import org.firstinspires.ftc.teamcode.subsystem.VisionSubsystem;


@Config
//@TeleOp(name = "RiFD_BLUE ", group = "00000")
@TeleOp(name = "RiFD BLUE \uD83E\uDD76 ", group = "00000")
public class RiFD_BLUE extends CommandOpMode {

    public static Follower follower;


    private VisionSubsystem vision;

    private Mecanum drive = null;



    private PIDFController pidf;
    public static double flykP = Globals.flykP;
    public static double flykI = Globals.flykI;
    public static double flykD = Globals.flykD;
    public static double flykF = Globals.flykF;
    public static double headingMultiplier = 0.8;


    /// RED Teleop
    shootersubsystem.TURRET_STATES humanPlayerTurret  = shootersubsystem.TURRET_STATES.FAR_BLUE_TELE_HUMANP; // for red
    public static double ImuOff = Math.PI; // for blue
    /// BLUE Teleop
//    public static double nearImuShoot = -135; // for blue
//    public static double ImuOff = Math.PI; // for blue
//    shootersubsystem.TURRET_STATES humanPlayerTurret  = shootersubsystem.TURRET_STATES.FAR_BLUE_TELE_HUMANP; // for red



    public static int f1 = 100;
    public static int f2 = 200;
    public static int f3 = 400;

    public static int c1 = 200;
    public static int c2 = 200;
    public static int c3 = 200;

    public static  boolean sloshoot = true;


    int counter = 0;
    ElapsedTime ClickShootTimer = new ElapsedTime();
    public static double DebounceClickShoot = 100;
    public static boolean singleShootOn = false;


    public static double speed = 1;
    public static double reducedSpeed = 0.7;
    private boolean toggleState = false;
    private boolean lastButtonState = false;
    public static double DEBOUNCE_TIME = 0.1; // seconds



    private ElapsedTime debounceTimer = new ElapsedTime();


    public static int d3 = 70;
    public static int d_near = 100;





    public static double kp=1.5;//1.1; //1.3b
    public static double ki=0.04;
    public static double kd=0.64;
    public static double kf=0;




    Pose2d startPose = new Pose2d(85,60 , Math.toRadians(-90));


    public static boolean inTeleop = true;
    public static double l= 0.66;


    intaksubsystem intake ;
    shootersubsystem shooter ;
    endgamesubsystem endgame ;


    public static boolean checkRange = false;
    public static boolean shootMode = false;

    public static boolean checkArtefacts = false;


    public static boolean intakeFromFront = false;
    public static boolean shooterStart = false;

    public static double P = shooter_P;
    public static double I = shooter_I;
    public static double D = shooter_D;
    public static double F = shooter_F;//16.67;//15.3;




    private ElapsedTime timer;


    public static double position;

    private boolean pHeadingLock = true;
    private double targetHeading;


    private final RobotHardware robot = RobotHardware.getInstance();

    //heading pid


    private GamepadEx driver;

    private boolean lock_robot_heading = false;

    //BULK READ
    List<LynxModule> allHubs = null;

    //TODO DRIVE
    private boolean ideal = false;


    @Override
    public void initialize() {


        intake = new intaksubsystem(robot);
        shooter = new shootersubsystem(robot);
        endgame = new endgamesubsystem(robot);

        CommandScheduler.getInstance().reset();

        robot.enabled = true;
        allHubs = hardwareMap.getAll(LynxModule.class);

        robot.init(hardwareMap,startPose);
        vision = new VisionSubsystem(hardwareMap);
        driver = new GamepadEx(gamepad1);

        new GamepadButton(driver, GamepadKeys.Button.A)
                .whileHeld(new VisionAlignCommand(vision, robot));

        pidf = new PIDFController(flykP, flykI, flykD, flykF);
        robot.slider.setPosition(sliderIn);


        debounceTimer.reset();
        ClickShootTimer.reset();

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(0,0,Math.toDegrees(0)));
        follower.startTeleopDrive(true);
        follower.update();


        drive = new Mecanum(hardwareMap, driveConstants);

        for(LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

    }

    @Override
    public void run() {
        super.run();
        ArtifactPipeline.Position pos = vision.getPosition();
        telemetry.addData("Artifact Position", pos);



        driveCurrent(telemetry,drive);
        follower.setTeleOpDrive(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x * headingMultiplier, false, ImuOff);
        follower.update();

        telemetry.addData("head", follower.getPose().getHeading());

        if (gamepad1.right_stick_button) {
            resetYaw();
        }


        flywheelVelocity(pidf,
                robot.shootLeft,
                robot.shootRight,
                (int)robot.shootLeft.getVelocity(),
                targetflyVelocity,
                20);


//        pidf.setTolerance(20);
//        double currentVelocity = robot.shootLeft.getVelocity();
//        double output = pidf.calculate(currentVelocity, targetflyVelocity);
//        robot.shootLeft.setPower(output);
//        robot.shootRight.setPower(output);


        /// Single Shooting
        singleshootseq();
        if (gamepad1.right_bumper&& gamepad1.left_trigger > 0.4 &&
                debounceTimer.milliseconds() >= DebounceClickShoot) {
            counter++;
            debounceTimer.reset();
        }

        if(gamepad1.left_trigger > 0.4){
            singleShootOn = true;
        }
        else{
            singleShootOn = false;
        }


        double loop = System.nanoTime();




        /// DEPLOYER
        boolean currentButtonState = gamepad1.left_bumper;
        if (currentButtonState && !lastButtonState &&
                debounceTimer.seconds() > DEBOUNCE_TIME  ) {

            toggleState = !toggleState;   // TOGGLE
            debounceTimer.reset();
        }


        lastButtonState = currentButtonState;
//        telemetry.addData("Toggle State", toggleState);

        /// Mechanisms

        // intake start
        if(gamepad1.left_bumper ){
            ideal = false;
            schedule(
                    new SequentialCommandGroup(
                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.IN),
                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.IN),
                            new SliderCommand( shooter , shootersubsystem.SLIDER_STATES.IN),
                            new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.ZERO),
                            new SliderCommand(shooter , shootersubsystem.SLIDER_STATES.IN),
                            new InstantCommand(()-> shooterStart = false),
                            new InstantCommand(()-> checkArtefacts = true),
                            new InstantCommand(()->shootMode = false)
                    ));

        }

        if(!sloshoot && robot.F1.getState() &&robot.F2.getState() &&robot.F3.getState() &&robot.R1.getState() &&robot.R2.getState() &&robot.R3.getState()){
            schedule(
                    new SequentialCommandGroup(
                            new WaitCommand(300),
                            new ParallelCommandGroup(
                                    new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
                                    new InstantCommand(()-> ideal = false),
                                    new InstantCommand(()->shootMode = false),
                                    new InstantCommand(()->checkArtefacts = true),
                                    new InstantCommand(()->checkRange = false),
                                    new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.IDEAL),
                                    new InstantCommand(()-> counter= 0)
                            )
                    )
            );
        }





        if(!ideal){
            // for front intake deployer
            if(!toggleState){
                /*intakeFromFront = false;
                schedule(new SequentialCommandGroup(
                        new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.REAR_INTAKE)
                ));*/


                intakeFromFront = true;
                schedule(new SequentialCommandGroup(
                        new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.FRONT_INTAKE)
                ));

            }
            // for rear intake deployer
            else{
                /*intakeFromFront = true;
                schedule(new SequentialCommandGroup(
                        new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.FRONT_INTAKE)
                ));*/


                intakeFromFront = false;
                schedule(new SequentialCommandGroup(
                        new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.REAR_INTAKE)
                ));

            }
        }

        if(gamepad2.square){
            ideal = true;
            schedule(
                    new SequentialCommandGroup(
                            new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.IDEAL)
                    )
            );
        }



        if(!shootMode) {
            // intake off if 3 balls inside and auto shooter start
            if (checkArtefacts && !robot.F1.getState() && (!robot.F3.getState() || !robot.R3.getState()) && !robot.R1.getState()) {
                schedule(
                        new SequentialCommandGroup(
                                new InstantCommand(()-> gamepad1.rumble(300)),
                                new InstantCommand(()-> gamepad2.rumble(300)),
                                new InstantCommand(() -> checkArtefacts = false),

                                new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.IN),
                                new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.IN),

                                new WaitCommand(500),
                                new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF),
                                new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF),
                                new InstantCommand(() -> shooterStart = true),

                                new ParallelCommandGroup(
                                        new InstantCommand(()-> shootMode = true),
                                        new HoodCommand(shooter, shootersubsystem.HOOD_STATES.NEAR),
                                        new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.NEAR),
                                        new TurretCommand(shooter, shootersubsystem.TURRET_STATES.NEAR),


                                        new InstantCommand(()->ideal = true),
                                        new DeployerCommand(intake, intaksubsystem.DEPLOYER_STATES.IDEAL)

                                )
                        )
                );
            }

            if(checkArtefacts && (!robot.R3.getState() || !robot.F3.getState()) && intake.deployerStates.equals(intaksubsystem.DEPLOYER_STATES.FRONT_INTAKE) && !robot.F1.getState()){
                schedule(new SequentialCommandGroup(
                        new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.REVERSE),
                        new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.IN),
                        new InstantCommand(() -> intakeFromFront = true)
                ));
            }

            if ( checkArtefacts && (!robot.R1.getState())&& intake.deployerStates.equals(intaksubsystem.DEPLOYER_STATES.FRONT_INTAKE)) {
                schedule(new SequentialCommandGroup(
                        new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF),
                        new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.IN)
                ));
            }

            if(checkArtefacts && (!robot.R3.getState() || !robot.F3.getState()) && intake.deployerStates.equals(intaksubsystem.DEPLOYER_STATES.REAR_INTAKE) && !robot.R1.getState()){
                schedule(new SequentialCommandGroup(
                        new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.REVERSE),
                        new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.IN),
                        new InstantCommand(() -> intakeFromFront = true)
                ));
            }


            if ( checkArtefacts && (!robot.F1.getState())&& intake.deployerStates.equals(intaksubsystem.DEPLOYER_STATES.REAR_INTAKE)) {
                schedule(new SequentialCommandGroup(
                        new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF),
                        new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.IN)
                ));
            }
        }


        if (gamepad1.square){
            schedule(new SequentialCommandGroup(
                    new InstantCommand(()-> shootMode = true),

                    new HoodCommand(shooter, shootersubsystem.HOOD_STATES.EXTREME_NEAR),
                    new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.EXTREME_NEAR),
                    new TurretCommand(shooter,shootersubsystem.TURRET_STATES.NEAR)
            ));
        }

        if (gamepad1.circle){
            schedule(new SequentialCommandGroup(
                    new InstantCommand(()-> shootMode = true),

                    new HoodCommand(shooter, shootersubsystem.HOOD_STATES.NEAR),
                    new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.NEAR),
                    new TurretCommand(shooter, shootersubsystem.TURRET_STATES.NEAR)

            ));
        }

        if (gamepad1.triangle){
            schedule(new SequentialCommandGroup(
                    new InstantCommand(()-> shootMode = true),

                    new HoodCommand(shooter, shootersubsystem.HOOD_STATES.FAR),
                    new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.FAR_TELE_HUMANP),
                    new TurretCommand(shooter, humanPlayerTurret)
            ));
        }
        if (gamepad1.cross){
            schedule(new SequentialCommandGroup(
                    new InstantCommand(()-> shootMode = true),

                    new HoodCommand(shooter, shootersubsystem.HOOD_STATES.NEAR_Center),
                    new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.NEAR_Center),
                    new TurretCommand(shooter, shootersubsystem.TURRET_STATES.NEAR)
            ));
        }



        if(gamepad1.left_trigger > 0.4){
            sloshoot = true;
        }
        else{
            sloshoot = false;

        }







        // Shooting

        if(gamepad1.right_bumper && !singleShootOn &&
                (shooter.shooterStates.equals(shootersubsystem.SHOOTER_STATES.NEAR) ||
                shooter.shooterStates.equals(shootersubsystem.SHOOTER_STATES.NEAR_Center) ||
                shooter.shooterStates.equals(shootersubsystem.SHOOTER_STATES.FAR_TELE_HUMANP) ||
                        (shooter.shooterStates.equals(shootersubsystem.SHOOTER_STATES.EXTREME_NEAR))))
        {
            schedule(
                    new SequentialCommandGroup(
                            new ParallelCommandGroup(
                                    new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.OUT),
                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.PUSH),
                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.PUSH)
                            ),

                            new WaitCommand(d3), // d3
//                            new WaitForSensorCommand(()-> robot.F1.getState() && robot.R1.getState()), //100
                            new ParallelCommandGroup(
                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.PUSH),
                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF)
                            ),
                            new WaitCommand(d_near), // d_near //100

                            new ParallelCommandGroup(
                                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.PUSH),
                                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.PUSH)
                            )

                    )
            );
            toggleState = false;

        }



//        /// Turn off all
        if(gamepad1.ps){
            schedule(new SequentialCommandGroup(
                    new ShooterCommand(shooter, shootersubsystem.SHOOTER_STATES.ZERO),
                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF),
                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF)
            ));
        }



        /// endgame
        if(gamepad1.back){
            schedule(new SequentialCommandGroup(
                    new InstantCommand(()->robot.rackNpinion.setPosition(0))
            ));
        }
        if(gamepad1.start){
            schedule(new SequentialCommandGroup(
                    new InstantCommand(()->robot.rackNpinion.setPosition(1))
            ));
        }



        /// Gate opener
        if(gamepad2.right_trigger > 0.4 || gamepad1.right_trigger > 0.4){
            schedule(new SequentialCommandGroup(
                    new GateCommand(intake, intaksubsystem.GATE_OPENER_STATES.Gate_OPEN)
            ));

            speed = reducedSpeed;
        }
        else {
            schedule(new SequentialCommandGroup(
                    new GateCommand(intake, intaksubsystem.GATE_OPENER_STATES.GAte_Close)
            ));
            speed = 1;
        }






        if(gamepad1.touchpad){
            schedule(
                    new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.OUT),
                    new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.REVERSE),
                    new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.REVERSE),
                    new WaitCommand(1000),
                    new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN)

                    );
        }

        boolean joystickIn = (Math.abs(gamepad1.left_stick_x) > 0.01 || Math.abs(gamepad1.left_stick_y) > 0.01 || Math.abs(gamepad2.left_stick_x) > 0.01);


        if(gamepad1.dpad_down){
            schedule(autopath(follower.getPose().getX(),follower.getPose().getY(),follower.getPose().getHeading()).interruptOn(()->joystickIn).
                    andThen(new InstantCommand(()->follower.startTeleopDrive())));
        }




        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }


        /// telemetry
        telemetry(loop);



    }/// run
//    public static void initAprilTag(HardwareMap hardwareMap) {
//        // Create the AprilTag processor by using a builder.
//        aprilTag = new AprilTagProcessor.Builder()
////                .setCameraPose(cameraPosition, cameraOrientation)
//                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
//                .setLensIntrinsics(531.897, 531.897, 340.151,254.67)
//                .build();
//        // Create the WEBCAM vision portal by using a builder.
//        visionPortal = new VisionPortal.Builder()
//                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
//                .addProcessor(aprilTag)
//                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
//                .build();
//    }
    public void driveCurrent(Telemetry t, Mecanum drive){
        t.addData("leftFront", drive.getMotors().get(0).getCurrent(CurrentUnit.AMPS));
        t.addData("leftRear",   drive.getMotors().get(1).getCurrent(CurrentUnit.AMPS));
        t.addData("rightFront", drive.getMotors().get(2).getCurrent(CurrentUnit.AMPS));
        t.addData("rightRear",  drive.getMotors().get(3).getCurrent(CurrentUnit.AMPS));

        t.addData("intake front", robot.frontIn.getCurrent(CurrentUnit.AMPS));
        t.addData("intake rear", robot.rearIn.getCurrent(CurrentUnit.AMPS));
        t.addData("shoot left", robot.shootLeft.getCurrent(CurrentUnit.AMPS));
        t.addData("shoot right", robot.shootRight.getCurrent(CurrentUnit.AMPS));


    }














    ///telemetry
    public void telemetry(double loop){

//        telemetry.addData("right vel", robot.shootRight.getVelocity());
//        telemetry.addData("left vel", robot.shootLeft.getVelocity());
//        telemetry.addData("right current", robot.shootRight.getCurrent(CurrentUnit.AMPS));
//        telemetry.addData("left current", robot.shootLeft.getCurrent(CurrentUnit.AMPS));
//        telemetry.addData("near Velo",Globals.nearVelocity);
//        telemetry.addData("far Velo",Globals.farVelocity);

//        telemetry.addData("heading (deg)", Math.toDegrees( robot.localizer.getPose().heading.toDouble()));
        telemetry.update();
       }



    public void singleshootseq(){

        /// counter = 0
        if(gamepad1.right_bumper && singleShootOn && counter == 0){
            schedule(new SequentialCommandGroup(
                    new ParallelCommandGroup(
                            new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.OUT),
                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.PUSH),
                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.PUSH)
                    ),
                    new WaitCommand(c1),
                    new ParallelCommandGroup(
                            new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF),
                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF)
                    )

            ));
        }

        /// counter = 1
        if(gamepad1.right_bumper && singleShootOn && counter == 1){
            schedule(new SequentialCommandGroup(
                    new ParallelCommandGroup(
                            new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.OUT),
                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.PUSH)
//                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.PUSH)
                    ),
                    new WaitCommand(c2),
                    new ParallelCommandGroup(
                            new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF)
//                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.PUSH)
                    )

            ));
        }

        /// counter = 2
        if(gamepad1.right_bumper && singleShootOn && counter == 2){
            schedule(new SequentialCommandGroup(
                    new ParallelCommandGroup(
                            new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.OUT),
                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.PUSH),
                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.PUSH)
                    ),
                    new WaitCommand(c3),
                    new ParallelCommandGroup(
                            new SliderCommand(shooter, shootersubsystem.SLIDER_STATES.IN),
                            new FrontIntakeCommand(intake, intaksubsystem.FRONT_INTAKE_STATES.OFF),
                            new RearIntakeCommand(intake, intaksubsystem.REAR_INTAKE_STATES.OFF)
                    ), new InstantCommand( ()-> counter = 0)

            ));
        }
    }
    public static void resetYaw() {
        follower.setPose(new Pose(follower.getPose().getX(), follower.getPose().getY(), 0));
    }


    public KPathCommand2 autopath(double x, double y, double head){
        PathChain auto = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(x, y),

                                new Pose(x+0.001, y+0.001)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(head), Math.toRadians(ImuOff))

                .build();
        return new KPathCommand2(follower,auto,2);
    }
}
