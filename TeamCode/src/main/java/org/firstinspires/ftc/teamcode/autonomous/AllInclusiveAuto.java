package org.firstinspires.ftc.teamcode.autonomous;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive;
import org.firstinspires.ftc.teamcode.sensors.BNO055IMUGyro;
import org.firstinspires.ftc.teamcode.sensors.Gyro;


@Autonomous(name = "Auto", group = "Autonomous")
public class AllInclusiveAuto extends LinearOpMode {
    private VuforiaLocalizer vuforia;
    private Gyro gyro;
    private static final String VUFORIA_KEY = "AdPNlBX/////AAABmVfye0Qoq0efoZI4OrEHeIQSRjhNr9KQMKROnvTahH08r6+kPliev3BPNHMIPuFAdFRiZ28ted7hD7VN11J8ThMrQUdfilKWo6DRpZ6tVR2qvf5HxAIB0DZX3G7dbCfVbNSeal9I5EDQ9DpVgLPJqk0Txk5NTCcco1g32oPU1D3qnIhMLPmco9oSrFwXFIvuwZYtd/iC1kQOpH+32afAE/x2fy7zphkojHhpaNmAEATUYs+63PMnG1hB/0LnHS/JrT3WjK2lHO28ESwRSOU96L9ljHl/lHKfW+397WDSNp2OAFoFhEpmk9dNnM5CPzh8i9BFXNMRj1EEraAQgrGr7sLzIS558bKfDgXHV+4zIMVy";
    double stoneDiff;
    double stoneDiff2;
    private static ColorSensor sensorColor;
    private static DistanceSensor sensorDistance;
    double driftAdjustment = -3;

    private int sideModifier;
    private double speed =0.75;
    private double deltaTime;
    private double FORWARD_VEL;
    private double STRAFE_VEL;
    private static float satValPos[] = {0F,0F,0F};//Saturation values for left,center, and right stones will be stored in this array of 3 elements
    private static float hsvValues[] = {0F, 0F, 0F};//Hue, saturation, & vue
    private MecanumDrive drive;

