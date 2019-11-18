package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.movement.Intake;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive;

import org.firstinspires.ftc.teamcode.util.Utils;
import org.firstinspires.ftc.teamcode.util.ButtonTracker;

public class IterativeOpMode extends OpMode {
    private MecanumDrive drive;
    private Intake intake;
    private ButtonTracker flywheelBT;

    @Override
    public void init() {
        drive = MecanumDrive.standard(hardwareMap);
        intake = Intake.standard(hardwareMap);
    }

    @Override
    public void loop() {
        double speed = gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;

        // Account for stick drifting
        if (Utils.accountDrift(strafe, speed)) {
            strafe = 0;
            speed = 0;
        } if (Utils.accountDrift(turn, 0)) {
            turn = 0;
        }

        // Drive in the inputted direction.
        MecanumDrive.Motor.Vector2D vector = new MecanumDrive.Motor.Vector2D(strafe, speed);
        drive.move(1, vector, turn, MecanumDrive.TurnBehavior.ADDSUBTRACT);

        // Activate and deactivate pivot flywheels
        flywheelBT.ifPress(gamepad1.y);
        if (flywheelBT.getMode()) {
            intake.spin();
        } else {
            intake.stopSpinning();
        }

        // Update BTs
        flywheelBT.update(gamepad1.y);

        // Telemetry data
        telemetry.addData("Speed", speed);
        telemetry.addData("Strafe", strafe);
        telemetry.addData("Turn", turn);

    }
}
