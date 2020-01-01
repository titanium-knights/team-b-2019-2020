package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.events.EventOpMode
import org.firstinspires.ftc.teamcode.events.doCond
import org.firstinspires.ftc.teamcode.events.isPressed
import org.firstinspires.ftc.teamcode.movement.Intake

@TeleOp(name = "Intake Test Op Mode", group = "Tests")
class IntakeTestOpMode: EventOpMode({
    val intake = Intake.standard(hardwareMap)

    doCond(
            gamepad2::x.isPressed to { intake.spinReverse() },
            gamepad2::b.isPressed to { intake.spin() },
            null to { intake.stopSpinning() }
    )
})