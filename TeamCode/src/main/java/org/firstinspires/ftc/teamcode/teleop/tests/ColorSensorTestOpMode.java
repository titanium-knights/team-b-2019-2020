package org.firstinspires.ftc.teamcode.teleop.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import org.firstinspires.ftc.teamcode.sensors.StandardSensors;

@Autonomous(name = "Color Sensor Test Op Mode", group = "Test")
public class ColorSensorTestOpMode extends OpMode {
    private ColorSensor left;
    private ColorSensor right;

    @Override
    public void init() {
        StandardSensors sensors = new StandardSensors(hardwareMap);
        left = sensors.getLeftColorSensor();
        right = sensors.getRightColorSensor();
    }

    @Override
    public void loop() {
        telemetry.addData("Left", (double)left.red() / left.green());
        telemetry.addData("Right", (double)right.red() / right.green());
    }
}