package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Motor Speed Test (Manual Distance)", group="Testing")
public class setup extends LinearOpMode {

    private DcMotorEx motorFL, motorFR, motorBL, motorBR;

    double[] speeds = {0.25, 0.50, 0.75, 1.0};
    int speedIndex = 0;

    long intervalMs = 100; // start at 100ms

    @Override
    public void runOpMode() {

        motorFL = hardwareMap.get(DcMotorEx.class, "motorFL");
        motorFR = hardwareMap.get(DcMotorEx.class, "motorFR");
        motorBL = hardwareMap.get(DcMotorEx.class, "motorBL");
        motorBR = hardwareMap.get(DcMotorEx.class, "motorBR");

        DcMotorEx[] motors = {motorFL, motorFR, motorBL, motorBR};

        for (DcMotorEx m : motors) {
            m.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            m.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
            m.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        }

        telemetry.addLine("Ready for Motor Speed Test");
        telemetry.addLine("Dpad Left/Right = change speed");
        telemetry.addLine("Dpad Up/Down = change duration (100ms steps)");
        telemetry.addLine("Press A to run test");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // adjust speed
            if (gamepad1.dpad_left)  { speedIndex = Math.max(0, speedIndex - 1); sleep(200); }
            if (gamepad1.dpad_right) { speedIndex = Math.min(speeds.length - 1, speedIndex + 1); sleep(200); }

            // adjust time
            if (gamepad1.dpad_up)   { intervalMs += 100; sleep(200); }
            if (gamepad1.dpad_down) { intervalMs = Math.max(100, intervalMs - 100); sleep(200); }

            double power = speeds[speedIndex];

            telemetry.addData("Power", power);
            telemetry.addData("Interval (ms)", intervalMs);
            telemetry.addLine("Press A to run");
            telemetry.update();

            // Run the test
            if (gamepad1.a) {

                // reset encoders
                for (DcMotorEx m : motors) {
                    m.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
                    m.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
                }

                // start motors (all forward)
                for (DcMotorEx m : motors) m.setPower(power);

                long startTime = System.currentTimeMillis();
                while (opModeIsActive() && System.currentTimeMillis() - startTime < intervalMs) {
                    // wait
                }

                // stop motors
                for (DcMotorEx m : motors) m.setPower(0);

                // read encoder values
                int fl = motorFL.getCurrentPosition();
                int fr = motorFR.getCurrentPosition();
                int bl = motorBL.getCurrentPosition();
                int br = motorBR.getCurrentPosition();

                telemetry.addLine("\n==== TEST COMPLETE ====");
                telemetry.addData("Power", power);
                telemetry.addData("Duration (ms)", intervalMs);
                telemetry.addData("FL Encoder", fl);
                telemetry.addData("FR Encoder", fr);
                telemetry.addData("BL Encoder", bl);
                telemetry.addData("BR Encoder", br);
                telemetry.addLine("Measure distance with ruler now.");
                telemetry.update();

                sleep(1500);
            }
        }
    }
}
