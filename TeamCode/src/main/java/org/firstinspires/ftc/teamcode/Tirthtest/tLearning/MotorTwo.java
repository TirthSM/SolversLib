package org.firstinspires.ftc.teamcode.Tirthtest.tLearning;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
/**
 * control Hub:
 * Motor Port 00: motorOne
 * Motor port 01: motorTwo
 */
@TeleOp
public class MotorTwo extends LinearOpMode {

    private DcMotor motorOne;
    private double motorOneZeroPower = 0.0;
    private double motorOnePower = 1.0;
    private DcMotor motorTwo;
    private double motorTwoZeroPower = 0.0;
    private double motorTwoSensitivity = 0.5;


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
        initMotorTwo();
    }
    public void initMotorOne(){
        motorOne = hardwareMap.get(DcMotor.class, "motorOne");
        motorOne.setDirection(DcMotor.Direction.FORWARD);
        motorOne.setPower(motorOneZeroPower);
        motorOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorOne.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void initMotorTwo(){
        motorTwo = hardwareMap.get(DcMotor.class, "motorTwo");
        motorTwo.setDirection(DcMotor.Direction.REVERSE);
        motorTwo.setPower(motorTwoZeroPower);
        motorTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
        motorTwo.setPower(gamepad2.right_stick_y * motorTwoSensitivity);
    }
    public void motorTelemetry(){
        telemetry.addData("motorOne","Encode: %2d, Power: %.2f", motorOne.getCurrentPosition(), motorOne.getPower());
        telemetry.addData("motorTwo","Encode: %2d, Power: %.2f", motorTwo.getCurrentPosition(), motorTwo.getPower());
        telemetry.update();
    }
}