package org.firstinspires.ftc.teamcode.subsystem;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.vision.VisionPortal;
import org.openftc.easyopencv.*;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.ArtifactPipeline;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class VisionSubsystem extends SubsystemBase {

    private OpenCvCamera camera;
    private ArtifactPipeline pipeline;

    public VisionSubsystem(HardwareMap hardwareMap) {

        int cameraMonitorViewId = hardwareMap.appContext
                .getResources()

                .getIdentifier("cameraMonitorViewId", "id",
                        hardwareMap.appContext.getPackageName());

        camera = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"),

        cameraMonitorViewId);

        pipeline = new ArtifactPipeline();
        camera.setPipeline(pipeline);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);

                FtcDashboard.getInstance().startCameraStream(camera,30);
            }

            @Override
            public void onError(int errorCode) {}
        });
    }

    public ArtifactPipeline.Position getPosition() {
        return pipeline.getPosition();
    }

    public void stopCamera() {
        camera.stopStreaming();
    }
}
