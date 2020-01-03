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

    @Override
    public void runOpMode() {

        drive = MecanumDrive.standard(hardwareMap);
        intake = Intake.standard(hardwareMap);
        outtake = ElevatorOuttake.standard(hardwareMap);
        plateClamp = PlateClamp.standard(hardwareMap);

        try {
            drive.forwardWithPower(1);
            Thread.sleep(1);
            plateClamp.setDown();
            Thread.sleep(1);
            drive.forwardWithPower(-1);
            Thread.sleep(1);
            plateClamp.setUp();
            Thread.sleep(1);
        } catch (Exception e) {

        }

    }

}
