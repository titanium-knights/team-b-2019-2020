package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.movement.BrickHook;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive;

public class AutoRelayOpMode extends LinearOpMode {

    private double deltaTime;
    private int sideModifier;

    private MecanumDrive drive;
    private BrickHook hook;

    private ColorSensor colorSensor;

    private int formation = 3;
    private double speed = 0.75;

    private double FORWARD_VEL;
    private double STRAFE_VEL;

    public AutoRelayOpMode(int side, double deltaTime) {
        sideModifier = side;
        this.deltaTime = deltaTime;
    }

    @Override
    public void runOpMode() {

        // MOVEMENT CONSTANTS
        /** average forward velocity of the robot at full power (inches per millisecond) */
        FORWARD_VEL = 0.0347 / deltaTime;
        /** average strafing velocity of the robot at full power (inches per millisecond) */
        STRAFE_VEL = 0.0257 / deltaTime;

        drive = MecanumDrive.standard(hardwareMap);
        hook = BrickHook.standard(hardwareMap);
        colorSensor = hardwareMap.get(ColorSensor.class, "color_sensor");

        drivePath();

    }

    private void drivePath () {

        try {
            if (formation == 1) {
                drive.forwardWithPower(speed * sideModifier);
                sleep((int)(4.5 / FORWARD_VEL));
                drive.strafeLeftWithPower(speed);
                sleep((int)(16 / STRAFE_VEL));
                drive.stop();
                hook.clamp();
                sleep(1000);
                drive.strafeRightWithPower(speed);
                sleep((int)(5 / STRAFE_VEL));
                drive.forwardWithPower(-speed * sideModifier);
                sleep((int)(34.5 / FORWARD_VEL));
                hook.release();
                drive.stop();
                sleep(1000);
                drive.forwardWithPower(speed * sideModifier);
                sleep((int)(46.5 / FORWARD_VEL));
                drive.strafeLeftWithPower(speed);
                sleep((int)(5 / STRAFE_VEL));
                drive.stop();
                hook.clamp();
                sleep(1000);
                drive.strafeRightWithPower(speed);
                sleep((int)(5 / STRAFE_VEL));
                drive.forwardWithPower(-speed * sideModifier);
                sleep((int)(46.5 / FORWARD_VEL));
                hook.release();
                drive.stop();
                sleep(1000);
                hook.stop();
            } else if (formation == 2) {
                drive.forwardWithPower(speed * sideModifier);
                sleep((int)(4.5 / FORWARD_VEL));
                drive.strafeLeftWithPower(speed);
                sleep((int)(16 / STRAFE_VEL));
                drive.stop();
                hook.clamp();
                sleep(1000);
                drive.strafeRightWithPower(speed);
                sleep((int)(5 / STRAFE_VEL));
                drive.forwardWithPower(-speed * sideModifier);
                sleep((int)(34.5 / FORWARD_VEL));
                hook.release();
                drive.stop();
                sleep(1000);
                drive.forwardWithPower(speed * sideModifier);
                sleep((int)(46.5 / FORWARD_VEL));
                drive.strafeLeftWithPower(speed);
                sleep((int)(5 / STRAFE_VEL));
                drive.stop();
                hook.clamp();
                sleep(1000);
                drive.strafeRightWithPower(speed);
                sleep((int)(5 / STRAFE_VEL));
                drive.forwardWithPower(-speed * sideModifier);
                sleep((int)(46.5 / FORWARD_VEL));
                hook.release();
                drive.stop();
                sleep(1000);
                hook.stop();
            } else if (formation == 3) {
                drive.forwardWithPower(speed * sideModifier);
                sleep((int)(4.5 / FORWARD_VEL));
                drive.strafeLeftWithPower(speed);
                sleep((int)(16 / STRAFE_VEL));
                drive.stop();
                hook.clamp();
                sleep(1000);
                drive.strafeRightWithPower(speed);
                sleep((int)(5 / STRAFE_VEL));
                drive.forwardWithPower(-speed * sideModifier);
                sleep((int)(34.5 / FORWARD_VEL));
                hook.release();
                drive.stop();
                sleep(1000);
                drive.forwardWithPower(speed * sideModifier);
                sleep((int)(46.5 / FORWARD_VEL));
                drive.strafeLeftWithPower(speed);
                sleep((int)(5 / STRAFE_VEL));
                drive.stop();
                hook.clamp();
                sleep(1000);
                drive.strafeRightWithPower(speed);
                sleep((int)(5 / STRAFE_VEL));
                drive.forwardWithPower(-speed * sideModifier);
                sleep((int)(46.5 / FORWARD_VEL));
                hook.release();
                drive.stop();
                sleep(1000);
                hook.stop();
            }
        } catch (Exception e) {

        }

    }

    private void checkBricks () {

        drive.strafeLeftWithPower(speed);
        sleep((int)(16 / STRAFE_VEL));
        int f1 = 0; // check color
        sleep(800);

        drive.forwardWithPower(-speed * FORWARD_VEL);
        sleep((int)(8 / FORWARD_VEL));
        drive.stop();
        int f2 = 0; // check color
        sleep(800);

        drive.forwardWithPower(-speed * FORWARD_VEL);
        sleep((int)(8 / FORWARD_VEL));
        drive.stop();
        int f3 = 0; // check color
        sleep(800);

        if ((f1 > f2) && (f1 > f3)) {
            formation = 1;
        }
        if ((f2 > f1) && (f2 > f3)) {
            formation = 2;
        }
        if ((f3 > f1) && (f3 > f2)) {
            formation = 3;
        }
    }

}