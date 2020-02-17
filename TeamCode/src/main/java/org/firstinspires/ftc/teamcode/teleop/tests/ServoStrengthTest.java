package org.firstinspires.ftc.teamcode.teleop.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import static java.lang.Thread.sleep;

@Autonomous(name = "Servo Strength Tester")
public class ServoStrengthTest extends OpMode {

    Servo servo;
    Servo servo2;
    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class, "tray_puller_l");
        servo2 = hardwareMap.get(Servo.class, "tray_puller_r");
        servo.setPosition(-1);
        servo2.setPosition(1);
    }

    @Override
    public void loop() {
        servo.setPosition(0);
        servo2.setPosition(1);
        try {
            sleep(1000);
        }
        catch (InterruptedException e){
            throw new RuntimeException(e);
        }

    }

}
