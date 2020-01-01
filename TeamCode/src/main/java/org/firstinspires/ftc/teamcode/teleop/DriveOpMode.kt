package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.events.EventOpMode
import org.firstinspires.ftc.teamcode.movement.MecanumDrive

@TeleOp(name = "Mecanum Drive Test Op Mode", group = "Tests")
class DriveOpMode: EventOpMode({
    val drive = MecanumDrive.standard(hardwareMap)
    gamepad1.setJoystickDeadzone(0.2F)

    drive.motors.forEach { it.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE }

    registerLoopHook {
        val vector = MecanumDrive.Motor.Vector2D(gamepad1.left_stick_x.toDouble(), -gamepad1.left_stick_y.toDouble())
        val turn = gamepad1.right_stick_x.toDouble()

        telemetry.addData("X", vector.x)
        telemetry.addData("Y", vector.y)
        telemetry.addData("T", turn)

        drive.move(1.0, vector, turn)
    }
})