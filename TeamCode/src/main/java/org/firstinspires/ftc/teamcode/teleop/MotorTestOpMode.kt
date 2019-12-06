package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.util.ButtonTracker

@TeleOp(name = "Motor Test Op Mode", group = "Tests")
class MotorTestOpMode: OpMode() {
    private val names = arrayOf("unknown_1", "unknown_2", "flywheel_l", "outtake_arm", "intake_pusher", "mecanum_br", "mecanum_fr", "flywheel_r")
    private val motors: List<DcMotor> by lazy { names.map { hardwareMap[DcMotor::class.java, it] } }
    private var currentIndex = 0

    private val prevMotor = ButtonTracker()
    private val nextMotor = ButtonTracker()

    override fun init() {
        gamepad1.setJoystickDeadzone(0.2F)
        motors
    }

    override fun loop() {
        telemetry.addData("Motor", names[currentIndex])

        val motor = motors[currentIndex]
        motor.power = gamepad1.left_stick_y * 0.3

        if (prevMotor.ifPress(gamepad1.dpad_left)) {
            motor.power = 0.0
            currentIndex = (currentIndex + 1) % names.size
        } else if (nextMotor.ifPress(gamepad1.dpad_right)) {
            motor.power = 0.0
            currentIndex = (currentIndex - 1 + names.size) % names.size
        }

        prevMotor.update(gamepad1.dpad_left)
        nextMotor.update(gamepad1.dpad_right)
    }
}