package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.movement.MecanumDrive;
import org.firstinspires.ftc.teamcode.movement.PlateClamp;

@Autonomous(name = "AutoFoundationOpMode")
public class AutoFoundationOpMode extends LinearOpMode {

    private double deltaTime;
    private int sideModifier;

    private MecanumDrive drive;
    private PlateClamp plateClamp;

    public AutoFoundationOpMode (int sideModifier, double deltaTime) {
        this.sideModifier = sideModifier;
        this.deltaTime = deltaTime;
    }

    @Override
    public void runOpMode() {

        double FORWARD_VEL = 0.03470 / deltaTime;
        double STRAFE_VEL = 0.0257 / deltaTime;

        drive = MecanumDrive.standard(hardwareMap);
        plateClamp = PlateClamp.standard(hardwareMap);

        try {
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
