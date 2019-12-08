@file:JvmName("EventUtils")

package org.firstinspires.ftc.teamcode.events

import kotlin.reflect.KProperty0

fun EventOpMode.doWhen(trigger: EventOpMode.() -> Boolean, action: EventOpMode.() -> Unit) = registerLoopHook {
    if (trigger()) action()
}

fun EventOpMode.doWhen(trigger: () -> Boolean, action: EventOpMode.() -> Unit) = registerLoopHook {
    if (trigger()) action()
}

fun EventOpMode.doWhen(
        trigger: EventOpMode.() -> Boolean,
        action: EventOpMode.() -> Unit,
        otherwise: EventOpMode.() -> Unit
) = registerLoopHook {
    if (trigger()) action() else otherwise()
}

fun EventOpMode.doWhen(
        trigger: () -> Boolean,
        action: EventOpMode.() -> Unit,
        otherwise: EventOpMode.() -> Unit
) = registerLoopHook {
    if (trigger()) action() else otherwise()
}

fun EventOpMode.doCond(vararg conditions: Pair<(EventOpMode.() -> Boolean)?, EventOpMode.() -> Unit>) = registerLoopHook {
    for (condition in conditions) {
        val (trigger, action) = condition
        if (trigger == null || trigger()) {
            action()
            break
        }
    }
}

val KProperty0<Boolean>.isPressed: EventOpMode.() -> Boolean get() = { this@isPressed.get() }