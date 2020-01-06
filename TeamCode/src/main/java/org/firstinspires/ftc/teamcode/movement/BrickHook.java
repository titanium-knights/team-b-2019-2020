package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class BrickHook {

    CRServo hook;

    public BrickHook (CRServo hook) { this.hook = hook; }

    public void clamp () {
        hook.setDirection(CRServo.Direction.FORWARD);
        hook.setPower(1);
    }

    public void release () {
        hook.setDirection(CRServo.Direction.REVERSE);
        hook.setPower(1);
    }

    public void stop () {
        hook.setPower(0);
    }

    static String[] standardMotorNames = {"brick_hook"};

    public static BrickHook standard(HardwareMap hardwareMap) {
        return new BrickHook(hardwareMap.get(CRServo.class, standardMotorNames[0]));
    }

}
