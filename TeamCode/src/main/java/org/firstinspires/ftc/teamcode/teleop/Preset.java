package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.movement.BrickHook;
import org.firstinspires.ftc.teamcode.movement.ElevatorOuttake;
import org.firstinspires.ftc.teamcode.movement.Intake;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive;
import org.firstinspires.ftc.teamcode.movement.PlateClamp;
import org.firstinspires.ftc.teamcode.util.ButtonBoolean;
import org.firstinspires.ftc.teamcode.util.ButtonToggler;

@TeleOp(name = "Preset")
public class Preset extends OpMode {
    private MecanumDrive drive;
    private Intake intake;

    private ElevatorOuttake elevatorOuttake;

    private PlateClamp plateClamp;
    private BrickHook brickHook;

    private ButtonToggler flywheelBT;
    private ButtonToggler overrideBT = new ButtonToggler();
    private ButtonToggler downBT = new ButtonToggler();
    private ButtonToggler midBT = new ButtonToggler();
    private ButtonToggler upBT = new ButtonToggler();

    private ButtonBoolean intakePower = new ButtonBoolean(gamepad2, "dpad_up");
    private ButtonBoolean intakeDirection = new ButtonBoolean(gamepad2, "dpad_down");
    /*
        intakePower.get() --> gives you the state (true or false) of the toggler
     */

    private ElapsedTime elapsedTime;

    private double lastLoop = -1;

    @Override
    public void init() {
        drive = MecanumDrive.standard(hardwareMap);
        intake = Intake.standard(hardwareMap);
        elevatorOuttake = ElevatorOuttake.standard(hardwareMap);
        plateClamp = PlateClamp.standard(hardwareMap);
        brickHook = BrickHook.standard(hardwareMap);
        elevatorOuttake.setEncoders();
        flywheelBT = new ButtonToggler();
        elapsedTime = new ElapsedTime();
    }

    @Override
    public void loop() {
        double seconds = elapsedTime.seconds();
        double delta = (lastLoop < 0) ? 0 : (seconds - lastLoop);
        lastLoop = seconds;

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

        // Drive in the inputted direction.
        MecanumDrive.Motor.Vector2D vector = new MecanumDrive.Motor.Vector2D(strafe, speed);
        drive.move(1, vector, turn);

        // Activate and deactivate pivot flywheels (toggles)
        flywheelBT.ifRelease(gamepad1.y);
        flywheelBT.update(gamepad1.y);

        // Either spins or doesn't depending on mode
        /* fix this code jason!
        if (flywheelBT.getMode()) {
            intake.spin();
        } else if (gamepad1.b) {
            intake.spinReverse();
        } else {
            intake.stopSpinning();
        }

         */

        double elevatorHeight = gamepad2.left_stick_y;
        double clawDistance = gamepad2.right_stick_y;
        boolean ltBtn = gamepad2.right_bumper;
        if(ltBtn){
            elevatorOuttake.moveToEncoder(0,100);
        }
        if (Math.abs(elevatorHeight) < 0.2) {
            elevatorHeight = 0;
        }
        if (Math.abs(clawDistance) < 0.2) {
            clawDistance = 0;
        }

        elevatorOuttake.moveElevators(elevatorHeight, clawDistance);

        if (gamepad2.dpad_up) {
            elevatorOuttake.moveClamp(1);
        } else if (gamepad2.dpad_down) {
            elevatorOuttake.moveClamp(-1);
        } else {
            elevatorOuttake.stopClamp();
        }

        if (gamepad2.dpad_left) {
            brickHook.release();
        } else if (gamepad2.dpad_right) {
            brickHook.clamp();
        } else {
            brickHook.stop();
        }

        // Telemetry data
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