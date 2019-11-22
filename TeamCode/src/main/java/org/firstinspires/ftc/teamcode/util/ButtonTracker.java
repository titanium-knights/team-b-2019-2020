package org.firstinspires.ftc.teamcode.util;

public class ButtonTracker {

    private boolean on;
    private boolean mode;

    public ButtonTracker () {
        on = false;
        mode = false;
    }

    public void ifPress (boolean current) {
        if (!on && current) {
            mode = !mode;
        }
    }

    public void ifRelease (boolean current) {
        if (on && !current) {
            mode = !mode;
        }
    }

    public void update (boolean current) {
        on = current;
    }

    public boolean getMode () {
        return mode;
    }

}
