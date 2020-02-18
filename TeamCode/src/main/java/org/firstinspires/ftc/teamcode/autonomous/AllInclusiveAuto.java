package org.firstinspires.ftc.teamcode.autonomous;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.movement.BrickHolder;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive;
import org.firstinspires.ftc.teamcode.movement.PlateClamp;
import org.firstinspires.ftc.teamcode.sensors.BNO055IMUGyro;
import org.firstinspires.ftc.teamcode.sensors.Gyro;


@Autonomous(name = "Red Auto", group = "Autonomous")
public class AllInclusiveAuto extends AutoBaseOpMode {
    private VuforiaLocalizer vuforia;
    private static final String VUFORIA_KEY = "AdPNlBX/////AAABmVfye0Qoq0efoZI4OrEHeIQSRjhNr9KQMKROnvTahH08r6+kPliev3BPNHMIPuFAdFRiZ28ted7hD7VN11J8ThMrQUdfilKWo6DRpZ6tVR2qvf5HxAIB0DZX3G7dbCfVbNSeal9I5EDQ9DpVgLPJqk0Txk5NTCcco1g32oPU1D3qnIhMLPmco9oSrFwXFIvuwZYtd/iC1kQOpH+32afAE/x2fy7zphkojHhpaNmAEATUYs+63PMnG1hB/0LnHS/JrT3WjK2lHO28ESwRSOU96L9ljHl/lHKfW+397WDSNp2OAFoFhEpmk9dNnM5CPzh8i9BFXNMRj1EEraAQgrGr7sLzIS558bKfDgXHV+4zIMVy";
    double stoneDiff;
    double stoneDiff2;
    private static ColorSensor sensorColor;
    private static DistanceSensor sensorDistance;
    private static DistanceSensor backDistance;
    private static DistanceSensor frontDistance;
    double driftAdjustment = -3;

    private PlateClamp clamp;
    private BrickHolder holder;
    private int sideModifier =1;
    private int positionOffset=0;
    private double speed =1;
    private double deltaTime =2.5;
    private double FORWARD_VEL;
    private double STRAFE_VEL;
    private static float satValPos[] = {0F,0F,0F};//Saturation values for left,center, and right stones will be stored in this array of 3 elements
    private static float hsvValues[] = {0F, 0F, 0F};//Hue, saturation, & vue

    private static double SCALE_FACTOR;
    /*public AllInclusiveAuto(int side, double deltaTime) {
        sideModifier = side;
        this.deltaTime = deltaTime;
    }*/

