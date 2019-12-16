package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.events.EventOpMode
import org.firstinspires.ftc.teamcode.movement.MecanumDrive

@TeleOp(name = "Mecanum Drive Test Op Mode", group = "Tests")
class DriveOpMode: EventOpMode({
    val drive = MecanumDrive.standard(hardwareMap)

    registerLoopHook {
        val vector = MecanumDrive.Motor.Vector2D(gamepad1.left_stick_x.toDouble(), -gamepad1.left_stick_y.toDouble())
        drive.move(1.0, vector, gamepad1.right_stick_x.toDouble())
    }
})