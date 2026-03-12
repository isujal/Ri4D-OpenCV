package org.firstinspires.ftc.teamcode.vision;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.MAGENTA;
import static org.firstinspires.ftc.teamcode.vision.ExampleProcessor.visionDash.maxS_green;
import static org.firstinspires.ftc.teamcode.vision.ExampleProcessor.visionDash.maxS_purple;
import static org.firstinspires.ftc.teamcode.vision.ExampleProcessor.visionDash.minV_purple;
import static org.opencv.core.Core.inRange;
import static org.opencv.core.CvType.CV_8U;
import static org.opencv.imgproc.Imgproc.CHAIN_APPROX_SIMPLE;
import static org.opencv.imgproc.Imgproc.COLOR_RGB2HSV;
import static org.opencv.imgproc.Imgproc.RETR_TREE;
import static org.opencv.imgproc.Imgproc.boundingRect;
import static org.opencv.imgproc.Imgproc.dilate;
import static org.opencv.imgproc.Imgproc.drawContours;
import static org.opencv.imgproc.Imgproc.erode;
import static org.opencv.imgproc.Imgproc.findContours;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.robotcore.external.function.Consumer;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
@Config
public class ExampleProcessor implements VisionProcessor, CameraStreamSource {

    public static int selectedRegion = -1;   // 0 = left, 1 = center, 2 = right
    public static int leftCount = 0;
    public static int centerCount = 0;
    public static int rightCount = 0;
    public static Rect largestRect;

    public static Rect largestPurpleRect;
    public static Rect largestGreenRect;

    public static int erodeConstant = 5;
    public static int dilateConstant = 5;

    public static boolean targetDetected = false;
    ArrayList<MatOfPoint> contoursGreen = new ArrayList<>();
    ArrayList<MatOfPoint> contoursPurple = new ArrayList<>();

    List <Rect> purpleRects = new ArrayList<>();
    List <Rect> greenRects = new ArrayList<>();
    private static int IMG_HEIGHT = 0;
    private static int IMG_WIDTH = 0;
    //
    // Sets up variables to collect image details
    private Mat output = new Mat(),
            maskGreen = new Mat(),
            maskPurple = new Mat();
    private Mat hierarchy = new Mat();

