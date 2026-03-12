package org.firstinspires.ftc.teamcode.paths.FAR;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class BlueFarPath_Final {

    public Pose startPose = new Pose(55.750, 6.880, Math.toRadians(0)); // 144 - 88.250

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

    public BlueFarPath_Final(Follower follower) {

        SHOOT0 = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(55.750, 6.880),
                        new Pose(59.000, 16.606)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        SPIKE2 = follower.pathBuilder().addPath(
                new BezierCurve(
                        new Pose(59.000, 16.606),
                        new Pose(53.140, 67.374),
                        new Pose(17, 61.443)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        SHOOT2 = follower.pathBuilder().addPath(
                new BezierCurve(
                        new Pose(15.500, 61.443),
                        new Pose(50.092, 48.244),
                        new Pose(59.000, 16.606)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        GATEINTAKE1 = follower.pathBuilder().addPath(
                new BezierCurve(
                        new Pose(59.000, 16.606),
                        new Pose(43.866, 49.730),
                        new Pose(19, 59.000)
//                        new Pose(17.500, 59.000)
                )
        ).setLinearHeadingInterpolation(0, Math.toRadians(-25.5)).build();

        GATESHOOT1 = follower.pathBuilder().addPath(
                new BezierCurve(
                        new Pose(19.00, 58.000),
                        new Pose(46.498, 45.218),
                        new Pose(59.000, 16.060)
                )
        ).setLinearHeadingInterpolation(Math.toRadians(-25.5), 0).build();

        SPIKE3 = follower.pathBuilder().addPath(
                new BezierCurve(
                        new Pose(59.000, 16.060),
                        new Pose(50.293, 37.000),
                        new Pose(17.500, 35.585)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        SHOOT3 = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(17.500, 35.649),
                        new Pose(59.000, 16.060)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        RANDOM3 = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(59.000, 16.060),
                        new Pose(41.990, 8.750)
                )
        ).setLinearHeadingInterpolation(0, Math.toRadians(-45)).build();

        RANDOM32 = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(41.990, 8.750),
                        new Pose(13.000, 8.500)
                )
        ).setLinearHeadingInterpolation(Math.toRadians(-45), 0).build();

        RANDOM33 = follower.pathBuilder().addPath(
                new BezierCurve(
                        new Pose(55.040, 16.606),
                        new Pose(48.695, 2.742),
                        new Pose(19.405, 8.263)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        RANDOM34 = follower.pathBuilder().addPath(
                new BezierCurve(
                        new Pose(55.040, 16.606),
                        new Pose(48.695, 2.742),
                        new Pose(13.000, 8.263)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        RSHOOT3 = follower.pathBuilder().addPath(
                new BezierCurve(
                        new Pose(13.000, 8.500),
                        new Pose(60.012, 5.433),
                        new Pose(59.000, 16.060)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        GATEPICK2 = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(59.000, 16.060),
                        new Pose(13.323, 59.500)
                )
        ).setLinearHeadingInterpolation(0, Math.toRadians(32.5)).build();

        GATESHOOT2 = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(13.323, 59.500),
                        new Pose(59.000, 16.060)
                )
        ).setLinearHeadingInterpolation(Math.toRadians(32.5), 0).build();

        SPIKE4 = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(59.000, 16.060),
                        new Pose(12.522, 16.000)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        SPIKE41 = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(12.522, 16.000),
                        new Pose(12.522, 7.500)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        SHOOT4 = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(12.522, 8.080),
                        new Pose(59.000, 16.060)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        Random_StraightCorner = follower.pathBuilder().addPath(
                new BezierCurve(
                        new Pose(55.040, 16.606),
                        new Pose(48.695, 2.742),
                        new Pose(19.405, 8.263)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        Random_StraightCorner_toshoot = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(19.500, 8.500),
                        new Pose(59.000, 16.060)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        Random_StraightCorner_extra_push = follower.pathBuilder().addPath(
                new BezierCurve(
                        new Pose(59.000, 16.606),
                        new Pose(48.695, 2.742),
                        new Pose(13.000, 8.263)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        Random_StraightCorner_extra_push_toshoot = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(13.000, 8.500),
                        new Pose(59.000, 16.060)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        Random_near_spike = follower.pathBuilder().addPath(
                new BezierCurve(
                        new Pose(59.000, 16.060),
                        new Pose(65.206, 23.794),
                        new Pose(10.680, 22.580)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        Random_near_spike_toshoot = follower.pathBuilder().addPath(
                new BezierCurve(
                        new Pose(10.680, 22.580),
                        new Pose(64.053, 22.025),
                        new Pose(59.000, 16.060)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        LEAVE = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(59.5, 16.606), new Pose(25, 20))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();


        First2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(59, 16.000),
//                                new Pose(55.000, 16.000),

                                new Pose(12, 12.820)
//                                new Pose(8.000, 12.820)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Corner = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(12, 12.820),
                                new Pose(29, 10.292),
                                new Pose(12, 6.8)
                        )
                ).setConstantHeadingInterpolation( Math.toRadians(0))

                .build();

        CornerShoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(12, 6.8),

                                new Pose(59, 16.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

    }
}
