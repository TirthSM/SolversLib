package org.firstinspires.ftc.teamcode.main;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class FarAuto extends LinearOpMode {

    private DcMotor leftFront, rightFront, leftBack, rightBack;
    private DcMotorEx launcherT, launcherB;
    private DcMotor revolver;
    private Servo flicker;
    DcMotor intake;
    public static double Hightvelocity = -1450;


    @Override
    public void runOpMode() {
        leftFront = hardwareMap.get(DcMotor.class, "Fl");
        leftBack  = hardwareMap.get(DcMotor.class, "Bl");
        rightFront = hardwareMap.get(DcMotor.class, "Fr");
        rightBack = hardwareMap.get(DcMotor.class, "Br");
        launcherT = hardwareMap.get(DcMotorEx.class,"shooterT");
        launcherB = hardwareMap.get(DcMotorEx.class,"shooterB");
        revolver = hardwareMap.get(DcMotor.class, "revolver");
        flicker = hardwareMap.get(Servo.class, "arm");
        intake = hardwareMap.get(DcMotor.class,"intake");

        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        launcherB.setVelocity(Hightvelocity);
        launcherT.setVelocity(Hightvelocity);
        RevolverunToPosition(48);
        sleep(3000);
        flicker.setPosition(1);
        sleep(300);
        flicker.setPosition(0);
        sleep(900);
        RevolverunToPosition(144);
        sleep(3000);
        flicker.setPosition(1);
        sleep(300);
        flicker.setPosition(0);
        RevolverunToPosition(240);
        sleep(2000);
        flicker.setPosition(1);
        sleep(300);
        flicker.setPosition(0);
        sleep(700);
        RevolverunToPosition(96);
        leftFront.setPower(0.6);
        leftBack.setPower(-0.6);
        rightBack.setPower(0.6);
        rightFront.setPower(-0.6);
        intake.setPower(1);
        sleep(1000);
        leftFront.setPower(0.4);
        leftBack.setPower(0.4);
        rightBack.setPower(0.4);
        rightFront.setPower(0.4);
        RevolverunToPosition(192);
        sleep(700);
        RevolverunToPosition(288);
        sleep(1000);
        leftFront.setPower(-0.4);
        leftBack.setPower(-0.4);
        rightBack.setPower(-0.4);
        rightFront.setPower(-0.4);
        sleep(1000);
        leftFront.setPower(-0.5);
        leftBack.setPower(0.5);
        rightBack.setPower(-0.5);
        rightFront.setPower(0.5);
        sleep(500);
        leftBack.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
        leftFront.setPower(0);
        RevolverunToPosition(48);
        sleep(2000);
        flicker.setPosition(1);
        sleep(300);
        flicker.setPosition(0);
        sleep(900);
        RevolverunToPosition(144);
        sleep(3000);
        flicker.setPosition(1);
        sleep(300);
        flicker.setPosition(0);
        RevolverunToPosition(240);
        sleep(3000);
        flicker.setPosition(1);
        sleep(300);
        flicker.setPosition(0);
        RevolverunToPosition(96);
        leftFront.setPower(0.3);
        leftBack.setPower(0.3);
        rightBack.setPower(0.3);
        rightFront.setPower(0.3);
    }

    public void RevolverunToPosition(int position) {
        revolver.setTargetPosition(position);
        revolver.setPower(1.0);
        revolver.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}


