package org.firstinspires.ftc.teamcode.autonomous;

import android.hardware.Camera;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.security.Policy;

public class AutoRelayOpMode extends LinearOpMode {

    private android.hardware.Camera.PictureCallback mPicture = new android.hardware.Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

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

        Camera cam = Camera.open();
        android.hardware.Camera.Parameters camParams = cam.getParameters();
        cam.startPreview();

    }

}
