package org.firstinspires.ftc.teamcode.autonomous.EasyOpenCV;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class SkystoneDetector extends OpenCvPipeline {
    private Mat matrix = new Mat();
    public int position = 2;
    public SkystoneDetector(){}
    @Override
    public final Mat processFrame(Mat givenMatrix){
        givenMatrix.copyTo(matrix);
        if(matrix.empty()){
            return matrix;
        }
        Imgproc.cvtColor(matrix,matrix,Imgproc.COLOR_RGB2YCrCb);
        //Given the converted matrix of the image the phone id currently seeing.
        // We crop the matrix so that we get three submatrices focusing on the 3 skystones
        //row start,rowend,columnstart,columnEnd
        Mat left = matrix.submat(120,150,10,50);
        Mat center = matrix.submat(120,150,80,120);
        Mat right = matrix.submat(120,150,150,190);
        double leftSum = Core.sumElems(left).val[2];
        double centerSum = Core.sumElems(center).val[2];
        double rightSum=Core.sumElems(right).val[2];
        if(leftSum>centerSum&&leftSum>rightSum){
            position=0;
        }
        else if(centerSum>leftSum&&centerSum>rightSum){
            position=1;
        }
        else{
            position=2;
        }
        return matrix;
    }
}
