package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.movement.BrickHook;
import org.firstinspires.ftc.teamcode.movement.Intake;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive;
import org.firstinspires.ftc.teamcode.movement.PlateClamp;
import org.firstinspires.ftc.teamcode.util.ButtonTracker;
import org.firstinspires.ftc.teamcode.util.Utils;

@TeleOp(name = "Auto Funct Test Mode")
public class ManualAutoTestOpMode extends OpMode {
    private MecanumDrive drive;
    private Intake intake;
    private BrickHook brickHook;
    //private StonePusher stonePusher;

    //private Outtake outtake;
    //private ElevatorOuttake elevatorOuttake;

    private PlateClamp plateClamp;

    private ButtonTracker flywheelBT;
    private ButtonTracker overrideBT = new ButtonTracker();
    private ButtonTracker downBT = new ButtonTracker();
    private ButtonTracker midBT = new ButtonTracker();
    private ButtonTracker upBT = new ButtonTracker();

    /** average forward velocity of the robot at full power (inches per millisecond) */
    private final double FORWARD_VEL = 0.0347;
    /** average strafing velocity of the robot at full power (inches per millisecond) */
    private final double STRAFE_VEL = 0.0257;

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
        brickHook = BrickHook.standard(hardwareMap);

        flywheelBT = new ButtonTracker();
        elapsedTime = new ElapsedTime();
    }

    @Override
    public void loop() {

        if (gamepad1.dpad_up) {
            drive.forwardWithPower(1);
        } else if (gamepad1.dpad_down) {
            drive.forwardWithPower(-1);
        } else if (gamepad1.dpad_left) {
            drive.strafeLeftWithPower(1);
        } else if (gamepad1.dpad_right) {
            drive.strafeRightWithPower(1);
        } else{
            drive.stop();
        }

        if (gamepad1.a) {
            brickHook.clamp();
        } else if (gamepad1.b) {
            brickHook.release();
        } else {
            brickHook.stop();
        }

        telemetry.addData("forward ms per foot", 12 / FORWARD_VEL);
        telemetry.addData("strafe  ms per foot", 12 / STRAFE_VEL);

    }
}
