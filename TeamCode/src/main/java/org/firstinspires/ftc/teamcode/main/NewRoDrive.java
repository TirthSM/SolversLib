package org.firstinspires.ftc.teamcode.main;

/**
 * Controls-
 * right_bumper- intake
 * left_bumper- outtake
 * right_trigger- launcher T/B on
 * left_trigger- launcher T/B off
 * dpad_down- stop intake
 * (a)- revolver intake
 * (b)- revolver launcher
 * dpad_up flicker
 */

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
@Disabled
@Config
@TeleOp
public class NewRoDrive extends LinearOpMode {


    private DcMotor leftFront, rightFront, leftBack, rightBack;
    private DcMotor intake;
    private DcMotorEx launcherT;
    private DcMotorEx launcherB;
    private DcMotor revolver;
    private Servo flicker;
    private IMU imu;



    public static double Highvelocity = -2000;
    public static double Lowvelocity = -1275;
    public static int revolverPos = 96;

    public void RevolverunToPosition(int position) {
        revolver.setTargetPosition(position);
        revolver.setPower(1.0);
        revolver.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void runOpMode() {

        leftFront = hardwareMap.get(DcMotor.class, "Fl");
        rightFront = hardwareMap.get(DcMotor.class, "Fr");
        leftBack = hardwareMap.get(DcMotor.class, "Bl");
        rightBack = hardwareMap.get(DcMotor.class, "Br");
        intake = hardwareMap.get(DcMotor.class, "intake");
        launcherB = hardwareMap.get(DcMotorEx.class, "shooterB");
        launcherT = hardwareMap.get(DcMotorEx.class, "shooterT");
        revolver = hardwareMap.get(DcMotor.class, "revolver");
        flicker = hardwareMap.get(Servo.class, "arm");
        imu = hardwareMap.get(IMU.class, "imu");

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        revolver = hardwareMap.get(DcMotorEx.class,"revolver");
        revolver.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        revolver.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        revolver.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.DOWN,
                RevHubOrientationOnRobot.UsbFacingDirection.RIGHT);
        imu.initialize(new IMU.Parameters(orientation));


        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);


        waitForStart();

        while (opModeIsActive()) {


            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            if (gamepad1.back) {
                imu.resetYaw();
            }

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);


            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);
            rotX = rotX * 1.1;

            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            leftFront.setPower(frontLeftPower);
            leftBack.setPower(backLeftPower);
            rightFront.setPower(frontRightPower);
            rightBack.setPower(backRightPower);


            if (gamepad1.right_bumper) {
                intake.setPower(1);
            }
            if (gamepad1.left_bumper) {
                intake.setPower(-1);
            }
            if (gamepad1.dpadDownWasPressed()) {
                intake.setPower(0);
            }

//launcher revolver
            if (gamepad1.bWasPressed()) {
                if (revolverPos == 96) {
                    RevolverunToPosition(192);
                    revolverPos = 192;
                } else if (revolverPos == 192) {
                    RevolverunToPosition(288);
                    revolverPos = 288;
                } else if (revolverPos == 288) {
                    RevolverunToPosition(96);
                    revolverPos = 96;
                }
            }
//intake revolver
            if (gamepad1.aWasPressed()) {
                if (revolverPos == 96) {
                    RevolverunToPosition(240);
                    revolverPos = 192;
                } else if (revolverPos == 192) {
                    RevolverunToPosition(144);
                    revolverPos = 288;
                } else if (revolverPos == 288) {
                    RevolverunToPosition(48);
                    revolverPos = 96;
                }
            }


            if (gamepad1.dpadUpWasPressed()) {
                flicker.setPosition(0.3);
            } else {
                flicker.setPosition(0);
            }

            if (gamepad1.right_trigger_pressed) {
                launcherB.setVelocity(Highvelocity);
                launcherT.setVelocity(Highvelocity);
            }
            if (gamepad1.yWasPressed()) {
                launcherB.setPower(Lowvelocity);
                launcherT.setPower(Lowvelocity);
            }
            if (gamepad1.left_trigger_pressed ) {
                launcherB.setPower(0);
                launcherT.setPower(0);
            }

            if (gamepad1.xWasPressed()){
                launcherB.setPower(0);
                launcherT.setPower(0);
                intake.setPower(0);
            }

            telemetry.addData("Revolver Pos", revolver.getCurrentPosition());
            telemetry.update();
        }
    }
}