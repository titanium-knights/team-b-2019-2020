package org.firstinspires.ftc.teamcode.movement

import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap

class ElevatorOuttake(
        val vertical: DcMotor,
        val horizontal: DcMotor,
        val clamp: CRServo,
        val clampB: CRServo
) {

    fun moveVertical(power: Double) {
        vertical.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        vertical.power = power
    }

    /** Stops the vertical movement of the elevator */
    fun stopVertical() = moveVertical(0.0)

    /** Moves the claw forwards or backwards at the given speed */

    fun moveHorizontal(power: Double) {
        horizontal.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        horizontal.power = power
    }
    fun setEncoders(){
        vertical.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontal.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /*fun moveToEncoder(horizontalVal:Int, verticalVal:Int){
        horizontal.setMode(DcMotor.RunMode.RUN_TO_POSITION)
        vertical.setMode(DcMotor.RunMode.RUN_TO_POSITION)
        vertical.setTargetPosition(verticalVal)
        horizontal.setTargetPosition(horizontalVal)
        horizontal.setPower(0.75)
        vertical.setPower(0.75)
        horizontal.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER)
        vertical.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER)
    }*/
    fun getHorizontalEncoder(): Int{
        return horizontal.getCurrentPosition()

    }
    fun getVerticalEncoder(): Int{
        return vertical.getCurrentPosition()
    }
    /*fun moveToEncoder(horizontalVal:Double, verticalVal:Double){
        if(vertical.getCurrentPosition()<verticalVal){
            while(vertical.getCurrentPosition()<verticalVal){
                moveVertical(0.75)
            }
            stopElevators()
        }
        else{
            while(vertical.getCurrentPosition() > verticalVal){
                moveVertical(-0.75)
            }
            stopElevators()
        }
        if(horizontal.getCurrentPosition()<horizontalVal){
            while(horizontal.getCurrentPosition()<horizontalVal){
                moveHorizontal(0.75)
            }
        }
        else{
            while(horizontal.getCurrentPosition()>=horizontalVal){
                moveHorizontal(-0.75)
            }
        }
    }*/
    fun moveToEncoder(horizontalVal: Double, verticalVal: Double){
        vertical.setTargetPosition(verticalVal.toInt())
        horizontal.setTargetPosition(horizontalVal.toInt())
        vertical.setPower(0.75)
        horizontal.setPower(0.75)
        vertical.setMode(DcMotor.RunMode.RUN_TO_POSITION)
        horizontal.setMode(DcMotor.RunMode.RUN_TO_POSITION)
        vertical.setPower(0.0)
        horizontal.setPower(0.0)
    }
    /** Stops the horizontal movement of the claw */
    fun stopHorizontal() = moveHorizontal(0.0)

    fun moveElevators(verticalPower: Double, horizontalPower: Double) {
        vertical.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        moveVertical(verticalPower)
        moveHorizontal(horizontalPower)
    }

    /** Stops the movement of the elevator and claw */
    fun stopElevators() {
        moveElevators(0.0, 0.0)
        vertical.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    }

    fun moveClamp(power: Double) {
        clamp.power = power
        clampB.power = 1-power
    }
    fun grabClamp(){
        clamp.power = 0.0
        clampB.power = 1.0
    }
    fun releaseClamp(){
        clamp.power = 1.0
        clampB.power = 0.0
    }
    fun stopClamp() = moveClamp(0.0)

    fun stopAll() {
        stopElevators()
        stopClamp()
    }

    companion object {
        @JvmStatic fun standard(hardwareMap: HardwareMap): ElevatorOuttake = ElevatorOuttake(
                hardwareMap[DcMotor::class.java, "vertical_elevator"],
                hardwareMap[DcMotor::class.java, "horizontal_elevator"],
                hardwareMap[CRServo::class.java, "outtake_claw"],
                hardwareMap[CRServo::class.java, "outtake_claw_right"]
        )
    }
}