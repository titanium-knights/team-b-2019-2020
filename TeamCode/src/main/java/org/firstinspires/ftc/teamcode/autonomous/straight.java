package org.firstinspires.ftc.teamcode.autonomous;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.internal.android.dx.cf.attrib.InnerClassList;
import org.firstinspires.ftc.teamcode.movement.BrickHolder;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive;
import org.firstinspires.ftc.teamcode.movement.PlateClamp;

@Autonomous(name = "straight", group = "Autonomous")
public class straight extends AutoBaseOpMode {
    double stoneDiff;
    double stoneDiff2;
    private static ColorSensor sensorColor;
    private static DistanceSensor sensorDistance;
    private static DistanceSensor backDistance;
    private static DistanceSensor backLeftDistance;
    private static DistanceSensor frontDistance;
    double driftAdjustment = -3;

    private PlateClamp clamp;
    private BrickHolder holder;
    private int sideModifier = 1;
    private int positionOffset = 0;
    private double speed = 1;
    private double deltaTime = 2.5;
    private double FORWARD_VEL;
    private double STRAFE_VEL;
    private static float satValPos[] = {0F, 0F, 0F};//Saturation values for left,center, and right stones will be stored in this array of 3 elements
    private static float hsvValues[] = {0F, 0F, 0F};//Hue, saturation, & vue

    private static double SCALE_FACTOR;
    /*public AllInclusiveAuto(int side, double deltaTime) {
        sideModifier = side;
        this.deltaTime = deltaTime;
    }*/

    @Override
    public void runOpMode() {
        super.runOpMode();
        waitForStart();
        drive.forwardWithPower(0.25);
        sleep(1000);
        drive.stop();


    }
}
