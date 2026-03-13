//package org.firstinspires.ftc.teamcode.terravision;
//
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
//
//import android.graphics.Color;
//import android.util.Size;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.vision.VisionPortal;
//import org.firstinspires.ftc.vision.opencv.Circle;
//import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
//import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor.Blob;
//import org.firstinspires.ftc.vision.opencv.ColorRange;
//
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import java.util.List;
//
//public class Vision2 {
//
//    WebcamName camera;
//    VisionPortal portal;
//
//    final int IMAGE_WIDTH = 1280;
//
//    int LEFT_BOUND = IMAGE_WIDTH / 3;
//    int CENTER_BOUND = 2 * IMAGE_WIDTH / 3;
//
//    double leftArea = 0;
//    double centerArea = 0;
//    double rightArea = 0;
//    public static int purple_erode = 15;
//    public static int purple_dilate = 30;
//
//    public static int green_erode = 10;
//    public static int green_dilate = 20;
//
//    public static int purple_blur = 5;
//    public static int green_blur = 5;
//    int region = -1;
//
//    ColorBlobLocatorProcessor purpleLocator =
//            new ColorBlobLocatorProcessor.Builder()
//                    .setTargetColorRange(ColorRange.ARTIFACT_PURPLE)
//                    .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)
//                    .setDrawContours(true)
//                    .setBlurSize(purple_blur)
//                    .setDilateSize(purple_dilate)
//                    .setErodeSize(purple_erode)
//                    .setMorphOperationType(ColorBlobLocatorProcessor.MorphOperationType.CLOSING)
//                    .setCircleFitColor(Color.YELLOW)
//                    .build();
//
//    ColorBlobLocatorProcessor greenLocator =
//            new ColorBlobLocatorProcessor.Builder()
//                    .setTargetColorRange(ColorRange.ARTIFACT_GREEN)
//                    .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)
//                    .setDrawContours(true)
//                    .setBlurSize(green_blur)
//                    .setDilateSize(green_dilate)
//                    .setErodeSize(green_erode)
//                    .setMorphOperationType(ColorBlobLocatorProcessor.MorphOperationType.CLOSING)
//                    .setCircleFitColor(Color.YELLOW)
//                    .build();
//
//    public void init(HardwareMap hardwareMap){
//
//        camera = hardwareMap.get(WebcamName.class,"Webcam 1");
//
//        portal = new VisionPortal.Builder()
//                .addProcessors(purpleLocator,greenLocator)
//                .setCamera(camera)
//                .setCameraResolution(new Size(1280,720))
//                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
//                .build();
//    }
//
//    public void update(){
//
//        leftArea = 0;
//        centerArea = 0;
//        rightArea = 0;
//
//        List<Blob> purpleBlobs = purpleLocator.getBlobs();
//        List<Blob> greenBlobs = greenLocator.getBlobs();
//
//        processBlobs(purpleBlobs);
//        processBlobs(greenBlobs);
//
//        region = 0;
//        double maxArea = leftArea;
//
//        if(centerArea > maxArea){
//            region = 1;
//            maxArea = centerArea;
//        }
//
//        if(rightArea > maxArea){
//            region = 2;
//        }
//
//
//    }
//
//    private void processBlobs(List<Blob> blobs){
//
//        for(Blob blob : blobs){
//
//            // Use real contour area instead of circle area
//            double area = blob.getContourArea();
//
//            // Ignore tiny noise
//            if(area < 300) continue;
//
//            // Optional: clamp huge merged contours
//            area = Math.min(area, 20000);
//
//            Circle circle = blob.getCircle();
//
//            double x = circle.getX();
//
//            if(x < LEFT_BOUND){
//
//                leftArea += area;
//
//            }
//            else if(x < CENTER_BOUND){
//
//                centerArea += area;
//
//            }
//            else{
//
//                rightArea += area;
//
//            }
//        }
//    }
//
//    public int getRegion(){
//        return region;
//    }
//}

package org.firstinspires.ftc.teamcode.terravision;

