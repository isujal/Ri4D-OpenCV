//package org.firstinspires.ftc.teamcode.terravision;
//
//import android.graphics.Color;
//import android.hardware.Camera;
//import android.util.Size;
//
//import com.acmerobotics.dashboard.config.Config;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.vision.VisionPortal;
//import org.firstinspires.ftc.vision.opencv.Circle;
//import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
//import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor.Blob;
//import org.firstinspires.ftc.vision.opencv.ColorRange;
//import org.firstinspires.ftc.vision.opencv.ImageRegion;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//@Config
//@TeleOp
//public class Vision extends LinearOpMode {
//
//    // loading zone ROI
//    public static double top = 1;
//    public static double right = 1;
//    public static double bottom = -1;
//
//    List<DetectedCircle> detectedCircles = new ArrayList<>();  // prioritised area list based on the area
//
//
//    // image constants
//    final int IMAGE_WIDTH = 1280;
//    final int IMAGE_WIDTH_MIDDLE = IMAGE_WIDTH / 2;
//
//    // loading zone
//    final int ROI_WIDTH = (int) (IMAGE_WIDTH_MIDDLE * right * 2);
//    final int ROI_FOURTH = ROI_WIDTH / 4;
//    final int DIST_LEFT_ROI = (int) (IMAGE_WIDTH_MIDDLE * (1 - right));
//    final int LEFT_BOUND = DIST_LEFT_ROI + ROI_FOURTH;
//    final int MIDDLE_BOUND = DIST_LEFT_ROI + ROI_FOURTH + ROI_FOURTH;
//    final int RIGHT_BOUND = DIST_LEFT_ROI + ROI_FOURTH + ROI_FOURTH + ROI_FOURTH;
//
//    int region = -1; // 0 = left, 1 = middle, 2 = right for the indexing
//
//    int region0AreaPurple = 0;
//    int region1AreaPurple = 0;
//    int region2AreaPurple = 0;
//    int region3AreaPurple = 0;
//
//    int region0AreaGreen = 0;
//    int region1AreaGreen = 0;
//    int region2AreaGreen = 0;
//    int region3AreaGreen = 0;
//
//    int biggestBlobAreaPurple = 0;
//    int biggestBlobAreaGreen = 0;
//
//    int purpleListLength = 0;
//    int greenListLength = 0;
//    public static int purple_erode = 15;
//    public static int purple_dialate= 30;
//    public static int green_erode = 10;
//    public static int green_dialate = 20;
//    public static int purple_blur = 5;
//    public static int green_blur = 5;
//
//
//
//
//    WebcamName camera;
//
//    ColorBlobLocatorProcessor purpleLocator = new ColorBlobLocatorProcessor.Builder()
//            .setTargetColorRange(ColorRange.ARTIFACT_PURPLE)   // use a predefined color match
//            .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)
//            .setRoi(ImageRegion.asUnityCenterCoordinates(-right, top, right, bottom))
//            .setDrawContours(true)   // show contours on the Stream Preview
//            .setBoxFitColor(0)       // disable the drawing of rectangles
//            .setCircleFitColor(Color.rgb(255, 255, 0)) // draw a circle
//            .setBlurSize(purple_blur)          // smooth the transitions between different colors in image
//
//            // the following options have been added to fill in perimeter holes.
//            .setDilateSize(purple_dialate)       // expand blobs to fill any divots on the edges
//            .setErodeSize(purple_erode)        // shrink blobs back to original size
//            .setMorphOperationType(ColorBlobLocatorProcessor.MorphOperationType.CLOSING)
//
//            .build();
//
//    ColorBlobLocatorProcessor greenLocator = new ColorBlobLocatorProcessor.Builder()
//            .setTargetColorRange(ColorRange.ARTIFACT_GREEN)   // use a predefined color match
//            .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)
//            .setRoi(ImageRegion.asUnityCenterCoordinates(-right, top, right, bottom))
//            .setDrawContours(true)   // show contours on the Stream Preview
//            .setBoxFitColor(0)       // disable the drawing of rectangles
//            .setCircleFitColor(Color.rgb(255, 255, 0)) // draw a circle
//            .setBlurSize(green_blur)          // smooth the transitions between different colors in image
//
//            // the following options have been added to fill in perimeter holes.
//            .setDilateSize(green_dialate)       // expand blobs to fill any divots on the edges
//            .setErodeSize(green_erode)        // shrink blobs back to original size
//            .setMorphOperationType(ColorBlobLocatorProcessor.MorphOperationType.CLOSING)
//
//            .build();
//
//    public VisionPortal portal;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        // build portal
//        camera = hardwareMap.get(WebcamName.class, "Webcam 1");
//
//        portal = new VisionPortal.Builder()
//                .addProcessors(purpleLocator, greenLocator)
//                .setCameraResolution(new Size(640, 480))
//                .setCamera(camera)
//                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
//                .build();
//
//        waitForStart();
//        while (opModeIsActive()) {
//            detectedCircles.clear();
//
//            // reset the areas per region
//            region0AreaPurple = 0;
//            region1AreaPurple = 0;
//            region2AreaPurple = 0;
//            region3AreaPurple = 0;
//            region0AreaGreen = 0;
//            region1AreaGreen = 0;
//            region2AreaGreen = 0;
//            region3AreaGreen = 0;
//
//            biggestBlobAreaPurple = 0;
//            biggestBlobAreaGreen = 0;
//
//            // retrieve blob lists for this frame from locators
//            List<Blob> purpleBlobs = purpleLocator.getBlobs();
//            List<Blob> greenBlobs = greenLocator.getBlobs();
//
//
//            // if required for debugging - e.g., not detecting anything
//            purpleListLength = purpleBlobs.size();
//            greenListLength = greenBlobs.size();
//
//            ColorBlobLocatorProcessor.Util.filterByCriteria(
//                    ColorBlobLocatorProcessor.BlobCriteria.BY_CONTOUR_AREA,
//                    300, 500_000, purpleBlobs);  // filter out very small blobs.
//
//            ColorBlobLocatorProcessor.Util.filterByCriteria(
//                    ColorBlobLocatorProcessor.BlobCriteria.BY_CONTOUR_AREA,
//                    300, 500_000, greenBlobs);  // filter out very small blobs.
//
//            // removed due to blobs being non circular detecting a group of artifacts
////        ColorBlobLocatorProcessor.Util.filterByCriteria(
////                ColorBlobLocatorProcessor.BlobCriteria.BY_CIRCULARITY,
////                0.6, 1, purpleBlobs);     // filter out non-circular blobs.
////
////        ColorBlobLocatorProcessor.Util.filterByCriteria(
////                ColorBlobLocatorProcessor.BlobCriteria.BY_CIRCULARITY,
////                0.6, 1, greenBlobs);     // filter out non-circular blobs.
//
//            for (Blob blob : purpleBlobs) {
//
//                Circle circle = blob.getCircle();
//
//                DetectedCircle detected = new DetectedCircle(
//                        circle.getX(),
//                        circle.getY(),
//                        circle.getRadius()
//                );
//
//                detectedCircles.add(detected);
//            }
//
//
//            for (Blob blob : greenBlobs) {
//
//                Circle circle = blob.getCircle();
//
//                DetectedCircle detected = new DetectedCircle(
//                        circle.getX(), //7.87
//                        circle.getY(), // 19.23
//                        circle.getRadius() // 16.7
//                );
//
//                detectedCircles.add(detected);
//            }
//
//            Collections.sort(detectedCircles, (a, b) -> Double.compare(b.area, a.area));
//            DetectedCircle targetCircle = null;
//
//            if(!detectedCircles.isEmpty()){
//                targetCircle = detectedCircles.get(0);
//            }
//
//
//            for (Blob blob : purpleBlobs) {
//                Circle blobCircle = blob.getCircle();
//                double circularity = blob.getCircularity();
//                float radius = blobCircle.getRadius();
//                int x = (int) blobCircle.getX();
//                int y = (int) blobCircle.getY();
//                int area = (int) (Math.PI * radius * radius);
//
//                biggestBlobAreaPurple = Math.max(biggestBlobAreaPurple, area);
//
//                if (x <= LEFT_BOUND) {
//                    region0AreaPurple = Math.max(region0AreaPurple, area);
//                } else if (x <= MIDDLE_BOUND) {
//                    region1AreaPurple = Math.max(region1AreaPurple, area);
//                } else if (x <= RIGHT_BOUND){
//                    region2AreaPurple = Math.max(region2AreaPurple, area);
//                } else {
//                    region3AreaPurple = Math.max(region3AreaPurple, area);
//                }
//            }
//
//            for (Blob blob : greenBlobs) {
//                Circle blobCircle = blob.getCircle();
//                double circularity = blob.getCircularity();
//                float radius = blobCircle.getRadius();
//                int x = (int) blobCircle.getX();
//                int y = (int) blobCircle.getY();
//                int area = (int) (Math.PI * radius * radius);
//
//                biggestBlobAreaGreen = Math.max(biggestBlobAreaGreen, area);
//
//                if (x <= LEFT_BOUND) {
//                    region0AreaGreen = Math.max(region0AreaGreen, area);
//                } else if (x <= MIDDLE_BOUND) {
//                    region1AreaGreen = Math.max(region1AreaGreen, area);
//                } else if (x <= RIGHT_BOUND){
//                    region2AreaGreen = Math.max(region2AreaGreen, area);
//                } else {
//                    region3AreaGreen = Math.max(region3AreaGreen, area);
//                }
//            }
//
//            int region0 = region0AreaPurple + region0AreaGreen;
//            int region1 = region1AreaPurple + region1AreaGreen;
//            int region2 = region2AreaPurple + region2AreaGreen;
//            int region3 = region3AreaPurple + region3AreaGreen;
//
//            region = 0;
//            int max = region0;
//
//            if (region1 > max) {
//                region = 1;
//                max = region1;
//            }
//            if (region2 > max) {
//                region = 2;
//                max = region2;
//            }
//            if (region3 > max) {
//                region = 3;
//            }
//
//            if (purpleBlobs.isEmpty() && greenBlobs.isEmpty()) region = -1;
//
//            double distance = estimateDistance(targetCircle.radius);
//            double pixelError = targetCircle.x - (IMAGE_WIDTH/2);
//            double angle = pixelError * (72.0 / IMAGE_WIDTH);
//
///*
//
//
//camera tilt angle = 12 degrees
//camera height from the ground level = 11 inches or 27.5 cm
//camera intrinsics :
//    fx = 396.874
//    cy = 421.638
//    fy = 905.527
//    cx = 662.777
//
// */
//
//            telemetry.addData("Circle Count", detectedCircles.size());
//            telemetry.addData("Circle Area Green", biggestBlobAreaGreen);
//            telemetry.addData("Circle Area Purple",biggestBlobAreaPurple);
//            if(targetCircle != null){
//                telemetry.addData("Target X", targetCircle.x);
//                telemetry.addData("Target Y", targetCircle.y);
//                telemetry.addData("Target Area", targetCircle.area);
//            }
//            telemetry.addData("distance of the prioritised circle", distance);
////            telemetry.addData("Circle Area (Prioristised)", detectedCircles.add((greenBlobs.size())));
//            telemetry.update();
//        }
//    }
//
//    public int getLoadingRegion() {
//        return region;
//    }
//    public double estimateDistance(double radius){
//        return 1500 / radius;
//    }
//}



