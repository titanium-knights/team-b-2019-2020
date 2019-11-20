package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.Servo;

public class Outtake {
    Servo pickUpServo;

    void pickUp() {
        pickUpServo.setPosition(0.25);
    }
    void dropServo() {
        pickUpServo.setPosition(0);
    }
}
