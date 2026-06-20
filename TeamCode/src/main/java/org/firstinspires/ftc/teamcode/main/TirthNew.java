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

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
@Disabled
@Config
@TeleOp
public class TirthNew extends LinearOpMode {

    private DcMotor leftFront, rightFront, leftBack, rightBack;
    private DcMotor intake;
    private DcMotorEx launcherT;
    private DcMotorEx launcherB;
    private DcMotorEx revolver;
    private Servo flicker;
    private IMU imu;

    public static double Highvelocity = -2000;
    public static double Lowvelocity = -1275;
    public static int revolverPos = 96;


    public static double p = 10.0;
    public static double i = 3.0;
    public static double d = 0.0;
    public static double f = 0.0;

    public void RevolverunToPosition(int position) {

        PIDFCoefficients pidf = new PIDFCoefficients(p, i, d, f);
        revolver.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, pidf);
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
        revolver = hardwareMap.get(DcMotorEx.class, "revolver");
        flicker = hardwareMap.get(Servo.class, "arm");
        imu = hardwareMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.DOWN,
                RevHubOrientationOnRobot.UsbFacingDirection.RIGHT);
        imu.initialize(new IMU.Parameters(orientation));

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);


        revolver.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        revolver.setTargetPosition(0);
        revolver.setMode(DcMotor.RunMode.RUN_TO_POSITION);

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
            if (gamepad1.dpad_down) {
                intake.setPower(0);
            }


            if (gamepad1.b) {
                if (revolverPos == 96) {
                    RevolverunToPosition(96);
                    revolverPos = 192;
                } else if (revolverPos == 192) {
                    RevolverunToPosition(192);
                    revolverPos = 288;
                } else if (revolverPos == 288) {
                    RevolverunToPosition(288);
                    revolverPos = 96;
                }
            }

            if (gamepad1.a) {
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

            if (gamepad1.dpad_up) {
                flicker.setPosition(0.3);
            } else {
                flicker.setPosition(0);
            }


            if (gamepad1.right_trigger > 0.3) {
                launcherB.setVelocity(Highvelocity);
                launcherT.setVelocity(Highvelocity);
            }
            if (gamepad1.y) {
                launcherB.setPower(Lowvelocity);
                launcherT.setPower(Lowvelocity);
            }
            if (gamepad1.left_trigger > 0.3) {
                launcherB.setPower(-0.1);
                launcherT.setPower(-0.1);
            }

            telemetry.addData("Revolver Target Pos", revolver.getTargetPosition());
            telemetry.addData("Revolver Current Pos", revolver.getCurrentPosition());
            telemetry.update();
        }
    }
}