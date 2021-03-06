package org.firstinspires.ftc.teamcode.util;

public class Utils {

    private Utils() {

    }

    /**
     * @param x The x value of the stick.
     * @param y The y value of the stick.
     * @return Returns if the stick is NOT drifting.
     */
    public static boolean accountDrift (double x, double y) {
        return Math.sqrt((x * x) + (y * y)) < 0.2;
    }
}
