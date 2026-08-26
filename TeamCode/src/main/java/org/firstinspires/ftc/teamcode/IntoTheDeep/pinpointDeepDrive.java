package org.firstinspires.ftc.teamcode.IntoTheDeep;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

/**
 * ControlHub>
 * Motors-
 * 0 FrontRight
 * 1 BackRight
 * 2 BackLeft
 * 3 FrontLeft
 * Servos-
 * 0 arm
 * 1 Wrist
 * 2 intake
 * I2c Bus 0-
 * imu
 * ExpansionHub>
 * Motors-
 * 0 slides
 */
@Config
@TeleOp(group = "primary")
public class pinpointDeepDrive extends OpMode {
    private DcMotor frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor;
    private GoBildaPinpointDriver pinpoint;
    private Servo arm;
    private Servo Wrist;
    private CRServo intake;
    private DcMotor slides;
    double speed = 1.0;

    public static int power = 1;



    @Override
    public void init() {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "Fl");
        backLeftMotor = hardwareMap.get(DcMotor.class, "Bl");
        frontRightMotor = hardwareMap.get(DcMotor.class, "Fr");
        backRightMotor = hardwareMap.get(DcMotor.class, "Br");
        arm = hardwareMap.get(Servo.class, "arm");
        Wrist = hardwareMap.get(Servo.class, "wrist");
        intake = hardwareMap.get(CRServo.class, "intake");
        slides = hardwareMap.get(DcMotor.class, "slides");

        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        Wrist.setPosition(0.3);
        arm.setPosition(0.8);

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        configurePinpoint();

        FtcDashboard dashboard = FtcDashboard.getInstance();

        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }


    @Override
    public void start() {

        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0));
    }

    @Override
    public void loop() {

        pinpoint.update();

        if (gamepad1.back) {
            pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0));
            telemetry.addData("IMU Status", "Yaw Reset Initiated!");
        }

        double botHeading = pinpoint.getHeading(AngleUnit.RADIANS);

        double y_input = -gamepad1.left_stick_y;
        double x_input = gamepad1.left_stick_x * 1.1;
        double rotation_input = gamepad1.right_stick_x;


        double rotX = x_input * Math.cos(-botHeading) - y_input * Math.sin(-botHeading);
        double rotY = x_input * Math.sin(-botHeading) + y_input * Math.cos(-botHeading);

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rotation_input), 1);

        double frontLeftPower = (rotY + rotX + rotation_input) / denominator *speed;
        double backLeftPower = (rotY - rotX + rotation_input) / denominator *speed;
        double frontRightPower = (rotY - rotX - rotation_input) / denominator *speed;
        double backRightPower = (rotY + rotX - rotation_input) / denominator *speed;

        frontLeftMotor.setPower(frontLeftPower *1);
        backLeftMotor.setPower(backLeftPower *1);
        frontRightMotor.setPower(frontRightPower *1);
        backRightMotor.setPower(backRightPower *1);

        Pose2D pose2D = pinpoint.getPosition();

        if (gamepad2.a){
            speed = 1;
        }
        if (gamepad1.b){
            speed = 0.5;
        }

        //Controls
        if (gamepad1.a) {
            Wrist.setPosition(0.3);
        }
        if (gamepad1.b) {
            Wrist.setPosition(0.7);
        }
        if (gamepad1.x) {
            Wrist.setPosition(0.0);
        }
        if (gamepad1.y) {
            Wrist.setPosition(1.0);
        }


        if (gamepad1.right_bumper){
            intake.setPower(1);
        }
        if (gamepad1.left_bumper){
            intake.setPower(-1);
        }
        if (gamepad1.right_trigger_pressed){
            intake.setPower(0);
        }


        if (gamepad1.dpadLeftWasPressed()){
            arm.setPosition(0.55);
        }
        if (gamepad1.dpadDownWasPressed()){
            arm.setPosition(0.23);
        }
        if (gamepad1.dpadUpWasPressed()){
            arm.setPosition(0.8);
        }

        if (gamepad2.dpad_left) {
            slides.setPower(0.5);
            slides.setTargetPosition(-2000);
            slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        if (gamepad2.dpad_down) {
            slides.setPower(0.4);
            slides.setTargetPosition(0);
            slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        if (gamepad2.dpad_up) {
            slides.setPower(0.5);
            slides.setTargetPosition(-2600);
            slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }


        telemetry.addData("WirstPosition", Wrist.getPosition());
        telemetry.addData("intake power", intake.getPower());
        telemetry.addData("Armposition", arm.getPosition());
        telemetry.addData("Slidesposition", slides.getCurrentPosition());
        telemetry.addData("X coordinate (IN)", pose2D.getX(DistanceUnit.INCH));
        telemetry.addData("Y coordinate (IN)", pose2D.getY(DistanceUnit.INCH));
        telemetry.addData("Heading angle (DEGREES)", pose2D.getHeading(AngleUnit.DEGREES));
    }

    public void configurePinpoint(){
        pinpoint.setOffsets(76.2, 127, DistanceUnit.MM);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED,
                GoBildaPinpointDriver.EncoderDirection.REVERSED);

        pinpoint.resetPosAndIMU();
    }
}