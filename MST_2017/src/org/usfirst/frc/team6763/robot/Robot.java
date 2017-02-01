package org.usfirst.frc.team6763.robot;

//Imports the other files needed by the program
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
//Imports the other files needed by the program
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	RobotDrive myRobot = new RobotDrive(0, 1, 2, 3);
	Joystick stick = new Joystick(1);
	Timer timer = new Timer();
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		timer.reset();
		timer.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */

	@Override
	public void stop() { // sets stop(); to stop all motors
		myRobot.drive(0.0, 0.0);
	}

	
	
	@Override
	public void autonomousPeriodic() {
		/*
		
		myRobot.drive(-0.5, -0.25);
		   ^	 ^		^	  ^
		   |	 |		|	  |
		   |	 |		|	  |
		   |	 |		|	  Sets the turning speed/curve
		   |	 |		|	  
		   |	 |		Sets the speed for the motors
		   |	 |		
		   |	 Runs the function we created in RobotDrive.class
		   |	 
		Declares that the drive code is for this robot
		
		*/
		// Drive for 2 seconds
		if (timer.get() < 2.0) {
			myRobot.drive(-0.5, 0.0); // drive forwards half speed
		} else if (timer.get() > 2.0 && timer.get() < 5.75)  { 
			myRobot.drive(-0.5, 0.5); // drive right 
		} else if (timer.get() > 5.75 && timer.get() < 7.75)  {
			myRobot.drive(-0.5, 0.0); // drive forward
		} else {
			stop(); // stop 
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

