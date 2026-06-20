package org.firstinspires.ftc.teamcode.Backups;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.controller.PIDController;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
@Disabled
@Config//important
@TeleOp
public class relovr extends OpMode {
    private DcMotor leftFront, rightFront, leftBack, rightBack;
    private DcMotor intake;
    private DcMotorEx launcherT;
    private DcMotorEx launcherB;
    private Servo flicker;
    private IMU imu;
    public static double Highvelocity = -2000;
    public static double Lowvelocity = -1275;
    private PIDController controller;//important



    public static double p = 0.1, i = 0, d= 0.001;
    public static double f = 0.000001;

    public static int target = 96;

    private final double ticks_in_degree = 360/ 180.0;//changes depending on the motor

    private DcMotorEx Revolver;

    @Override
    public void init(){
        controller = new PIDController(p, i, d);

        imu = hardwareMap.get(IMU.class, "imu");
        leftFront = hardwareMap.get(DcMotor.class, "Fl");
        rightFront = hardwareMap.get(DcMotor.class, "Fr");
        leftBack = hardwareMap.get(DcMotor.class, "Bl");
        rightBack = hardwareMap.get(DcMotor.class, "Br");
        intake = hardwareMap.get(DcMotor.class, "intake");
        flicker = hardwareMap.get(Servo.class, "arm");
        launcherB = hardwareMap.get(DcMotorEx.class, "shooterB");
        launcherT = hardwareMap.get(DcMotorEx.class, "shooterT");

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());//allow to do stuff in dashboard
        Revolver = hardwareMap.get(DcMotorEx.class,"revolver");
        Revolver.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);//better stopping
        Revolver.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        Revolver.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);//it better to run without encoders because it is faster
    }


    @Override
    public void loop(){
        controller.setPIDF(p, i, d, f);
        int revpose = Revolver.getCurrentPosition();
        double pid = controller.calculate(revpose, target);//math
        double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;//math

        controller.setTolerance(0.5);//makes more accurete
        controller.atSetPoint();//this always paired with setTolerance
        double power = pid + ff;//math that sets the power
        Revolver.setPower(power);
        telemetry.addData("pose1",revpose);
        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.DOWN,
                RevHubOrientationOnRobot.UsbFacingDirection.RIGHT);
        imu.initialize(new IMU.Parameters(orientation));


        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);

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

        if (gamepad1.a) {
            if (target == 96) {
            } else if (target == 192){
            } else if (target == 288);
        }
        if (gamepad1.b){
            if (target == 48){
            } else if (target== 144){
            } else if (target == 244);
        }

        if (gamepad1.right_bumper) {
            intake.setPower(1);
        }
        if (gamepad1.left_bumper) {
            intake.setPower(-1);
        }
        if (gamepad1.dpad_down) {
            intake.setPower(0);
        }

        if (gamepad1.dpad_up) {
            flicker.setPosition(0.3);
        } else {
            flicker.setPosition(0);
        }

        if (gamepad1.right_trigger_pressed) {
            launcherB.setVelocity(Highvelocity);
            launcherT.setVelocity(Highvelocity);
        }
        if (gamepad1.y) {
            launcherB.setPower(Lowvelocity);
            launcherT.setPower(Lowvelocity);
        }
        if (gamepad1.left_trigger_pressed) {
            launcherB.setPower(-0.1);
            launcherT.setPower(-0.1);
        }



        telemetry.update();
    }
}