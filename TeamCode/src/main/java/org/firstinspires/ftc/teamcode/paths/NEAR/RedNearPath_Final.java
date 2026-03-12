package org.firstinspires.ftc.teamcode.paths.NEAR;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class RedNearPath_Final extends CommandBase {

    public Pose startPose = new Pose(119.328, 127, Math.toRadians(0));


    public PathChain SHOOT0;
    public PathChain SPIKE2;
    public PathChain SHOOT2;
    public PathChain GateIntake10;
    public PathChain GateIntake1;

    public PathChain GateShoo1;
    public PathChain Gate_Touch;
    public PathChain Go_far;
    public PathChain Collect;
    public PathChain GateTouchToShoot;


    public PathChain SPIKE3;
    public PathChain SHOOT3;
    public PathChain SPIKE1;
    public PathChain SHOOT1;
    public PathChain LEAVE;
    public RedNearPath_Final(Follower follower) {
        SHOOT0 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(119.328, 127), new Pose(89.911, 83.743))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        SPIKE2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(89.911, 83.743),
                                new Pose(87.301, 56.224),
                                new Pose(119 +4+3, 59.783)
//                                new Pose(124, 59.783)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        SHOOT2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(120, 59.783),
                                new Pose(89.911, 83.743))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        GateIntake10 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(89.911, 83.743),
                                new Pose(88.02, 66.66),
//                                new Pose(130, 60)  //129.5, 132.5 , 59
                                new Pose(128.5, 60.5)  //129.5, 132.5 , 59
//                                new Pose(130.5, 60)  //133.5 , 59.5
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(26.5)) //26.5, 28.5

                .build();

        GateIntake1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(128.5, 60.5),
                                new Pose(134.5, 60.5)  // 132, 133.5 , 59.5
//                                new Pose(135, 60)  //135
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(26.5), Math.toRadians(27.5)) //28.5

                .build();

        GateShoo1 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(134.5, 60.5),
                                new Pose(110.550, 62.155),
                                new Pose(89.911, 83.743)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(27.5), Math.toRadians(0))
                .build();

        Gate_Touch = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(89.911, 83.743),
                                new Pose(101.621, 63.857),
                                new Pose(126.5, 62.000) //125.500
//                                new Pose(123.500, 62.000)
                        )
//                        new BezierLine(
//                                new Pose(89.911, 83.743),
//
//                                new Pose(125.500, 63)
////                                new Pose(127.500, 64.5)
//                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();

        Go_far = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(125.500, 62),

                                new Pose(131 +1.5, 55.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(32.5))

                .build();



        Collect = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(131 + 1.5, 55),

                                new Pose(131 + 1.5, 58)
                        )
                ).setConstantHeadingInterpolation( Math.toRadians(32.5))

                .build();

        GateTouchToShoot = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(131, 58),
                                new Pose(110.550, 62.155),
                                new Pose(89.911, 83.743)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(32.5), Math.toRadians(0))
                .build();

        SPIKE3 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(89.911, 83.700),
                                new Pose(89.748, 28.834),
                                new Pose(127.5, 35.208) // 129.714
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();

        SHOOT3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(127.5, 35.208),

                                new Pose(89.911, 83.700)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();

        SPIKE1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(89.911, 83.743),
                                new Pose(124.784 +2, 83.743))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        SHOOT1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(124.784, 83.743), new Pose(89.911, 83.743))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();


        LEAVE = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(89.911, 83.743), new Pose(124.784, 83))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

    }
}
