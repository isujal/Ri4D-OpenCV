package org.firstinspires.ftc.teamcode.paths.FAR;

import android.graphics.Point;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;

public class RedFarPath_Final {


    // 132,12.82
    // 131,8.11
    public static Pose startPose = new Pose(88.250, 6.880, Math.toRadians(0));

    public PathChain SHOOT0;
    public PathChain First2;
    public PathChain Corner;
    public PathChain CornerShoot;

    public PathChain SPIKE2;
    public PathChain SHOOT2;
    public PathChain GATEINTAKE1;
    public PathChain GATESHOOT1;
    public PathChain SPIKE3;
    public PathChain SHOOT3;
    public PathChain RANDOM2;
    public PathChain RSHOOT2;
    public PathChain RANDOM3;
    public PathChain RANDOM32;
    public PathChain RANDOM33;
    public PathChain RANDOM34;
    public PathChain RSHOOT3;
    public PathChain GATEPICK2;
    public PathChain GATESHOOT2;
    public PathChain SPIKE4;
    public PathChain SPIKE41;
    public PathChain SHOOT4;

    public PathChain Random_StraightCorner;
    public PathChain Random_StraightCorner_toshoot;
    public PathChain Random_StraightCorner_extra_push;
    public PathChain Random_StraightCorner_extra_push_toshoot;
    public PathChain Random_near_spike;
    public PathChain Random_near_spike_toshoot;
    public PathChain LEAVE;
    public static PathChain buildDynamicPath(Follower follower, Pose startPose, Pose targetPose){

        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                startPose,
                                targetPose
                        )
                )
                .setLinearHeadingInterpolation(
                        startPose.getHeading(),
                        targetPose.getHeading()
                )
                .build();
    }

    public RedFarPath_Final(Follower follower) {

        SHOOT0 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(88.250, 6.880),

                                new Pose(85.000, 16.606)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();

        SPIKE2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(85.000, 16.606),
                                new Pose(90.860, 67.374),
                                new Pose(126.50, 61.443)
//                                new Pose(128.50, 61.443)
//                                new Pose(130.50, 61.443)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();

        SHOOT2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(130.50, 61.443),
                                new Pose(93.908, 48.244),
                                new Pose(85.000, 16.606)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();

        GATEINTAKE1 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(85.000, 16.606),
                                new Pose(100.134, 49.730),
                                new Pose(129, 59)
//                                new Pose(131, 59.5)
//                                new Pose(129.577, 58)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(27)) // 33

                .build();

        GATESHOOT1 = follower.pathBuilder().addPath(
                        new BezierCurve(
//                                new Pose(131.677, 59.539),
                                new Pose(129.5, 58),
                                new Pose(97.502, 45.218),
                                new Pose(85.000, 16.060)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(27), Math.toRadians(0))

                .build();

        SPIKE3 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(85.000, 16.060),
                                new Pose(93.707, 37.000),
                                new Pose(126.5, 35.585)
//                                new Pose(130.613, 35.649)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();

        SHOOT3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(126.5, 35.649),

                                new Pose(85.000, 16.060)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();

        RANDOM2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(85.000, 16.060),
                                new Pose(141.643, 1.413),
                                new Pose(134.270, 45.456)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(90))

                .build();

        RSHOOT2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(129.5, 40),
                                new Pose(85, 16.606))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0))
                .build();

        RANDOM3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(85, 16.06),

                                new Pose(102.010, 8.75)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-45))

                .build();

        RANDOM32 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(102.010, 8.75),

                                new Pose(131.000, 8.50)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(0))

                .build();

        RANDOM33 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(88.960, 16.606),
                                new Pose(95.305, 2.742),
                                new Pose(124.595, 8.263)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();

        RANDOM34 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(88.960, 16.606),
                                new Pose(95.305, 2.742),
                                new Pose(131, 8.263)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();


        RSHOOT3 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(131.000, 8.500),
                                new Pose(83.988, 5.433),
                                new Pose(85.000, 16.060))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        GATEPICK2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(85.000, 16.060),

                                new Pose(130.677, 59.500)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(32.5))

                .build();

        GATESHOOT2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(130.677, 59.500),

                                new Pose(85.000, 16.060)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(32.5), Math.toRadians(0))

                .build();

        SPIKE4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(85.000, 16.060),new Pose(131.478, 16.0)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();

        SPIKE41 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(131.478, 16.0),

                                new Pose(131.478, 7.500)
//                                new Pose(131.478, 8.580)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();

        SHOOT4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(131.478, 8.080),

                                new Pose(85.000, 16.060)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();

        Random_StraightCorner = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(85.00, 16.060),
                                new Pose(95.305, 2.742),
                                new Pose(124.595, 8.263)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();

        Random_StraightCorner_toshoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(124.5, 8.25),
//                                new Pose(84.614, 42.542),
                                new Pose(85.000, 16.060)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();

        Random_StraightCorner_extra_push = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(85, 16.606),
                                new Pose(95.305, 2.742),
                                new Pose(131, 8.263)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();

        Random_StraightCorner_extra_push_toshoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(131, 8.5),
//                                new Pose(84.614, 42.542),
                                new Pose(85.000, 16.060)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();


        Random_near_spike = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(85.000, 16.060),
                                new Pose(78.794, 23.794),
                                new Pose(133.320, 22.580)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();

        Random_near_spike_toshoot = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(133.320, 22.580),
                                new Pose(79.947, 22.025),
                                new Pose(85.000, 16.060)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();

        LEAVE = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(85, 16.606), new Pose(118.853, 20))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();



                First2 = follower.pathBuilder().addPath(
                                new BezierLine(
                                        new Pose(85.000, 16.000),

                                        new Pose(132.000, 12.820)
                                )
                        ).setConstantHeadingInterpolation(Math.toRadians(0))

                        .build();


                Corner = follower.pathBuilder().addPath(
                                new BezierCurve(
                                        new Pose(132.000, 12.820),
                                        new Pose(115.698, 10.292),
                                        new Pose(132.100, 6.8)
                                )
                        ).setConstantHeadingInterpolation( Math.toRadians(0))

                        .build();

                CornerShoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(132.100, 6.8),

                                new Pose(85.000, 16.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();



    }

}

