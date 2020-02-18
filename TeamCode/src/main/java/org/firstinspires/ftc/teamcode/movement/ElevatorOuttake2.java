package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static android.os.SystemClock.sleep;

public class ElevatorOuttake2 {

    public CRServo left;
    //public Servo armB;
    public CRServo right;
    //public Servo clawB;

    public ElevatorOuttake2 (CRServo left, CRServo right) {
        this.left = left;
        //this.armB = armB;
        this.right = right;
        //this.clawB = clawB;
    }
    public void grab(){
        left.setDirection(CRServo.Direction.FORWARD);
        left.setPower(1);
        right.setDirection(CRServo.Direction.REVERSE);
        right.setPower(1);

    }
    public void release(){
        left.setDirection(CRServo.Direction.REVERSE);
        left.setPower(1);
        right.setDirection(CRServo.Direction.FORWARD);
        right.setPower(1);
    }
    public void stop(){
        left.setPower(0);
        right.setPower(0);
    }
    private static String[] standardMotorNames = {"outtake_claw", "outtake_claw_right"};

    public static ElevatorOuttake2 standard(HardwareMap hardwareMap) {
        return new ElevatorOuttake2(hardwareMap.get(CRServo.class, standardMotorNames[0]), hardwareMap.get(CRServo.class, standardMotorNames[1]));
    }

}
