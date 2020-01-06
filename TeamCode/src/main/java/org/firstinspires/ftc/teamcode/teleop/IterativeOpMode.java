package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.movement.*;

import org.firstinspires.ftc.teamcode.util.Utils;
import org.firstinspires.ftc.teamcode.util.ButtonTracker;

@TeleOp(name = "Tele Op Mode")
public class IterativeOpMode extends OpMode {
    private MecanumDrive drive;
    private Intake intake;
    //private StonePusher stonePusher;

    //private Outtake outtake;
    //private ElevatorOuttake elevatorOuttake;

    private PlateClamp plateClamp;

    private ButtonTracker flywheelBT;
    private ButtonTracker overrideBT = new ButtonTracker();
    private ButtonTracker downBT = new ButtonTracker();
    private ButtonTracker midBT = new ButtonTracker();
    private ButtonTracker upBT = new ButtonTracker();

    private ElapsedTime elapsedTime;

    private double lastLoop = -1;

    @Override
    public void init() {
        drive = MecanumDrive.standard(hardwareMap);
        intake = Intake.standard(hardwareMap);
        //stonePusher = StonePusher.standard(hardwareMap);
        //outtake = Outtake.standard(hardwareMap);
        //elevatorOuttake = ElevatorOuttake.standard(hardwareMap);
        plateClamp = PlateClamp.standard(hardwareMap);

        flywheelBT = new ButtonTracker();
        elapsedTime = new ElapsedTime();
    }

    @Override
    public void loop() {
        double seconds = elapsedTime.seconds();
        double delta = (lastLoop < 0) ? 0 : (seconds - lastLoop);
        lastLoop = seconds;

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
        MecanumDrive.Motor.Vector2D vector = new MecanumDrive.Motor.Vector2D(strafe, -speed);
        drive.move(1, vector, turn);


        // Activate and deactivate pivot flywheels (toggles)
        flywheelBT.ifRelease(gamepad1.y);
        flywheelBT.update(gamepad1.y);

        // Either spins or doesn't depending on mode
        if (flywheelBT.getMode()) {
            intake.spin();
        } else if (gamepad1.b) {
            intake.spinReverse();
        } else {
            intake.stopSpinning();
        }

        overrideBT.ifRelease(gamepad2.back);
        overrideBT.update(gamepad2.back);
        boolean enforceLimits = false; // Super secret escape button

        // move the intake pusher
        // "DEPRECATED"
        /*
        if (gamepad2.x) {
            stonePusher.pushStone(true, enforceLimits);
        } else if (gamepad2.b) {
            stonePusher.pushStone(false, enforceLimits);
        } else {
            stonePusher.stopPusher();
        }
         */

        /*
        // Update outtake deltaTime
        outtake.updateTime();

        // Raise and lower arm
        double armPower = gamepad2.left_stick_y;
        armPower = Utils.accountDrift(armPower, 0) ? 0 : armPower;
        if (armPower > 0) {
            armPower *= 0.8;                                                                                                                                                                                                                                    ;
        } else {
            armPower *= 0.3;
        }
        outtake.moveArm(armPower, enforceLimits);

        // Rotate wrist
        if (gamepad2.left_bumper) {
            outtake.rotateWrist(true, 1);
            outtake.updateWristTime(true);
        } else if (gamepad2.right_bumper) {
            outtake.rotateWrist(false, 1);
            outtake.updateWristTime(false);
        } else {
            outtake.stopWrist();
        }

        // Clamp claw
        if (gamepad2.y) {
            outtake.moveClaw(true, 1);
            outtake.updateClawTime(true);
        } else if (gamepad2.a) {
            outtake.moveClaw(false,1);
            outtake.updateClawTime(false);
        } else {
            outtake.stopClaw();
        } */

        boolean down = downBT.ifRelease(gamepad2.dpad_down);
        boolean mid = midBT.ifRelease(gamepad2.dpad_left);
        boolean up = upBT.ifRelease(gamepad2.dpad_up);

        downBT.update(gamepad2.dpad_down);
        midBT.update(gamepad2.dpad_left);
        upBT.update(gamepad2.dpad_up);

        if (down) {
            plateClamp.setDown();
        } else if (mid) {
            plateClamp.setMid();
        } else if (up) {
            plateClamp.setUp();
        }

        double elevatorHeight = gamepad2.left_stick_y;
        double clawDistance = gamepad2.right_stick_y;

        if (Utils.accountDrift(0, elevatorHeight)) {
            elevatorHeight = 0;
        } if (Utils.accountDrift(0, clawDistance)) {
            clawDistance = 0;
        }

        //elevatorOuttake.moveElevators(elevatorHeight, clawDistance);

        // Telemetry data
        telemetry.addData("Speed", speed);
        telemetry.addData("Strafe", strafe);
        telemetry.addData("Turn", turn);
        telemetry.addData("Elevator Height", elevatorHeight);
        telemetry.addData("Claw Distance", clawDistance);
        telemetry.addData("Enforcing Limits", enforceLimits ? "Yes" : "No");
        telemetry.addData("Delta Time", delta);
        // telemetry.addData("Wrist Pos", outtake.getWristPosition());
    }
}
