package org.firstinspires.ftc.teamcode.Backups;
/*
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config//important

@TeleOp
public class goodRevolver extends OpMode {
    private PIDFController controller;//important

    public static double p = 0.1, i = 0, d= 0.001;
    public static double f = 0.000001;

    public static int target = 96;



    private final double ticks_in_degree = 700/ 180.0;//changes depending on the motor

    private DcMotorEx Revolver;

    @Override
    public void init(){
        controller = new PIDController(p, i, d);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());//allow to do stuff in dash board
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


        if (gamepad1.a) {
            Revolver.setTargetPosition(target);
        }

        if (gamepad1.aWasReleased()){
            Revolver.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            Revolver.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        telemetry.addData("Target",target);
        telemetry.update();

    }
}
 */