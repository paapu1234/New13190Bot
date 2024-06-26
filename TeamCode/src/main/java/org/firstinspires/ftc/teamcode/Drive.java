package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Drive extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");
        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");
        DcMotor backRight = hardwareMap.dcMotor.get("backRight");
        DcMotor liftRight = hardwareMap.dcMotor.get("liftRight");
        DcMotor liftLeft = hardwareMap.dcMotor.get("liftLeft");
        DcMotor intakeMotor = hardwareMap.get(DcMotor.class,"intakeMotor");
        Servo arm = hardwareMap.get(Servo.class,"arm");
        Servo claw = hardwareMap.get(Servo.class,"claw");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
//        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double x = gamepad1.left_stick_y;
            double y = -gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;
            double liftPower = 0.7;
            double intakeMotorPower = 1;

            final double POSITION = 0.3;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x - rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x + rx) / denominator;

            if (gamepad1.a) {
                liftRight.setPower(liftPower);
                liftLeft.setPower(-liftPower);
            } else {
                liftRight.setPower(0);
                liftLeft.setPower(0);
            }
            if (gamepad1.b) {
                liftRight.setPower(-liftPower);
                liftLeft.setPower(liftPower);
            } else {
                liftRight.setPower(0);
                liftLeft.setPower(0);
            }
            if (gamepad1.left_bumper) {
                intakeMotor.setPower(-intakeMotorPower);
            } else if (gamepad1.right_bumper){
                intakeMotor.setPower(intakeMotorPower);
            } else {
                intakeMotor.setPower(0);
            }

            if (gamepad1.dpad_left) {
              arm.setPosition(-POSITION);
          } else if (gamepad1.dpad_right) {
                arm.setPosition(1);
            }

            telemetry.addData("Servo Position", arm.getPosition());

            if (gamepad1.x) {
                claw.setPosition(0);
            }
            if (gamepad1.y) {
                claw.setPosition(0.15);
            }
            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);

            telemetry.update();
        }
    }
}