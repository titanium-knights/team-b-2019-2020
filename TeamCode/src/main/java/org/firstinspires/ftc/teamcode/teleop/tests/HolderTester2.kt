package org.firstinspires.ftc.teamcode.teleop.tests

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.events.EventOpMode
import org.firstinspires.ftc.teamcode.events.doWhen
import org.firstinspires.ftc.teamcode.events.isPressed
import org.firstinspires.ftc.teamcode.events.makeButton
import org.firstinspires.ftc.teamcode.movement.BrickHolder

@TeleOp(name = "Holder Tester 2", group = "Tests")
class HolderTester2: EventOpMode({
    val holder = BrickHolder.standard(hardwareMap)
    var mode = false

    doWhen(makeButton(gamepad1::x).pushed) {
        mode = !mode
    }

    doWhen(makeButton(gamepad1::dpad_up).pushed) {
        if (mode) {
            holder.armA.position += 0.1
            //holder.armB.position -= 0.1
        } else {
            holder.clawA.position = 1.0
        }
    }

    doWhen(makeButton(gamepad1::dpad_down).pushed) {
        if (mode) {
            holder.armA.position -= 0.1
            //holder.armB.position += 0.1
        } else {
            holder.clawA.position = 0.0
        }
    }

    registerLoopHook {
        telemetry.addData("Name", if (mode) "arm" else "claw")
        telemetry.addData("Arm Pos", holder.armA.position)
    }
})