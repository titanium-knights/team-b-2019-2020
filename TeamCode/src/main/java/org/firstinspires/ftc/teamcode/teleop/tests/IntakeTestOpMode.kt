package org.firstinspires.ftc.teamcode.teleop.tests

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.events.EventOpMode
import org.firstinspires.ftc.teamcode.events.doCond
import org.firstinspires.ftc.teamcode.events.isPressed
import org.firstinspires.ftc.teamcode.movement.Intake

fun EventOpMode.registerIntakeHooks() {
    val intake = Intake.standard(hardwareMap)

    doCond(
            gamepad2::a.isPressed to { intake.spinReverse() },
            gamepad2::y.isPressed to { intake.spin() },
            null to { intake.stopSpinning() }
    )
}

@TeleOp(name = "Intake Test Op Mode", group = "Tests")
class IntakeTestOpMode: EventOpMode({
    registerIntakeHooks()
})