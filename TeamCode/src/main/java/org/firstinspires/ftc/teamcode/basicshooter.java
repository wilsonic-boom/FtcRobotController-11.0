package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="basicshooter", group="Linear OpMode")
public class basicshooter extends LinearOpMode {

    DcMotorEx shooter = null;
    Servo flap;

    @Override
    public void runOpMode() {

        // Create necessary objects to operate shooter

        DcMotorEx shooter = hardwareMap.get(DcMotorEx.class, "shooter");

        flap  = hardwareMap.get(Servo.class, "flap");

        // Create positions to refer servo to

        double rest = 0.1;
        double shoot = 0.5;

        // Let the servo rest initially

        flap.setPosition(rest);

        waitForStart();

        while (opModeIsActive()) {

            while (opModeIsActive()) {

                //shoot
                shooter.setPower(gamepad1.right_trigger);

                // Change flap position to push ball up
                if (gamepad1.a) {
                    flap.setPosition(shoot);
                } else {
                    flap.setPosition(rest);
                }

                telemetry.addData("Shooter Power is ", shooter.getPower());
                telemetry.addData("Flap Position is ", flap.getPosition());
                telemetry.addData("a", gamepad1.a);
                telemetry.addData("right_trigger", gamepad1.right_trigger);
                telemetry.update();

            }
        }

    }
}