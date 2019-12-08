package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.events.EventOpMode
import org.firstinspires.ftc.teamcode.events.held
import org.firstinspires.ftc.teamcode.events.on
import org.firstinspires.ftc.teamcode.infix.at
import org.firstinspires.ftc.teamcode.infix.move
import org.firstinspires.ftc.teamcode.infix.power
import org.firstinspires.ftc.teamcode.infix.the

@TeleOp(name = "Event Infix Test Op Mode", group = "Tests")
class EventInfixTestOpMode: EventOpMode({
    val pusher = hardwareMap[DcMotor::class.java, "intake_pusher"]

    on(gamepad1::y.held, {
        move the pusher at power(0.5)
    }, {
        move the pusher at power(0.0)
    })
})