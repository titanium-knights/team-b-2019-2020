package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class BrickHolder {

    public Servo armA;
    public Servo armB;
    public Servo clawA;
    public Servo clawB;

    public BrickHolder (Servo armA, Servo armB, Servo clawA, Servo clawB) {
        this.armA = armA;
        this.armB = armB;
        this.clawA = clawA;
        this.clawB = clawB;
    }
    public void initialClawAPosition(){
        clawA.setPosition(1);
    }
    public void moveArm (int pos) {
        armA.setPosition(armA.getPosition() + pos);
        armB.setPosition(armB.getPosition() - pos);
    }

    public void moveClaw (int pos) {
        clawB.setPosition(clawB.getPosition() + pos);
    }

    public void clamp () {
        clawB.setPosition(1);
    }

    public void release () {
        clawB.setPosition(-1) ;
    }

    public void raise () {
        armA.setPosition(-1);
        armB.setPosition(1);
    }

    public void lower () {
        armA.setPosition(1);
        armB.setPosition(-1);
    }

    private static String[] standardMotorNames = {"holder_arm_a", "holder_arm_b", "holder_claw_a", "holder_claw_b"};

    public static BrickHolder standard(HardwareMap hardwareMap) {
        return new BrickHolder(hardwareMap.get(Servo.class, standardMotorNames[0]), hardwareMap.get(Servo.class, standardMotorNames[1]), hardwareMap.get(Servo.class, standardMotorNames[2]), hardwareMap.get(Servo.class, standardMotorNames[3]));
    }

}
