package org.firstinspires.ftc.teamcode.Backups;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.SubSystem.revolversub;
@Disabled
@Config
@Autonomous
public class AutoTryTirth extends LinearOpMode {

    private DcMotor leftFront, rightFront, leftBack, rightBack;
    private DcMotor intake;
    private DcMotorEx shooterB, shooterT;
    private Servo flicker;
    private IMU imu;
    private revolversub revolver;

    public static double HighVelocity = 1500;
    public static double F = 15;
    public static double P = 200;

    @Override
    public void runOpMode() throws InterruptedException {

        revolver = new revolversub(hardwareMap);
        imu = hardwareMap.get(IMU.class, "imu");
        leftFront = hardwareMap.get(DcMotor.class, "Fl");
        rightFront = hardwareMap.get(DcMotor.class, "Fr");
        leftBack = hardwareMap.get(DcMotor.class, "Bl");
        rightBack = hardwareMap.get(DcMotor.class, "Br");
        intake = hardwareMap.get(DcMotor.class, "intake");
        flicker = hardwareMap.get(Servo.class, "arm");
        shooterB = hardwareMap.get(DcMotorEx.class, "shooterB");
        shooterT = hardwareMap.get(DcMotorEx.class, "shooterT");

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);

        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.DOWN,
                RevHubOrientationOnRobot.UsbFacingDirection.RIGHT);
        imu.initialize(new IMU.Parameters(orientation));

        shooterT.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterT.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterB.setDirection(DcMotorSimple.Direction.REVERSE);

        PIDFCoefficients pidf = new PIDFCoefficients(P, 0, 0, F);
        shooterT.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);
        shooterB.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);

        telemetry.addLine("Autonomous Initialization Complete");
        telemetry.update();


        waitForStart();

        if (isStopRequested()) return;


        driveRobot(0.5, 0, 0);
        sleepAndUpdate(1800);
        stopRobot();
        intake.setPower(1.0);
        driveRobot(0.15, 0, 0);
        revolver.setTargetPosition(96);
        sleepAndUpdate(2500);
        intake.setPower(0);
        stopRobot();
        driveRobot(-0.4, -0.2, 0);
        shooterT.setVelocity(HighVelocity);
        shooterB.setVelocity(HighVelocity);
        sleepAndUpdate(2000);
        stopRobot();
        flicker.setPosition(0.3);
        sleepAndUpdate(500);
        flicker.setPosition(0);
        revolver.setTargetPosition(192);
        sleepAndUpdate(500);
        flicker.setPosition(0.3);
        sleepAndUpdate(500);
        flicker.setPosition(0);
        revolver.setTargetPosition(288);
        sleepAndUpdate(500);
        flicker.setPosition(0.3);
        sleepAndUpdate(500);
        flicker.setPosition(0);
        revolver.setTargetPosition(0);
        sleepAndUpdate(500);
        flicker.setPosition(0.3);
        sleepAndUpdate(500);
        flicker.setPosition(0);
        shooterT.setPower(0);
        shooterB.setPower(0);


        driveRobot(0, 0.5, 0);
        sleepAndUpdate(1500);
        stopRobot();
    }


    private void sleepAndUpdate(long milliseconds) {
        double startTime = getRuntime();
        while (opModeIsActive() && (getRuntime() - startTime) < (milliseconds / 1000.0)) {
            revolver.update();
            telemetry.addData("Revolver Chamber Target", revolver.getTarget());
            telemetry.update();
        }
    }

    private void driveRobot(double y, double x, double rx) {
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        leftFront.setPower((y + x + rx) / denominator);
        leftBack.setPower((y - x + rx) / denominator);
        rightFront.setPower((y - x - rx) / denominator);
        rightBack.setPower((y + x - rx) / denominator);
    }

    private void stopRobot() {
        driveRobot(0, 0, 0);
    }
}
