package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.lang.reflect.Field;

public class InputRegister {

    private Gamepad gamepad;
    private Field field;
    private boolean lastFrame = false;
    private boolean currFrame = false;

    public InputRegister (Gamepad gamepad, String inputName) {
        try {
            this.gamepad = gamepad;
            field = gamepad.getClass().getDeclaredField(inputName);
        } catch (Exception exc) {
            throw new IllegalArgumentException("Could not find " + inputName + ".");
        }
    }

    public void update () {
        try {
            lastFrame = currFrame;
            currFrame = field.getBoolean(gamepad);
        } catch (Exception exc) {
            throw new RuntimeException("Could not update button.");
        }
    }

    public boolean ifPress () {
        if (!lastFrame && currFrame) {
            return true;
        }
        return false;
    }

    public boolean ifRelease () {
        if (lastFrame && !currFrame) {
            return true;
        }
        return false;
    }

}
