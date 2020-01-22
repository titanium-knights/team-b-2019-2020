package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.teamcode.autonomous.VuforiaStuff;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive;
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

    private int sideModifier;
    private double speed =0.75;
    private double deltaTime;
    private double FORWARD_VEL;
    private double STRAFE_VEL;
    private MecanumDrive drive;

    public AllInclusiveAuto(int side, double deltaTime) {
        sideModifier = side;
        this.deltaTime = deltaTime;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        roboInit();
        pos = vuforiaStuff.vuforiascan(false, false);
        switch (pos) {
            case LEFT:
                // Declaration --> servo 1 is the the arm of the thing that picks up the skystone
                //Declaration --> servo 2 is the 'claw' of the thing that picks up the skystone
                //strafe to block
                drive.strafeLeftWithPower(speed);
                sleep((int)(29 / STRAFE_VEL));
                //back up all the way
                drive.forwardWithPower(-speed*sideModifier);
                sleep((int)(25/FORWARD_VEL));
                //servo 1 down

                //servo 2 down

                //servo 1 up

                //strafe right a little bit
                drive.strafeLeftWithPower(-speed);
                sleep((int)(5/STRAFE_VEL));
                //drive forward to the building zone
                drive.forwardWithPower(speed);
                sleep((int)(120/FORWARD_VEL));
                //strafe a little left
                drive.strafeLeftWithPower(speed);
                sleep((int)(2/STRAFE_VEL));
                //servo 2 up
                //servo 1 up
                //strafe right 2 inches
                drive.strafeLeftWithPower(-speed);
                sleep((int)(2/STRAFE_VEL));
                //drive backwards to next set of three skystones
                drive.forwardWithPower(-speed);
                sleep((int)(96/FORWARD_VEL));
                //servo 2 up
                //servo 1 down
                //servo 2 down
                //servo 1 up
                //strafe right a little bit
                drive.strafeLeftWithPower(-speed);
                sleep((int)(5/STRAFE_VEL));
                //drive forward to the building zone
                drive.forwardWithSpeed(speed);
                sleep((int)(120/FORWARD_VEL));
                //strafe a little left
                drive.strafeLeftWithPower(speed);
                sleep((int)(5/FORWARD_VEL));
                //servo 1 down
                //servo 2 up
                //servo 3 down
                //servo 4 down
                //strafe right as much as possible
                drive.strafeLeftWithPower(-speed);
                sleep((int)(24/STRAFE_VEL));
                //slowly back up
                break;
            case CENTER:
                //insert code here

                drive.strafeLeftWithPower(1);
                sleep((int)(15 / STRAFE_VEL));

                // grab brick
                sleep(1000);

                drive.strafeLeftWithPower(-1);
                sleep((int)(5 / STRAFE_VEL));

                drive.forwardWithPower(sideModifier);
                sleep((int)(24 / FORWARD_VEL));

                drive.forwardWithPower(-sideModifier);
                sleep((int)(36 / FORWARD_VEL));

                drive.strafeLeftWithPower(1);
                sleep((int)(5 / STRAFE_VEL));

                // grab brick
                sleep(1000);

                drive.strafeLeftWithPower(-1);
                sleep((int)(5 / STRAFE_VEL));

                drive.forwardWithPower(sideModifier);
                sleep((int)(36 / FORWARD_VEL));

                drive.forwardWithPower(-sideModifier);
                sleep((int)(6 / FORWARD_VEL));

                drive.stop();

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

        drive = MecanumDrive.standard(hardwareMap);
        // MOVEMENT CONSTANTS
        /** average forward velocity of the robot at full power (inches per millisecond) */
        FORWARD_VEL = 0.0347 / deltaTime;
        /** average strafing velocity of the robot at full power (inches per millisecond) */
        STRAFE_VEL = 0.0257 / deltaTime;

        waitForStart();
    }

    }
