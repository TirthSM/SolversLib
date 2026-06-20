package org.firstinspires.ftc.teamcode.Backups;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Disabled
@TeleOp
public class DeepDriveTirth extends LinearOpMode {


private DcMotor FrontRight,FrontLeft,BackRight,BackLeft;
private Servo arm,Wrist;
private CRServo intake;
private IMU imu;
private DcMotor slides;


    @Override
    public void runOpMode() throws InterruptedException {

        FrontLeft = hardwareMap.get(DcMotor.class, "Fl");
        FrontRight = hardwareMap.get(DcMotor.class, "Fr");
        BackLeft = hardwareMap.get(DcMotor.class, "Bl");
        BackRight = hardwareMap.get(DcMotor.class, "Br");
        arm = hardwareMap.get(Servo.class,"arm");
        Wrist = hardwareMap.get(Servo.class,"wrist");
        intake = hardwareMap.get(CRServo.class,"intake");
        slides = hardwareMap.get(DcMotor.class,"slides");
        imu = hardwareMap.get(IMU.class,"imu");



        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.DOWN,
                RevHubOrientationOnRobot.UsbFacingDirection.RIGHT);
        imu.initialize(new IMU.Parameters(orientation));


        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);


        waitForStart();

        while (opModeIsActive()) {


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

            FrontLeft.setPower(frontLeftPower);
            BackLeft.setPower(backLeftPower);
            FrontRight.setPower(frontRightPower);
            BackRight.setPower(backRightPower);
            //
            if (gamepad1.dpad_up){
                slides.setPower(-50);
            }
            if (gamepad1.dpad_down) {
                slides.setPower(0);
            }

            //
            if (gamepad1.a){
                Wrist.setPosition(5);
            }
            if (gamepad1.b){
                Wrist.setPosition(100);
            }
            if (gamepad1.x){
                Wrist.setPosition(0);
            }
            if (gamepad1.y){
             Wrist.setPosition(50);
            }
            //
            if (gamepad1.right_bumper){
                intake.setPower(1);
            }
            if (gamepad1.left_bumper){
                intake.setPower(-1);
            }
            //
            if (gamepad1.dpad_left){
                arm.setPosition(0.7);
            }

        }
    }
}