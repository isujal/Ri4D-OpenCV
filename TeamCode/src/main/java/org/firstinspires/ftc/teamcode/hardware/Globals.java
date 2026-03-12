package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Globals {


    public static double flykP = 0.002;
    public static double flykI = 0.0;
    public static double flykD = 0.00006;
    public static double flykF = 0.000335;

    public static  int targetflyVelocity = 0;

    /// Teleop heading
    public static double teleTransferHeading = 0;



    /// Shooter PIDF
    public static double shooter_P =30;
    public static double shooter_I =0;
    public static double shooter_D =0;
    public static double shooter_F =10.9;



    //    public static double kp_turret = 0.008, ki_turret = 0, kd_turret = 0.0008 , kf_turret = -0.003;
    public static double
            kp_turret = 0,//0.04 /*0.0085*/,
            ki_turret = 0,
            kd_turret = 0,//0.0015 ,
            kf_turret = 0,//0.01,
            MAX_X_OFFSET = 0,
            MAX_X_OFFSET_NEAR = 0,
            MAX_X_OFFSET_FAR = 0;

    public static double
            dyna_rangeNear = 25.1,
            dyna_rangeFar = 85,//70,
            dyna_velNear = 1100,//1075,
            dyna_velFar = 1500,//1400,//1250,
            dyna_hoodNear = 0.155,
            dyna_hoodFar = 0.37,//0.32,
            dyna_exp_power_hood = 0.85,//0.8,
            dyna_exp_power_velo = 1.2;//0.8;

    public static double
            dyna_Long_rangeNear = 107,
            dyna_Long_rangeFar = 137,//70,
            dyna_Long_velNear = 1625,//1075,
            dyna_Long_velFar = 1775,//1400,//1250,
            dyna_Long_hoodNear = 0.37,
            dyna_Long_turretNear = -72,//-63 -6.7,  // 65.24
            dyna_Long_turretFar = -60,//-63, // 58.53
            dyna_Long_hoodFar = 0.37,//0.32,
            dyna_Long_exp_power_hood = 1,//0.8,
            dyna_Long_exp_power_velo = 1,//0.8;
            dyna_Long_exp_power_turret = 1;//0.8;

    /*        dyna_rangeNear = 25.1,
            dyna_rangeFar = 85,//70,
            dyna_velNear = 1100,//1075,
            dyna_velFar = 1500,//1400,//1250,
            dyna_hoodNear = 0.155,
            dyna_hoodFar = 0.32,
            dyna_exp_power_hood = 0.85,//0.8,
            dyna_exp_power_velo = 1;//0.8;
    */



    public static  double tagSize = 0;
    public static  int tagId = 2;
    public static double range = 0;


    public static  double GLOBAL_TURRET_OFFSET = 280 ;//153;
    public static double turretOffset = 145;
    public static double turretOffsetBlueNear = 30;

    public static double limelight_height = 8.58;
    public static double limelight_pitch = 90-64.2;
    public static double limelight_forword_dist = 4.77;

    public static double _k_static = 0.03; ;
    public static double _D = 0.15; ;


    /// Limelight targets
    public static double targetX = 0;
    public static double targetY = 0;


    //TELEOP
    public static boolean AUTO = false;
    public static boolean USING_IMU = true;
    public static boolean USE_WHEEL_FEEDFORWARD = false;



    /// Shooter
    public static int farVelocity = 0; //1625;//1600 <--- works
    public static int A_farVelocity = 0; //1675;//1630;//1600 <--- works
    public static int farVelocityHumanPlayer = 0; //1775;//1800;//1825;//1750;//1700;// 1900;//1450;//1450;//1400;//1500;

    public static int Extreme_nearVelocity = 0; //1125;//900;
    public static int nearVelocity =0; //1230;// 1300;//1325;//1350;
    public static int nearVelocitycenter =0; //1325;// 1300;//1325;//1350;


    public static int A_nearVelocity = 0; //1245 /*1325 - 40*/;
    public static int idealVelocity = 0; //500;
    public static int zeroVelocity = 0;
    public static int targetVelocityWithCamera = 0; //1200;





    /// Hood
    public static double hoodExtremeNear = 0.17;
    public static double hoodNear = 0.2;//0.345;//0.61;//0.55;
    public static double hoodNearCenter = 0.345;//0.61;//0.55;

    public static double hoodFar =0.37;//0.36;// 0.46;//0.49;//0.46;//0.7;//0.75;//0.66;//0.85;//0.75;
    public static double A_hoodFar =0.37;//0.4;//0.37;//0.36;// 0.46;//0.49;//0.46;//0.7;//0.75;//0.66;//0.85;//0.75;
    public static double A_hoodNear = 0.345;//0.61;//0.55;
    public static double aprilTagHood = 0.5;
    public static double hoo_far_shoot_mid = 0.49;//0.46;//0.73 ;// 0.74;
    public static double hoo_near_shoot_mid = 0.36;// 0.67;


    /// Turret
//    public static double turretFarAutoRed =0.3;//0.255;// 0.26;
//    public static double turretFarAutoBlue =0.72;//0.25;//0.255;// 0.26;
//    public static double turretNearAuto = 0.3189;
    public static double turretNearAutoRed = -43.5;//-50;
    public static double turretNearAutoBlue = -140;//-144;//-135;

    public static double turretNear = -90;//-15;//30 ;//0.47;//0.5;

    public static double turretFarRed_tele = -30;//0.262;// 0.26;
    public static double turretFarBlue_tele = -61;//0.262;// 0.26;

    public static double turretFarRed_tele_human = -57;//-54;//-62;//-55;//0.262;// 0.26;
    public static double turretFarBlue_tele_human =-128.5;//-127;// -126;//0.262;// 0.26;


    public static double turretFarAutoRed = -64;//-69 + 1.6;//-75;//-70;//0.262;// 0.26;
    public static double turretFarAutoBlue = -116.5 - 3 ;//-110;//0.262;// 0.26;

    public static double aprilTagTurret = 0.5;  // take in deg
    public static double cameraTurret = 0.5; // take in deg

    public static double RED_FAR_AUTO_TURRET_OFFSET = -70;//97;//76;
    public static double RED_NEAR_AUTO_TURRET_OFFSET = 77;//-90;//-4
    public static double BLUE_NEAR_AUTO_TURRET_OFFSET = 31;
    public static double BLUE_FAR_AUTO_TURRET_OFFSET = 40;







    /// Slider state
    public static double sliderIn =0.42;//0.6067;//0.51;//0.3867;//0.4139;// 0.4656;
    public static double sliderOut = 0.2456;//0.685;//0.71;// 0.7539;//0.6772;
    public static double sliderMid = 0.5;


    /// Intake
    public static double frontInPower = 1;
    public static double frontOutPower = -1;
    public static double frontPushPower = 1;
    public static double frontOFF = 0;
    public static double frontslow = 0.5;
    public static double frontReverse = -0.5;
    public static double frontAutoPush = 0.7;

    public static double rearInPower = 1;
    public static double rearOutPower = -1;
    public static double rearPushPower = 1;
    public static double rearOFF = 0;
    public static double rearslow = 0.5;
    public static double rearReverse = -0.5;
    public static double rearAutoPush = 0.8;


    /// Deployer
    public static double deployFront = 0.32;//0.42;//0.5;// 0.17;//0.18;//0.18;// 0.2;// 0.1528;//0.3283;//0.27;//0.302222;//0.38;//0.07;
    public static double deployRear = 0.51;;//0.71;//0.7;//0.39;//0.4;// 0.38;//0.4183;//0.64;//0.53;//0.5522;//0.638;//0.4111;
    public static double deployIdeal = 0.38;//0.6;//0.24;//0.25;


    /// End game
    public static double ptoEngaged = 0.5;
    public static double ptoDisEngaged = 0.5;


    public static double platformUp = 0.1911;
    public static double platformGateOpen = 0.4094;
    public static double platformDown = 0.4094;

    public static double min = -118;
    public static double max = 104;

    /// Gate opener
    public static  double gateOpen = 0.82;
    public static  double gateClose = 0.57;


}