    @Override
    public void runOpMode() {
        super.runOpMode();

        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color");
        SCALE_FACTOR = 255;
        sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_distance");
        backDistance = hardwareMap.get(DistanceSensor.class, "back_distance");
        frontDistance = hardwareMap.get(DistanceSensor.class, "front_distance");
        holder = BrickHolder.standard(hardwareMap);

        //pos = vuforiaStuff.vuforiascan(false, false);
        float satLeft;
        float satCenter;
        float satRight;
        roboInit();
        sensorColor.enableLed(true);
        double startAngle = gyro.getAngle();
        speed = 0.25;
        drive.forwardWithPower(-1*speed);
        sleep((int)((20/FORWARD_VEL)*4));
        drive.stop();
        //sensorDrive(new MecanumDrive.Motor.Vector2D(1.0, 0.0), startAngle, sensorDistance, 4 );
        drive.strafeLeftWithPower(speed);
        long placeHolder=0L;
        while(sensorDistance.getDistance(DistanceUnit.INCH)>5){
            placeHolder++;
        }
        drive.stop();
        drive.forwardWithPower((-1*speed));
        sleep(500);
        drive.stop();
        sleep(750);
        Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                (int) (sensorColor.green() * SCALE_FACTOR),
                (int) (sensorColor.blue() * SCALE_FACTOR),
                hsvValues);
        satLeft = hsvValues[1];
        telemetry.addData("First", satLeft);
        telemetry.update();
        //sensorDrive(new MecanumDrive.Motor.Vector2D(0, -1), startAngle, backDistance, -8, true);
        drive.forwardWithPower(speed);
        sleep((long)(3/FORWARD_VEL)*4);
        drive.stop();
        sleep(1000);
        Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                (int) (sensorColor.green() * SCALE_FACTOR),
                (int) (sensorColor.blue() * SCALE_FACTOR),
                hsvValues);
        satCenter = hsvValues[1];
        telemetry.addData("Center", satCenter);
        telemetry.update();

        //sensorDrive(new MecanumDrive.Motor.Vector2D(0, -1), startAngle, backDistance, -16, true);
        drive.forwardWithPower(speed);
        sleep((long)(3/FORWARD_VEL)*4);
        drive.stop();
        sleep(1000);
        Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                (int) (sensorColor.green() * SCALE_FACTOR),
                (int) (sensorColor.blue() * SCALE_FACTOR),
                hsvValues);
        satRight = hsvValues[1];
        telemetry.addData("Right",satRight);
        telemetry.update();
        int pos = decidePositionBasedOnVal(satLeft,satCenter,satRight);
        positionOffset = 8
                *(pos+1);
        telemetry.addData("Pos", pos);
        telemetry.update();
        sleep(5000L);
        drive.forwardWithPower(-speed);
        double backVar;
        if(pos==0){
            backVar = 18.5;
        }
        else if(pos==1){
            backVar = 10.5;
        }
        else{
            backVar = 2.5;
        }
        while(backDistance.getDistance(DistanceUnit.INCH)>backVar){
            placeHolder++;
        }
        drive.stop();

        placeHolder=0;
        drive.strafeLeftWithPower(speed);
        while(sensorDistance.getDistance(DistanceUnit.INCH)>2){
            placeHolder++;
        }
        drive.stop();
        holder.lower();
        sleep(2000);
        holder.clamp();
        holder.raise();
        sleep(500);


        //Done with sensing and poistion should be stored in the pos variable.
        /*if (pos == 2) {
            sensorDrive(new MecanumDrive.Motor.Vector2D(0, -1), startAngle, backDistance, -20);
        } else {p 
            sensorDrive(new MecanumDrive.Motor.Vector2D(0, 1), startAngle, backDistance, pos * 8 + 2);
        }

        sensorDrive(new MecanumDrive.Motor.Vector2D(0,-1), startAngle, backDistance, 12-positionOffset);
        // grab brick
        drive.stop();
        holder.lower();
        sleep(500);
        holder.clamp();
        sleep(500);
        holder.raise();
        sleep(500);


        drive.strafeLeftWithPower(-speed); //strafe right a little bit
        sleep((long)(5/STRAFE_VEL));
        drive.stop();
        //sensorDrive(new MecanumDrive.Motor.Vector2D(0, -1), startAngle, frontDistance, 50);
        drive.forwardWithPower(speed);
        sleep((long)(20/FORWARD_VEL));
        drive.stop();
        drive.strafeLeftWithPower(speed);
        sleep((long)(5/STRAFE_VEL));
        drive.stop();
        holder.lower();
        sleep(500);
        holder.release();
        sleep(500);
        holder.raise();
        sleep(500);

        drive.strafeLeftWithPower(-speed);
        sleep((long)(5/STRAFE_VEL));
        drive.stop();
        sensorDrive(new MecanumDrive.Motor.Vector2D(0,-1), startAngle, backDistance, 38-positionOffset);
        drive.stop();
        holder.lower();
        sleep(500);
        holder.clamp();
        sleep(500);
        holder.raise();
        sleep(500);
        // drive.strafeLeftWithPower(speed);//strafe a little left
        // sleep((int)(2/STRAFE_VEL));

        // place brick
         drive.stop();
        holder.lower();
        sleep(500);
        holder.release();
        sleep(500);
        holder.raise();
        sleep(500);

        drive.strafeLeftWithPower(-speed);//strafe right 2 inches
        sleep((long)(2/STRAFE_VEL));
        drive.stop();
        drive.forwardWithPower(-speed);//drive backwards to next set of three skystones
        sleep((long)(96/FORWARD_VEL));
        drive.stop();
        drive.strafeLeftWithPower(speed);
        sleep((long)(5/STRAFE_VEL));
        drive.stop();
        // grab brick
        drive.stop();
        holder.lower();
        sleep(500);
        holder.clamp();
        sleep(500);
        holder.raise();
        sleep(500);

        drive.strafeLeftWithPower(-speed);
        sleep((long)(5/STRAFE_VEL));
        drive.stop();
        drive.forwardWithPower(speed);//drive forward to the building zone
        sleep((long)(120/FORWARD_VEL));
        drive.stop();
        drive.strafeLeftWithPower(speed);//strafe a little left
        sleep((long)(5/FORWARD_VEL));
        drive.stop();
        // place brick
        drive.stop();
        holder.lower();
        sleep(500);
        holder.release();
        sleep(500);
        holder.raise();
        sleep(500);

        drive.strafeLeftWithPower(-speed);//strafe right 5 inches
        sleep((int)(5/STRAFE_VEL));
        drive.stop();
        //back up 40 inches
        drive.forwardWithPower(-speed);
        sleep((int)(40/FORWARD_VEL));
        drive.stop();
        // clamp the foundation
        drive.stop();


        sleep(200);
        if (pos == VuforiaStuff.skystonePos.RIGHT) {

        }
        else {

        }
        */
        if(1==1){
            return;
        }

    }
    public void roboInit() {
        /*int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;


        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);*/

        // MOVEMENT CONSTANTS
        /** average forward velocity of the robot at full power (inches per millisecond) */
        FORWARD_VEL = 0.06 / deltaTime;
        /** average strafing velocity of the robot at full power (inches per millisecond) */
        STRAFE_VEL = 0.0365/ deltaTime;

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
        if(left < center && left < right){
            return 0;
        }
        else if(center < left && center < right){
            return 1;
        }
        else if(right < center && right < left){
            return 2;
        }
        return -1;
    }

    }
