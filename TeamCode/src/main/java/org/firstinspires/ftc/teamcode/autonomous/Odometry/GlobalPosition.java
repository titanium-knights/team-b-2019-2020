package org.firstinspires.ftc.teamcode.autonomous.Odometry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;

public class GlobalPosition implements Runnable{
    private DcMotor odoLeft,odoRight,odoHorizontal;
    private int REFRESH_RATE;
    private File wheelDistance = AppUtil.getInstance().getSettingsFile("DONOTDELETEOdometry1.TXT");
    private File horizontalOff = AppUtil.getInstance().getSettingsFile("DONOTDELETEOdometry2.TXT");
    private double robotWheelDistance;
    private boolean running;
    private double horizontalOffsetDegree;
    private double odoRightPos =0;
    private double odoLeftPos =0;
    private double odoHorizontalPos = 0;
    private double deltaOrientation =0;
    private double globalX=0;
    private double globalY=0;
    private double globalAngleRadians =0;
    private double prevOdoLeftPos=0;
    private double prevOdoRightPos=0;
    private double prevOdoHorizontalPos=0;
    private double odoLeftReverser=1, odoRightReverser=1,odoHorizontalReverser=1;
    private double encoderCount;
    private HardwareMap hardwareMap;
    public GlobalPosition(DcMotor odoLeft, DcMotor odoRight, DcMotor odoHorizontal, double encoderCount, int refreshRate){
        this.odoLeft = odoLeft;
        this.odoRight = odoRight;
        this.odoHorizontal = odoHorizontal;
        this.REFRESH_RATE = refreshRate;
        this.encoderCount=encoderCount;
        this.robotWheelDistance = Double.parseDouble(ReadWriteFile.readFile(wheelDistance));
        this.horizontalOffsetDegree = Double.parseDouble(ReadWriteFile.readFile(horizontalOff));

    }
    public GlobalPosition(HardwareMap hardwareMap, double encoderCount, int refreshRate){
        this.odoLeft = hardwareMap.get(DcMotor.class, "odoL");
        this.odoRight = hardwareMap.get(DcMotor.class, "odoR");
        this.odoHorizontal = hardwareMap.get(DcMotor.class, "odoH");
        this.REFRESH_RATE = refreshRate;
        this.encoderCount=encoderCount;
        this.robotWheelDistance = Double.parseDouble(ReadWriteFile.readFile(wheelDistance));
        this.horizontalOffsetDegree = Double.parseDouble(ReadWriteFile.readFile(horizontalOff));
        initialize();
    }
    public void initialize(){
        odoLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        odoRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        odoHorizontal.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        odoRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        odoHorizontal.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        odoLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        odoLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        odoRight.setDirection(DcMotorSimple.Direction.REVERSE);
        odoHorizontal.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void updateGlobalPosition(){
        odoLeftPos = odoLeft.getCurrentPosition() *odoLeftReverser;
        odoRightPos=odoLeft.getCurrentPosition()*odoRightReverser;
        odoHorizontalPos=odoHorizontal.getCurrentPosition()*odoHorizontalReverser;
        //-----------------------------------------
        double deltaRight = odoRightPos-prevOdoRightPos;
        double deltaLeft = odoLeftPos-prevOdoLeftPos;
        double deltaTotalHorizontal = odoHorizontalPos-prevOdoHorizontalPos;
        double deltaAngle = (deltaLeft-deltaRight)/robotWheelDistance;
        globalAngleRadians += deltaAngle;
        double deltaHorizontal =deltaTotalHorizontal - (deltaAngle*horizontalOffsetDegree);
        //-----------------------------------------------
        double averageChangeLR = (deltaLeft +deltaRight)/2;
        globalX += ((averageChangeLR*Math.sin(globalAngleRadians))+(deltaHorizontal*Math.cos(globalAngleRadians)));
        globalY += ((averageChangeLR*Math.sin(globalAngleRadians))-(deltaHorizontal*Math.cos(globalAngleRadians)));
        //-----------------------------------------------
        prevOdoLeftPos = odoLeftPos;
        prevOdoHorizontalPos = odoHorizontalPos;
        prevOdoRightPos = odoRightPos;
    }
    public double getGlobalX(){
        return globalX;
    }
    public double getGlobalY(){
        return globalY;
    }
    public double getGlobalAngleRadians(){
        return globalAngleRadians;
    }
    public double getGlobalAngleDegrees(){
        return Math.toDegrees(globalAngleRadians);
    }
    public void stop(){
        running=false;
    }
    public void reverseOdoLeft(){
        odoLeftReverser=-1;
    }
    public void reverseOdoRight(){
        odoRightReverser=-1;
    }
    public void reverseOdoHorizontal(){
        odoHorizontalReverser=-1;
    }
    @Override
    public void run(){
        while(running){
            updateGlobalPosition();
            try{
               Thread.sleep(REFRESH_RATE);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
