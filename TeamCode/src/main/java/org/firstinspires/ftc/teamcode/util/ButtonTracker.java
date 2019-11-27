package org.firstinspires.ftc.teamcode.util;

/**
 * A class used for tracking advanced button values.
 */
public class ButtonTracker {

    private boolean on;
    private boolean mode;
    private double duration;

    public ButtonTracker () {
        on = false;
        mode = false;
        duration = 0;
    }

    /**
     * Toggles the mode if the button was just pressed.
     * @param current The current value of the toggling button.
     */
    public void ifPress (boolean current) {
        if (!on && current) {
            mode = !mode;
        }
    }

    /**
     * Toggles the mode if the button was just released.
     * @param current The current value of the toggling button.
     */
    public void ifRelease (boolean current) {
        if (on && !current) {
            mode = !mode;
        }
    }

    /**
     * Updates the button's last value to the new value.
     * &nbsp; This should be called at the end of each call of init.
     * @param current The current value of the toggling button.
     */
    public void update (boolean current) {
        on = current;
    }

    /**
     * Returns the current mode of the ButtonTracker.
     * @return The current mode of the ButtonTracker.
     */
    public boolean getMode () {
        return mode;
    }

}
