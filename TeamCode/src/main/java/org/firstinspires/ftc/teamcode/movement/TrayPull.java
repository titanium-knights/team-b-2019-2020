package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static android.os.SystemClock.sleep;

public class TrayPull {

    public Servo left;
    //public Servo armB;
    public Servo right;
    //public Servo clawB;

    public TrayPull (Servo left, Servo right) {
        this.left = left;
        //this.armB = armB;
        this.right = right;
        //this.clawB = clawB;
    }
    public void down(){
        left.setPosition(1);
        right.setPosition(0);

    }
    public void up(){
        left.setPosition(0);
        right.setPosition(1);
    }
    private static String[] standardMotorNames = {"tray_puller_l", "tray_puller_r"};

    public static TrayPull standard(HardwareMap hardwareMap) {
        return new TrayPull(hardwareMap.get(Servo.class, standardMotorNames[0]), hardwareMap.get(Servo.class, standardMotorNames[1]));
    }

}