import android.graphics.Color;
import android.util.Size;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.Circle;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor.Blob;
import org.firstinspires.ftc.vision.opencv.ColorRange;
import org.opencv.imgproc.Imgproc;
import android.graphics.Canvas;
import android.graphics.Paint;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Mat;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════════════════╗
 * ║                  Vision2 — Region Selector                      ║
 * ╠══════════════════════════════════════════════════════════════════╣
 * ║  5-layer scoring pipeline:                                       ║
 * ║                                                                  ║
 * ║  1. CIRCULARITY WEIGHT   — blob.getDensity() ∈ [0,1] suppresses ║
 * ║     jagged noise; real balls are nearly perfect circles.         ║
 * ║                                                                  ║
 * ║  2. SIZE MULTIPLIER      — blobs > DOUBLE_BALL_AREA are likely  ║
 * ║     two touching balls → vote counted as 2.                      ║
 * ║                                                                  ║
 * ║  3. PROXIMITY BOOST      — uses CameraProjection to convert      ║
 * ║     pixel Y → real ground distance. Closer balls score higher   ║
 * ║     (more reachable). Clamped to [PROXIMITY_MIN, PROXIMITY_MAX].║
 * ║                                                                  ║
 * ║  4. BOUNDARY SPLIT VOTE  — a ball straddling LEFT_BOUND or      ║
 * ║     CENTER_BOUND splits its vote proportionally across regions   ║
 * ║     by how much of its diameter falls on each side.              ║
 * ║     Eliminates flickering caused by boundary-edge blobs.         ║
 * ║                                                                  ║
 * ║  5. CROSS-CHANNEL BONUS  — purple and green are scored          ║
 * ║     separately. A region that gets votes from BOTH channels      ║
 * ║     gets a score multiplier (CROSS_CHANNEL_BOOST). Two           ║
 * ║     independent colour sensors agreeing = stronger signal.       ║
 * ║                                                                  ║
 * ║  Tie-break: if top two regions within SCORE_TIE_THRESHOLD,      ║
 * ║  pick the one whose centroids are clustered tightest (min var).  ║
 * ║                                                                  ║
 * ║  Temporal smoothing: committed only after CONFIRM_FRAMES         ║
 * ║  consecutive frames agree on the same winner.                    ║
 * ╚══════════════════════════════════════════════════════════════════╝
 */
@Config
public class Vision2 {

    // ── Camera / Portal ──────────────────────────────────────────────────────
    WebcamName camera;
    VisionPortal portal;

    // ── Frame geometry ───────────────────────────────────────────────────────
    final int IMAGE_WIDTH = 1280;
    final int IMAGE_HEIGHT = 720;
    final int LEFT_BOUND = IMAGE_WIDTH / 3;        // 427 px
    final int CENTER_BOUND = 2 * IMAGE_WIDTH / 3;    // 853 px

    // ── Morphology tuning ────────────────────────────────────────────────────
    public static int purple_erode = 15;
    public static int purple_dilate = 15;
    public static int green_erode = 15;
    public static int green_dilate = 15;
    public static int purple_blur = 5;
    public static int green_blur = 5;

    // ── Scoring constants ─────────────────────────────────────────────────────

    /**
     * Blobs below this area (px²) are noise — ignored entirely
     */
    private static final double MIN_AREA = 500.0;

    /**
     * Blobs above this area are likely two touching balls → vote x2
     */
    private static final double DOUBLE_BALL_AREA = 8000.0;
    private static final double DOUBLE_BALL_MULTIPLIER = 2.0;

    /**
     * PROXIMITY BOOST constants (Layer 3).
     * Score = clamp(PROXIMITY_REF_DIST / groundDistCm, MIN, MAX)
     * A ball at REF_DIST scores exactly 1.0.
     */
    private static final double PROXIMITY_REF_DIST = 50.0; // cm
    private static final double PROXIMITY_MIN = 0.5;
    private static final double PROXIMITY_MAX = 2.0;

    /**
     * CROSS-CHANNEL BONUS (Layer 5).
     * Applied when a region has votes from both purple and green.
     * 1.3 = 30% bonus. Set to 1.0 to disable.
     */
    private static final double CROSS_CHANNEL_BOOST = 1.3;

    /**
     * Tie-break threshold.
     * If top two scores differ by less than this fraction, use X-variance.
     */
    private static final double SCORE_TIE_THRESHOLD = 0.15;

