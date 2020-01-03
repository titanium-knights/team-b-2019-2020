package org.firstinspires.ftc.teamcode.teleop.tests

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.events.EventOpMode

@TeleOp(name = "Intake & Elevator Test Op Mode", group = "Tests")
class IntakeElevatorTestOpMode: EventOpMode({
    gamepad2.setJoystickDeadzone(0.2F)
    registerElevatorHooks()
    registerIntakeHooks()
})