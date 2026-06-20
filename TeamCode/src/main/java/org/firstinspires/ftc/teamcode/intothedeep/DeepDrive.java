package org.firstinspires.ftc.teamcode.intothedeep;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * control hub
 */
@Disabled
@TeleOp(group = "primary")
public class DeepDrive extends LinearOpMode {

    private DcMotor frontLeft;
    //private double frontLeftSensitivity = 0.5;
    private DcMotor frontRight;
    //private double frontRightSensitivity = 0.5;
    private DcMotor backRight;
    //private double backRightSensitivity = 0.5;
    private DcMotor backLeft;
    //private double backLeftSensitivity = 0.5;

    private DcMotor flip;
    private DcMotor arm;
    private DcMotor hang;

    private CRServo intakeBottom;
    private CRServo intakeTop;
    private Servo launcher;

    @Override
    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        backRight = hardwareMap.get(DcMotor.class, "BackRight");
        backLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        flip = hardwareMap.get(DcMotor.class, "Flip");
        arm = hardwareMap.get(DcMotor.class, "Arm");
        hang = hardwareMap.get(DcMotor.class, "Hang");


        intakeTop = hardwareMap.get(CRServo.class, "IntakeTop");
        intakeBottom = hardwareMap.get(CRServo.class, "IntakeBottom");
        launcher = hardwareMap.get(Servo.class, "Launcher");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        flip.setDirection(DcMotorSimple.Direction.REVERSE);
        flip.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flip.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hang.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hang.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double speed = 1.0;

        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

        initHardware();
        while (!isStarted()) {
//            motorTelemetry();
        }
        waitForStart();
        while (opModeIsActive()) {
//            motorTelemetry();

            double ArmPos = arm.getCurrentPosition();
            double ArmPower = gamepad2.right_stick_y;
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            if (gamepad1.back) {
                imu.resetYaw();
            }

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);


            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;  // Counteract imperfect strafing

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = ((rotY + rotX + rx) / denominator) * speed;
            double backLeftPower = ((rotY - rotX + rx) / denominator) * speed;
            double frontRightPower = ((rotY - rotX - rx) / denominator) * speed;
            double backRightPower = ((rotY + rotX - rx) / denominator) * speed;

            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);

//        vertical = -gamepad1.left_stick_y;
//        horizontal = gamepad1.left_stick_x;
//        pivot = gamepad1.right_stick_x;
//
//        frontRight.setPower(-pivot + vertical - horizontal);
//        backRight.setPower(-pivot + vertical + horizontal);
//        frontLeft.setPower(pivot + vertical + horizontal);
//        backLeft.setPower(pivot + vertical - horizontal);

            // arm
            if (ArmPower < 0 && ArmPos < 700) {
                arm.setTargetPosition(arm.getCurrentPosition() + 100);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm.setPower(1.0);
            }
            if (ArmPower < 0 && ArmPos > 700 && ArmPos < 1720) {
                arm.setPower(0.5);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm.setTargetPosition(arm.getCurrentPosition() + 50);
            }
            if (ArmPos <= 5) {
                arm.setPower(0);
                arm.setTargetPosition(arm.getCurrentPosition());
            }
            if (ArmPower > 0 && ArmPos > 0 && ArmPos < 700) {
                arm.setTargetPosition(arm.getCurrentPosition() - 50);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm.setPower(0.5);
            }
            if (ArmPower > 0 && ArmPos > 700 && ArmPos < 1800) {
                arm.setPower(0.5);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm.setTargetPosition(arm.getCurrentPosition() - 50);
            }
            if ((gamepad1.right_bumper) && (gamepad1.left_bumper)) {
                arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }

            // programming buttons test/ flip
            if (gamepad2.y) {
                flip.setPower(1.0);
                flip.setTargetPosition(750);
                flip.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (gamepad2.a) {
                flip.setPower(1.0);
                flip.setTargetPosition(1);
                flip.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (gamepad2.left_bumper) { //Override flip down
                flip.setTargetPosition(flip.getCurrentPosition() - 30);
                flip.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                flip.setPower(1.0);
            }
            if (gamepad2.right_bumper) { //Override flip up
                flip.setTargetPosition(flip.getCurrentPosition() + 30);
                flip.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                flip.setPower(1.0);
            }
            if ((gamepad2.right_bumper) && (gamepad2.left_bumper)) { //RESET FLIP ENCODER
                flip.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }

            if (gamepad2.b) { //in/out take system off
                flip.setPower(0);
                intakeTop.setPower(0);
                intakeBottom.setPower(0);
            }
            if (gamepad2.left_trigger > 0) { // outtake
                intakeBottom.setPower(0.3);
                intakeTop.setPower(0.3);
                intakeTop.setDirection(CRServo.Direction.FORWARD);
                intakeBottom.setDirection(DcMotorSimple.Direction.REVERSE);
            }
            if (gamepad2.right_trigger > 0) { //intake
                intakeBottom.setPower(1);
                intakeTop.setPower(1);
                intakeTop.setDirection(CRServo.Direction.REVERSE);
                intakeBottom.setDirection(CRServo.Direction.FORWARD);
            }

            //Launcher
            if (gamepad2.dpad_left) {
                launcher.setPosition(0.25);
            }
            // hang
            if (gamepad2.dpad_up) { //hang up
                hang.setPower(1.0);
                hang.setTargetPosition(-10500);
                hang.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (gamepad2.dpad_down) { //down
                hang.setPower(1.0);
                hang.setTargetPosition(-6000);
                hang.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (gamepad1.dpad_up) { //Override hang up
                hang.setTargetPosition(hang.getCurrentPosition() - 200);
                hang.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                hang.setPower(1.0);
            }
            if (gamepad1.dpad_down) { //Override hang down
                hang.setTargetPosition(hang.getCurrentPosition() + 200);
                hang.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                hang.setPower(1.0);
            }
            // speed change
            if (gamepad1.x) {
                speed = 0.4;
            }
            if (gamepad1.a) {
                speed = 1.0;
            }


            // programming joystick test
            //frontRight.setPower(gamepad2.right_stick_y * frontRightSensitivity);

            telemetry.addData("Angle",botHeading);
            telemetry.addData("IntakeTop", "power: %.2f", intakeTop.getPower());
            telemetry.addData("IntakeBottom", "power: %.2f", intakeBottom.getPower());
            telemetry.addData("Flip","Encoder: %2d, power: %.2f", flip.getCurrentPosition(), flip.getPower());
            telemetry.addData("Arm","Encoder: %2d, power: %.2f", arm.getCurrentPosition(), arm.getPower());
            telemetry.addData("Hang","Encoder: %2d, power: %.2f", hang.getCurrentPosition(), hang.getPower());
            telemetry.update();
        }

    }

    public void initHardware() {
    }
}
