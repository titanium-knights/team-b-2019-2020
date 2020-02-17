package org.firstinspires.ftc.teamcode.sensors;

import android.graphics.Color;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * <p>Helper class preconfigured for easy retrieval of our robot's sensors.</p>
 *
 */
public class StandardSensors {
    private HardwareMap hardwareMap;

    /**
     * Constructs a StandardSensors object with the given hardware map.
     * @param hardwareMap hardware map of the robot.
     */
    public StandardSensors(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public double getVoltage() {
        double result = Double.MAX_VALUE;
        for (VoltageSensor sensor: hardwareMap.voltageSensor) {
            double voltage = sensor.getVoltage();
            if (voltage <= 0) {
                result = Math.min(result, voltage);
            }
        }

        return result;
    }

    /**
     * Returns a color sensor preconfigured for our robot.
     * @return a preconfigured color sensor.
     */
    @Deprecated public ColorSensor getColorSensor() {
        return getLeftColorSensor();
    }
    public ColorSensor getLeftColorSensor() {
        return hardwareMap.get(ColorSensor.class, "left_color");
    }
    public ColorSensor getRightColorSensor() {
        return hardwareMap.get(ColorSensor.class, "right_color");
    }

    private DistanceSensor getDistanceSensor(String name) {
        return hardwareMap.get(DistanceSensor.class, name);
    }

    @Deprecated public DistanceSensor getFrontDistanceSensor() { return getDistanceSensor("front_distance"); }
    public DistanceSensor getLeftDistanceSensor() { return getDistanceSensor("left_distance"); }
    public DistanceSensor getRightDistanceSensor() { return getDistanceSensor("right_distance"); }
    public DistanceSensor getBackDistanceSensor() { return getDistanceSensor("back_distance"); }
    public DistanceSensor getArmDistanceSensor() { return getDistanceSensor("arm_distance"); }

    public TouchSensor getArmTouchSensor() { return hardwareMap.get(TouchSensor.class, "arm_touch"); }
}