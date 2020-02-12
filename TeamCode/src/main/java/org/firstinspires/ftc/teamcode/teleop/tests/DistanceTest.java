package org.firstinspires.ftc.teamcode.teleop.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "Distance Test")
public class DistanceTest extends OpMode {
    DistanceSensor leftDistance;
    DistanceSensor frontDistance;
    DistanceSensor backDistance;
    @Override
    public void init() {
        leftDistance = hardwareMap.get(DistanceSensor.class, "sensor_distance");
        backDistance = hardwareMap.get(DistanceSensor.class, "back_distance");
        frontDistance = hardwareMap.get(DistanceSensor.class, "front_distance");
    }
    public void loop(){
        telemetry.addData("Left Distance", leftDistance.getDistance(DistanceUnit.INCH));

        telemetry.addData("Front Distance", frontDistance.getDistance(DistanceUnit.INCH));
        telemetry.addData("Back Distance", backDistance.getDistance(DistanceUnit.INCH));
        telemetry.update();
    }
}
