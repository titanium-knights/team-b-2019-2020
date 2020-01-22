package org.firstinspires.ftc.teamcode.teleop.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Servo Strength Tester")
public class ServoStrengthTest extends OpMode {

    Servo servo;

    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class, "big_buff_servo");
        servo.setPosition(0);
    }

    @Override
    public void loop() {

    }

}
