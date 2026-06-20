package org.firstinspires.ftc.teamcode.Tirthtest.tLearning;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
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
@TeleOp
public class limelightTry extends LinearOpMode {

    private DcMotor leftFront, rightFront, leftBack, rightBack;
    private DcMotor intake;
    private DcMotorEx launcherT;
    private DcMotorEx launcherB;
    private DcMotor revolver;
    private Servo flicker;
    private IMU imu;
    private Limelight3A limelight;

    public static double velocity = 2000;
    public static int revolverPos = 96;

    final double Kp = 0.035;
    final double min_command = 0.05;

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

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);
        limelight.start();

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


            if (gamepad1.right_trigger > 0.1) {
                LLResult result = limelight.getLatestResult();
                if (result != null && result.isValid()) {
                    double tx = result.getTx();

                    rx = tx * Kp;

                    if (tx > 1.0) rx += min_command;
                    else if (tx < -1.0) rx -= min_command;
                }
            }


            if (gamepad1.options) {
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


            if (gamepad1.right_bumper) intake.setPower(1);
            else if (gamepad1.left_bumper) intake.setPower(-1);
            else if (gamepad1.dpad_down) intake.setPower(0);


            if (gamepad1.b) {
                if (revolverPos == 96) { RevolverunToPosition(96); revolverPos = 192; }
                else if (revolverPos == 192) { RevolverunToPosition(192); revolverPos = 288; }
                else if (revolverPos == 288) { RevolverunToPosition(288); revolverPos = 96; }
            }

            if (gamepad1.dpad_up) flicker.setPosition(0.3);
            else flicker.setPosition(0);

            if (gamepad1.y) {
                launcherB.setPower(-0.7);
                launcherT.setPower(-0.7);
            }
            if (gamepad1.dpad_left) {
                launcherB.setPower(0);
                launcherT.setPower(0);
            }
        }
    }
}