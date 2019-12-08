package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.events.EventOpMode
import org.firstinspires.ftc.teamcode.events.doCond
import org.firstinspires.ftc.teamcode.events.doWhen
import org.firstinspires.ftc.teamcode.events.isPressed
import org.firstinspires.ftc.teamcode.infix.at
import org.firstinspires.ftc.teamcode.infix.move
import org.firstinspires.ftc.teamcode.infix.power
import org.firstinspires.ftc.teamcode.infix.the

@TeleOp(name = "Event Infix Test Op Mode", group = "Tests")
class EventInfixTestOpMode: EventOpMode({
    val pusher = hardwareMap[DcMotor::class.java, "intake_pusher"]

    doCond(
            gamepad1::x.isPressed to {move the pusher at power(0.5)},
            gamepad1::a.isPressed to {move the pusher at power(-0.5)},
            null to {move the pusher at power(0.0)}
    )
})