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

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.movement.ElevatorOuttake;
import org.firstinspires.ftc.teamcode.movement.Intake;
import org.firstinspires.ftc.teamcode.movement.MecanumDrive;

public class AutoRelayOpMode extends LinearOpMode {

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

    private MecanumDrive drive;
    private Intake intake;
    private ElevatorOuttake outtake;

    private int formation;

    private ImageView imageView;
    private static final int REQUEST_IMAGE_CAPTURE = 101;

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
    };

    @Override
    public void runOpMode() {

        drive = MecanumDrive.standard(hardwareMap);
        intake = Intake.standard(hardwareMap);
        outtake = ElevatorOuttake.standard(hardwareMap);

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
        getSkystoneSetup();

    }

    private void getSkystoneSetup () {

    }

    private void drivePath () {

        try {
            if (formation == 1) {
                drive.strafeLeftWithPower(1);
                Thread.sleep(1);
                drive.forwardWithPower(1);
                Thread.sleep(1);
                drive.steerWithPower(0.1, 1);
                Thread.sleep(1);
                drive.steerWithPower(1, 1); // drive forward while slightly turning towards the brick
                intake.spin();
                Thread.sleep(1);
                drive.steerWithPower(1, 1);
                intake.stopSpinning();
                Thread.sleep(1);
                intake.spinReverse();
                Thread.sleep(1);
                drive.steerWithPower(-1, -1);
                intake.stopSpinning();
                Thread.sleep(1);
                drive.steerWithPower(1, -1);
                intake.spin();
                Thread.sleep(1);
                drive.steerWithPower(1, 1);
                Thread.sleep(1);
                intake.spinReverse();
                drive.stop();
            } else if (formation == 2) {
                drive.forwardWithPower(1);
                Thread.sleep(1);
            } else if (formation == 3) {
                drive.strafeRightWithPower(1);
                Thread.sleep(1);
                drive.forwardWithPower(1);
                Thread.sleep(1);
            }
        } catch (Exception e) {

        }

    }

}
