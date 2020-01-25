package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class BrickHolder {

    public Servo armA;
    public Servo armB;
    public Servo claw;

    public BrickHolder (Servo armA, Servo armB, Servo claw) {
        this.armA = armA;
        this.armB = armB;
        this.claw = claw;
    }

    public void moveArm (int pos) {
        armA.setPosition(armA.getPosition() + pos);
        armB.setPosition(armB.getPosition() - pos);
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

    private static String[] standardMotorNames = {"holder_arm_a", "holder_arm_b", "holder_claw"};

    public static BrickHolder standard(HardwareMap hardwareMap) {
        return new BrickHolder(hardwareMap.get(Servo.class, standardMotorNames[0]), hardwareMap.get(Servo.class, standardMotorNames[1]), hardwareMap.get(Servo.class, standardMotorNames[2]));
    }

}
