package org.firstinspires.ftc.teamcode.autonomous.Odometry;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
@TeleOp(name = "Passive GPS Test", group = "Calibration")
public class PassiveCoordinateFinder extends LinearOpMode {
    DcMotor motorLF,motorLB, motorRF,motorRB;
    DcMotor odoLeft,odoRight,odoHorizontal;
    BNO055IMU imu;
    double COUNTS_PER_INCH = 307.699557;
    @Override
    public void runOpMode(){
        initialize();
        waitForStart();
        GlobalPosition gp = new GlobalPosition(odoLeft, odoRight, odoHorizontal, COUNTS_PER_INCH, 75);
        Thread positionThread = new Thread(gp);
        positionThread.start();
        while(opModeIsActive()){
            telemetry.addData("X", gp.getGlobalX()/COUNTS_PER_INCH);
            telemetry.addData("Y",gp.getGlobalY()/COUNTS_PER_INCH);
            telemetry.addData("Angle", gp.getGlobalAngleDegrees());
            telemetry.update();
        }
        gp.stop();
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
