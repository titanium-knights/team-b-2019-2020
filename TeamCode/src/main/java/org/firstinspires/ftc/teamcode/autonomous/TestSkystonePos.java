package org.firstinspires.ftc.teamcode.autonomous;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "SkystonePos Test")
public class TestSkystonePos extends OpMode {
    DistanceSensor leftDistance;
    DistanceSensor frontDistance;
    DistanceSensor backDistance;
    ColorSensor sensorColor;
    private static double SCALE_FACTOR;
    private static float hsvValues[] = {0F, 0F, 0F};
    static float satLeft=0f;
    static float satCenter=0f;
    static float satRight=0f;

    @Override
    public void init() {
        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color");
        leftDistance = hardwareMap.get(DistanceSensor.class, "sensor_distance");
        backDistance = hardwareMap.get(DistanceSensor.class, "back_distance");
        frontDistance = hardwareMap.get(DistanceSensor.class, "front_distance");
        SCALE_FACTOR =255;

    }

    @Override
    public void loop(){

        telemetry.addData("Left Distance", leftDistance.getDistance(DistanceUnit.INCH));
        telemetry.addData("Front Distance", frontDistance.getDistance(DistanceUnit.INCH));
        telemetry.addData("Back Distance", backDistance.getDistance(DistanceUnit.INCH));

        telemetry.addData("red", sensorColor.red());
        telemetry.addData("green", sensorColor.green());
        telemetry.addData("blue", sensorColor.blue());
        Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                (int) (sensorColor.green() * SCALE_FACTOR),
                (int) (sensorColor.blue() * SCALE_FACTOR),
                hsvValues);
        telemetry.addData("Color", hsvValues[1]);

        if(gamepad1.y) {
            telemetry.addLine("y pressed");
            satLeft = hsvValues[1];
        }
        if(gamepad1.b) {
            satCenter = hsvValues[1];
        }
        if(gamepad1.a) {
            satRight = hsvValues[1];
        }
        telemetry.addData("skystone position",decidePositionBasedOnVal(satLeft,satCenter,satRight));
        telemetry.update();
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
