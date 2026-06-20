package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp
public class FlyWheelTuning extends OpMode {

    public DcMotorEx FlywheelmotorT, FlywheelmotorB;
    public double HighVelocity= -1500;
    public double LowVelocity= -1250;
    double curTargetVelocity = HighVelocity;

    double F = 0;
    double P = 0;

    double[] stepSizes = {10.0, 1.0, 0.1, 0.001, 0.0001};
    int stepIndex = 1;


    @Override
    public void init() {
        FlywheelmotorB = hardwareMap.get(DcMotorEx.class,"shooterB");
        FlywheelmotorT = hardwareMap.get(DcMotorEx.class,"shooterT");
        FlywheelmotorB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FlywheelmotorT.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FlywheelmotorB.setDirection(DcMotorSimple.Direction.REVERSE);
        FlywheelmotorT.setDirection(DcMotorSimple.Direction.REVERSE);

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0,0, F);
        PIDFCoefficients pidfCoefficients1 = new PIDFCoefficients(P, 0,0, F);
        FlywheelmotorB.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        FlywheelmotorT.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients1);
        telemetry.addLine("Init complete");
    }

    @Override
    public void loop() {
        if (gamepad1.yWasPressed()){
             if (curTargetVelocity == HighVelocity){
                 curTargetVelocity = LowVelocity;
             } else { curTargetVelocity = HighVelocity; }
        }

        if (gamepad1.bWasPressed()){
            stepIndex =(stepIndex + 1) % stepSizes.length;
        }

        if (gamepad1.dpadLeftWasPressed()){
            F -= stepSizes[stepIndex];
        }
        if (gamepad1.dpadRightWasPressed()){
            F += stepSizes[stepIndex];
        }
        if (gamepad1.dpadDownWasPressed()){
            P += stepSizes[stepIndex];
        }
        if (gamepad1.dpadUpWasPressed()){
            P -= stepSizes[stepIndex];
        }

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0,0, F);
        PIDFCoefficients pidfCoefficients1 = new PIDFCoefficients(P, 0,0, F);
        FlywheelmotorB.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        FlywheelmotorT.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients1);

        FlywheelmotorT.setVelocity(curTargetVelocity);
        FlywheelmotorB.setVelocity(curTargetVelocity);

        double curVelocity = FlywheelmotorB.getVelocity();
        double error = curTargetVelocity - curVelocity;

        telemetry.addData("Target Velocity", curTargetVelocity);
        telemetry.addData("Current Velocity", "%.2f", curVelocity);
        telemetry.addData("Error", "%.2f", error);
        telemetry.addLine("-----------------------------");
        telemetry.addData("Tuning P", "%.4f (D-Pad U/D)", P);
        telemetry.addData("Tuning f", "%.4f (D-Pad L/R)", F);
        telemetry.addData("Step Size", "%.4f (B Button)", stepSizes[stepIndex]);

    }
}
