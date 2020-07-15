package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.opencv.core.DMatch;

public class MecanumDrive2 {
    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    public MecanumDrive2(HardwareMap hardwareMap){
       this.fl = hardwareMap.get(DcMotor.class, "mecanum_fl");
            this.fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            this.fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            this.fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.fr = hardwareMap.get(DcMotor.class, "mecanum_fr");
            this.fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            this.fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            this.fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.bl = hardwareMap.get(DcMotor.class, "mecanum_bl");
            this.bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            this.bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            this.bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.br = hardwareMap.get(DcMotor.class, "mecanum_br");
            this.br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            this.br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            this.br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void setPower(double fl, double fr, double bl, double br){
        this.fl.setPower(fl);
        this.fr.setPower(fr);
        this.bl.setPower(bl);
        this.br.setPower(br);
    }
    public void stop(){
        setPower(0,0,0,0);
    }
    public void diagonal(double speed, double angleDegrees){
        double angle = Math.toRadians(angleDegrees);
        double componentsFRBL = (Math.cos(angle)+Math.sin(angle));
        double componentsFLBR = (Math.cos(angle)-Math.sin(angle));
        double flPower = speed*componentsFLBR;
        double frPower = speed*componentsFRBL;
        double blPower = speed*componentsFRBL;
        double brPower = speed*componentsFLBR;
        setPower(flPower,frPower,blPower,brPower);

    }

    public void driveTutorialTeleOp(double magnitude, double robotAngle, double rightX){
        double fld = magnitude * Math.cos(robotAngle) + rightX;
        double frd = magnitude * Math.sin(robotAngle) - rightX;
        double bld = magnitude * Math.sin(robotAngle) + rightX;
        double brd = magnitude * Math.cos(robotAngle) - rightX;
        setPower(fld,frd,bld,brd);
    }
    public void driveForwardWithPower(double power){
        diagonal(power,0);
    }
    public void strafeLeftWithPower(double power){
        diagonal(power, 90);
    }
    public void strafeRightWithPower(double power){
        diagonal(power, 270);
    }
    public void driveBackwardWithPower(double power){
        diagonal(power, 180);
    }
    public void turnInPlace(boolean clockwise, double power){
        if(clockwise){
            this.fl.setPower(power);
            this.bl.setPower(power);
            this.fr.setPower(power*-1);
            this.br.setPower(power*-1);
        }
        else{
            this.fl.setPower(-1*power);
            this.bl.setPower(-1*power);
            this.fr.setPower(power);
            this.br.setPower(power);
        }
    }
    public void reverseFL(){
        this.fl.setDirection(DcMotor.Direction.REVERSE);
    }
    public void reverseFR(){
        this.fr.setDirection(DcMotor.Direction.REVERSE);
    }
    public void reverseBL() { this.bl.setDirection(DcMotor.Direction.REVERSE); }
    public void reverseBr() { this.bl.setDirection(DcMotor.Direction.REVERSE); }
    public int getFLPos(){return this.fl.getCurrentPosition();}
    public int getFRPos(){return this.fr.getCurrentPosition();}
    public int getBLPos(){return this.bl.getCurrentPosition();}
    public int getBRPos(){return this.br.getCurrentPosition();}
    public void setMotorPos(int flPos, int frPos, int blPos, int brPos){
        this.fl.setTargetPosition(flPos);
        this.fr.setTargetPosition(frPos);
        this.bl.setTargetPosition(blPos);
        this.br.setTargetPosition(brPos);
    }



}
