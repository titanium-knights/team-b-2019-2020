package org.firstinspires.ftc.teamcode.autonomous.Odometry;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.movement.MecanumDrive2;

public class Odometry {
    GlobalPosition gps;
    MecanumDrive2 drive;
    Thread positionThread;
    public Odometry(HardwareMap hmap, double ENCODER_COUNT){
        gps = new GlobalPosition(hmap,ENCODER_COUNT,75);
        drive = new MecanumDrive2(hmap);
        positionThread = new Thread(gps);
        positionThread.start();
    }
    public void goTo(double x, double y, double speed, double heading, double marginofError){
        double distanceRemaining = Math.hypot(x,y);
        while(distanceRemaining>marginofError){
            double distanceX = x - gps.getGlobalX();
            double distanceY = y - gps.getGlobalY();
            double neccessaryAngle = Math.atan2(distanceY, distanceX);
            double xComp = getXComp(neccessaryAngle, speed);
            double yComp = getYComp(neccessaryAngle,speed);
            boolean turnRight  = heading>gps.getGlobalAngleDegrees();
            double angleCorrection = Math.abs(heading-gps.getGlobalAngleDegrees());
            double adjustment=0;
            drive.diagonal(speed, neccessaryAngle);
        }
        drive.stop();
    }
    public double getXComp(double angle, double speed){
        return (Math.sin(angle)*speed); //Sin because 0 degrees is true north rather than east like seen in a unit circle
    }
    public double getYComp(double angle, double speed){
        return (Math.cos(angle)*speed);
    }
}
