package org.firstinspires.ftc.teamcode.autonomous;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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

    private Camera.PictureCallback camPC = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {

            // here, were process the JPEG
            // i'm still figuring that out

            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
            int y = (int)(bitmap.getHeight() * EYE_LEVEL);

            for (double xpos : STONE_LOCATIONS) {
                int center = (int)(bitmap.getWidth() * xpos);

                int sumBrightness = 0;
                for (int offset = STONE_REF_DIMENS / -2; offset <= STONE_REF_DIMENS / 2; offset++) {
                    int currTile = bitmap.getPixel(offset * STONE_REF_SPACING + center, y);
                }
            }

            camera.stopPreview();

        }
    };

    @Override
    public void runOpMode() {

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

        Camera cam = Camera.open();

        android.hardware.Camera.Parameters camParams = cam.getParameters();
        cam.startPreview();

        cam.takePicture(null, null, null, camPC);

    }

}
