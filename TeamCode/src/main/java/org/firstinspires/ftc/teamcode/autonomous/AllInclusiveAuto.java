package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.teamcode.autonomous.VuforiaStuff;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.util.DbgLog;


@Autonomous(name = "Auto", group = "Autonomous")
public class AllInclusiveAuto extends LinearOpMode {
    public VuforiaStuff vuforiaStuff;
    private VuforiaLocalizer vuforia;
    private static final String VUFORIA_KEY = "AdPNlBX/////AAABmVfye0Qoq0efoZI4OrEHeIQSRjhNr9KQMKROnvTahH08r6+kPliev3BPNHMIPuFAdFRiZ28ted7hD7VN11J8ThMrQUdfilKWo6DRpZ6tVR2qvf5HxAIB0DZX3G7dbCfVbNSeal9I5EDQ9DpVgLPJqk0Txk5NTCcco1g32oPU1D3qnIhMLPmco9oSrFwXFIvuwZYtd/iC1kQOpH+32afAE/x2fy7zphkojHhpaNmAEATUYs+63PMnG1hB/0LnHS/JrT3WjK2lHO28ESwRSOU96L9ljHl/lHKfW+397WDSNp2OAFoFhEpmk9dNnM5CPzh8i9BFXNMRj1EEraAQgrGr7sLzIS558bKfDgXHV+4zIMVy";
    VuforiaStuff.skystonePos pos;
    double stoneDiff;
    double stoneDiff2;

    double driftAdjustment = -3;

    @Override
    public void runOpMode() throws InterruptedException {
        roboInit();
        pos = vuforiaStuff.vuforiascan(false, false);
        switch (pos) {
            case LEFT:
                // Declaration --> servo 1 is the the arm of the thing that picks up the skystone
                //Declaration --> servo 2 is the 'claw' of the thing that picks up the skystone
                //back up all the way

                //strafe to block
                //servo 1 down
                //servo 2 down
                //servo 1 up
                //strafe right a little bit
                //drive forward to the building zone
                //strafe a little left
                //servo 2 up
                //servo3 and servo 4 down
                //strafe right all the way
                //slowly back up
                break;
            case CENTER:
                //insert code here
                break;
            case RIGHT:
                //insert code here
                break;
        }

        sleep(200);
        /*if (pos == VuforiaStuff.skystonePos.RIGHT) {

        }
        else {

        }*/
        sleep(300);
    }
    public void roboInit() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;


        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        waitForStart();
    }

    }