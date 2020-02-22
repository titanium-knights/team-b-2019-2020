package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static android.os.SystemClock.sleep;

public class BrickHolder {

    public Servo armA;
    //public Servo armB;
    public CRServo clawA;
    //public Servo clawB;

    public BrickHolder (Servo armA, CRServo clawA) {
        this.armA = armA;
        //this.armB = armB;
        this.clawA = clawA;
        //this.clawB = clawB;
    }
    public void initialClawPos(){
        clawA.setPower(1);
    }
    public void stop(){
        clawA.setPower(0);
    }
    public void moveArm (double pos) {
        armA.setPosition(pos);
        //armB.setPosition(armB.getPosition() - pos);
    }
    public void moveClaw (int pos) {
        clawA.setPower(1);
        sleep(1000);
        clawA.setPower(0);
    }

    public void clamp () {
        clawA.setPower(1);
    }

    public void release () {
        clawA.setPower(-1) ;
    }

    public void raise () {
        armA.setPosition(0.0);
    }

    public void lower () {
        armA.setPosition(1);

    }

    private static String[] standardMotorNames = {"holder_arm_a", "holder_claw_a"};

    public static BrickHolder standard(HardwareMap hardwareMap) {
        return new BrickHolder(hardwareMap.get(Servo.class, standardMotorNames[0]), hardwareMap.get(CRServo.class, standardMotorNames[1]));
    }

}
