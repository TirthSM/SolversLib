package org.firstinspires.ftc.teamcode.Tirthtest.tLearning;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Control Hub
 * Motor port 00: motorOne
 */
@Disabled
@TeleOp
public class MotorOne extends LinearOpMode {

    private DcMotor motorOne;
    private double motorOneZeroPower = 0.0;
    private double motorOnePower = 1.0;

    @Override
    public void runOpMode() throws InterruptedException {
        initHardware();
        while (!isStarted()) {
            motorTelemetry();
        }
        waitForStart();
        while (opModeIsActive()) {
            teleOpControls();
            motorTelemetry();
        }
    }

    public void initHardware(){
        initMotorOne();
    }
    public void initMotorOne(){
        motorOne = hardwareMap.get(DcMotor.class, "motorOne");
        motorOne.setDirection(DcMotor.Direction.FORWARD);
        motorOne.setPower(motorOneZeroPower);
        motorOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorOne.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void teleOpControls(){
        if (gamepad2.x){
            motorOne.setPower(-motorOnePower);
        }
        if (gamepad2.a){
            motorOne.setPower(motorOneZeroPower);
        }
        if (gamepad2.b){
            motorOne.setPower(motorOnePower);
        }
    }
    public void motorTelemetry(){
        telemetry.addData("motorOne","Encode: %2d, Power: %.2f", motorOne.getCurrentPosition(), motorOne.getPower());
        telemetry.update();
    }
}