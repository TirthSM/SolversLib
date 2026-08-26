package org.firstinspires.ftc.teamcode.Decode.NewRo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
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
        turret = hardwareMap.get(CRServo.class,"turret");

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
        sleep(1000);
        rightBack.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        leftFront.setPower(0);
        RevolverunToPosition(48);
        sleep(3000);
        flicker.setPosition(1);
        sleep(3000);
        flicker.setPosition(0);
        sleep(3000);
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
    }
    public void RevolverunToPosition(int position) {
        revolver.setTargetPosition(position);
        revolver.setPower(1.0);
        revolver.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
