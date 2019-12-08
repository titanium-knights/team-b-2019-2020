package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    Motor[] flywheels;
    Motor pusher;

    public static class Motor {
        String name = null;
        DcMotor motor;

        public Motor(DcMotor motor, String name) {
            this.motor = motor;
            this.name = name;
        }
    }

    public Intake (Motor[] flywheels, Motor pusher) {
        this.flywheels = flywheels;
        for (Motor m : flywheels) {
            if (m.name.equals("flywheel_l")) {
                m.motor.setDirection(DcMotor.Direction.FORWARD);
            } else if (m.name.equals("flywheel_r")) {
                m.motor.setDirection(DcMotor.Direction.REVERSE);
            }
        }

        this.pusher = pusher;
        pusher.motor.setDirection(DcMotor.Direction.FORWARD);
    }

    /**
     * Sets the pivot flywheels' speeds to 1.
     */
    public void spin () {
        for (Motor m : flywheels) {
            m.motor.setPower(1);
        }
    }

    /**
     * Sets the pivot flywheels' speeds to -1, driving them in reverse.
     */
    public void spinReverse () {
        for (Motor m : flywheels) {
            m.motor.setPower(-0.7);
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

    public void pushStone (boolean thwacking, boolean enforceLimits) {
        if (thwacking) {
            if (!enforceLimits || pusher.motor.getCurrentPosition() < 300) {
                pusher.motor.setPower(1);
            } else {
                pusher.motor.setPower(0);
            }
        } else {
            if (!enforceLimits || pusher.motor.getCurrentPosition() > 0) {
                pusher.motor.setPower(-1);
            } else {
                pusher.motor.setPower(0);
            }
        }
    }

    public void stopPusher() {
        pusher.motor.setPower(0);
    }

    static String[] standardMotorNames = {"flywheel_l", "flywheel_r", "intake_pusher"};

    public static Intake standard(HardwareMap hardwareMap) {
        Motor[] motors = new Motor[standardMotorNames.length];
        for (int i = 0; i < motors.length; i++) {
            DcMotor motor = hardwareMap.get(DcMotor.class, standardMotorNames[i]);
            motors[i] = new Motor(motor, standardMotorNames[i]);
        }

        DcMotor motor = hardwareMap.get(DcMotor.class, standardMotorNames[2]);
        Motor pusher = new Motor(motor, standardMotorNames[2]);

        return new Intake(motors, pusher);
    }

}
