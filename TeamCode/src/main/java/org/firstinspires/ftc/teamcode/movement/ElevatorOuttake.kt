package org.firstinspires.ftc.teamcode.movement

import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap

class ElevatorOuttake(
        val vertical: DcMotor,
        val horizontal: DcMotor,
        val clamp: CRServo
) {
    fun moveVertical(power: Double) {
        vertical.power = power
    }

    fun moveVertical(power: Int) = moveVertical(power.toDouble())
    fun stopVertical() = moveVertical(0)

    fun moveHorizontal(power: Double) {
        horizontal.power = power
    }

    fun moveHorizontal(power: Int) = moveHorizontal(power.toDouble())
    fun stopHorizontal() = moveHorizontal(0)

    fun moveElevators(verticalPower: Double, horizontalPower: Double) {
        moveVertical(verticalPower)
        moveHorizontal(horizontalPower)
    }

    fun moveElevators(verticalPower: Int, horizontalPower: Int) = moveElevators(verticalPower.toDouble(), horizontalPower.toDouble())
    fun stopElevators() = moveElevators(0, 0)

    fun moveClamp(power: Double) {
        clamp.power = power
    }

    fun moveClamp(power: Int) = moveClamp(power.toDouble())
    fun stopClamp() = moveClamp(0)

    fun moveClaw(power: Double) = moveClamp(power)
    fun moveClaw(power: Int) = moveClamp(power)
    fun stopClaw() = stopClamp()

    fun stopAll() {
        stopElevators()
        stopClamp()
    }

    companion object {
        @JvmStatic fun standard(hardwareMap: HardwareMap): ElevatorOuttake = ElevatorOuttake(
                hardwareMap[DcMotor::class.java, "vertical_elevator"],
                hardwareMap[DcMotor::class.java, "horizontal_elevator"],
                hardwareMap[CRServo::class.java, "outtake_claw"]
        )
    }
}