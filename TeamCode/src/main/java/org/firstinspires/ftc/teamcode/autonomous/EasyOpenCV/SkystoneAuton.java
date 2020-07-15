package org.firstinspires.ftc.teamcode.autonomous.EasyOpenCV;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.AutoBaseOpMode;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous(name="OpenCV Autonomous")
public class SkystoneAuton extends AutoBaseOpMode {
    private OpenCvInternalCamera camera;
    private SkystoneDetector detector= new SkystoneDetector();
    private int position;
    @Override
    public void runOpMode(){
        super.runOpMode();
        int cameraId = hardwareMap.appContext.getResources().getIdentifier("cameraId", "id",hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraId);
        camera.openCameraDevice();
        camera.setPipeline(detector);
        camera.startStreaming(4032,3024, OpenCvCameraRotation.UPRIGHT);
        while(!isStarted()){
            position=detector.position;
            if(position==0) {
                telemetry.addData("Position", "Left");
            }
            else if(position==1){
                    telemetry.addData("Position", "Center");
            }
            else{
                telemetry.addData("Position", "Right");
            }
        }
    }
}
