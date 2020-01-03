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

    /** Stops the vertical movement of the elevator */
    fun stopVertical() = moveVertical(0.0)

    /** Moves the claw forwards or backwards at the given speed */
    fun moveHorizontal(power: Double) {
        horizontal.power = power
    }

    /** Stops the horizontal movement of the claw */
    fun stopHorizontal() = moveHorizontal(0.0)

    fun moveElevators(verticalPower: Double, horizontalPower: Double) {
        moveVertical(verticalPower)
        moveHorizontal(horizontalPower)
    }

    /** Stops the movement of the elevator and claw */
    fun stopElevators() = moveElevators(0.0, 0.0)

    fun moveClamp(power: Double) {
        clamp.power = power
    }

    fun stopClamp() = moveClamp(0.0)

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