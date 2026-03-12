package org.firstinspires.ftc.teamcode.paths.NEAR;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class BlueNearPath_Final extends CommandBase {

    // 144 - 119.328 = 24.672
    public Pose startPose = new Pose(24.672, 127, Math.toRadians(0));

    public PathChain SHOOT0;
    public PathChain SPIKE2; //mid
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

    public BlueNearPath_Final(Follower follower) {

        SHOOT0 = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(24.672, 127),
                        new Pose(54.089, 83.743)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        SPIKE2 = follower.pathBuilder().addPath(
                new BezierCurve(
                        new Pose(54.089, 83.743),
                        new Pose(56.699, 56.224),
                        new Pose(23.234-4-3, 59.783)
//                        new Pose(20.234, 59.783)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        SHOOT2 = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(23.5, 59.783),
                        new Pose(54.089, 83.743)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        GateIntake10 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(54.089, 83.743),
                                new Pose(55.987, 66.662),
                                new Pose(14.00, 59.50) //14.5
//                                new Pose(12.52, 59.5)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-26.5)) //-23

                .build();

        GateIntake1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(14.00, 59.5),
                                new Pose(9, 59.50) // 9.5
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(-26.53), Math.toRadians(-26.53))

                .build();

//        GateIntake1 = follower.pathBuilder().addPath(
//                new BezierCurve(
//                        new Pose(54.089, 83.743),
//                        new Pose(37.720, 59.545),
//                        new Pose(17.50, 59.00)
////                        new Pose(12.500, 59.000)
//                )
//        ).setLinearHeadingInterpolation(0, Math.toRadians(-23)).build(); //-28.5

        GateShoo1 = follower.pathBuilder().addPath(
                new BezierCurve(
                        new Pose(9, 59.50),
                        new Pose(33.450, 62.155),
                        new Pose(54.089, 83.743)
                )
        ).setLinearHeadingInterpolation(Math.toRadians(-26.53), 0).build();

        Gate_Touch = follower.pathBuilder().addPath(
                new BezierCurve(
                        new Pose(54.089, 83.743),
                        new Pose(42.379, 63.857),
                        new Pose(18.00, 62.00)
//        new Pose(19.000, 62.000)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        Go_far = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(18.500, 62),
                        new Pose(12.000 -1.5, 55.000)
                )
        ).setLinearHeadingInterpolation(0, Math.toRadians(-32.5)).build();


        ///  todo edit
        Collect = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(12.000 -1.5, 55.000),
                        new Pose(12 -1.5, 58)
                )
        ).setConstantHeadingInterpolation(Math.toRadians(-32.5)).build();

        GateTouchToShoot = follower.pathBuilder().addPath(
                new BezierCurve(
                        new Pose(13.000, 55),
                        new Pose(33.450, 62.155),
                        new Pose(54.089, 83.743)
                )
        ).setLinearHeadingInterpolation(Math.toRadians(-32.50), 0).build();

        SPIKE3 = follower.pathBuilder().addPath(
                new BezierCurve(
                        new Pose(54.089, 83.700),
                        new Pose(54.252, 28.834),
                        new Pose(14.286, 35.208)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        SHOOT3 = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(14.286, 35.208),
                        new Pose(54.089, 83.700)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        SPIKE1 = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(54.089, 83.743),
                        new Pose(19.216-2, 83.743)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        SHOOT1 = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(19.216, 83.743),
                        new Pose(54.089, 83.743)
                )
        ).setLinearHeadingInterpolation(0, 0).build();

        LEAVE = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(54.089, 83.743),
                        new Pose(19.216, 83.000)
                )
        ).setLinearHeadingInterpolation(0, 0).build();
    }
}
