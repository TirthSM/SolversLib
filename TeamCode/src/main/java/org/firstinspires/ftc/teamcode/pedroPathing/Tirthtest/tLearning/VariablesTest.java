package org.firstinspires.ftc.teamcode.pedroPathing.Tirthtest.tLearning;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp
public class VariablesTest extends OpMode {
    public void init() {
        int teamNumber = 22111;
        double motorSpeed = 0.75;
        boolean clawClosed = true;
        String teamName = "Mustangs";
        int motorAngle = 90;

        telemetry.addData(" Team Number", teamNumber);
        telemetry.addData("motor Speed", motorSpeed);
        telemetry.addData("claw closed", clawClosed);
        telemetry.addData("Name", teamName);
        telemetry.addData("motor Angle",motorAngle);
    }
    @Override
public void loop(){
    }
}
