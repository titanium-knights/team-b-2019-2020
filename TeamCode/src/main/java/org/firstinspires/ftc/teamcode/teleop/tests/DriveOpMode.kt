package org.firstinspires.ftc.teamcode.teleop.tests

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.events.EventOpMode
import org.firstinspires.ftc.teamcode.events.registerMecanumHook
import org.firstinspires.ftc.teamcode.movement.MecanumDrive

@TeleOp(name = "Mecanum Drive Test Op Mode", group = "Tests")
class DriveOpMode: EventOpMode({
    gamepad1.setJoystickDeadzone(0.2F)
    registerMecanumHook(true)
})