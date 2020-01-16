package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.Gamepad;

public class ButtonSelector<ValType> extends InputRegister {

    private ValType[] values;
    private int index = 0;

    public ButtonSelector (Gamepad gamepad, String inputName, ValType[] values) {
        super(gamepad, inputName);
        this.values = values;
    }

    @Override
    public boolean ifPress() {
         if (super.ifPress()) {
             index = (index + 1) % values.length;
             return true;
         }
         return false;
    }

    public ValType get () {
        return values[index];
    }
}
