package org.firstinspires.ftc.teamcode.teleop.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.movement.BrickHolder;

@TeleOp(name = "Holder Tester")
public class HolderTester extends OpMode {

    private BrickHolder holder;

    @Override
    public void init () {
        holder = BrickHolder.standard(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up) {
            holder.moveArm(1);
        }
        if (gamepad1.dpad_down) {
            holder.moveArm(-1);
        }
        if (gamepad1.dpad_left) {
            holder.moveClaw(1);
        }
        if (gamepad1.dpad_right) {
            holder.moveClaw(-1);
        }

        telemetry.addData("Arm Pos: ", holder.arm.getPosition());
        telemetry.addData("Claw Pos: ", holder.claw.getPosition());
    }

}
