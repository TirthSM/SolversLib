package org.firstinspires.ftc.teamcode.intothedeep;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Disabled
@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class DeepAuto extends LinearOpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor backLeft;


    @Override
    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        backRight = hardwareMap.get(DcMotor.class, "BackLeft");
        backLeft = hardwareMap.get(DcMotor.class, "BackRight");


        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        Forward(100,1);
        Stop();
        CrabLeft(0,1);

    }

    public void Forward(Integer position, double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(-power);
        backRight.setPower(-power);

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + position);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + position);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - position);
        backRight.setTargetPosition(backRight.getCurrentPosition() - position);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void RotateLeft(Integer position, double power) {
        frontLeft.setPower(power);
        frontRight.setPower(-power);
        backLeft.setPower(-power);
        backRight.setPower(power);

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - position);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + position);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - position);
        backRight.setTargetPosition(backRight.getCurrentPosition() + position);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void RotateRight(Integer position, double power) {
        frontLeft.setPower(power);
        frontRight.setPower(-power);
        backLeft.setPower(power);
        backRight.setPower(-power);

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + position);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - position);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - position);
        backRight.setTargetPosition(backRight.getCurrentPosition() + position);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void Backward(Integer position, double power) {
        frontLeft.setPower(-power);
        frontRight.setPower(-power);
        backLeft.setPower(power);
        backRight.setPower(power);

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - position);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - position);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + position);
        backRight.setTargetPosition(backRight.getCurrentPosition() + position);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void Stop() {
        Forward(0, 0);
        Backward(0, 0);
        RotateLeft(0, 0);
        RotateRight(0, 0);
    }

    public void CrabLeft(Integer position, double power) {
        frontLeft.setPower(-power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(-power);

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - position);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - position);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + position);
        backRight.setTargetPosition(backRight.getCurrentPosition() + position);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }
    public void CrabRight(Integer position, double power) {
        frontLeft.setPower(power);
        frontRight.setPower(-power);
        backLeft.setPower(-power);
        backRight.setPower(power);

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - position);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - position);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + position);
        backRight.setTargetPosition(backRight.getCurrentPosition() + position);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }
}