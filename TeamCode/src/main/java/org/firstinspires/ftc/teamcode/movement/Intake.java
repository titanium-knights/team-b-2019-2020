package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    Motor[] motors;

    public static class Motor {
        String name = null;
        DcMotor motor;

        public Motor(DcMotor motor) {
            this.motor = motor;
        }
    }

    public Intake (Motor[] motors) {
        this.motors = motors;
        for (Motor m : motors) {
            m.motor.setDirection(DcMotor.Direction.FORWARD);
        }
    }

    public void spin () {
        for (Motor m : motors) {
            m.motor.setPower(1);
        }
    }

    public void stopSpinning () {
        for (Motor m : motors) {
            m.motor.setPower(0);
        }
    }

    static String[] standardMotorNames = {"flywheel_l", "flywheel_r"};

    public static Intake standard(HardwareMap hardwareMap) {
        Motor[] motors = new Motor[standardMotorNames.length];
        for (int i = 0; i < motors.length; i++) {
            DcMotor motor = hardwareMap.get(DcMotor.class, standardMotorNames[i]);
            motors[i] = new Motor(motor);
        }
        return new Intake(motors);
    }

}
