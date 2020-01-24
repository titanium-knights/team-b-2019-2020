package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class BrickHolder {

    public Servo arm;
    public Servo claw;

    public BrickHolder (Servo arm, Servo claw) {
        this.arm = arm;
        this.claw = claw;
    }

    public void moveArm (int pos) {
        arm.setPosition(arm.getPosition() + pos);
    }

    public void moveClaw (int pos) {
        claw.setPosition(claw.getPosition() + pos);
    }

    public void clamp () {

    }

    public void release () {

    }

    public void raise () {

    }

    public void lower () {

    }

    static String[] standardMotorNames = {"holder_arm", "holder_claw"};

    public static BrickHolder standard(HardwareMap hardwareMap) {
        return new BrickHolder(hardwareMap.get(Servo.class, standardMotorNames[0]), hardwareMap.get(Servo.class, standardMotorNames[1]));
    }

}
