package org.firstinspires.ftc.teamcode.autonomous.Odometry;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;

//Used Videos created by Wizrds.exe for aid with this
@TeleOp(name = "Calibrate Odometry")
public class CalibrateAndCreateConstants extends LinearOpMode {
    DcMotor motorLF,motorLB, motorRF,motorRB;
    DcMotor odoLeft,odoRight,odoHorizontal;
    BNO055IMU imu;
    final double PIVSPEED =0.5;
    final double COUNTS_PER_INCH = 307.699557;
    ElapsedTime timer = new ElapsedTime();
    double horizontalOffset=0;
    File wheelDistance = AppUtil.getInstance().getSettingsFile("DONOTDELETEOdometry1.TXT");
    File horizontalOff = AppUtil.getInstance().getSettingsFile("DONOTDELETEOdometry2.TXT");
    @Override
    public void runOpMode() throws InterruptedException{
        initialize();
        waitForStart();
        while(angle()<90 && opModeIsActive()){
            setPowerAll(PIVSPEED,PIVSPEED,-PIVSPEED,-PIVSPEED);
            if(angle()>=60){
                setPowerAll(-PIVSPEED/2,-PIVSPEED/2, -PIVSPEED/2, -PIVSPEED/2);
            }
            telemetry.addData("Angle", angle());
            telemetry.update();
        }
        setPowerAll(0,0,0,0);
        double placeholder=0;
        timer.reset();
        while(timer.milliseconds()<1000 && opModeIsActive()){
            placeholder*=1;
        }
        double odoLeftPosition,odoRightPosition, odoHorizontalPosition,angle;
        angle = angle();
        odoLeftPosition = odoLeft.getCurrentPosition();
        odoRightPosition = odoRight.getCurrentPosition();
        odoHorizontalPosition = odoHorizontal.getCurrentPosition();
        double difference = Math.abs(odoLeftPosition)+Math.abs(odoRightPosition);
        double verticalOffPerDegree = difference/angle;
        double wheelSeperation = (180*verticalOffPerDegree)/(Math.PI*COUNTS_PER_INCH);
        horizontalOffset = odoHorizontal.getCurrentPosition()/(Math.toRadians(angle()));
        ReadWriteFile.writeFile(wheelDistance,String.valueOf(wheelSeperation));
        ReadWriteFile.writeFile(horizontalOff, String.valueOf(horizontalOffset));
        

    }
    public void initialize(){
        motorLF = hardwareMap.get(DcMotor.class, "motorLF");
        motorRF = hardwareMap.get(DcMotor.class, "motorRF");
        motorLB = hardwareMap.get(DcMotor.class, "motorLB");
        motorRB = hardwareMap.get(DcMotor.class, "motorRB");
        //-----------------------------------------------------------
        odoLeft = hardwareMap.get(DcMotor.class, "odoL");
        odoRight = hardwareMap.get(DcMotor.class, "odoR");
        odoHorizontal = hardwareMap.get(DcMotor.class, "odoH");
        //-----------------------------------------------------------
        motorLF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        odoLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        odoRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        odoHorizontal.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //-----------------------------------------------------------
        motorLF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        odoRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        odoHorizontal.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        odoLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //-----------------------------------------------------------
        motorRF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //------------------------------------------------------------
        odoLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        odoRight.setDirection(DcMotorSimple.Direction.REVERSE);
        odoHorizontal.setDirection(DcMotorSimple.Direction.REVERSE);

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
            parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
            parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
            parameters.loggingEnabled      = true;
            parameters.loggingTag          = "IMU";
            parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(parameters);
        telemetry.addData("IMU Setup", "Initialization is Complete");
        telemetry.update();
    }
    public double angle(){
        return (-imu.getAngularOrientation().firstAngle);
    }
    public void setPowerAll(double lf,double lb,double rf,double rb){
        motorLF.setPower(lf);
        motorLB.setPower(lb);
        motorRB.setPower(rb);
        motorRF.setPower(rf);
    }


}
