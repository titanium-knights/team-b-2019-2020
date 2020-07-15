package org.firstinspires.ftc.teamcode.autonomous.Odometry;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive2;

@TeleOp(name = "Passive GPS Test", group = "Calibration")
public class GoToSample extends LinearOpMode {
    DcMotor motorLF,motorLB, motorRF,motorRB;
    GlobalPosition gps;
    DcMotor odoLeft,odoRight,odoHorizontal;
    MecanumDrive2 md2;
    BNO055IMU imu;
    double COUNTS_PER_INCH = 307.699557;
    @Override
    public void runOpMode(){
        md2= new MecanumDrive2(hardwareMap);
        initialize();
        waitForStart();
        gps = new GlobalPosition(odoLeft, odoRight, odoHorizontal, COUNTS_PER_INCH, 75);
        Thread positionThread = new Thread(gps);
        positionThread.start();
        while(opModeIsActive()){
            telemetry.addData("X", gps.getGlobalX()/COUNTS_PER_INCH);
            telemetry.addData("Y",gps.getGlobalY()/COUNTS_PER_INCH);
            telemetry.addData("Angle", gps.getGlobalAngleDegrees());
            telemetry.update();
        }
        gps.stop();
    }
    public void goTo(double x, double y, double speed, double heading, double marginofError){
        double distanceRemaining = Math.hypot(x,y);
        while(opModeIsActive() && distanceRemaining>marginofError){
            double distanceX = x - gps.getGlobalX();
            double distanceY = y - gps.getGlobalY();
            double neccessaryAngle = Math.atan2(distanceY, distanceX);
            double xComp = getXComp(neccessaryAngle, speed);
            double yComp = getYComp(neccessaryAngle,speed);
            boolean turnRight  = heading>gps.getGlobalAngleDegrees();
            double angleCorrection = Math.abs(heading-gps.getGlobalAngleDegrees());
            double adjustment=0;
            md2.diagonal(speed, neccessaryAngle);
        }
        md2.stop();
    }
    public double getXComp(double angle, double speed){
        return (Math.sin(angle)*speed); //Sin because 0 degrees is true north rather than east like seen in a unit circle
    }
    public double getYComp(double angle, double speed){
        return (Math.cos(angle)*speed);
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
}
