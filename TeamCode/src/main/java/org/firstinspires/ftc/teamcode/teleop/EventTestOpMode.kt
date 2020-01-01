package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.events.*
import org.firstinspires.ftc.teamcode.movement.*

@TeleOp(name = "Event Test Op Mode", group = "Tests")
class EventTestOpMode: EventOpMode({
    arrayOf(gamepad1, gamepad2).forEach { it.setJoystickDeadzone(0.2F) }

    val mecanumDrive = MecanumDrive.standard(hardwareMap)
    val intake = Intake.standard(hardwareMap)
    val stonePusher = StonePusher.standard(hardwareMap)
    val outtake = Outtake.standard(hardwareMap)
    val plateClamp = PlateClamp.standard(hardwareMap)

    registerLoopHook {
        val vector = MecanumDrive.Motor.Vector2D(gamepad1.left_stick_x.toDouble(), -gamepad1.left_stick_y.toDouble())
        mecanumDrive.move(1.0, vector, gamepad1.right_stick_x.toDouble())
    }

    val enforceLimits = false

    doCond(
            gamepad2::x.isPressed to {stonePusher.pushStone(true, enforceLimits)},
            gamepad2::b.isPressed to {stonePusher.pushStone(false, enforceLimits)},
            null to {stonePusher.stopPusher()}
    )

    doCond(
            makeToggleButton(gamepad1::y).on to intake::spin,
            gamepad1::b.isPressed to intake::spinReverse,
            null to intake::stopSpinning
    )

    registerLoopHook {
        val stick = gamepad2.left_stick_y.toDouble()
        val armPower = stick * (if (stick > 0) 0.8 else 0.3)
        outtake.moveArm(armPower, enforceLimits)
    }

    doCond(
            gamepad2::left_bumper.isPressed to {outtake.rotateWrist(true, 1.0)},
            gamepad2::right_bumper.isPressed to {outtake.rotateWrist(false, 1.0)},
            null to {outtake.stopWrist()}
    )

    doCond(
            gamepad2::y.isPressed to {outtake.moveClaw(true, 1.0)},
            gamepad2::a.isPressed to {outtake.moveClaw(false, 1.0)},
            null to outtake::stopClaw
    )

    doWhen(makeButton(gamepad2::dpad_down).pushed) { plateClamp.setDown() }
    doWhen(makeButton(gamepad2::dpad_left).pushed) { plateClamp.setMid() }
    doWhen(makeButton(gamepad2::dpad_up).pushed) { plateClamp.setUp() }
})