package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.movement.Intake;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive;

import org.firstinspires.ftc.teamcode.movement.Outtake;
import org.firstinspires.ftc.teamcode.util.Utils;
import org.firstinspires.ftc.teamcode.util.ButtonTracker;

@TeleOp(name = "Test Mode")
public class IterativeOpMode extends OpMode {
    //private MecanumDrive drive;
    private Intake intake;
    private Outtake outtake;

    private ButtonTracker flywheelBT;

    @Override
    public void init() {
        //drive = MecanumDrive.standard(hardwareMap);
        intake = Intake.standard(hardwareMap);
        outtake = Outtake.standard(hardwareMap);

        flywheelBT = new ButtonTracker();
    }

    @Override
    public void loop() {
        /*
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
         */

        // Activate and deactivate pivot flywheels (toggles)
        flywheelBT.ifPress(gamepad1.y);

        // Either spins or doesn't depending on mode
        if (flywheelBT.getMode()) {
            intake.spin();
        } else {
            intake.stopSpinning();
        }

        // Update BTs
        flywheelBT.update(gamepad1.y);

        // Update outtake deltaTime
        outtake.updateTime();

        // Raise and lower arm
        if (gamepad1.dpad_down) {
            outtake.moveArm(false, true);
        }
        else if (gamepad1.dpad_up) {
            outtake.moveArm(true, true);
        } else {
            outtake.stopArm();
        }

        // Rotate wrist
        if (gamepad1.dpad_left) {
            outtake.rotateWrist(true, 1);
            outtake.updateWristTime(true);
        } else if (gamepad1.dpad_right) {
            outtake.rotateWrist(false, 1);
            outtake.updateWristTime(false);
        } else {
            outtake.stopWrist();
        }

        // Clamp claw
        if (gamepad1.left_bumper) {
            outtake.moveClaw(true, 1);
            outtake.updateClawTime(true);
        } else if (gamepad1.right_bumper) {
            outtake.moveClaw(false,1);
            outtake.updateClawTime(false);
        } else {
            outtake.stopClaw();
        }

        // Telemetry data
        /*
        telemetry.addData("Speed", speed);
        telemetry.addData("Strafe", strafe);
        telemetry.addData("Turn", turn);
         */

    }
}
