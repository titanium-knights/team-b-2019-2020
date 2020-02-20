package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.movement.*;

import org.firstinspires.ftc.teamcode.util.ButtonBoolean;
import org.firstinspires.ftc.teamcode.util.ButtonSelector;
import org.firstinspires.ftc.teamcode.util.ButtonToggler;

import static java.lang.Thread.sleep;

@TeleOp(name = "Tele Op Mode")
public class IterativeOpMode extends OpMode {
    private MecanumDrive drive;
    private Intake intake;

    private ElevatorOuttake elevatorOuttake;
    private ElevatorOuttake2 elevatorOuttake2;

    private PlateClamp plateClamp;
    private TrayPull trayPuller;
    // private BrickHook brickHook;

    private ButtonToggler flywheelBT;
    private ButtonToggler trayBT;
    private ButtonToggler overrideBT = new ButtonToggler();
    private ButtonToggler downBT = new ButtonToggler();
    private ButtonToggler midBT = new ButtonToggler();
    private ButtonToggler upBT = new ButtonToggler();
    private ButtonToggler speedBT = new ButtonToggler();
    public MecanumDrive.Motor.Vector2D bump;
    private ButtonBoolean intakePower ;
    private ButtonBoolean intakeDirection;
    /*
        intakePower.get() --> gives you the state (true or false) of the toggler
     */

    private ElapsedTime elapsedTime;

    private double speedMode;

    private double lastLoop = -1;

    @Override
    public void init() {
        drive = MecanumDrive.standard(hardwareMap);
        intake = Intake.standard(hardwareMap);
        elevatorOuttake = ElevatorOuttake.standard(hardwareMap);
        elevatorOuttake2 = ElevatorOuttake2.standard(hardwareMap);
        trayPuller = TrayPull.standard(hardwareMap);
        // brickHook = BrickHook.standard(hardwareMap);

        flywheelBT = new ButtonToggler();
        trayBT = new ButtonToggler();
        elapsedTime = new ElapsedTime();
        intakePower = new ButtonBoolean(gamepad2, "dpad_down");
        intakeDirection = new ButtonBoolean(gamepad2, "dpad_down");
        MecanumDrive.Motor.Vector2D bump;
        speedMode = 1.0;
    }

    @Override
    public void loop(){
        double seconds = elapsedTime.seconds();
        double delta = (lastLoop < 0) ? 0 : (seconds - lastLoop);
        lastLoop = seconds;

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
                telemetry.addData("MISC DRIVING", "Left Bumper");
                drive.turnInPlace(speedMode, true);
            } else if (gamepad1.right_bumper) {
                telemetry.addData("MISC DRIVING", "Right Bumper");
                drive.turnInPlace(speedMode, false);
            } else if (gamepad1.left_trigger > 0.2f) {
                telemetry.addData("MISC DRIVING", "Left Trigger");
                drive.strafeLeftWithPower(gamepad1.left_trigger);
            } else if (gamepad1.right_trigger > 0.2f) {
                telemetry.addData("MISC DRIVING", "Right Trigger");
                drive.strafeRightWithPower(gamepad1.right_trigger);
            } else {
                telemetry.addData("MISC DRIVING", "none");
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

        if(gamepad1.dpad_down){
            trayPuller.down();
            telemetry.addData("gamepad1-Down Pressed", true);
        }
        if(gamepad1.dpad_up){
            trayPuller.up();
            telemetry.addData("gamepad1-Up Pressed", true);
        }

        /*if(gamepad2.dpad_left){
            elevatorOuttake.moveToEncoder(0,100);
        }*/

        /*
        if (gamepad2.dpad_left) {
            brickHook.release();
        } else if (gamepad2.dpad_right) {
            brickHook.clamp();
        } else {
            brickHook.stop();
        }
         */

        // Telemetry data
        telemetry.addData("Speed Mode", speedBT.getMode() ? "Fast" : "Slow");

        telemetry.addData("Speed", speed);
        telemetry.addData("Strafe", strafe);
        telemetry.addData("Turn", turn);
        telemetry.addData("Elevator Height", elevatorHeight);
        telemetry.addData("Claw Distance", clawDistance);
        telemetry.addData("Delta Time", delta);
        telemetry.addData("Vertical Elevator Encoder", elevatorOuttake.getVerticalEncoder());
        telemetry.addData("Horizontal Elevator Encoder", elevatorOuttake.getHorizontalEncoder());
    }

}