    private final AtomicReference<Bitmap> lastFrame =
            new AtomicReference<>(Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565));
    // This is for camera stream to dashboard
    // Can be ignored entirely



    @Config
    public static class visionDash{
        public static int maxH_green = 95;
        public static int maxS_green = 255;
        public static int maxV_green = 255;
        public static int minH_green = 75;
        public static int minS_green = 100;
        public static int minV_green = 80;

        public static int maxH_purple = 170;
        public static int maxS_purple = 200;
        public static int maxV_purple = 255;
        public static int minH_purple = 120;
        public static int minS_purple = 30;
        public static int minV_purple = 50;
    }


    @Override
    public Mat processFrame(Mat input, long captureTimeNanos) { //does the actual stuff
        input.copyTo(output);
        // Clear previous contours
        contoursGreen.clear();
        contoursPurple.clear();
        greenRects.clear();
        purpleRects.clear();

        IMG_HEIGHT = input.rows();
        IMG_WIDTH = input.cols();
        float regionWidth = IMG_WIDTH / 3;
        Scalar MIN_THRESH_GREEN = new Scalar(visionDash.minH_green, visionDash.minS_green, visionDash.minV_green);
        Scalar MAX_THRESH_GREEN = new Scalar(visionDash.maxH_green, maxS_green, visionDash.maxV_green);

        Scalar MIN_THRESH_PURPLE = new Scalar(visionDash.minH_purple, visionDash.minS_purple, minV_purple);
        Scalar MAX_THRESH_PURPLE = new Scalar(visionDash.maxH_purple, maxS_purple, visionDash.maxV_purple);

        Imgproc.cvtColor(input, maskGreen, COLOR_RGB2HSV);
        Imgproc.cvtColor(input, maskPurple, COLOR_RGB2HSV);
        inRange(maskGreen, MIN_THRESH_GREEN, MAX_THRESH_GREEN, maskGreen);
        inRange(maskPurple, MIN_THRESH_PURPLE, MAX_THRESH_PURPLE, maskPurple);

        Rect submatRect = new Rect(new Point(4, 4), new Point(IMG_WIDTH, IMG_HEIGHT));
        maskGreen = maskGreen.submat(submatRect);
        maskPurple = maskPurple.submat(submatRect);
        // Actual threshold thing to correct for top of screen being weird and glitchy, can use to only detect part of screen

        erode(maskGreen, maskGreen, new Mat(erodeConstant, erodeConstant, CV_8U));
        dilate(maskGreen, maskGreen, new Mat(dilateConstant, dilateConstant, CV_8U));
        erode(maskPurple, maskPurple, new Mat(erodeConstant, erodeConstant, CV_8U));
        dilate(maskPurple, maskPurple, new Mat(dilateConstant, dilateConstant, CV_8U));

        findContours(maskGreen, contoursGreen, hierarchy, RETR_TREE, CHAIN_APPROX_SIMPLE);
        findContours(maskPurple, contoursPurple, hierarchy, RETR_TREE, CHAIN_APPROX_SIMPLE);

        largestPurpleRect = getObjectsDetected(contoursPurple, MAGENTA);
        largestGreenRect = getObjectsDetected(contoursGreen, GREEN);



        Bitmap b = Bitmap.createBitmap(output.width(), output.height(), Bitmap.Config.RGB_565);
        Utils.matToBitmap(output, b);
        lastFrame.set(b);
        // This is used to determine which Mat is output to dashboard
        List<Rect> combined = new ArrayList<>();
        combined.addAll(purpleRects);
        combined.addAll(greenRects);
        countRegions(combined);
        return output;
    }
    private void countRegions(List<Rect> rects) {

        leftCount = 0;
        centerCount = 0;
        rightCount = 0;

        int regionWidth = IMG_WIDTH / 3;

        for (Rect rect : rects) {

            int centerX = rect.x + rect.width / 2;

            if (centerX < regionWidth) {
                leftCount++;
            }
            else if (centerX < regionWidth * 2) {
                centerCount++;
            }
            else {
                rightCount++;
            }
        }

        // Decide region with max count
        int max = Math.max(leftCount, Math.max(centerCount, rightCount));

        if (max == 0) {
            selectedRegion = -1;
        }
        else if (max == leftCount) {
            selectedRegion = 0;
        }
        else if (max == centerCount) {
            selectedRegion = 1;
        }
        else {
            selectedRegion = 2;
        }
    }
    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        Paint regionPaint = new Paint();
        regionPaint.setColor(Color.WHITE);
        regionPaint.setStrokeWidth(scaleCanvasDensity * 4);

        float regionWidth = onscreenWidth / 3f;

        canvas.drawLine(regionWidth, 0,
                regionWidth ,
                onscreenHeight, regionPaint);

        canvas.drawLine(regionWidth * 2 , 0,
                regionWidth * 2,
                onscreenHeight, regionPaint);

        Paint highlightPaint = new Paint();
        highlightPaint.setStyle(Paint.Style.FILL);
        highlightPaint.setColor(Color.argb(60, 255, 255, 0));  // translucent yellow

        if (selectedRegion != -1) {

            float left = selectedRegion * regionWidth;
            float right = left + regionWidth;

            canvas.drawRect(left, 0, right, onscreenHeight, highlightPaint);
        }


        Paint artifactPaintGreen = new Paint();
        artifactPaintGreen.setColor(GREEN);
        artifactPaintGreen.setStyle(Paint.Style.STROKE);
        artifactPaintGreen.setStrokeWidth(scaleCanvasDensity * 8);

        Paint artifactPaintPurple = new Paint();
        artifactPaintPurple.setColor(Color.RED);
        artifactPaintPurple.setStyle(Paint.Style.STROKE);
        artifactPaintPurple.setStrokeWidth(scaleCanvasDensity * 8);

        if(targetDetected) {

            if (largestGreenRect != null) {
                 canvas.drawRect(makeGraphicsRect(largestGreenRect, scaleBmpPxToCanvasPx), artifactPaintGreen);
            }
            if (largestPurpleRect != null) {
                canvas.drawRect(makeGraphicsRect(largestPurpleRect, scaleBmpPxToCanvasPx), artifactPaintPurple);
            }
        }

    }
    @Override
    public void getFrameBitmap(Continuation<? extends Consumer<Bitmap>> continuation) {
        continuation.dispatch(bitmapConsumer -> bitmapConsumer.accept(lastFrame.get()));
    }

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        // This code runs when you set up the camera processor
        // I mostly just leave this bit alone

        lastFrame.set(Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565));
        // More dashboard setup
    }

    public Rect getObjectsDetected(ArrayList<MatOfPoint> contours, int color){
        if (!contours.isEmpty()) {
            MatOfPoint largestContour = findLargestContour(contours);
          //  MatOfPoint2f largestContour2f = new MatOfPoint2f();
            if (largestContour != null) {

                List<Rect> rects = new ArrayList<>();

                for (int i = 0; i < contours.size(); i++) {
                    Rect rect = boundingRect(contours.get(i));
                    rects.add(rect);

                    if (rect.width * rect.height > 500) {
                        rects.add(rect);
                    }
                }
                if (!rects.isEmpty()) {
                    this.largestRect = VisionUtils.sortRectsByMaxOption(1, VisionUtils.RECT_OPTION.AREA, rects).get(0);
                    targetDetected = true;
                    if(color == GREEN && !rects.isEmpty()){ //this is the world's goofiest code but it works ig
                        greenRects=VisionUtils.sortRectsByMaxOption(1, VisionUtils.RECT_OPTION.AREA, rects);
                    } else if (color == MAGENTA && !rects.isEmpty()) {
                        purpleRects=VisionUtils.sortRectsByMaxOption(1, VisionUtils.RECT_OPTION.AREA, rects);
                    }
                } else {
                    largestRect = null;
                    targetDetected = false;
                }
            }


                return largestRect;
            }else{
            return null;
    }}
    private MatOfPoint findLargestContour(List<MatOfPoint> contours) {
        double maxArea = 0;
        MatOfPoint largestContour = null;
        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area > maxArea) {
                maxArea = area;
                largestContour = contour;
            }
        }
        return largestContour;
    }




    private android.graphics.Rect makeGraphicsRect(Rect rect, float scaleBmpPxToCanvasPx) {
        int left = Math.round(rect.x * scaleBmpPxToCanvasPx);
        int top = Math.round(rect.y * scaleBmpPxToCanvasPx);
        int right = left + Math.round(rect.width * scaleBmpPxToCanvasPx);
        int bottom = top + Math.round(rect.height * scaleBmpPxToCanvasPx);

        return new android.graphics.Rect(left, top, right, bottom);
    }

   }