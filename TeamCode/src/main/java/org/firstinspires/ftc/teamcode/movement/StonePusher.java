package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class StonePusher {
    DcMotor pusher;

    public StonePusher(DcMotor pusher) {
        this.pusher = pusher;
        this.pusher.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void pushStone (boolean thwacking, boolean enforceLimits) {
        if (thwacking) {
            if (!enforceLimits || pusher.getCurrentPosition() < 300) {
                pusher.setPower(1);
            } else {
                pusher.setPower(0);
            }
        } else {
            if (!enforceLimits || pusher.getCurrentPosition() > 0) {
                pusher.setPower(-1);
            } else {
                pusher.setPower(0);
            }
        }
    }

    public void stopPusher() {
        pusher.setPower(0);
    }

    public static StonePusher standard(HardwareMap hardwareMap) {
        return new StonePusher(hardwareMap.get(DcMotor.class, "intake_pusher"));
    }
}
