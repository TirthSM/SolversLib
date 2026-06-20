package org.firstinspires.ftc.teamcode.main;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

@Autonomous
public class CloseAuto extends LinearOpMode{
    private DcMotor leftFront, rightFront, leftBack, rightBack;
    private DcMotorEx launcherT, launcherB;
    private DcMotor revolver;
    private Servo flicker;
    private DcMotor intake;
    private CRServo turret;


    @Override
    public void runOpMode() {
        leftFront = hardwareMap.get(DcMotor.class, "Fl");
        leftBack = hardwareMap.get(DcMotor.class, "Bl");
        rightFront = hardwareMap.get(DcMotor.class, "Fr");
        rightBack = hardwareMap.get(DcMotor.class, "Br");
        launcherT = hardwareMap.get(DcMotorEx.class, "shooterT");
        launcherB = hardwareMap.get(DcMotorEx.class, "shooterB");
        revolver = hardwareMap.get(DcMotor.class, "revolver");
        flicker = hardwareMap.get(Servo.class, "arm");
        intake = hardwareMap.get(DcMotor.class, "intake");
        turret = hardwareMap.get(CRServo.class,"Turret");



        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        launcherB.setVelocity(1275);
        launcherT.setVelocity(1275);
        sleep(500);
        rightBack.setPower(-0.5);
        rightFront.setPower(-0.5);
        leftBack.setPower(-0.5);
        leftFront.setPower(-0.5);
        sleep(5000);
        rightBack.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        leftFront.setPower(0);
        revolver.setTargetPosition(48);
        sleep(2000);
        flicker.setPosition(1);
        sleep(300);
        flicker.setPosition(0);
        sleep(900);
        revolver.setTargetPosition(144);
        sleep(3000);
        flicker.setPosition(1);
        sleep(300);
        flicker.setPosition(0);
        revolver.setTargetPosition(240);
        sleep(2000);
        flicker.setPosition(1);
        sleep(300);
        flicker.setPosition(0);
        sleep(700);
        revolver.setTargetPosition(96);
        sleep(500);
        rightBack.setPower(0.3);
        rightFront.setPower(0.3);
        leftBack.setPower(-0.3);
        leftFront.setPower(-0.3);
        sleep(700);
        rightBack.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        leftFront.setPower(0);
        intake.setPower(1);
        sleep(500);
        rightBack.setPower(0.4);
        rightFront.setPower(0.4);
        leftBack.setPower(0.4);
        leftFront.setPower(0.4);

    }
    public void RevolverunToPosition(int position) {
        revolver.setTargetPosition(position);
        revolver.setPower(1.0);
        revolver.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
