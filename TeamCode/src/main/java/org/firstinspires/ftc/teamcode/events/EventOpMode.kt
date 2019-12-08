package org.firstinspires.ftc.teamcode.events

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import java.util.*

typealias EventOpModeHook = EventOpMode.() -> Unit

open class EventOpMode(private val registerEventListeners: EventOpModeHook): OpMode() {
    private val loopHooks: MutableList<EventOpModeHook> = mutableListOf()
    fun registerLoopHook(hook: EventOpModeHook) {
        loopHooks.add(hook)
    }

    override fun init() {
        registerEventListeners()
    }

    override fun loop() {
        loopHooks.forEach { it() }
    }
}