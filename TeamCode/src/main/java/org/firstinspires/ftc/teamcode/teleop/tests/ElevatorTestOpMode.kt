package org.firstinspires.ftc.teamcode.teleop.tests

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.events.EventOpMode
import org.firstinspires.ftc.teamcode.events.doCond
import org.firstinspires.ftc.teamcode.events.isPressed
import org.firstinspires.ftc.teamcode.movement.ElevatorOuttake

@TeleOp(name = "Elevator Test Op Mode", group = "Tests")
class ElevatorTestOpMode: EventOpMode({
    val outtake = ElevatorOuttake.standard(hardwareMap)
    gamepad2.setJoystickDeadzone(0.2F)

    registerLoopHook {
        val vertical = gamepad2.left_stick_y.toDouble()
        val horizontal = gamepad2.left_stick_x.toDouble()

        outtake.moveElevators(vertical, horizontal)

        telemetry.addData("Elevator (H)", horizontal)
        telemetry.addData("Elevator (V)", vertical)
    }

    doCond(
            gamepad2::x.isPressed to { outtake.moveClamp(1) },
            gamepad2::b.isPressed to { outtake.moveClamp(-1) },
            null to { outtake.stopClamp() }
    )
})