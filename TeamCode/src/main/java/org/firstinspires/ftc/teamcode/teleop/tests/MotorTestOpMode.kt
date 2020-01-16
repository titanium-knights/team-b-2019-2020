package org.firstinspires.ftc.teamcode.teleop.tests

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.events.EventOpMode
import org.firstinspires.ftc.teamcode.util.ButtonToggler

@TeleOp(name = "Motor Test Op Mode", group = "Tests")
class MotorTestOpMode: EventOpMode({
    val names = arrayOf("mecanum_bl", "mecanum_fl", "mecanum_br", "mecanum_fr")
    val motors = names.map { hardwareMap[DcMotor::class.java, it] }
    var currentIndex = 0

    val prevMotor = ButtonToggler()
    val nextMotor = ButtonToggler()

    gamepad1.setJoystickDeadzone(0.2F)

    registerLoopHook {
        telemetry.addData("Motor", names[currentIndex])

        val motor = motors[currentIndex]
        motor.power = gamepad1.left_stick_y * 0.3

        if (prevMotor.ifRelease(gamepad1.dpad_left)) {
            motor.power = 0.0
            currentIndex = (currentIndex + 1) % names.size
        } else if (nextMotor.ifRelease(gamepad1.dpad_right)) {
            motor.power = 0.0
            currentIndex = (currentIndex - 1 + names.size) % names.size
        }

        prevMotor.update(gamepad1.dpad_left)
        nextMotor.update(gamepad1.dpad_right)
    }
})