//
//package org.firstinspires.ftc.teamcode.terravision;
//
//import android.graphics.Color;
//import android.util.Size;
//
//import com.acmerobotics.dashboard.config.Config;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.vision.VisionPortal;
//import org.firstinspires.ftc.vision.opencv.Circle;
//import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
//import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor.Blob;
//import org.firstinspires.ftc.vision.opencv.ColorRange;
//import org.firstinspires.ftc.vision.opencv.ImageRegion;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@Config
//@TeleOp
//public class Vision extends LinearOpMode {
//
//    public static double top = 1;
//    public static double right = 1;
//    public static double bottom = -1;
//
//    public static int purple_erode = 15;
//    public static int purple_dialate= 30;
//    public static int green_erode = 10;
//    public static int green_dialate = 20;
//
//    public static int purple_blur = 5;
//    public static int green_blur = 5;
//    public static double startX = 0;
//    public static double startY = 0;
//    public static double startHeadingDeg = 0;
//    WebcamName camera;
//    public VisionPortal portal;
//
//    List<DetectedCircle> detectedCircles = new ArrayList<>();
//
//    DetectedCircle lockedCircle = null;
//    boolean circleLocked = false;
//
//    public double targetOffsetX = 0;
//    public double targetForwardZ = 0;
//
//    ColorBlobLocatorProcessor purpleLocator =
//            new ColorBlobLocatorProcessor.Builder()
//                    .setTargetColorRange(ColorRange.ARTIFACT_PURPLE)
//                    .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)
//                    .setRoi(ImageRegion.asUnityCenterCoordinates(-right, top, right, bottom))
//                    .setDrawContours(true)
//                    .setCircleFitColor(Color.rgb(255,255,0))
//                    .setBlurSize(purple_blur)
//                    .setDilateSize(purple_dialate)
//                    .setErodeSize(purple_erode)
//                    .setMorphOperationType(ColorBlobLocatorProcessor.MorphOperationType.CLOSING)
//                    .build();
//
//    ColorBlobLocatorProcessor greenLocator =
//            new ColorBlobLocatorProcessor.Builder()
//                    .setTargetColorRange(ColorRange.ARTIFACT_GREEN)
//                    .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)
//                    .setRoi(ImageRegion.asUnityCenterCoordinates(-right, top, right, bottom))
//                    .setDrawContours(true)
//                    .setCircleFitColor(Color.rgb(255,255,0))
//                    .setBlurSize(green_blur)
//                    .setDilateSize(green_dialate)
//                    .setErodeSize(green_erode)
//                    .setMorphOperationType(ColorBlobLocatorProcessor.MorphOperationType.CLOSING)
//                    .build();
//
//    @Override
//    public void runOpMode() {
//
//        camera = hardwareMap.get(WebcamName.class, "Webcam 1");
//
//        portal = new VisionPortal.Builder()
//                .addProcessors(purpleLocator, greenLocator)
//                .setCameraResolution(new Size(640,480))
//                .setCamera(camera)
//                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
//                .build();
//
//        waitForStart();
//
//        while(opModeIsActive()){
//
//            detectedCircles.clear();
//
//            List<Blob> purpleBlobs = purpleLocator.getBlobs();
//            List<Blob> greenBlobs = greenLocator.getBlobs();
//
//            ColorBlobLocatorProcessor.Util.filterByCriteria(
//                    ColorBlobLocatorProcessor.BlobCriteria.BY_CONTOUR_AREA,
//                    300, 500000, purpleBlobs);
//
//            ColorBlobLocatorProcessor.Util.filterByCriteria(
//                    ColorBlobLocatorProcessor.BlobCriteria.BY_CONTOUR_AREA,
//                    300, 500000, greenBlobs);
//
//            for(Blob blob : purpleBlobs){
//
//                Circle c = blob.getCircle();
//
//                DetectedCircle dc = new DetectedCircle(
//                        c.getX(),
//                        c.getY(),
//                        c.getRadius()
//                );
//
//                detectedCircles.add(dc);
//            }
//
//            for(Blob blob : greenBlobs){
//
//                Circle c = blob.getCircle();
//
//                DetectedCircle dc = new DetectedCircle(
//                        c.getX(),
//                        c.getY(),
//                        c.getRadius()
//                );
//
//                detectedCircles.add(dc);
//            }
//
//            Collections.sort(detectedCircles,
//                    (a,b)->Double.compare(b.area,a.area));
//
//            telemetry.addData("Circle Count", detectedCircles.size());
//
//            if(!circleLocked && !detectedCircles.isEmpty()){
//
//                lockedCircle = detectedCircles.get(0);
//                circleLocked = true;
//            }
//
//            if(lockedCircle != null){
//
//                double groundPixelY =
//                        lockedCircle.y + lockedCircle.radius;
//
//                double[] ground =
//                        CameraProjection.pixelToGround(
//                                lockedCircle.x,
//                                groundPixelY
//                        );
//
//                targetOffsetX = ground[0];
//                targetForwardZ = ground[1];
//
//                double heading = Math.toRadians(startHeadingDeg);
//
//                double fieldX =
//                        startX +
//                                targetForwardZ * Math.cos(heading) -
//                                targetOffsetX * Math.sin(heading);
//
//                double fieldY =
//                        startY +
//                                targetForwardZ * Math.sin(heading) +
//                                targetOffsetX * Math.cos(heading);
//
//
//                telemetry.addLine("----- ROBOT RELATIVE -----");
//                telemetry.addData("Forward (cm)", targetForwardZ);
//                telemetry.addData("Offset (cm)", targetOffsetX);
//
//                telemetry.addLine("----- FIELD COORDINATE -----");
//                telemetry.addData("Field X", fieldX);
//                telemetry.addData("Field Y", fieldY);
//                telemetry.addLine("----------------------------");
//
//                telemetry.addData("Locked Circle X", lockedCircle.x);
//                telemetry.addData("Locked Circle Y", lockedCircle.y);
//                telemetry.addData("Circle Area", lockedCircle.area);
//
//                telemetry.addData("Offset X cm", targetOffsetX);
//                telemetry.addData("Forward Z cm", targetForwardZ);
//            }
//
//            telemetry.update();
//        }
//    }
//
//    public double getTargetForwardDistance(){
//        return targetForwardZ;
//    }
//
//    public double getTargetOffset(){
//        return targetOffsetX;
//    }
//
//}
//
//
//



