package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.movement.MecanumDrive
import org.firstinspires.ftc.teamcode.util.FunctionOpMode

private const val NAME = "Cross Skybridge"

open class CrossSkybridgeOpMode(delayBetweenMovements: Long, vararg directions: MecanumDrive.Motor.Vector2D): FunctionOpMode({
    val drive = MecanumDrive.standard(hardwareMap)
    waitForStart()

    directions.forEach { direction ->
        drive.move(-1.0, direction, 0.0)
        sleep(1250)
        drive.move(0.0, direction, 0.0)

        sleep(delayBetweenMovements)
    }

    requestOpModeStop()
})

val straight = MecanumDrive.Motor.Vector2D(0.0, 1.0)
val left = MecanumDrive.Motor.Vector2D(-1.0, 0.0)
val right = MecanumDrive.Motor.Vector2D(1.0, 0.0)

@Autonomous(name = "$NAME (straight)", group = NAME)
class CrossSkybridgeOpModeStraight: CrossSkybridgeOpMode(0, straight)

@Autonomous(name = "$NAME (left then straight)", group = NAME)
class CrossSkybridgeOpModeLeftThenStraight: CrossSkybridgeOpMode(0, left, straight)

@Autonomous(name = "$NAME (right then straight)", group = NAME)
class CrossSkybridgeOpModeRightThenStraight: CrossSkybridgeOpMode(0, right, straight)

val delayMs = 1500L

@Autonomous(name = "$NAME (left, straight, then right)", group = NAME)
class CrossSkybridgeOpModeLeftThenStraightDelayed: CrossSkybridgeOpMode(delayMs, left, straight, right)

@Autonomous(name = "$NAME (right, straight, then left)", group = NAME)
class CrossSkybridgeOpModeRightThenStraightDelayed: CrossSkybridgeOpMode(delayMs, right, straight, left)