package org.firstinspires.ftc.teamcode.Tirthtest.tLearning;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp
public class Test extends LinearOpMode  {

    @Override
    public void runOpMode() throws InterruptedException {
        initHareware();
        while (!isStarted()) {
        }
        waitForStart();
        while (opModeIsActive()) {
        }
    }

    public void initHareware(){
    }
}
