package org.firstinspires.ftc.teamcode.Backups;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
@Disabled
@TeleOp
public class drivesorter extends OpMode {

    private DcMotor leftFront, rightFront, leftBack, rightBack;
    private CRServo intake;
    private DcMotor launcher;
    private Servo flicker;
    private DcMotor revolver;
    private IMU imu;

    // Constants
    double INTAKE_POWER = 0.90;
    double LAUNCH_POWER = -1;
    double SORT_POWER   = 0.60;
    double FLICKER_UP   = 0.50;
    double FLICKER_DOWN = 0.10;


    private int currentPositionMultiplier = 0;
    private final int POSITION_INCREMENT = 180;
    private boolean lastDpadRight = false;

    @Override
    public void init() {
        leftFront = hardwareMap.get(DcMotor.class, "Fl");
        leftBack = hardwareMap.get(DcMotor.class, "Bl");
        rightFront = hardwareMap.get(DcMotor.class, "Fr");
        rightBack = hardwareMap.get(DcMotor.class, "Br");
        intake = hardwareMap.get(CRServo.class, "intake");
        launcher = hardwareMap.get(DcMotor.class, "shooter");
        flicker = hardwareMap.get(Servo.class, "arm");
        revolver = hardwareMap.get(DcMotor.class, "revolver");
        imu = hardwareMap.get(IMU.class, "imu");
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        revolver.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        revolver.setTargetPosition(0);
        revolver.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        revolver.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        launcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        imu.initialize(parameters);

        flicker.setPosition(FLICKER_DOWN);
    }

    @Override
    public void loop() {
        FieldCentric();


        if (gamepad1.right_bumper) {
            intake.setPower(-INTAKE_POWER);
        } else if (gamepad1.left_bumper) {
            intake.setPower(INTAKE_POWER);
        } else {
            intake.setPower(0);
        }


        if (gamepad2.dpad_right && !lastDpadRight) {
            currentPositionMultiplier++;
        }
        lastDpadRight = gamepad2.dpad_right;

        revolver.setTargetPosition(currentPositionMultiplier * POSITION_INCREMENT);
        revolver.setPower(SORT_POWER);


        if (gamepad2.dpad_up) {
            flicker.setPosition(FLICKER_UP);
        } else if (gamepad2.dpad_down) {
            flicker.setPosition(FLICKER_DOWN);
        }


        if (gamepad2.right_trigger > 0.1) {
            launcher.setPower(LAUNCH_POWER);
        } else {
            launcher.setPower(0);
        }

        telemetry.addData("Revolver Target", currentPositionMultiplier * POSITION_INCREMENT);
        telemetry.addData("Current Encoder", revolver.getCurrentPosition());
        telemetry.update();
    }

    public void FieldCentric() {
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        if (gamepad1.options) imu.resetYaw();

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        leftFront.setPower((rotY + rotX + rx) / denominator);
        leftBack.setPower((rotY - rotX + rx) / denominator);
        rightFront.setPower((rotY - rotX - rx) / denominator);
        rightBack.setPower((rotY + rotX - rx) / denominator);
    }
}