package org.firstinspires.ftc.teamcode.pedroPathing.Tirthtest.tLearning;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.List;
@Disabled
@TeleOp
public class LimeLightt extends OpMode {

    private DcMotor motorone;
    private Limelight3A limelight3A;

    @Override
    public void init() {

        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(0);

        motorone = hardwareMap.get(DcMotor.class, "motorOne");
        motorone.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void start() {
        limelight3A.start();
    }

    @Override
    public void loop() {

        LLResult result = limelight3A.getLatestResult();


        if (result != null && result.isValid()) {

            List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
            for (LLResultTypes.FiducialResult fr : fiducialResults) {
                telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());

            telemetry.addData("Target X offset", result.getTx());
            telemetry.addData("Target Y offset", result.getTy());
            telemetry.addData("Target Area offset", result.getTa());
            motorone.setPower(1);

          if (result == null) {
            telemetry.addData("Target", "NOT DETECTED");
            motorone.setPower(0);
        }

    }
        }
    }

    @Override
    public void stop() {

        limelight3A.stop();
    }
    }

