package org.firstinspires.ftc.teamcode.pedroPathing.Tirthtest.tLearning;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
@TeleOp
public class ServoThree extends LinearOpMode {
    private Servo servoOne;
    private double servoOneInitPosition = 0.5;
    private double servoOnePositionOne = 0.0;
    private double servoOnePositionTwo = 1.0;
    private int servoOneDelay = 10;

    private CRServo crServoThree;
    private double crServoThreeZeroPower = 0.0;
    private double crServoThreePower = 1.0;


    @Override
    public void runOpMode() throws InterruptedException {
        initHardware();
        while (!isStarted()) {
            servoTelemetry();
        }
        waitForStart();
        while (opModeIsActive()) {
            teleOpControls();
            servoTelemetry();
        }
    }

    public void initHardware() {

        initServoOne();
        initServoThree();
    }

    public void initServoOne() {
        servoOne = hardwareMap.get(Servo.class, "servoOne");
        servoOne.setDirection(Servo.Direction.FORWARD);
        servoOne.setPosition(servoOneInitPosition);
    }
    public void initServoThree() {
        crServoThree = hardwareMap.get(CRServo.class, "crServoThree");
        crServoThree.setDirection(CRServo.Direction.FORWARD);
        crServoThree.setPower(crServoThreeZeroPower);

    }

    public void teleOpControls() {
        if (gamepad1.a) {
            servoOne.setPosition(servoOnePositionOne);
        }
        if (gamepad1.b) {
            servoOne.setPosition(servoOnePositionTwo);
        }
        if(gamepad1.right_bumper){
            servoOneSlower(servoOnePositionOne, servoOnePositionTwo, servoOneDelay);
        }

        if (gamepad1.dpad_left) {
            crServoThree.setPower(-crServoThreePower);
        }
        if (gamepad1.dpad_right) {
            crServoThree.setPower(crServoThreePower);
        }
        if(gamepad1.dpad_down){
            crServoThree.setPower(crServoThreeZeroPower);
        }
    }
    public void servoOneSlower(double startPostition, double endPosition, int delay){
        double range = ((endPosition - startPostition) * 100);
        for(int i = 0; i <= range; i++) {
            servoOne.setPosition(startPostition);
            sleep(delay);
            startPostition = startPostition + .01;
        }
    }

    public void servoTelemetry() {
//        telemetry.log().clear();
//        telemetry.addData("Position", servoOne.getPosition());
//        telemetry.addData("Direction", servoOne.getDirection());
        telemetry.addData("Servo three Power", crServoThree.getDirection());
        telemetry.addData("Servo three Direction", crServoThree.getDirection());
        telemetry.update();
    }
}