    private static double SCALE_FACTOR;
    public AllInclusiveAuto(int side, double deltaTime) {
        sideModifier = side;
        this.deltaTime = deltaTime;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        gyro = BNO055IMUGyro.standard(hardwareMap);
        gyro.initialize();
        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color");
        SCALE_FACTOR = 255;
        sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_color");
        //pos = vuforiaStuff.vuforiascan(false, false);
        speed = 0.25;
        float satLeft;
        float satCenter;
        float satRight;

        roboInit();


        drive.forwardWithPower(-1*speed);
        sleep((int)(24/FORWARD_VEL));
        while(getDistance()>6) {
            drive.strafeLeftWithPower(speed);
        }
        drive.stop();
        Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                (int) (sensorColor.green() * SCALE_FACTOR),
                (int) (sensorColor.blue() * SCALE_FACTOR),
                hsvValues);
        satLeft = hsvValues[1];
        //saturation value of the color of the left is stored in satLeft
        drive.forwardWithPower(speed);
        sleep((int)(8/FORWARD_VEL));
        Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                (int) (sensorColor.green() * SCALE_FACTOR),
                (int) (sensorColor.blue() * SCALE_FACTOR),
                hsvValues);
        satCenter = hsvValues[1];
        drive.forwardWithPower(speed);
        sleep((int)(8/FORWARD_VEL));
        Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                (int) (sensorColor.green() * SCALE_FACTOR),
                (int) (sensorColor.blue() * SCALE_FACTOR),
                hsvValues);
        satRight = hsvValues[1];
        int pos = decidePositionBasedOnVal(satLeft,satCenter,satRight);
        speed=1;
        switch (pos) {
            case 0: // Stone is at Left pos
                // Declaration --> servo 1 is the the arm of the thing that picks up the skystone
                //Declaration --> servo 2 is the 'claw' of the thing that picks up the skystone


                //back up to the first stone from the third stone
                drive.forwardWithPower(-speed);
                sleep((int)(-8/FORWARD_VEL));
                //servo 1 down

                //servo 2 down

                //servo 1 up


                drive.strafeLeftWithPower(-speed); //strafe right a little bit
                sleep((int)(5/STRAFE_VEL));
                drive.forwardWithPower(speed);//drive forward to the building zone
                sleep((int)(120/FORWARD_VEL));

                drive.strafeLeftWithPower(speed);//strafe a little left
                sleep((int)(2/STRAFE_VEL));
                //servo 2 up
                //servo 1 up

                drive.strafeLeftWithPower(-speed);//strafe right 2 inches
                sleep((int)(2/STRAFE_VEL));

                drive.forwardWithPower(-speed);//drive backwards to next set of three skystones
                sleep((int)(96/FORWARD_VEL));
                //servo 2 up
                //servo 1 down
                //servo 2 down
                //servo 1 up


                drive.strafeLeftWithPower(speed);//strafe right a little bit
                sleep((int)(5/STRAFE_VEL));

                //servo 2 up
                //servo 1 down
                //servo 2 down
                drive.strafeLeftWithPower(-speed);
                sleep((int)(5/STRAFE_VEL));
                drive.forwardWithPower(speed);//drive forward to the building zone
                sleep((int)(120/FORWARD_VEL));

                drive.strafeLeftWithPower(speed);//strafe a little left
                sleep((int)(5/FORWARD_VEL));

                //servo 1 down
                //servo 2 up

                drive.strafeLeftWithPower(-speed);//strafe right 16 inches
                sleep((int)(16/STRAFE_VEL));
                while(gyro.getAngle()>-90){        //turn right -90 degrees
                    drive.turnInPlace(speed,true);
                }
                drive.stop();
                //back up 6 inches
                drive.forwardWithPower(-speed);
                sleep((int)(6/FORWARD_VEL));
                //servo 3 down
                //servo 4 down

                //drive forward as much as possible
                drive.forwardWithPower(speed);
                sleep((int)(24/STRAFE_VEL));
                //servo 3 up
                //servo 4 up
                //slowly strafe right
                speed = 0.25;
                drive.strafeLeftWithPower(-speed);
                sleep((int)(10/STRAFE_VEL));
                //back up to stay in line
                drive.forwardWithPower(-speed);
                sleep((int)(18/FORWARD_VEL));
                drive.strafeLeftWithPower(-speed);
                sleep((int)(10/STRAFE_VEL));
                //park in our lane

                break;
            case 1: // Stone is at Center pos
                // Declaration --> servo 1 is the the arm of the thing that picks up the skystone
                //Declaration --> servo 2 is the 'claw' of the thing that picks up the skystone


                //back up to the first stone from the third stone
                drive.forwardWithPower(-speed);
                sleep((int)(4/FORWARD_VEL));
                //servo 1 down

                //servo 2 down

                //servo 1 up


                drive.strafeLeftWithPower(-speed); //strafe right a little bit
                sleep((int)(5/STRAFE_VEL));

                drive.forwardWithPower(speed);//drive forward to the building zone
                sleep((int)(120/FORWARD_VEL));

                drive.strafeLeftWithPower(speed);//strafe a little left
                sleep((int)(2/STRAFE_VEL));
                //servo 2 up
                //servo 1 up

                drive.strafeLeftWithPower(-speed);//strafe right 2 inches
                sleep((int)(2/STRAFE_VEL));

                drive.forwardWithPower(-speed);//drive backwards to next set of three skystones
                sleep((int)(96/FORWARD_VEL));
                //servo 2 up
                //servo 1 down
                //servo 2 down
                //servo 1 up


                drive.strafeLeftWithPower(speed);//strafe right a little bit
                sleep((int)(5/STRAFE_VEL));

                //servo 2 up
                //servo 1 down
                //servo 2 down
                drive.strafeLeftWithPower(-speed);
                sleep((int)(5/STRAFE_VEL));
                drive.forwardWithPower(speed);//drive forward to the building zone
                sleep((int)(120/FORWARD_VEL));

                drive.strafeLeftWithPower(speed);//strafe a little left
                sleep((int)(5/FORWARD_VEL));

                //servo 1 down
                //servo 2 up

                drive.strafeLeftWithPower(-speed);//strafe right 16 inches
                sleep((int)(16/STRAFE_VEL));
                while(gyro.getAngle()>-90){        //turn right -90 degrees
                    drive.turnInPlace(speed,true);
                }
                drive.stop();
                //back up 6 inches
                drive.forwardWithPower(-speed);
                sleep((int)(6/FORWARD_VEL));
                //servo 3 down
                //servo 4 down

                //drive forward as much as possible
                drive.forwardWithPower(speed);
                sleep((int)(24/STRAFE_VEL));
                //servo 3 up
                //servo 4 up
                //slowly strafe right
                speed = 0.25;
                drive.strafeLeftWithPower(-speed);
                sleep((int)(10/STRAFE_VEL));
                //back up to stay in line
                drive.forwardWithPower(-speed);

                //park in our lane

                break;
            case 2: //sotne is at right pos
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
        /*int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;


        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);*/

        drive = MecanumDrive.standard(hardwareMap);
        // MOVEMENT CONSTANTS
        /** average forward velocity of the robot at full power (inches per millisecond) */
        FORWARD_VEL = 0.0347 / deltaTime;
        /** average strafing velocity of the robot at full power (inches per millisecond) */
        STRAFE_VEL = 0.0257 / deltaTime;

        waitForStart();
    }
    public static float getSatVal(){
        Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                (int) (sensorColor.green() * SCALE_FACTOR),
                (int) (sensorColor.blue() * SCALE_FACTOR),
                hsvValues);
        return hsvValues[1];
    }
    public static double getDistance(){
        return (sensorDistance.getDistance(DistanceUnit.INCH));
    }
    public static int decidePositionBasedOnVal(float left, float center, float right){
        if(left>center && left >right){
            return 0;
        }
        else if(center > left && center>right){
            return 1;
        }
        else if(right>center && right>left){
            return 2;
        }
        return -1;
    }
    }
