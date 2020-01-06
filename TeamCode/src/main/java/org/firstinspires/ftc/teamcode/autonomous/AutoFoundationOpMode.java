package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.movement.ElevatorOuttake;
import org.firstinspires.ftc.teamcode.movement.Intake;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive;
import org.firstinspires.ftc.teamcode.movement.PlateClamp;

public class AutoFoundationOpMode extends LinearOpMode {

    private MecanumDrive drive;
    private Intake intake;
    private ElevatorOuttake outtake;
    private PlateClamp plateClamp;

    // MOVEMENT CONSTANTS
    /** average velocity of the robot at full power (inches per millisecond) */
    private final double VELOCITY = 0.027;
    /** average angular velocity of the robot at full power (degrees per millisecond) */
    private final double ANGULAR_VELOCITY = 1;

    @Override
    public void runOpMode() {

        drive = MecanumDrive.standard(hardwareMap);
        intake = Intake.standard(hardwareMap);
        //outtake = ElevatorOuttake.standard(hardwareMap);
        plateClamp = PlateClamp.standard(hardwareMap);

        try {
            drive.forwardWithPower(1);
            Thread.sleep((int)(24 / VELOCITY));
            drive.strafeRightWithPower(1);
            Thread.sleep(1000);
            plateClamp.setDown();
            Thread.sleep(3000);
            drive.forwardWithPower(-1);
            Thread.sleep((int)(24 / VELOCITY));
            plateClamp.setUp();
            Thread.sleep(3000);
            drive.strafeLeftWithPower(1);
            Thread.sleep((int)(6 / VELOCITY));
            drive.stop();

            drive.strafeRightWithPower(1);
            Thread.sleep((int)(22 / VELOCITY));
            drive.stop();
            plateClamp.setDown();
            Thread.sleep(2000);
            drive.strafeLeftWithPower(1);
            Thread.sleep((int)(18 / VELOCITY));
            drive.stop();
            plateClamp.setUp();
            Thread.sleep(2000);
            drive.forwardWithPower(1);
            Thread.sleep((int)(15 / VELOCITY));
            drive.strafeRightWithPower(1);
            Thread.sleep((int)(25 / VELOCITY));
            drive.forwardWithPower(-1);
            Thread.sleep((int)(15 / VELOCITY));
            drive.strafeLeftWithPower(1);
            Thread.sleep((int)(30 / VELOCITY));
            drive.forwardWithPower(1);
            Thread.sleep((int)(15 / VELOCITY));
            drive.strafeLeftWithPower(1);
            Thread.sleep((int)(19 / VELOCITY));
            drive.forwardWithPower(1);
            Thread.sleep((int)(15 / VELOCITY));
            drive.stop();
        } catch (Exception e) {

        }

    }

}