    /**
     * Temporal confirmation.
     * Region must win for this many consecutive frames to become official.
     * Set to 1 to disable (immediate commit).
     */
    private static final int CONFIRM_FRAMES = 3;

    // ── Per-frame accumulators ────────────────────────────────────────────────
    private double[] purpleScore = new double[3];  // channel-separated for Layer 5
    private double[] greenScore = new double[3];
    private double[] regionScore = new double[3];  // final combined score
    private int[] regionCount = new int[3];      // integer ball count (telemetry)

    @SuppressWarnings("unchecked")
    private List<Double>[] regionCentroidsX = new List[]{
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
    };
    // ── Temporal smoothing state ──────────────────────────────────────────────
    private int pendingRegion = -1;
    private int pendingStreak = 0;
    private int region = -1;

    // ── Debug fields (readable from TeleOp for tuning) ────────────────────────
    public boolean[] debugCrossChannelApplied = new boolean[3];

    // ── Blob processors ───────────────────────────────────────────────────────
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


    // ── Init ──────────────────────────────────────────────────────────────────
    public void init(HardwareMap hardwareMap) {
        camera = hardwareMap.get(WebcamName.class, "Webcam 1");
        portal = new VisionPortal.Builder()
                .addProcessors(purpleLocator, greenLocator)
                .setCamera(camera)
                .setCameraResolution(new Size(IMAGE_WIDTH, IMAGE_HEIGHT))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .build();

    }


    // ── Main update — called every loop iteration ─────────────────────────────
    public void update() {

        // Reset
        for (int i = 0; i < 3; i++) {
            purpleScore[i] = greenScore[i] = regionScore[i] = 0;
            regionCount[i] = 0;
            regionCentroidsX[i].clear();
            debugCrossChannelApplied[i] = false;
        }

        // Layers 1-4: score each colour channel independently
        processBlobs(purpleLocator.getBlobs(), purpleScore);
        processBlobs(greenLocator.getBlobs(), greenScore);

        // Layer 5: merge with cross-channel bonus
        for (int i = 0; i < 3; i++) {
            double combined = purpleScore[i] + greenScore[i];
            if (purpleScore[i] > 0 && greenScore[i] > 0) {
                combined *= CROSS_CHANNEL_BOOST;
                debugCrossChannelApplied[i] = true;
            }
            regionScore[i] = combined;
        }

        // Find best and second-best
        int best = 0, second = 1;
        for (int i = 1; i < 3; i++) {
            if (regionScore[i] > regionScore[best]) {
                second = best;
                best = i;
            } else if (regionScore[i] > regionScore[second]) {
                second = i;
            }
        }

        if (regionScore[best] == 0) return; // nothing detected

        // Tiebreaker
        boolean isTie = (Math.abs(regionScore[best] - regionScore[second]) / regionScore[best])
                < SCORE_TIE_THRESHOLD;
        int winner = isTie
                ? (xVariance(regionCentroidsX[best]) <= xVariance(regionCentroidsX[second]) ? best : second)
                : best;

        // Temporal smoothing
        if (autoMode) {
            // In auto: commit immediately, no streak required
            region = winner;
        } else {
            // In teleop/debug: require CONFIRM_FRAMES for stability
            if (winner == pendingRegion) pendingStreak++;
            else {
                pendingRegion = winner;
                pendingStreak = 1;
            }
            if (pendingStreak >= CONFIRM_FRAMES) region = pendingRegion;
        }
    }

