package org.firstinspires.ftc.teamcode.util

import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.movement.MecanumDrive
import org.firstinspires.ftc.teamcode.sensors.Gyro

interface PIDControllerMovable {
    val drive: MecanumDrive
    val gyro: Gyro

    fun idle()
}

class PIDController(
        var Kp: Double,
        var Ki: Double,
        var Kd: Double
) {
    private var integral: Double = 0.0
    private var previous: Double = 0.0

    fun reset() {
        integral = 0.0
        previous = 0.0
    }

    init {
        reset()
    }

    fun evaluate(current: Double, setpoint: Double, deltaTime: Double): Double {
        val error = setpoint - current
        integral += error

        val result = Kp * error + Ki * integral * deltaTime + Kd * (error - previous) / deltaTime
        previous = error

        return result
    }

    fun move(opMode: PIDControllerMovable, power: Double, vector: MecanumDrive.Motor.Vector2D, time: Long) {
        val elapsedTime = ElapsedTime()
        val startTime = elapsedTime.milliseconds()
        val setpoint = opMode.gyro.angle
        var previous = startTime

        while (elapsedTime.milliseconds() - startTime < time) {
            val now = elapsedTime.milliseconds()

            opMode.drive.move(power, vector, evaluate(opMode.gyro.angle, setpoint, now - previous))
            opMode.idle()

            previous = now
        }

        opMode.drive.stop()
    }

    fun strafeLeft(opMode: PIDControllerMovable, power: Double, time: Long) =
            move(opMode, power, MecanumDrive.Motor.Vector2D(-1.0, 0.0), time)

    fun forward(opMode: PIDControllerMovable, power: Double, time: Long) =
            move(opMode, power, MecanumDrive.Motor.Vector2D(0.0, 1.0), time)
}