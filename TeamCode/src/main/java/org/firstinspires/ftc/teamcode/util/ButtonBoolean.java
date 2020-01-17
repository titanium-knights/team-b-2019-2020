package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.Gamepad;

public class ButtonBoolean extends InputRegister {

    public boolean on = false;

    public ButtonBoolean (Gamepad gamepad, String inputName) {
        super(gamepad, inputName);
    }

}