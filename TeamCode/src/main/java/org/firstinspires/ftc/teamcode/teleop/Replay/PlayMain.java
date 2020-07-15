package org.firstinspires.ftc.teamcode.teleop.Replay;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.Odometry.Odometry;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class PlayMain extends LinearOpMode {
    Scanner input;
    Play player;
    MecanumDrive2 mecDrive;
    long start;
    Odometry odometry;
    double timeDiff;
    boolean onTime = true;
    double timeStamp;
    double COUNTS_PER_INCH = 307.699557;
    public void initi() throws FileNotFoundException {
        odometry = new Odometry(hardwareMap,COUNTS_PER_INCH);
        mecDrive = new MecanumDrive2(hardwareMap);
        input = new Scanner(new File(Record.autoFile));
        input.useDelimiter(",|\\n");
        start = System.currentTimeMillis();
    }
    @Override
    public void runOpMode() {
        try {
            initi();
        }catch(IOException e){
            e.printStackTrace();
        }
        while(opModeIsActive()){
            if(input!=null && input.hasNextDouble()){
                if(onTime){
                    timeStamp = input.nextDouble();
                }
                timeDiff = timeStamp-(System.currentTimeMillis()-start);
                if(timeDiff<=0){
                    mecDrive.setMotorPos(input.nextInt(),input.nextInt(),input.nextInt(),input.nextInt());
                    onTime=true;
                }
                else{
                    onTime=false;
                }
            }
            else{
                this.end();
                if (input != null) {
                    input.close();
                    input = null;
                }
            }
        }
    }
    public void end() {
        mecDrive.setMotorPos(0,0,0,0);
        if (input != null) {
            input.close();
        }
    }

}
