package org.firstinspires.ftc.teamcode.autonomous;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.movement.BrickHook;
import org.firstinspires.ftc.teamcode.movement.ElevatorOuttake;
import org.firstinspires.ftc.teamcode.movement.Intake;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive;

public class AutoRelayOpMode extends LinearOpMode {

    private double deltaTime;
    private int sideModifier;

    // CAMERA CONSTANTS (unknown as of yet)
    /** percentage of way up the screen the stones are (0.00 to 1.00)*/
    private final double EYE_LEVEL = 0.5;
    /** percentage of way across screen for each center of the stones (0.00 to 1.00) */
    private final double[] STONE_LOCATIONS = {};
    /** pixels between each reference pixel */
    private final int STONE_REF_SPACING = 3;
    /** number of pixels in one dimension that we will use as reference (should be and odd number) */
    private final int STONE_REF_DIMENS = 5;

    // FORMATION CONSTANTS (unknown as of yet)
    /** seconds to strafe to face the 2-block gap*/
    private final int[] GAP_STRAFE_DISTS = {0, 0, 0};

    /** average angular velocity of the robot at full power (degrees per millisecond) */
    private final double ANGULAR_VELOCITY = 1;

    private MecanumDrive drive;
    private Intake intake;
    //private ElevatorOuttake outtake;
    private BrickHook hook;

    private ColorSensor colorSensor;

    private int formation = 3;
    private double speed = 0.75;

    private ImageView imageView;
    private static final int REQUEST_IMAGE_CAPTURE = 101;

    private double FORWARD_VEL;
    private double STRAFE_VEL;

    public AutoRelayOpMode(int side, double deltaTime) {
        sideModifier = side;
        this.deltaTime = deltaTime;
    }

    /*
    private Camera.PictureCallback camPC = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {

            // here, were process the JPEG
            // this likely will be useless depending on how the color sensor works
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
            int y = (int)(bitmap.getHeight() * EYE_LEVEL);

            for (double xpos : STONE_LOCATIONS) {
                int center = (int)(bitmap.getWidth() * xpos);

                int sumBrightness = 0;
                for (int offsetx = STONE_REF_DIMENS / -2; offsetx <= STONE_REF_DIMENS / 2; offsetx++) {
                    for (int offsety = STONE_REF_DIMENS / -2; offsety <= STONE_REF_DIMENS / 2; offsety++) {
                        int currTile = bitmap.getPixel(offsetx * STONE_REF_SPACING + center, offsety * STONE_REF_SPACING + y);
                        sumBrightness += Color.red(currTile) + Color.green(currTile) + Color.blue(currTile);
                    }
                }
            }

            camera.stopPreview();

        }
    }; */

    @Override
    public void runOpMode() {

        // MOVEMENT CONSTANTS
        /** average forward velocity of the robot at full power (inches per millisecond) */
        FORWARD_VEL = 0.0347 / deltaTime;
        /** average strafing velocity of the robot at full power (inches per millisecond) */
        STRAFE_VEL = 0.0257 / deltaTime;

        drive = MecanumDrive.standard(hardwareMap);
        intake = Intake.standard(hardwareMap);
        //outtake = ElevatorOuttake.standard(hardwareMap);
        hook = BrickHook.standard(hardwareMap);

        /*
        [ KOOL-AID METHOD ]
        - Use camera to figure out formation and consequently the 2-block gap
        - Strafe to face the gap
        - Move forward to OH YEAH the unnecessary blocks
            Hopefully, the skystone should be hit in the same way each time, figured out via testing
        - Take the "upper" skystone and ferry to the build zone
            We can curve instead of strafe to save time
            Since our bot is a square on the XZ-plane, rotating will likely bonk the other skystone
        - Trace path backwards to past location
            Do NOT turn to face back, just drive backwards
        - Take the "lower" skystone and ferry to the build zone
            Do this second because (if I understand correctly) we'll have some added consistency due to the wall it touches
        - Park under the bridge
         */

        // because takePicture() is asynchronous, we have to call the rest of the code after the callback
        // getSkystoneSetup();
        drivePath();

    }

    private void getSkystoneSetup () {

    }

