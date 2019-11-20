package org.firstinspires.ftc.teamcode.util

import kotlin.math.pow
import kotlin.math.sqrt

@JvmName("Utils")

/**
 * @param x The x value of the stick.
 * @param y The y value of the stick.
 * @return Returns true if the stick is NOT drifting.
 */
fun accountDrift(x: Double, y: Double) = accountDrift(sqrt(x.pow(2) + y.pow(2)))

/**
 * @param x Value of a stick.
 * @return Returns true if the stick is drifting.
 */
fun accountDrift(x: Double) = x < 0.2