package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static android.os.SystemClock.sleep;

public class TrayPull {

    public CRServo left;
    //public Servo armB;
    public CRServo right;
    //public Servo clawB;

    public TrayPull (CRServo left, CRServo right) {
        this.left = left;
        //this.armB = armB;
        this.right = right;
        //this.clawB = clawB;
    }
    public void down(){
        left.setDirection(CRServo.Direction.FORWARD);
        left.setPower(1);
        right.setDirection(CRServo.Direction.REVERSE);
        right.setPower(1);

    }
    public void up(){
        left.setDirection(CRServo.Direction.REVERSE);
        left.setPower(-1);
        right.setDirection(CRServo.Direction.FORWARD);
        right.setPower(-1);
    }
    public void stop(){
        left.setPower(0);
        right.setPower(0);
    }
    private static String[] standardMotorNames = {"tray_puller_l", "tray_puller_r"};

    public static TrayPull standard(HardwareMap hardwareMap) {
        return new TrayPull(hardwareMap.get(CRServo.class, standardMotorNames[0]), hardwareMap.get(CRServo.class, standardMotorNames[1]));
    }

}