    private void drivePath () {

        try {
            if (formation == 1) {
                drive.forwardWithPower(speed * sideModifier);
                sleep((int)(4.5 / FORWARD_VEL));
                drive.strafeLeftWithPower(speed);
                sleep((int)(16 / STRAFE_VEL));
                drive.stop();
                hook.clamp();
                sleep(1000);
                drive.strafeRightWithPower(speed);
                sleep((int)(5 / STRAFE_VEL));
                drive.forwardWithPower(-speed * sideModifier);
                sleep((int)(34.5 / FORWARD_VEL));
                hook.release();
                drive.stop();
                sleep(1000);
                drive.forwardWithPower(speed * sideModifier);
                sleep((int)(46.5 / FORWARD_VEL));
                drive.strafeLeftWithPower(speed);
                sleep((int)(5 / STRAFE_VEL));
                drive.stop();
                hook.clamp();
                sleep(1000);
                drive.strafeRightWithPower(speed);
                sleep((int)(5 / STRAFE_VEL));
                drive.forwardWithPower(-speed * sideModifier);
                sleep((int)(46.5 / FORWARD_VEL));
                hook.release();
                drive.stop();
                sleep(1000);
                hook.stop();
            } else if (formation == 2) {
                drive.forwardWithPower(speed * sideModifier);
                sleep((int)(4.5 / FORWARD_VEL));
                drive.strafeLeftWithPower(speed);
                sleep((int)(16 / STRAFE_VEL));
                drive.stop();
                hook.clamp();
                sleep(1000);
                drive.strafeRightWithPower(speed);
                sleep((int)(5 / STRAFE_VEL));
                drive.forwardWithPower(-speed * sideModifier);
                sleep((int)(34.5 / FORWARD_VEL));
                hook.release();
                drive.stop();
                sleep(1000);
                drive.forwardWithPower(speed * sideModifier);
                sleep((int)(46.5 / FORWARD_VEL));
                drive.strafeLeftWithPower(speed);
                sleep((int)(5 / STRAFE_VEL));
                drive.stop();
                hook.clamp();
                sleep(1000);
                drive.strafeRightWithPower(speed);
                sleep((int)(5 / STRAFE_VEL));
                drive.forwardWithPower(-speed * sideModifier);
                sleep((int)(46.5 / FORWARD_VEL));
                hook.release();
                drive.stop();
                sleep(1000);
                hook.stop();
            } else if (formation == 3) {
                drive.forwardWithPower(speed * sideModifier);
                sleep((int)(4.5 / FORWARD_VEL));
                drive.strafeLeftWithPower(speed);
                sleep((int)(16 / STRAFE_VEL));
                drive.stop();
                hook.clamp();
                sleep(1000);
                drive.strafeRightWithPower(speed);
                sleep((int)(5 / STRAFE_VEL));
                drive.forwardWithPower(-speed * sideModifier);
                sleep((int)(34.5 / FORWARD_VEL));
                hook.release();
                drive.stop();
                sleep(1000);
                drive.forwardWithPower(speed * sideModifier);
                sleep((int)(46.5 / FORWARD_VEL));
                drive.strafeLeftWithPower(speed);
                sleep((int)(5 / STRAFE_VEL));
                drive.stop();
                hook.clamp();
                sleep(1000);
                drive.strafeRightWithPower(speed);
                sleep((int)(5 / STRAFE_VEL));
                drive.forwardWithPower(-speed * sideModifier);
                sleep((int)(46.5 / FORWARD_VEL));
                hook.release();
                drive.stop();
                sleep(1000);
                hook.stop();
            }
        } catch (Exception e) {

        }

    }
    private int color (){
        return colorSensor.alpha();





    }
    // create function that traces path
    private void findSkystone(){
        drive.strafeLeftWithPower(1);
        int first_color=color();
        drive.forwardWithPower(1);
        int second_color=color();
        drive.forwardWithPower(1);
        int third_color=color();
        double leastColor=color();
        if (first_color <= second_color && first_color <= third_color) {
            leastColor = first_color;
        } else if (second_color <= third_color && second_color <= first_color) {
            leastColor = second_color;
        } else {
            leastColor = third_color;
        }
    }

    private void checkBricks () {

        drive.strafeLeftWithPower(speed);
        sleep((int)(16 / STRAFE_VEL));
        int f1 = 0; // check color
        sleep(800);

        drive.forwardWithPower(-speed * FORWARD_VEL);
        sleep((int)(8 / FORWARD_VEL));
        drive.stop();
        int f2 = 0; // check color
        sleep(800);

        drive.forwardWithPower(-speed * FORWARD_VEL);
        sleep((int)(8 / FORWARD_VEL));
        drive.stop();
        int f3 = 0; // check color
        sleep(800);

        if ((f1 > f2) && (f1 > f3)) {
            formation = 1;
        }
        if ((f2 > f1) && (f2 > f3)) {
            formation = 2;
        }
        if ((f3 > f1) && (f3 > f2)) {
            formation = 3;
        }
    }

}
