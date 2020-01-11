package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.movement.ElevatorOuttake;
import org.firstinspires.ftc.teamcode.movement.Intake;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive;
import org.firstinspires.ftc.teamcode.movement.PlateClamp;

@Autonomous(name = "AutoFoundationOpMode")
public class AutoFoundationOpMode extends LinearOpMode {

    private MecanumDrive drive;
    private Intake intake;
    private ElevatorOuttake outtake;
    private PlateClamp plateClamp;

    private int sideModifier = 1;

    // MOVEMENT CONSTANTS
    /** average forward velocity of the robot at full power (inches per millisecond) */
    private final double FORWARD_VEL = 0.03470;
    /** average strafing velocity of the robot at full power (inches per millisecond) */
    private final double STRAFE_VEL = 0.0257;
    /** average angular velocity of the robot at full power (degrees per millisecond) */
    private final double ANGULAR_VELOCITY = 1;

    @Override
    public void runOpMode() {

        drive = MecanumDrive.standard(hardwareMap);
        intake = Intake.standard(hardwareMap);
        //outtake = ElevatorOuttake.standard(hardwareMap);
        plateClamp = PlateClamp.standard(hardwareMap);

        try {
            /*
            drive.forwardWithPower(1);
            Thread.sleep((int)(24 / FORWARD_VEL));
            drive.strafeRightWithPower(1);
            Thread.sleep(1000);
            plateClamp.setDown();
            Thread.sleep(3000);
            drive.forwardWithPower(-1);
            Thread.sleep((int)(24 / FORWARD_VEL));
            plateClamp.setUp();
            Thread.sleep(3000);
            drive.strafeLeftWithPower(1);
            Thread.sleep((int)(6 / STRAFE_VEL));
            drive.stop();
            */

            drive.strafeRightWithPower(1);
            Thread.sleep((int)(22 / STRAFE_VEL));
            drive.stop();
            plateClamp.setDown();
            Thread.sleep(2000);
            drive.strafeLeftWithPower(1);
            Thread.sleep((int)(18 / STRAFE_VEL));
            drive.stop();
            plateClamp.setUp();
            Thread.sleep(2000);
            drive.forwardWithPower(1 * sideModifier);
            Thread.sleep((int)(15 / FORWARD_VEL));
            drive.strafeRightWithPower(1);
            Thread.sleep((int)(25 / STRAFE_VEL));
            drive.forwardWithPower(-1 * sideModifier);
            Thread.sleep((int)(15 / FORWARD_VEL));
            drive.strafeLeftWithPower(1);
            Thread.sleep((int)(30 / STRAFE_VEL));
            drive.forwardWithPower(1 * sideModifier);
            Thread.sleep((int)(15 / FORWARD_VEL));
            drive.strafeLeftWithPower(1);
            Thread.sleep((int)(19 / STRAFE_VEL));
            drive.forwardWithPower(1 * sideModifier);
            Thread.sleep((int)(15 / FORWARD_VEL));
            drive.stop();
        } catch (Exception e) {

        }

    }

}