package org.firstinspires.ftc.teamcode.terravision;

import android.graphics.Color;
import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.Circle;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor.Blob;
import org.firstinspires.ftc.vision.opencv.ColorRange;

import java.util.List;

@Config
@TeleOp(name="Vision Region Debug")
public class Vision extends LinearOpMode {

    WebcamName camera;
    VisionPortal portal;

    final int IMAGE_WIDTH = 1280;

    int LEFT_BOUND = IMAGE_WIDTH / 3;
    int CENTER_BOUND = 2 * IMAGE_WIDTH / 3;

    double leftArea = 0;
    double centerArea = 0;
    double rightArea = 0;

    int region = -1;

    public static int purple_erode = 15;
    public static int purple_dilate = 30;

    public static int green_erode = 10;
    public static int green_dilate = 20;

    public static int purple_blur = 5;
    public static int green_blur = 5;

    ColorBlobLocatorProcessor purpleLocator =
            new ColorBlobLocatorProcessor.Builder()
                    .setTargetColorRange(ColorRange.ARTIFACT_PURPLE)
                    .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)
                    .setDrawContours(true)
                    .setCircleFitColor(Color.YELLOW)
                    .setBlurSize(purple_blur)
                    .setDilateSize(purple_dilate)
                    .setErodeSize(purple_erode)
                    .setMorphOperationType(ColorBlobLocatorProcessor.MorphOperationType.CLOSING)
                    .build();

    ColorBlobLocatorProcessor greenLocator =
            new ColorBlobLocatorProcessor.Builder()
                    .setTargetColorRange(ColorRange.ARTIFACT_GREEN)
                    .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)
                    .setDrawContours(true)
                    .setCircleFitColor(Color.YELLOW)
                    .setBlurSize(green_blur)
                    .setDilateSize(green_dilate)
                    .setErodeSize(green_erode)
                    .setMorphOperationType(ColorBlobLocatorProcessor.MorphOperationType.CLOSING)
                    .build();

    @Override
    public void runOpMode() {

        camera = hardwareMap.get(WebcamName.class,"Webcam 1");

        portal = new VisionPortal.Builder()
                .addProcessors(purpleLocator, greenLocator)
                .setCamera(camera)
                .setCameraResolution(new Size(1280,720))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .build();

        telemetry.addLine("Vision Ready");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()) {

            leftArea = 0;
            centerArea = 0;
            rightArea = 0;

            List<Blob> purpleBlobs = purpleLocator.getBlobs();
            List<Blob> greenBlobs = greenLocator.getBlobs();

            processBlobs(purpleBlobs);
            processBlobs(greenBlobs);

            region = 0;
            double maxArea = leftArea;

            if(centerArea > maxArea) {
                region = 1;
                maxArea = centerArea;
            }

            if(rightArea > maxArea) {
                region = 2;
            }

            telemetry.addLine("----- REGION AREA -----");
            telemetry.addData("Left Area", leftArea);
            telemetry.addData("Center Area", centerArea);
            telemetry.addData("Right Area", rightArea);

            telemetry.addLine("----- TARGET REGION -----");

            if(region == 0) telemetry.addLine("LEFT");
            if(region == 1) telemetry.addLine("CENTER");
            if(region == 2) telemetry.addLine("RIGHT");

            telemetry.update();
        }
    }

    private void processBlobs(List<Blob> blobs){

        for(Blob blob : blobs){

            Circle circle = blob.getCircle();

            double x = circle.getX();
            double radius = circle.getRadius();

            double area = Math.PI * radius * radius;

            if(area < 500) continue;

            if(x < LEFT_BOUND){

                leftArea += area;

            }
            else if(x < CENTER_BOUND){

                centerArea += area;

            }
            else{

                rightArea += area;

            }
        }
    }

    public int getRegion(){
        return region;
    }
}




