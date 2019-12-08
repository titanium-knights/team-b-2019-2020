package org.firstinspires.ftc.teamcode.util

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

open class FunctionOpMode(private val func: FunctionOpMode.() -> Unit): LinearOpMode() {
    override fun runOpMode() {
        func()
    }
}