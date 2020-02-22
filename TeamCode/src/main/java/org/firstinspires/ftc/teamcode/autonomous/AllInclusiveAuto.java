package org.firstinspires.ftc.teamcode.autonomous;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.internal.android.dx.cf.attrib.InnerClassList;
import org.firstinspires.ftc.teamcode.movement.BrickHolder;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive;
import org.firstinspires.ftc.teamcode.movement.PlateClamp;

@Autonomous(name = "Red Auto", group = "Autonomous")
public class AllInclusiveAuto extends AutoBaseOpMode {
    private VuforiaLocalizer vuforia;
    //private static final String VUFORIA_KEY = "AdPNlBX/////AAABmVfye0Qoq0efoZI4OrEHeIQSRjhNr9KQMKROnvTahH08r6+kPliev3BPNHMIPuFAdFRiZ28ted7hD7VN11J8ThMrQUdfilKWo6DRpZ6tVR2qvf5HxAIB0DZX3G7dbCfVbNSeal9I5EDQ9DpVgLPJqk0Txk5NTCcco1g32oPU1D3qnIhMLPmco9oSrFwXFIvuwZYtd/iC1kQOpH+32afAE/x2fy7zphkojHhpaNmAEATUYs+63PMnG1hB/0LnHS/JrT3WjK2lHO28ESwRSOU96L9ljHl/lHKfW+397WDSNp2OAFoFhEpmk9dNnM5CPzh8i9BFXNMRj1EEraAQgrGr7sLzIS558bKfDgXHV+4zIMVy";
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
        while(!isStopRequested()){
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
        holder.raise();
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
        double backVar;
        speed=0.125;
        if(pos==2){
            backVar = 18;
            drive.forwardWithPower(speed);
            while(backDistance.getDistance(DistanceUnit.INCH)<backVar){
                placeHolder++;
            }
        }
        else if(pos==1){
            backVar = 11;
            drive.forwardWithPower(-1*speed);
            while(backDistance.getDistance(DistanceUnit.INCH)>backVar){
                placeHolder++;
            }
            drive.stop();
        }
        else{
            backVar = 4;
            drive.forwardWithPower(-1*speed);
            while(backDistance.getDistance(DistanceUnit.INCH)>backVar){
                placeHolder++;
            }
            drive.stop();

        }

        drive.stop();
        placeHolder=0;
        drive.strafeLeftWithPower(0.2);
        while(sensorDistance.getDistance(DistanceUnit.INCH)>1.5){
            placeHolder++;
        }
        speed=0.125;
        drive.stop();

        drive.stop();
        holder.lower();
        holder.lower();
        holder.lower();
        sleep(2000);
        holder.clamp();
        sleep(1000);
        holder.raise();
        holder.raise();
        holder.raise();
        holder.raise();
        sleep(500);
        speed=0.25;
        drive.strafeLeftWithPower(-1*speed);
        sleep((long)(5/STRAFE_VEL)*4);
        placeHolder =0;
        drive.forwardWithPower(speed);
        while(backDistance.getDistance(DistanceUnit.INCH)<40){
            placeHolder++;
        }
        drive.stop();
        sleep(1000);
        drive.forwardWithPower(speed);
        sleep((long)(20/FORWARD_VEL)*4);
        /*placeHolder=0;
        while(frontDistance.getDistance(DistanceUnit.INCH)>30){
            placeHolder++;
        }*/

        drive.stop();
        placeHolder=0;
        drive.forwardWithPower(speed);
        while(sensorDistance.getDistance(DistanceUnit.INCH)>30){
            placeHolder++;
        }
        drive.stop();
        drive.forwardWithPower(speed);
        sleep((long)(13/FORWARD_VEL)*4);
        drive.strafeLeftWithPower(speed);
        placeHolder=0;
        while(sensorDistance.getDistance(DistanceUnit.INCH)>3){
            placeHolder++;
        }
        holder.release();
        sleep(1000);
        //DONE WITH STONE ONE (HOPEFULLY)

        drive.strafeLeftWithPower(-1*speed);
        sleep((long)(5/STRAFE_VEL)*4);
        placeHolder=0;
        drive.stop();
        drive.forwardWithPower(-1*speed);
        sleep((long)(30/FORWARD_VEL)*4);
        drive.stop();
        drive.forwardWithPower(-1*speed);

        while(backDistance.getDistance(DistanceUnit.INCH)>(backVar+30 )){
            placeHolder++;
        }

        drive.stop();
        //holder.release();
        //sleep(500);

        drive.strafeLeftWithPower(speed);
        placeHolder=0;
        while(sensorDistance.getDistance(DistanceUnit.INCH)>2){
            placeHolder++;
        }
        drive.stop();
        holder.lower();
        sleep(4000);
        holder.clamp();
        sleep(1000);
        holder.raise();
        sleep(500);
        speed=0.25;
        drive.strafeLeftWithPower(-1*speed);
        sleep((long)(4/STRAFE_VEL)*4);
        drive.forwardWithPower(speed);
        sleep((long)(41/FORWARD_VEL)*4);
    /*placeHolder=0;
    while(frontDistance.getDistance(DistanceUnit.INCH)>30){
        placeHolder++;
    }*/

        drive.stop();
        drive.strafeLeftWithPower(speed);
        placeHolder=0;
        while(sensorDistance.getDistance(DistanceUnit.INCH)>4){
            placeHolder++;
        }
        drive.stop();
        holder.release();
        //done with second skystone
        sleep(500);
        drive.strafeLeftWithPower(-1*speed);
        sleep((long)(5/STRAFE_VEL)*4);
        drive.stop();
        drive.forwardWithPower(-1*speed);
        sleep((long)(25/FORWARD_VEL)*4);

        drive.stop();


        if(1==1){
            return;
        }}

    }
    public void roboInit() {

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
