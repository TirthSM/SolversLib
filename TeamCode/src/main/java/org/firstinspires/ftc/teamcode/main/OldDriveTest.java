package org.firstinspires.ftc.teamcode.main;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * controlHub
 * motors-
 * port00-Bl
 * port01-Fl
 * port02-Fr
 * port03-Br
 * servos-
 * intake
 * stopper
 * arm
 * expansionHub
 * motors-
 * port00-revolver
 * port01-shooter
 */
/**
 * Controls-
 * right_Trigger-launcher on
 * left_Trigger-launcher stop
 * right_Bumper-intake
 * left_Bumper-outtake
 * Dpad_up-revolver
 * Dpad_down-intake stop
 * (a)-flicker up
 * right_Trigger-stopper up
 * left_Trigger-stopper down
 */
@Disabled
@Config
@TeleOp
public class OldDriveTest extends OpMode {

    private DcMotor leftFront, rightFront, leftBack, rightBack;
    private CRServo intake;
    private DcMotorEx launcher;
    private Servo flicker;
    private DcMotor revolver;
    private Servo stopper;
    private IMU imu;


    double INTAKE_POWER = 1;
    double FLICKER_UP   = 0.209;
    double FLICKER_DOWN = -0.825;
    double STOPPER_DOWN = -30;
    double STOPPER_UP = 15;

    public static double F = 20;
    public static double P = 14;

//    public static double velocity = 2000;
    public static int revolverPos = 96;
    public static int pos = 96;

    public void RevolverunToPosition(int position){
        revolver.setTargetPosition(position);
        revolver.setPower(1.0);
        revolver.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void init() {

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 14, 20, F);
        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        telemetry.addLine("init complete");

        leftFront = hardwareMap.get(DcMotor.class, "Fl");
        leftBack = hardwareMap.get(DcMotor.class, "Bl");
        rightFront = hardwareMap.get(DcMotor.class, "Fr");
        rightBack = hardwareMap.get(DcMotor.class, "Br");

        intake = hardwareMap.get(CRServo.class, "intake");
        flicker = hardwareMap.get(Servo.class, "arm");
        revolver = hardwareMap.get(DcMotor.class, "revolver");
        stopper = hardwareMap.get(Servo.class, " stopper");
        stopper.setPosition(-20);
        imu = hardwareMap.get(IMU.class, "imu");


        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        revolver.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        imu.initialize(parameters);


        launcher = hardwareMap.get(DcMotorEx.class,"shooter");
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcher.setDirection(DcMotorSimple.Direction.REVERSE);


        revolver.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        revolver.setTargetPosition(96);
        revolver.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        revolverPos = 96;

        telemetry.addLine("Get To Driving");
    }

    public void FieldCentric() {
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

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

    }

    @Override
    public void loop() {

        FieldCentric();


        if (gamepad1.right_bumper) {
            intake.setPower(-INTAKE_POWER);
        }
        if (gamepad1.left_bumper) {
            intake.setPower(INTAKE_POWER);
        }
        if (gamepad1.dpad_down) {
            intake.setPower(0);
        }


        if (gamepad1.dpadUpWasPressed()) {
            if (revolverPos == 96) {
                RevolverunToPosition(910);
                revolverPos = 192;
            } else if (revolverPos == 192) {
                RevolverunToPosition(535);
                revolverPos = 288;
            } else if (revolverPos == 288) {
                RevolverunToPosition(30);
                revolverPos = 96;
            }
        }
        if (gamepad1.dpadLeftWasPressed()){
            revolver.setTargetPosition(96);
        }
        if (gamepad1.dpadLeftWasReleased()) {
            revolver.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            revolver.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }



        if (gamepad1.right_trigger_pressed) {
            launcher.setVelocity(P);
        }
        if (gamepad1.left_trigger_pressed) {
            launcher.setVelocity(0);
        }

        if (gamepad1.a) {
            flicker.setPosition(FLICKER_UP);
        } else {
            flicker.setPosition(FLICKER_DOWN);
        }

        if (gamepad1.right_trigger_pressed) {
            stopper.setPosition(STOPPER_UP);
        }
        if (gamepad1.left_trigger_pressed){
            stopper.setPosition(STOPPER_DOWN);
        }

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 14, 20, F);
        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        telemetry.addData("Launcher Power", "%.2f", launcher.getPower());
        telemetry.addData("Sorter Power", revolver.getPower());
        telemetry.addData("Pusher Position", flicker.getPosition());
        telemetry.update();
    }

}
