package org.firstinspires.ftc.teamcode.Tirthtest.tLearning;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
@TeleOp
public class ServoOne extends LinearOpMode {
    private Servo servoOne;
    private double servoOneInitPosition = 0.5;
    private double servoOnePositionOne = 0.0;
    private double servoOnePositionTwo = 1.0;
    private int servoOneDelay = 10;


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
    }

    public void initServoOne() {
        servoOne = hardwareMap.get(Servo.class, "servoOne");
        servoOne.setDirection(Servo.Direction.FORWARD);
        servoOne.setPosition(servoOneInitPosition);
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
        telemetry.log().clear();
        telemetry.addData("Position", servoOne.getPosition());
        telemetry.addData("Direction", servoOne.getDirection());
        telemetry.update();
    }
}