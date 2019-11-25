package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Outtake {
    //Servo pickUpServo;
    CRServo pickUpServo;
    double pickUpTime;

    void pickUp() {
        //pickUpServo.setPosition(0.25);
    }
    void dropServo() {
        //pickUpServo.setPosition(0);
    }

    Motor arm;

    public static class Motor {
        String name = null;
        DcMotor motor;

        public Motor(DcMotor motor) {
            this.motor = motor;
        }
    }

    // Moves the arm in the given direction.
    public void moveArm (boolean direction, boolean limited) {
        int currpos = arm.motor.getCurrentPosition();

        // up = true, down = false
        if (direction && (!limited || currpos < 300)) {
            arm.motor.setDirection(DcMotor.Direction.FORWARD);
        } else if (!direction && (!limited || currpos > 0)) {
            arm.motor.setDirection(DcMotor.Direction.REVERSE);
        }
    }

    // Stops the arm's motor.
    public void stopArm () {
        arm.motor.setPower(0);
    }

    public void moveClawIn (double power) {
        if (pickUpTime > 0) {
            pickUpServo.setDirection(DcMotorSimple.Direction.REVERSE);
        }
    }

    public void moveClawOut (double power) {
        if (pickUpTime < 1) {
            pickUpServo.setDirection(DcMotorSimple.Direction.FORWARD);
        }
    }

    public void stopClaw () {
        pickUpServo.setPower(0);
    }

    public void updateClawTime (double time, boolean turningIn) {
        if (turningIn) {
            pickUpTime = pickUpTime - time;
        } else {
            pickUpTime = pickUpTime + time;
        }
    }

}
