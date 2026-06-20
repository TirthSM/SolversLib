package org.firstinspires.ftc.teamcode.intothedeep;

import android.graphics.Canvas;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
@Disabled
public class OpenCV implements VisionProcessor {

    Rect Left_Rectangle;
    Rect Middle_Rectangle;
    Rect Right_Rectangle;

    Mat hsvMat = new Mat();

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        Left_Rectangle = new Rect(
                new Point(0, 0),
                new Point(0.33 * width, height)

        );

        Middle_Rectangle = new Rect(
                new Point(0.33 * width, 0),
                new Point(0.66 * width, height)
        );

        Right_Rectangle = new Rect(
                new Point(0.66 * width, 0),
                new Point(width, height)
        );
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {

        Imgproc.cvtColor(frame, hsvMat, Imgproc.COLOR_RGB2HSV);


        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
}
