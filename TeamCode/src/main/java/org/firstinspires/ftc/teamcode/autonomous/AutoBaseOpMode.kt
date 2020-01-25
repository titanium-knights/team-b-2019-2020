package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DistanceSensor
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.movement.MecanumDrive
import org.firstinspires.ftc.teamcode.sensors.BNO055IMUGyro
import org.firstinspires.ftc.teamcode.sensors.Gyro
import org.firstinspires.ftc.teamcode.util.PIDController
import kotlin.math.sign

open class AutoBaseOpMode: LinearOpMode() {
    lateinit var drive: MecanumDrive
    lateinit var gyro: Gyro
    lateinit var elapsedTime: ElapsedTime
    val pidController = PIDController(0.06, 0.0, 0.0)

    override fun runOpMode() {
        drive = MecanumDrive.standard(hardwareMap)
        gyro = BNO055IMUGyro.standard(hardwareMap)
        elapsedTime = ElapsedTime()

        gyro.initialize()
    }

    fun sensorDrive(vector: MecanumDrive.Motor.Vector2D, targetAngle: Double, sensor: DistanceSensor, inches: Double, stop: Boolean) {
        var previous = elapsedTime.milliseconds()

        pidController.reset()

        var prevDistance: Double
        var distance = sensor.getDistance(DistanceUnit.INCH) * sign(inches)
        sleep(10L)

        do {
            prevDistance = distance
            distance = sensor.getDistance(DistanceUnit.INCH) * sign(inches)
            val avgDistance = (prevDistance + distance) / 2

            val power = if (stop) ((avgDistance - inches) / 18).coerceIn(0.375..1.0) else 1.0

            val currentAngle = gyro.angle
            val now = elapsedTime.milliseconds()
            val turn = pidController.evaluate(currentAngle, targetAngle, now - previous)
            previous = now

            drive.move(power, vector, -turn, MecanumDrive.TurnBehavior.ADDSUBTRACT)

            sleep(8L)
        } while ((prevDistance + distance) / 2 - inches >= 3 && opModeIsActive())

        if (stop) {
            drive.stop()
        }
    }

    fun sensorDrive(vector: MecanumDrive.Motor.Vector2D, targetAngle: Double, sensor: DistanceSensor, inches: Double) =
            sensorDrive(vector, targetAngle, sensor, inches, true)
}