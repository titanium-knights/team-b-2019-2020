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

@Autonomous(name = "Fast Blue Auto", group = "Autonomous")
public class FastBlueAuto extends AutoBaseOpMode {
    double stoneDiff;
    double stoneDiff2;
    private static ColorSensor sensorColor;
    private static DistanceSensor sensorDistance;
    private static DistanceSensor backDistance;
    private static DistanceSensor backLeftDistance;
    private static DistanceSensor frontDistance;
    double driftAdjustment = -3;

    private PlateClamp clamp;
    private BrickHolder holder;
    private int sideModifier  =1;
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
        backLeftDistance = hardwareMap.get(DistanceSensor.class, "front_distance");
        holder = BrickHolder.standard(hardwareMap);

        //pos = vuforiaStuff.vuforiascan(false, false);
        float satLeft;
        float satCenter;
        float satRight;
        roboInit();
        sensorColor.enableLed(true);

        long placeHolder=0;
        speed=0.4;
        drive.strafeLeftWithPower(speed);
        while(sensorDistance.getDistance(DistanceUnit.INCH)>5){
            placeHolder++;
        }
        drive.stop();
        drive.forwardWithPower(-1*speed);
        placeHolder=0;
        while(frontDistance.getDistance(DistanceUnit.INCH)>124){
            placeHolder++;
        }
        Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                (int) (sensorColor.green() * SCALE_FACTOR),
                (int) (sensorColor.blue() * SCALE_FACTOR),
                hsvValues);
        satLeft = hsvValues[1];
        telemetry.addData("First", satLeft);
        telemetry.update();
        //sensorDrive(new MecanumDrive.Motor.Vector2D(0, -1), startAngle, backDistance, -8, true);
        drive.forwardWithPower(speed);
        sleep((long) ((3 / FORWARD_VEL) * 1.5));
        drive.stop();
        sleep(250);
        Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                (int) (sensorColor.green() * SCALE_FACTOR),
                (int) (sensorColor.blue() * SCALE_FACTOR),
                hsvValues);
        satCenter = hsvValues[1];
        telemetry.addData("Center", satCenter);
        telemetry.update();

        drive.forwardWithPower(speed);
        sleep((long) ((3 / FORWARD_VEL) * 1.5));
        drive.stop();
        sleep(250);
        Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                (int) (sensorColor.green() * SCALE_FACTOR),
                (int) (sensorColor.blue() * SCALE_FACTOR),
                hsvValues);
        satRight = hsvValues[1];
        telemetry.addData("Right", satRight);
        telemetry.update();
        int pos = decidePositionBasedOnVal(satLeft, satCenter, satRight);
        positionOffset = 8
                * (pos + 1);
        telemetry.addData("Pos", pos);
        telemetry.update();
        double frontVar;
        if (pos == 2) {
            frontVar = 44;
            drive.forwardWithPower(speed);
            while (frontDistance.getDistance(DistanceUnit.INCH) < frontVar) {
                placeHolder++;
            }
        } else if (pos == 1) {
            frontVar = 35;
            drive.forwardWithPower(speed);
            while (frontDistance.getDistance(DistanceUnit.INCH) > frontVar) {
                placeHolder++;
            }
            drive.stop();
        } else {
            frontVar = 29;
            drive.forwardWithPower(speed);
            while (frontDistance.getDistance(DistanceUnit.INCH) > frontVar) {
                placeHolder++;
            }
            drive.stop();

        }
        drive.stop();
        holder.lower();
        sleep(350);
        holder.clamp();

        sleep(500);
        holder.raise();
        sleep(250);
        speed = 0.4;
        drive.strafeLeftWithPower(-1*speed);
        sleep((long)((6/STRAFE_VEL)*2.5));
        speed=0.5;
        drive.forwardWithPower(-1*speed);
        placeHolder=0;
        while (frontDistance.getDistance(DistanceUnit.INCH) < 40) {
            placeHolder++;
        }
        drive.stop();

        drive.forwardWithPower(-1*speed);
        sleep((long)((30/FORWARD_VEL)));
        /*placeHolder=0;
        while(frontDistance.getDistance(DistanceUnit.INCH)>30){
            placeHolder++;
        }*/
        drive.stop();
        placeHolder = 0;
        drive.forwardWithPower(-1*speed);
        while (sensorDistance.getDistance(DistanceUnit.INCH) > 30) {
            placeHolder++;
        }
        drive.stop();
        drive.forwardWithPower(-1*speed);
        sleep((long) ((10 / FORWARD_VEL) * 2));
        drive.stop();
        drive.stop();
        speed=0.4;
        drive.strafeLeftWithPower(speed);
        placeHolder = 0;
        while (sensorDistance.getDistance(DistanceUnit.INCH) > 4) {
            placeHolder++;
        }
        drive.stop();
        holder.release();
        sleep(1000);
        //DONE WITH STONE ONE (HOPEFULLY)

        drive.strafeLeftWithPower(-1 * speed);
        sleep((long) ((3 / STRAFE_VEL) * 2));
        placeHolder = 0;
        drive.stop();
        speed=0.5;
        drive.forwardWithPower(speed);
        sleep((long) ((30 / FORWARD_VEL) * 2));
        drive.stop();
        drive.forwardWithPower(speed);
        speed=0.4;
        placeHolder=0;
        while(frontDistance.getDistance(DistanceUnit.INCH)>20){
            placeHolder++;
        }

        drive.stop();
        //holder.release();
        //sleep(500);
        drive.strafeLeftWithPower(speed);
        placeHolder = 0;
        while (sensorDistance.getDistance(DistanceUnit.INCH) > 4.5) {
            placeHolder++;
        }
        drive.stop();
        placeHolder=0;
        speed=0.25;
        drive.forwardWithPower(speed);
        if(pos==2){
            while(backDistance.getDistance(DistanceUnit.INCH)<(frontVar+22)){
                placeHolder++;
            }
        }
        speed=0.4;
        drive.stop();
        drive.stop();
        holder.lower();
        sleep(350);
        holder.clamp();

        sleep(500);
        holder.raise();
        sleep(250);
        speed = 0.4;
        drive.strafeLeftWithPower(-1 * speed);
        sleep((long) ((3 / STRAFE_VEL) * 2));
        drive.forwardWithPower(-1*speed);
        sleep((long)(50/FORWARD_VEL));
    /*placeHolder=0;
    while(frontDistance.getDistance(DistanceUnit.INCH)>30){
        placeHolder++;
    }*/

        drive.stop();
        drive.strafeLeftWithPower(speed);
        placeHolder = 0;
        while (sensorDistance.getDistance(DistanceUnit.INCH) > 4) {
            placeHolder++;
        }

        drive.stop();
        holder.release();
        //done with second skystone
        sleep(500);
        drive.strafeLeftWithPower(-1 * speed);
        sleep((long) (5 / STRAFE_VEL) * 2);
        drive.stop();
        drive.forwardWithPower(speed);
        sleep((long) ((26 / FORWARD_VEL) * 1.5));

        drive.stop();


        if (1 == 1) {
            return;
        }
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
            return 2;
        }
        else if(center < left && center < right){
            return 1;
        }
        else if(right < center && right < left){
            return 0;
        }
        return -1;
    }

}
