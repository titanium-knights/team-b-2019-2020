package org.firstinspires.ftc.teamcode.teleop.Replay;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.autonomous.Odometry.GlobalPosition;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive2;

import java.io.FileWriter;
import java.io.IOException;

public class Record {
    long startTime;
    FileWriter writer;
    MecanumDrive2 drive;
    public static String autoFile = "replayData.csv";
    //GlobalPosition gps;
    public Record(HardwareMap hmap) throws IOException{
        startTime = System.currentTimeMillis();
        writer=new FileWriter(autoFile);
        double COUNTS_PER_INCH = 307.699557;
        //gps = new GlobalPosition(hmap, COUNTS_PER_INCH, 75);
        //Thread positionThread = new Thread(gps);
        //positionThread.start();
        drive = new MecanumDrive2(hmap);
    }
    public void record() throws IOException{
        if(writer!=null) {/*
            writer.append("" + (System.currentTimeMillis() - startTime));
            writer.append("," + gps.getGlobalX());
            writer.append("," + gps.getGlobalY());
            writer.append("," + gps.getGlobalAngleDegrees());
            writer.append("\n");*/
            writer.append("" + (System.currentTimeMillis() - startTime));
            writer.append("," + drive.getFLPos());
            writer.append("," + drive.getFRPos());
            writer.append("," + drive.getBLPos());
            writer.append("," + drive.getBRPos());
            writer.append("\n");
        }
    }
    public void end() throws IOException{
        if(writer !=null) {
            writer.flush();
            writer.close();
        }
    }
}
