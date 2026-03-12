package org.firstinspires.ftc.teamcode.terravision;

public class CameraProjection {

    static double fx = 396.874;
    static double fy = 905.527;
    static double cx = 662.777;
    static double cy = 421.638;

    static double cameraHeight = 27.5; // cm
    static double tilt = Math.toRadians(12);

    public static double[] pixelToGround(double u, double v){

        double x = (u - cx) / fx;
        double y = (v - cy) / fy;

        double cosT = Math.cos(tilt);
        double sinT = Math.sin(tilt);

        double yRot = y*cosT - 1*sinT;
        double zRot = y*sinT + 1*cosT;

        double Z = cameraHeight / (-yRot);
        double X = x * Z;

        return new double[]{X, Z};
    }
}