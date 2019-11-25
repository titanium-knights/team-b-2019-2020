package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Outtake {
    ElapsedTime deltaTime;

    //Servo pickUpServo;
    CRServo claw;
    double clawPos;
    CRServo wrist;
    double wristPos;
    DcMotor arm;

    public Outtake (DcMotor arm, CRServo wrist, CRServo claw) {
        this.arm = arm;
        this.wrist = wrist;
        this.claw = claw;

        clawPos = 0;
        wristPos = 0;
    }

    public void updateTime () {
        deltaTime.reset();
    }

    // Moves the arm in the given direction.
    public void moveArm (boolean direction, boolean limited) {
        int currpos = arm.getCurrentPosition();

        // up = true, down = false
        if (direction && (!limited || currpos < 300)) {
            arm.setDirection(DcMotor.Direction.FORWARD);
        } else if (!direction && (!limited || currpos > 0)) {
            arm.setDirection(DcMotor.Direction.REVERSE);
        }
    }

    // Stops the arm's motor.
    public void stopArm () {
        arm.setPower(0);
    }

    public void moveClaw (boolean clamping, double power) {
        if (clamping && wristPos > 0) {
            claw.setDirection(DcMotorSimple.Direction.FORWARD);
            claw.setPower(power);
        } else if (!clamping && wristPos < 1) {
            claw.setDirection(DcMotorSimple.Direction.REVERSE);
            claw.setPower(power);
        } else {
            claw.setPower(0);
        }
    }

    public void stopClaw () {
        claw.setPower(0);
    }

    public void updateClawTime (boolean clamping) {
        if (clamping && clawPos > 0) {
            clawPos = clawPos - deltaTime.seconds();
        } else if (!clamping && clawPos < 1){
            clawPos = clawPos + deltaTime.seconds();
        }
    }

    public void rotateWrist (boolean left, double power) {
        if (left && wristPos > -1) {
            wrist.setDirection(DcMotorSimple.Direction.FORWARD);
            wrist.setPower(power);
        } else if (!left && wristPos < 1) {
            wrist.setDirection(DcMotorSimple.Direction.REVERSE);
            wrist.setPower(power);
        } else {
            wrist.setPower(0);
        }
    }

    public void stopWrist () {
        wrist.setPower(0);
    }

    public void updateWristTime (boolean left) {
        if (left && clawPos > -1) {
            clawPos = clawPos - deltaTime.seconds();
        } else if (!left && clawPos < 1){
            clawPos = clawPos + deltaTime.seconds();
        }
    }

    private static String[] standardMotorNames = {"outtake_arm", "outtake_wrist", "outtake_claw"};

    public static Outtake standard(HardwareMap hardwareMap) {

        // arm
        DcMotor arm = hardwareMap.get(DcMotor.class, standardMotorNames[0]);
        arm.setDirection(DcMotor.Direction.FORWARD);

        // wrist
        CRServo wrist = hardwareMap.get(CRServo.class, standardMotorNames[1]);
        wrist.setDirection(DcMotorSimple.Direction.FORWARD);

        // claw
        CRServo claw = hardwareMap.get(CRServo.class, standardMotorNames[2]);
        claw.setDirection(DcMotorSimple.Direction.FORWARD);

        return new Outtake(arm, wrist, claw);
    }

}
