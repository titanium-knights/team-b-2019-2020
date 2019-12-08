package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.infix.*
import org.firstinspires.ftc.teamcode.movement.MecanumDrive
import org.firstinspires.ftc.teamcode.util.FunctionOpMode

private const val NAME = "Cross Skybridge"

open class CrossSkybridgeOpMode(vararg directions: MecanumDrive.Motor.Vector2D): FunctionOpMode({
    val drive = MecanumDrive.standard(hardwareMap)
    waitForStart()

    directions.forEach { properDirection ->
        move the drive with MecanumMovementModifier(1.0, properDirection)
        wait(1.second)
        stop the drive
    }

    requestOpModeStop()
})

val straight = MecanumDrive.Motor.Vector2D(0.0, 1.0)
val left = MecanumDrive.Motor.Vector2D(-1.0, 0.0)

@Autonomous(name = "$NAME (straight)", group = NAME)
class CrossSkybridgeOpModeStraight: CrossSkybridgeOpMode(straight)

@Autonomous(name = "$NAME (left)", group = NAME)
class CrossSkybridgeOpModeLeft: CrossSkybridgeOpMode(left)

@Autonomous(name = "$NAME (straight then left)", group = NAME)
class CrossSkybridgeOpModeStraightThenLeft: CrossSkybridgeOpMode(straight, left)

@Autonomous(name = "$NAME (left then straight)", group = NAME)
class CrossSkybridgeOpModeLeftThenStraight: CrossSkybridgeOpMode(left, straight)