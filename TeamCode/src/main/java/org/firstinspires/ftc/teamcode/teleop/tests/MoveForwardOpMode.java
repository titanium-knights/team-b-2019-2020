package org.firstinspires.ftc.teamcode.teleop.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive;

@TeleOp(name = "Move Forward Op Mode")
public class MoveForwardOpMode extends OpMode {
    MecanumDrive drive;

    @Override
    public void init() {
        drive = MecanumDrive.standard(hardwareMap);
    }

    @Override
    public void loop() {
        boolean buttonPressed = gamepad1.x;

        if (buttonPressed) {
            drive.forwardWithPower(1);
        } else {
            drive.forwardWithPower(0);
        }
    }
}
