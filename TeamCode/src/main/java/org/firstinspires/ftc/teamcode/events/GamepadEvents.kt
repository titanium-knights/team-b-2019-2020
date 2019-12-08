@file:JvmName("GamepadEvents")

package org.firstinspires.ftc.teamcode.events

import kotlin.reflect.KProperty0

val KProperty0<Boolean>.isPressed: EventOpMode.() -> Boolean get() = { this@isPressed.get() }
val KProperty0<Boolean>.isReleased: EventOpMode.() -> Boolean get() = { !this@isReleased.get() }

open class Button(val property: KProperty0<Boolean>) {
    protected var previousState = false
    protected var currentState = false

    open fun update() {
        previousState = currentState
        currentState = property.get()
    }

    val isPushed: Boolean = !previousState && currentState
    val isReleased: Boolean = previousState && !currentState

    val pushed: EventOpMode.() -> Boolean = { this@Button.isPushed }
    val released: EventOpMode.() -> Boolean = { this@Button.isReleased }
}

class ToggleButton(property: KProperty0<Boolean>, var isOn: Boolean = false): Button(property) {
    override fun update() {
        super.update()
        if (isPushed) {
            isOn = !isOn
        }
    }

    val isOff get() = !isOn

    val on: EventOpMode.() -> Boolean = { this@ToggleButton.isOn }
    val off: EventOpMode.() -> Boolean = { this@ToggleButton.isOff }
}

fun EventOpMode.makeButton(property: KProperty0<Boolean>): Button {
    val button = Button(property)
    registerLoopHook {
        button.update()
    }
    return button
}

fun EventOpMode.makeToggleButton(property: KProperty0<Boolean>, isOn: Boolean = false): ToggleButton {
    val button = ToggleButton(property, isOn)
    registerLoopHook {
        button.update()
    }
    return button
}