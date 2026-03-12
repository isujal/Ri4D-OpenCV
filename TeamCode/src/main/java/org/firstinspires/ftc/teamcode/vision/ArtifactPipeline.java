//package org.firstinspires.ftc.teamcode.vision;
//
//import com.acmerobotics.dashboard.config.Config;
//
//import org.opencv.core.*;
//import org.opencv.imgproc.Imgproc;
//import org.openftc.easyopencv.OpenCvPipeline;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ArtifactPipeline extends OpenCvPipeline {
//
//    public enum Position {
//        LEFT, CENTER, RIGHT, NONE
//    }
//
//    private Position position = Position.NONE;
//
//    private Mat hsv = new Mat();
//    private Mat mask = new Mat();
//
//    // Tune these for your artifact
//
//    public static Scalar lower = new Scalar(20, 100, 100);
//    public static Scalar upper = new Scalar(30, 255, 255);
//
//    @Override
//    public Mat processFrame(Mat input) {
//
//        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);
//        Core.inRange(hsv, lower, upper, mask);
//
//        int width = mask.cols();
//        int height = mask.rows();
//        int third = width / 3;
//
//        Rect leftRect = new Rect(0, 0, third, height);
//        Rect centerRect = new Rect(third, 0, third, height);
//        Rect rightRect = new Rect(2 * third, 0, third, height);
//
//        Mat left = mask.submat(leftRect);
//        Mat center = mask.submat(centerRect);
//        Mat right = mask.submat(rightRect);
//
//        int leftCount = Core.countNonZero(left);
//        int centerCount = Core.countNonZero(center);
//        int rightCount = Core.countNonZero(right);
//
//        int threshold = 500;
//
//        if (leftCount < threshold &&
//            centerCount < threshold &&
//            rightCount < threshold) {
//            position = Position.NONE;
//        }
//        else if (leftCount > centerCount && leftCount > rightCount) {
//            position = Position.LEFT;
//        }
//        else if (centerCount > rightCount) {
//            position = Position.CENTER;
//        }
//        else {
//            position = Position.RIGHT;
//        }
//
//        Imgproc.rectangle(input, leftRect, new Scalar(255,0,0),2);
//        Imgproc.rectangle(input, centerRect, new Scalar(0,255,0),2);
//        Imgproc.rectangle(input, rightRect, new Scalar(0,0,255),2);
//
//        return input;
//    }
//
//    public Position getPosition() {
//        return position;
//    }
//}
//
//
//





package org.firstinspires.ftc.teamcode.vision;

import com.acmerobotics.dashboard.config.Config;

import org.opencv.core.*;
        import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;
import  com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

@Config
public class ArtifactPipeline extends OpenCvPipeline {

    // ---------------- DASHBOARD TUNABLE HSV ----------------
    public static double lowH = 20;
    public static double highH = 35;

    public static double lowS = 100;
    public static double highS = 255;

    public static double lowV = 100;
    public static double highV = 255;

    // ---------------- AREA FILTER ----------------
    public static double minArea = 800;

    // ---------------- MORPH SETTINGS ----------------
    public static int blurSize = 5;
    public static int kernelSize = 5;

    // ---------------- STABILITY ----------------
    private int stableCounter = 0;
    private Position lastPosition = Position.NONE;
    private Position stablePosition = Position.NONE;

    public enum Position {
        LEFT, CENTER, RIGHT, NONE
    }

    private Mat hsv = new Mat();
    private Mat mask = new Mat();
    private Mat hierarchy = new Mat();

    @Override
    public Mat processFrame(Mat input) {


        // 1️⃣ Convert to HSV
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

        // 2️⃣ Threshold
        Scalar lower = new Scalar(lowH, lowS, lowV);
        Scalar upper = new Scalar(highH, highS, highV);
        Core.inRange(hsv, lower, upper, mask);

        // 3️⃣ Blur
        Imgproc.GaussianBlur(mask, mask, new Size(blurSize, blurSize), 0);

        // 4️⃣ Morphology
        Mat kernel = Imgproc.getStructuringElement(
                Imgproc.MORPH_RECT,
                new Size(kernelSize, kernelSize));

        Imgproc.morphologyEx(mask, mask,
                Imgproc.MORPH_OPEN, kernel);

        Imgproc.morphologyEx(mask, mask,
                Imgproc.MORPH_CLOSE, kernel);

        // 5️⃣ Contours
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(mask, contours, hierarchy,
                Imgproc.RETR_EXTERNAL,
                Imgproc.CHAIN_APPROX_SIMPLE);

        double maxArea = 0;
        Rect bestRect = null;

        for (MatOfPoint contour : contours) {

            double area = Imgproc.contourArea(contour);

            if (area > minArea && area > maxArea) {
                maxArea = area;
                bestRect = Imgproc.boundingRect(contour);
            }
        }

        Position currentPosition = Position.NONE;

        if (bestRect != null) {

            Imgproc.rectangle(input, bestRect,
                    new Scalar(0, 255, 0), 3);

            int centerX = bestRect.x + bestRect.width / 2;
            int width = input.width();

            if (centerX < width / 3)
                currentPosition = Position.LEFT;
            else if (centerX < 2 * width / 3)
                currentPosition = Position.CENTER;
            else
                currentPosition = Position.RIGHT;
        }

        // 6️⃣ Stability Filter
        if (currentPosition == lastPosition) {
            stableCounter++;
        } else {
            stableCounter = 0;
        }

        if (stableCounter > 3) {
            stablePosition = currentPosition;
        }

        lastPosition = currentPosition;

        return input;
    }

    public Position getPosition() {
        return stablePosition;
    }
}
