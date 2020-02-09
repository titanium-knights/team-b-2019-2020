package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class BrickHolder {

    public Servo armA;
    //public Servo armB;
    public Servo clawA;
    //public Servo clawB;

    public BrickHolder (Servo armA, Servo clawA) {
        this.armA = armA;
        //this.armB = armB;
        this.clawA = clawA;
        //this.clawB = clawB;
    }
    public void moveArm (int pos) {
        armA.setPosition(armA.getPosition() + pos);
        //armB.setPosition(armB.getPosition() - pos);
    }
    public void moveClaw (int pos) {
        clawA.setPosition(clawA.getPosition() + pos);
    }

    public void clamp () {
        clawA.setPosition(1);
    }

    public void release () {
        clawA.setPosition(0) ;
    }

    public void raise () {
        armA.setPosition(0);
    }

    public void lower () {
        armA.setPosition(1);
    }

    private static String[] standardMotorNames = {"holder_arm_a", "holder_claw_a"};

    public static BrickHolder standard(HardwareMap hardwareMap) {
        return new BrickHolder(hardwareMap.get(Servo.class, standardMotorNames[0]), hardwareMap.get(Servo.class, standardMotorNames[1]));
    }

}
