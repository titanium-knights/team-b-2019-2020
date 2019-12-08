package org.firstinspires.ftc.teamcode.infix

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

fun LinearOpMode.wait(ms: Long) = sleep(ms)
val Int.seconds: Long get() = toLong() * 1000
val Int.second: Long get() = seconds