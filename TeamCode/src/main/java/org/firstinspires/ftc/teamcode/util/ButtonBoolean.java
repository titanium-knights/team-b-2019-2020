package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.Gamepad;

public class ButtonBoolean extends InputRegister {

    private boolean on = false;

    public ButtonBoolean (Gamepad gamepad, String inputName) {
        super(gamepad, inputName);
    }

    public boolean get () {
        return on;
    }

    @Override
    public void update() {
        super.update();
        if (ifPress()) {
            on = !on;
        }
    }
}

