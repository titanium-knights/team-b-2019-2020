package org.firstinspires.ftc.teamcode.infix

import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.movement.MecanumDrive

object move
object stop

interface MovementRequest<Modifier> {
    fun fulfill(modifier: Modifier)
}

data class SimpleMovementModifier(val power: Double)
val power = ::SimpleMovementModifier
class DcMotorMovementRequest(val motor: DcMotor): MovementRequest<SimpleMovementModifier> {
    override fun fulfill(modifier: SimpleMovementModifier) {
        motor.power = modifier.power
    }
}
infix fun move.the(motor: DcMotor): DcMotorMovementRequest = DcMotorMovementRequest(motor)

data class MecanumMovementModifier(
        val power: Double,
        val direction: MecanumDrive.Motor.Vector2D = MecanumDrive.Motor.Vector2D(0.0, 1.0),
        val turn: Double = 0.0
)
val mecanumPower = ::MecanumMovementModifier
class MecanumMovementRequest(val drive: MecanumDrive): MovementRequest<MecanumMovementModifier> {
    override fun fulfill(modifier: MecanumMovementModifier) {
        drive.move(modifier.power, modifier.direction, modifier.turn)
    }
}
infix fun move.the(drive: MecanumDrive): MecanumMovementRequest = MecanumMovementRequest(drive)

infix fun <Modifier> MovementRequest<Modifier>.at(modifier: Modifier) = fulfill(modifier)
infix fun <Modifier> MovementRequest<Modifier>.using(modifier: Modifier) = fulfill(modifier)
infix fun <Modifier> MovementRequest<Modifier>.with(modifier: Modifier) = fulfill(modifier)