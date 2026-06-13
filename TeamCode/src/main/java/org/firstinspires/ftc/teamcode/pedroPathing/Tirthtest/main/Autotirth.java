package org.firstinspires.ftc.teamcode.pedroPathing.Tirthtest.main;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class Autotirth extends LinearOpMode {

    private DcMotor leftFront, rightFront, leftBack, rightBack;
    private DcMotorEx launcherT,launcherB;
    private DcMotor revolver;
    private Servo flicker;
    private DcMotor intake;

    public static double Hightvelocity = -1450;
    public static int revolverPos;



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

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        revolver.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        revolver.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        launcherB.setVelocity(Hightvelocity);
        launcherT.setVelocity(Hightvelocity);
        sleep(1500);
        flicker.setPosition(1);
        sleep(700);
        flicker.setPosition(0);
        sleep(1000);
        RevolverunToPosition(96);
        sleep(3000);
        flicker.setPosition(1);
        sleep(700);
        flicker.setPosition(0);
        sleep(1000);
        RevolverunToPosition(192);
        sleep(3000);
        flicker.setPosition(1);
        sleep(1000);
        flicker.setPosition(0);
        leftFront.setPower(0.5);
        leftBack.setPower(0.5);// change from here
        rightBack.setPower(0.5);
        rightFront.setPower(0.5);
        sleep(1000);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);

        /*
        intake.setPower(1);
        sleep(1000);
        RevolverunToPosition(144);
        sleep(500);
        leftFront.setPower(0.5);
        leftBack.setPower(0.5);
        rightBack.setPower(0.5);
        rightFront.setPower(0.5);
        sleep(4000);
        RevolverunToPosition(48);
        sleep(1000);
        leftFront.setPower(-.6);
        leftBack.setPower(-.6);
        rightBack.setPower(-.6);
        rightFront.setPower(-.6);
        RevolverunToPosition(96);
        sleep(3000);
        flicker.setPosition(1);
        sleep(1000);
        flicker.setPosition(0);
        sleep(1000);
        RevolverunToPosition(192);
        sleep(3000);
        flicker.setPosition(1);
        sleep(1000);
        flicker.setPosition(0);
         */
    }
    public void RevolverunToPosition(int position) {
        revolver.setTargetPosition(position);
        revolver.setPower(1.0);
        revolver.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}