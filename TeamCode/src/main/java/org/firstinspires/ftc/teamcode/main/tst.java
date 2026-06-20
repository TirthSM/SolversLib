package org.firstinspires.ftc.teamcode.main;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
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
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.SubSystem.revolversub;

@Config//important
@TeleOp
public class tst extends OpMode {
    private DcMotor leftFront, rightFront, leftBack, rightBack;
    private DcMotor intake;
    private DcMotorEx shooterB;
    private DcMotorEx shooterT;
    private Servo flicker;
    private IMU imu;
    private CRServo turret;
    private Limelight3A limelight;
    public static double HighVelocity = 1500;
    public static double LowVelocity = 1275;
    public double curTargetVelocity = HighVelocity;
    public static double F = 15;//17.5
    public static double P = 200;

    revolversub revolver;

    @Override
    public void init(){


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
        turret = hardwareMap.get(CRServo.class, "Turret");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());//allow to do stuff in dashboard


        shooterT = hardwareMap.get(DcMotorEx.class,"shooterT");
        shooterT.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterT.setDirection(DcMotorSimple.Direction.REVERSE);

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        shooterT.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        telemetry.addLine("init complete");

        shooterB = hardwareMap.get(DcMotorEx.class,"shooterB");
        shooterB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterB.setDirection(DcMotorSimple.Direction.REVERSE);

        PIDFCoefficients pidfCoefficients1 = new PIDFCoefficients(P,0,0,F);
        shooterB.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients1);
        telemetry.addLine("init complete");
    }


    @Override
    public void loop(){
        revolver.update();

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

        if (gamepad1.aWasPressed()){
            if (revolver.getTarget() == 0){
                revolver.setTargetPosition(96);
            } else if (revolver.getTarget()== 96){
                revolver.setTargetPosition(192);
            } else if (revolver.getTarget()== 192) {
                revolver.setTargetPosition(288);
            }else {
                revolver.setTargetPosition(96);
            }
        }
        if (gamepad1.bWasPressed()){
            if (revolver.getTarget() == 0|| revolver.getTarget() == 96|| revolver.getTarget() ==192|| revolver.getTarget() == 288){
                revolver.setTargetPosition(48);
            } else if (revolver.getTarget()== 48){
                revolver.setTargetPosition(144);
            } else if (revolver.getTarget()== 144) {
                revolver.setTargetPosition(240);
            }else {
                revolver.setTargetPosition(0);
            }
        }
        if(gamepad1.xWasPressed()){
            revolver.goToSlot(0);
        }

        if (gamepad1.right_bumper) {
            intake.setPower(1);
        }
        if (gamepad1.left_bumper) {
            intake.setPower(-1);
        }
        if (gamepad1.dpadDownWasPressed()) {
            intake.setPower(0);
        }

        if (gamepad1.dpadUpWasPressed()) {
            flicker.setPosition(0.3);
        } else {
            flicker.setPosition(0);
        }

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        PIDFCoefficients pidfCoefficients1 = new PIDFCoefficients(P,0, 0, F);
        shooterT.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        shooterB.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients1);

        if (gamepad1.right_trigger_pressed) {
            shooterT.setVelocity(curTargetVelocity);
            shooterB.setVelocity(curTargetVelocity);
        }
        if (gamepad1.yWasPressed()) {
            shooterB.setVelocity(LowVelocity);
            shooterT.setVelocity(LowVelocity);
        }
        if (gamepad1.left_trigger_pressed) {
            shooterB.setPower(0);
            shooterT.setPower(0);
        }
        if (gamepad2.dpadRightWasPressed()){
            turret.setPower(0.2);
        }
        if (gamepad2.dpadLeftWasPressed()){
            turret.setPower(-0.2);
        }

        telemetry.addData("Revolver Target Pos", revolver.getTarget());
        telemetry.addData("Revolver Current Pos", revolver.getTarget());
        telemetry.update();
    }
}