    // ── Score one colour channel's blobs (Layers 1-4) ────────────────────────
    private void processBlobs(List<Blob> blobs, double[] scores) {

        for (Blob blob : blobs) {

            Circle circle = blob.getCircle();
            double bx = circle.getX();
            double by = circle.getY();
            double radius = circle.getRadius();
            double area = Math.PI * radius * radius;

            if (area < MIN_AREA) continue;

            // Layer 1: circularity — suppresses jagged noise blobs
            double circularity = Math.max(0.1, blob.getDensity());

            // Layer 2: size multiplier — two touching balls fused into one blob
            double sizeMultiplier = (area > DOUBLE_BALL_AREA) ? DOUBLE_BALL_MULTIPLIER : 1.0;

            // Layer 3: proximity boost — closer balls score higher
            double proximityWeight = computeProximityWeight(bx, by);

            // Final vote weight for this blob
            double voteWeight = circularity * sizeMultiplier * proximityWeight;

            // Layer 4: boundary split vote
            // How much of the ball's diameter falls in each region?
            double blobLeft = bx - radius;
            double blobRight = bx + radius;
            double diameter = 2.0 * radius;

            double f0 = overlapFraction(blobLeft, blobRight, 0, LEFT_BOUND, diameter);
            double f1 = overlapFraction(blobLeft, blobRight, LEFT_BOUND, CENTER_BOUND, diameter);
            double f2 = overlapFraction(blobLeft, blobRight, CENTER_BOUND, IMAGE_WIDTH, diameter);

            double fTotal = f0 + f1 + f2;
            if (fTotal < 1e-6) continue;

            // Distribute vote proportionally across regions
            scores[0] += voteWeight * (f0 / fTotal);
            scores[1] += voteWeight * (f1 / fTotal);
            scores[2] += voteWeight * (f2 / fTotal);

            // Primary region (centroid) for tiebreaker + count
            int primary = (bx < LEFT_BOUND) ? 0 : (bx < CENTER_BOUND) ? 1 : 2;
            regionCentroidsX[primary].add(bx);
            regionCount[primary] += (int) sizeMultiplier;
        }
    }

    /**
     * Layer 3 implementation.
     * Converts blob pixel position to real ground distance via CameraProjection,
     * then returns a proximity weight: closer = higher score.
     */
    private double computeProximityWeight(double pixelX, double pixelY) {
        try {
            double[] ground = CameraProjection.pixelToGround(pixelX, pixelY);
            double distCm = ground[1]; // forward distance in cm
            if (distCm <= 0) return PROXIMITY_MIN;
            double w = PROXIMITY_REF_DIST / distCm;
            return Math.max(PROXIMITY_MIN, Math.min(PROXIMITY_MAX, w));
        } catch (Exception e) {
            return 1.0; // safe fallback
        }
    }

    /**
     * Layer 4 helper.
     * Returns what fraction of [blobLeft, blobRight] overlaps [regionLeft, regionRight],
     * normalised by the blob's full diameter.
     */
    private double overlapFraction(double blobLeft, double blobRight,
                                   double regionLeft, double regionRight,
                                   double diameter) {
        double overlap = Math.max(0, Math.min(blobRight, regionRight) - Math.max(blobLeft, regionLeft));
        return overlap / diameter;
    }

    /**
     * X-variance of a centroid list. Lower = tighter cluster.
     * Used as tiebreaker when top two region scores are very close.
     */
    private double xVariance(List<Double> xs) {
        if (xs.isEmpty()) return Double.MAX_VALUE;
        double mean = 0;
        for (double v : xs) mean += v;
        mean /= xs.size();
        double var = 0;
        for (double v : xs) var += (v - mean) * (v - mean);
        return var / xs.size();
    }

    // ── Public API ────────────────────────────────────────────────────────────

    /**
     * 0=LEFT  1=CENTER  2=RIGHT  -1=not yet committed
     */
    public int getRegion() {
        return region;
    }

    /**
     * Raw integer ball count in region r (telemetry)
     */
    public int getRegionCount(int r) {
        return regionCount[r];
    }

    /**
     * Final weighted score for region r (after all 5 layers + cross-channel)
     */
    public double getRegionScore(int r) {
        return regionScore[r];
    }

    /**
     * Purple-only score before cross-channel bonus (tuning)
     */
    public double getPurpleScore(int r) {
        return purpleScore[r];
    }

    /**
     * Green-only score before cross-channel bonus (tuning)
     */
    public double getGreenScore(int r) {
        return greenScore[r];
    }

    /**
     * Whether cross-channel bonus was applied to region r this frame
     */
    public boolean isCrossChannelApplied(int r) {
        return debugCrossChannelApplied[r];
    }

    /**
     * Frames the current pending region has been continuously winning
     */
    public int getConfidenceStreak() {
        return pendingStreak;
    }

    private boolean autoMode = false;

    // Add this method
    public void setAutoMode(boolean autoMode) {
        this.autoMode = autoMode;
    }
}
