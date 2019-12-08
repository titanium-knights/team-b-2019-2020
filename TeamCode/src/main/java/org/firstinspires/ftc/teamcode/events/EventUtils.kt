@file:JvmName("EventUtils")

package org.firstinspires.ftc.teamcode.events

import kotlin.reflect.KProperty0

fun EventOpMode.on(trigger: EventOpMode.() -> Boolean, action: EventOpMode.() -> Unit) = registerLoopHook {
    if (trigger()) action()
}

fun EventOpMode.on(
        trigger: EventOpMode.() -> Boolean,
        action: EventOpMode.() -> Unit,
        otherwise: EventOpMode.() -> Unit
) = registerLoopHook {
    if (trigger()) action() else otherwise()
}

val KProperty0<Boolean>.held: EventOpMode.() -> Boolean get() = { this@held.get() }