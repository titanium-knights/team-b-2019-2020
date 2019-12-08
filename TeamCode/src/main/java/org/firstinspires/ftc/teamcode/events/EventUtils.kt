@file:JvmName("EventUtils")

package org.firstinspires.ftc.teamcode.events

fun EventOpMode.doWhen(trigger: EventOpMode.() -> Boolean, action: EventOpMode.() -> Unit) = registerLoopHook {
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

fun EventOpMode.doCond(vararg conditions: Pair<(EventOpMode.() -> Boolean)?, () -> Unit>) = registerLoopHook {
    for (condition in conditions) {
        val (trigger, action) = condition
        if (trigger == null || trigger()) {
            action()
            break
        }
    }
}