package org.usfirst.frc.team6763.robot;

//Imports the other files needed by the program
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
 
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * CHANGE HISTORY:
 * 170204 jps Eliminated some libraries that were imported multiple times
 *            Removed arcadeDrive from stop(), driveForward(), driveReverse(), etc
 *            Changed automatedPeriodic method to just drive and give sensor readouts
 */
public class Robot extends IterativeRobot {
	
	int targetVal = 240;
	int rightTargetVal = 120;
	int revPerInch = 13;
	int stopPoint = targetVal * revPerInch;
	int autoState = 0;
	double speed = 0;
	double turnRate = 0;

	DigitalInput limitSwitch = new DigitalInput(3);
	Encoder leftEncoder = new Encoder (1,0);
	Encoder rightEncoder = new Encoder (1,1); // check these values, may not be right
	                                          // encoder was not installed when I wrote code
	int numClicksLeft = 0;
	int numClicksRight = 0;
	int numPass = 0;
	
	Timer timer = new Timer();
	
	RobotDrive myRobot = new RobotDrive(0, 2);
	Joystick stick = new Joystick(0);
	
	public void robotInit() {
    	leftEncoder.setReverseDirection(true);
    	rightEncoder.setReverseDirection(true);
	}

	@Override
	public void stop() { // sets all motors to stop
		speed = 0;
		turnRate = 0;
		myRobot.drive(speed, turnRate);
	}

	@Override
	public void driveForward() { // drives the robot forward
		speed = -0.5;  // Our robot is "backwards", so we use a negative speed to go forward
		turnRate = 0.0;
		myRobot.drive(speed, turnRate);
	}

	@Override
	public void driveReverse() { // drives the robot backwards
		speed = 0.5;
		turnRate = 0.0;
		myRobot.drive(speed, turnRate);
	}	
	
	@Override
	public void driveForwardRight() { // turns robot to the right
		speed = 0.5;
		turnRate = 0.5;  // positive value is a right turn, negative value is left turn
		myRobot.drive(speed, turnRate);
	}

	@Override
	public void driveForwardLeft() { // turns robot left 
		speed = -0.5;
		turnRate = -0.5;
		myRobot.drive(speed, turnRate);
	}
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */	
	@Override
	public void autonomousInit() {
		System.out.println("autonomousInit: Resetting and Starting timer");
		timer.reset();
		timer.start();
		System.out.println("autonomousInit: Resetting left and right encoders");
		leftEncoder.reset();
		rightEncoder.reset();
		
		// Let's check to make sure that we reset them correctly - both should have 0 clicks
		numClicksLeft = leftEncoder.get();
		numClicksRight = rightEncoder.get();
		System.out.println("autonomousInit: numClicksLeft = " + numClicksLeft);
		System.out.println("autonomousInit: numClicksRight = " + numClicksRight);

	}

	/* This method is called repeatedly (every 20ms) while the robot is in autonomous mode */
	@Override
	public void autonomousPeriodic() {

		if (numPass < 5) {
			drive2SecsCheckClicks(); 
			numPass++;
		} else {
			stop();
		}
	}
	
	@Override
	public void drive2SecsCheckClicks() {
		// Drive for 2 seconds
		if (timer.get() < 2.0) {
			driveForward();
		} else {
            // We moved for 2 seconds, let's pause, check out the clicks and reset for next pass
			stop(); // stop all motors
			System.out.println("autonomousInit: numClicksLeft = " + numClicksLeft);
			System.out.println("autonomousInit: numClicksRight = " + numClicksRight);
			try {
				// sleep for 4 seconds (NOTE: I used sleep, because I couldn't find any doc on 
				// Timer and don't know if it measures in seconds or milliseconds. I know sleep()
				// measures in milliseconds!
				Thread.sleep(4000); 
			} catch (InterruptedException e) {
				// Auto-generated catch block
				e.printStackTrace();
			}
			timer.reset(); // reset timer so robot moves for another 2 seconds
		}
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		myRobot.arcadeDrive(stick);
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
