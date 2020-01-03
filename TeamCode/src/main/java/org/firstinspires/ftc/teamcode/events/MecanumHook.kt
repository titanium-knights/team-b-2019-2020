package org.firstinspires.ftc.teamcode.events

import org.firstinspires.ftc.teamcode.movement.MecanumDrive

fun EventOpMode.registerMecanumHook(withTelemetry: Boolean = false): MecanumDrive {
    val drive = MecanumDrive.standard(hardwareMap)

    registerLoopHook {
        val vector = MecanumDrive.Motor.Vector2D(gamepad1.left_stick_x.toDouble(), -gamepad1.left_stick_y.toDouble())
        val turn = gamepad1.right_stick_x.toDouble()

        if (withTelemetry) {
            telemetry.addData("X", vector.x)
            telemetry.addData("Y", vector.y)
            telemetry.addData("T", turn)
        }

        drive.move(1.0, vector, turn)
    }

    return drive
}