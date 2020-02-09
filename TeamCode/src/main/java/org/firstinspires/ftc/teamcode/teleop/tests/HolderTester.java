package org.firstinspires.ftc.teamcode.teleop.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.movement.BrickHolder;

@TeleOp(name = "Holder Tester")
public class HolderTester extends OpMode {

    private BrickHolder holder;
    private int speed = 0;

    @Override
    public void init () {
        holder = BrickHolder.standard(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up) {
            holder.moveArm(speed);
        }
        if (gamepad1.dpad_down) {
            holder.moveArm(-speed);
        }
        if (gamepad1.dpad_left) {
            holder.moveClaw(speed);
        }
        if (gamepad1.dpad_right) {
            holder.moveClaw(-speed);
        }

        if (gamepad1.a) {
            speed += 1;
        }
        if (gamepad1.b && speed > 0) {
            speed -= 1;
        }

        telemetry.addData("Arm A Pos: ", holder.armA.getPosition());
        //telemetry.addData("Arm B Pos: ", holder.armB.getPosition());
        //telemetry.addData("Claw Pos: ");
        telemetry.addData("Speed", speed);
    }

}
