package org.firstinspires.ftc.teamcode.terravision;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.graphics.Color;
import android.util.Size;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.Circle;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor.Blob;
import org.firstinspires.ftc.vision.opencv.ColorRange;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.List;

public class Vision2 {

    WebcamName camera;
    VisionPortal portal;

    final int IMAGE_WIDTH = 1280;

    int LEFT_BOUND = IMAGE_WIDTH / 3;
    int CENTER_BOUND = 2 * IMAGE_WIDTH / 3;

    double leftArea = 0;
    double centerArea = 0;
    double rightArea = 0;
    public static int purple_erode = 15;
    public static int purple_dilate = 30;

    public static int green_erode = 10;
    public static int green_dilate = 20;

    public static int purple_blur = 5;
    public static int green_blur = 5;
    int region = -1;

    ColorBlobLocatorProcessor purpleLocator =
            new ColorBlobLocatorProcessor.Builder()
                    .setTargetColorRange(ColorRange.ARTIFACT_PURPLE)
                    .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)
                    .setDrawContours(true)
                    .setBlurSize(purple_blur)
                    .setDilateSize(purple_dilate)
                    .setErodeSize(purple_erode)
                    .setMorphOperationType(ColorBlobLocatorProcessor.MorphOperationType.CLOSING)
                    .setCircleFitColor(Color.YELLOW)
                    .build();

    ColorBlobLocatorProcessor greenLocator =
            new ColorBlobLocatorProcessor.Builder()
                    .setTargetColorRange(ColorRange.ARTIFACT_GREEN)
                    .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)
                    .setDrawContours(true)
                    .setBlurSize(green_blur)
                    .setDilateSize(green_dilate)
                    .setErodeSize(green_erode)
                    .setMorphOperationType(ColorBlobLocatorProcessor.MorphOperationType.CLOSING)
                    .setCircleFitColor(Color.YELLOW)
                    .build();

    public void init(HardwareMap hardwareMap){

        camera = hardwareMap.get(WebcamName.class,"Webcam 1");

        portal = new VisionPortal.Builder()
                .addProcessors(purpleLocator,greenLocator)
                .setCamera(camera)
                .setCameraResolution(new Size(1280,720))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .build();
    }

    public void update(){

        leftArea = 0;
        centerArea = 0;
        rightArea = 0;

        List<Blob> purpleBlobs = purpleLocator.getBlobs();
        List<Blob> greenBlobs = greenLocator.getBlobs();

        processBlobs(purpleBlobs);
        processBlobs(greenBlobs);

        region = 0;
        double maxArea = leftArea;

        if(centerArea > maxArea){
            region = 1;
            maxArea = centerArea;
        }

        if(rightArea > maxArea){
            region = 2;
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