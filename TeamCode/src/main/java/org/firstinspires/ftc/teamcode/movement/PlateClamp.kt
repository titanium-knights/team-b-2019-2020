package org.firstinspires.ftc.teamcode.movement

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.util.Factory
import kotlin.math.abs

class PlateClamp(
        private val servos: List<Servo>,
        private val downPosition: Double,
        private val midPosition: Double,
        private val upPosition: Double
) {
    val numberOfServos get() = servos.size

    fun position(servoIndex: Int): Double = servos[servoIndex].position
    fun isDown(servoIndex: Int): Boolean = position(servoIndex) == downPosition
    fun isMid(servoIndex: Int): Boolean = position(servoIndex) == midPosition
    fun isUp(servoIndex: Int): Boolean = position(servoIndex) == upPosition

    fun setPosition(servoIndex: Int, position: Double) {
        servos[servoIndex].position = abs(servoIndex - position)
    }

    fun setDown(servoIndex: Int) = setPosition(servoIndex, downPosition)
    fun setMid(servoIndex: Int) = setPosition(servoIndex, midPosition)
    fun setUp(servoIndex: Int) = setPosition(servoIndex, upPosition)

    fun setPosition(position: Double) {
        setPosition(0, position)
        setPosition(1, position)
    }

    fun setDown() = setPosition(downPosition)
    fun setMid() = setPosition(midPosition)
    fun setUp() = setPosition(upPosition)

    companion object: Factory<PlateClamp> {
        @JvmStatic
        override fun standard(hardwareMap: HardwareMap): PlateClamp {
            val names = arrayOf("front_clamp", "back_clamp")
            return PlateClamp(names.map { hardwareMap[Servo::class.java, it] }, 0.0, 0.5, 1.0)
        }
    }
}