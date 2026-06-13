package org.firstinspires.ftc.teamcode.pedroPathing.Tirthtest.SubSystem;

import com.acmerobotics.dashboard.config.Config;
import com.seattlesolvers.solverslib.controller.PIDFController;

@Config
public class PIDFClass {
   private static PIDFController controller = new PIDFController(0,0,0,0);

   public static double p = 0.07, i = 0.12, d = 0;

   public static double f = 0.00001;

   private static final double ticks_in_degree = 700 / 180;

   static double power;

   public static double returnRevPID(double target, double revpose){

       controller.setPIDF(p,i,d,f);

       double pid = controller.calculate(revpose, target);
       double ff = Math.cos(Math.toRadians(target/ticks_in_degree));


       controller.setTolerance(0.5);
       controller.atSetPoint();

       power = pid + ff;

       return power;
   }
}
