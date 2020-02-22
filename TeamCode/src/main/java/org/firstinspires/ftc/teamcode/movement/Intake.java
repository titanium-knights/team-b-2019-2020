package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    Motor[] flywheels;

    public static class Motor {
        String name = null;
        DcMotor motor;

        public Motor(DcMotor motor, String name) {
            this.motor = motor;
            this.name = name;
        }
    }

    public Intake (Motor[] flywheels) {
        this.flywheels = flywheels;
        for (Motor m : flywheels) {
            if (m.name.equals("flywheel_l")) {
                m.motor.setDirection(DcMotor.Direction.FORWARD);
            } else if (m.name.equals("flywheel_r")) {
                m.motor.setDirection(DcMotor.Direction.REVERSE);
            }
        }
    }

    /**
     * Sets the pivot flywheels' speeds to 1.
     */
    public void spin () {
        for (Motor m : flywheels) {
            m.motor.setPower(0.75);
        }

    }

    /**
     * Sets the pivot flywheels' speeds to -1, driving them in reverse.
     */
    public void spinReverse () {
        for (Motor m : flywheels) {
            m.motor.setPower(-0.75);
        }
    }

    /**
     * Sets the pivot flywheels' speeds to 0.
     */
    public void stopSpinning () {
        for (Motor m : flywheels) {
            m.motor.setPower(0);
        }
    }

    static String[] standardMotorNames = {"flywheel_l", "flywheel_r"};

    public static Intake standard(HardwareMap hardwareMap) {
        Motor[] motors = new Motor[standardMotorNames.length];
        for (int i = 0; i < motors.length; i++) {
            DcMotor motor = hardwareMap.get(DcMotor.class, standardMotorNames[i]);
            motors[i] = new Motor(motor, standardMotorNames[i]);
        }

        return new Intake(motors);
    }

}
