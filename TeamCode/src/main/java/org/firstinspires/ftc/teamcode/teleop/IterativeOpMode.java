package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.movement.*;
import org.firstinspires.ftc.teamcode.util.ButtonToggler;

@TeleOp(name = "Tele Op Mode")
public class IterativeOpMode extends OpMode {

    private MecanumDrive drive;
    private Intake intake;
    private ElevatorOuttake elevatorOuttake;
    private ElevatorOuttake2 elevatorOuttake2;
    private BrickHolder brickHolder;
    private TrayPull trayPuller;

    private ButtonToggler flywheelBT = new ButtonToggler();
    private ButtonToggler speedBT = new ButtonToggler();

    private double speedMode;

    @Override
    public void init() {

        brickHolder=BrickHolder.standard(hardwareMap);
        brickHolder.moveArm(0.25);

        drive = MecanumDrive.standard(hardwareMap);
        intake = Intake.standard(hardwareMap);
        elevatorOuttake = ElevatorOuttake.standard(hardwareMap);
        elevatorOuttake2 = ElevatorOuttake2.standard(hardwareMap);
        trayPuller = TrayPull.standard(hardwareMap);

        flywheelBT = new ButtonToggler();
        speedMode = 1.0;

    }

    @Override
    public void loop(){

        // Toggles the speed between fast and slow
        speedBT.ifPress(gamepad1.x);
        speedMode = speedBT.getMode() ? 0.8 : 0.5;
        speedBT.update(gamepad1.x);

        // Gets the speed, strafe, and turn of the robot and accounts for stick drifting
        double speed = gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;
        if (Math.abs(turn) < 0.2) {
            turn = 0;
        }
        if (Math.abs(strafe) < 0.2) {
            strafe = 0;
        }
        if (Math.abs(speed) < 0.2) {
            speed = 0;
        }

        // Drives in the inputted direction.
        MecanumDrive.Motor.Vector2D vector = new MecanumDrive.Motor.Vector2D(strafe, speed);
        drive.move(speedMode, vector, turn);

        // If not driving, check for turning in place or pure strafing
        if (turn == 0 && strafe == 0 && speed == 0) {
            if (gamepad1.left_bumper) {
                drive.strafeLeftWithPower(speedMode);
            } else if (gamepad1.right_bumper) {
                drive.strafeRightWithPower(speedMode);
            } else if (gamepad1.left_trigger > 0.2f) {
                drive.turnInPlace(gamepad1.left_trigger, false);
            } else if (gamepad1.right_trigger > 0.2f) {
                drive.turnInPlace(gamepad1.right_trigger, true);
            }
        }

        // Activate and deactivate pivot flywheels (toggles)
        flywheelBT.ifRelease(gamepad1.y);
        flywheelBT.update(gamepad1.y);

        // Determines if the flywheels spin, spin in reverse, or freeze
        if (flywheelBT.getMode()) {
            intake.spin();
        } else if (gamepad1.b) {
            intake.spinReverse();
        } else {
            intake.stopSpinning();
        }

        // Gets the elevator's movement inputs and accounts for stick drifting
        double elevatorHeight = -gamepad2.left_stick_y;
        double clawDistance = gamepad2.right_stick_y;
        if (Math.abs(elevatorHeight) < 0.2) {
            elevatorHeight = 0;
        }
        if (Math.abs(clawDistance) < 0.2) {
            clawDistance = 0;
        }

        // Moves the elevators and controls the claw and tray pullers
        elevatorOuttake.moveElevators(elevatorHeight, clawDistance);
        try {
            if (gamepad2.dpad_up) {
                elevatorOuttake2.grab();
                telemetry.addData("gamepad 2-up Pressed ", true);
                //elevatorOuttake.grabClamp();
            } else if (gamepad2.dpad_down) {
                elevatorOuttake2.release();
                telemetry.addData("gamepad2-down Pressed", true);
                Thread.sleep(250);
                elevatorOuttake2.stop();
            }
        }
        catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        // Raise or lower the tray pullers
        if(gamepad1.dpad_down){
            trayPuller.down();
        }
        if(gamepad1.dpad_up){
            trayPuller.up();
        }

        // Telemetry data
        telemetry.addData("Speed Mode", speedBT.getMode() ? "Fast" : "Slow");
        telemetry.addData("Speed", speed);
        telemetry.addData("Strafe", strafe);
        telemetry.addData("Turn", turn);
        telemetry.addData("Elevator Height", elevatorHeight);
        telemetry.addData("Claw Distance", clawDistance);
        telemetry.addData("Vertical Elevator Encoder", elevatorOuttake.getVerticalEncoder());
        telemetry.addData("Horizontal Elevator Encoder", elevatorOuttake.getHorizontalEncoder());
    }

}