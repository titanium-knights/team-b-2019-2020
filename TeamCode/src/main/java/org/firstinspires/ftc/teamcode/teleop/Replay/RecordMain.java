package org.firstinspires.ftc.teamcode.teleop.Replay;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.autonomous.Odometry.GlobalPosition;
import org.firstinspires.ftc.teamcode.movement.BrickHolder;
import org.firstinspires.ftc.teamcode.movement.ElevatorOuttake;
import org.firstinspires.ftc.teamcode.movement.ElevatorOuttake2;
import org.firstinspires.ftc.teamcode.movement.Intake;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive2;

import java.io.FileWriter;
import java.io.IOException;

public class RecordMain extends LinearOpMode {
    private MecanumDrive2 mecDrive;
    private BrickHolder brickHolder;
    DcMotor motorLF,motorLB, motorRF,motorRB;
    GlobalPosition gps;
    DcMotor odoLeft,odoRight,odoHorizontal;
    MecanumDrive2 md2;
    BNO055IMU imu;
    double COUNTS_PER_INCH = 307.699557;
    String autoFile = "replayData.csv";
    FileWriter writer;
    long startTime;
    Record recorder;
    public void initi(){
        mecDrive = new MecanumDrive2(hardwareMap);
        try {
            recorder = new Record(hardwareMap);
        }
        catch(IOException e){
            telemetry.addLine("IOEXCEPTION");
            telemetry.update();
        }
    }

    @Override
    public void runOpMode(){
        initi();
        while(opModeIsActive()) {
            double magnitude = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
            double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = gamepad1.right_stick_x;
            mecDrive.driveTutorialTeleOp(magnitude, robotAngle, rightX);
            try {
                recorder.record();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try{
            if(recorder != null){
                recorder.end();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
