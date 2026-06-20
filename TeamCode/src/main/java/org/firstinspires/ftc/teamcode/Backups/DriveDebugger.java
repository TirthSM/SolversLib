package org.firstinspires.ftc.teamcode.Backups;



import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
/**
 * Motor FL- 00
 * Motor BL- 01
 * Motor FR- 02
 * Motor BR- 03
 */
@TeleOp
public class DriveDebugger extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("Fl");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("Bl");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("Fr");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("Br");

        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);


        waitForStart();


        if (isStopRequested()) return;

        while (opModeIsActive()) {

            if (gamepad1.x) {
                frontLeftMotor.setPower(1);
            }
            if (gamepad1.xWasReleased()) {
                frontLeftMotor.setPower(0);
            }

            if (gamepad1.b) {
                backLeftMotor.setPower(1);
            }
            if (gamepad1.bWasReleased()) {
                backLeftMotor.setPower(0);
            }

            if (gamepad1.y) {
                frontRightMotor.setPower(1);
            }

            if (gamepad1.yWasReleased()) {
                frontRightMotor.setPower(0);
            }
            if (gamepad1.a) {
                backRightMotor.setPower(1);
            }
            if (gamepad1.aWasReleased()) {
                backRightMotor.setPower(0);
            }

        }
    }
}
