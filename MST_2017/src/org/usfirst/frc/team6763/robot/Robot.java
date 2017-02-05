
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
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Encoder;

 
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	
	int targetVal = 240;
	int rightTargetVal = 120;
	int revPerInch = 13;
	int stopPoint = targetVal * revPerInch;
	int autoState = 0;
		
	
	DigitalInput limitSwitch;
	Encoder myEncoder;

	@Override
	public void stop() { // sets stop(); to stop all motors
//		myRobot.drive(0.0, 0.0);
		myRobot.arcadeDrive(0, 0);

	
	}

	@Override
	public void driveForward() { // sets stop(); to stop all motors
		//myRobot.drive(-0.5, 0.0);
		myRobot.arcadeDrive(-0.5, 0);
		
	}

	@Override
	public void driveReverse() { // sets stop(); to stop all motors
//myRobot.drive(0.5, 0.0);

		myRobot.arcadeDrive(0.5, 0);

	}	
	
	@Override
	public void driveForwardRight() { // sets stop(); to stop all motors
		//myRobot.drive(-0.5, 0.5);
		myRobot.arcadeDrive(-0.5, 0.5);

	
	}

	
	@Override
	public void driveForwardLeft() { // sets stop(); to stop all motors
	//	myRobot.drive(-0.5, -0.5);

		myRobot.arcadeDrive(-0.5, -0.5);

	
	}
	
	
	
	
	public void robotInit() {
    	limitSwitch = new DigitalInput(3);
    	myEncoder = new Encoder(1, 0);
    	myEncoder.setReverseDirection(true);
    }

    public void operatorControl() {
    	// more code here
    	while (limitSwitch.get()) {
    		Timer.delay(10);
    	}
        // more code here
    }

	
	
	RobotDrive myRobot = new RobotDrive(0, 2);
	Joystick stick = new Joystick(0);
	Timer timer = new Timer();
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		timer.reset();
		timer.start();
		myEncoder.reset();
		autoState = 0;
		
		System.out.println("stopPoint = " + stopPoint);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	
	 public void disabledPeriodic(){
		 System.out.println("myEncoder = " + myEncoder.get());
	 }
	 
	@Override
	public void autonomousPeriodic() {
		/*
		
		myRobot.drive(-0.5, -0.25);
		   ^	 ^		^	  ^
		   |	 |		|	  |
		   |	 |		|	  |
		   |	 |		|	  Sets the turning speed/curve offset
		   |	 |		|	  
		   |	 |		Sets the speed for the motors
		   |	 |		 
		   |	 Runs the function we created in RobotDrive.class
		   |	 
		Declares that the drive code is for this robot
		
		
		*/
		
		int currentPoint = myEncoder.get();
		
		//System.out.println("stopPoint =" + stopPoint);
		switch (autoState) {
		
		case 0:
			if (currentPoint < stopPoint) 
			{
				driveForward();
				System.out.println("encoder count" + currentPoint);
			} else
			{
				autoState++;
				myEncoder.reset();
				currentPoint = myEncoder.get();
				stopPoint = rightTargetVal * revPerInch;
				System.out.println("reset encoder count = " + currentPoint);
				System.out.println("stopPoint = " + stopPoint);
			}
			break;
			case 1:
				
				if (currentPoint < stopPoint) 
				{
					
					driveForwardLeft();
					System.out.println("encoder count" + currentPoint);
				} else
				{
					autoState++;
					myEncoder.reset();

					stopPoint = targetVal * revPerInch;

					
				}
				break;
			case 2:
				
				if (currentPoint < stopPoint) 
				{
					driveForward();
					System.out.println("encoder count" + currentPoint);
				} else
				{
					autoState++;
					myEncoder.reset();


					stop();
					
				}	
				break;
				
				
			}

		/*		
		// Drive for 2 seconds
		if (timer.get() < 2.0) {
			myRobot.drive(-0.5, 0.0); // drive forwards half speed
			print("Driving forwards for 2 seconds");
		} else if (timer.get() > 2.0 && timer.get() < 5.75)  { 
			myRobot.drive(-0.5, 0.5); // drive right 
			print("Turning 450* for seconds 3.75 Seconds");
		} else if (timer.get() > 5.75 && timer.get() < 7.75)  {
			myRobot.drive(-0.5, 0.0); // drive forward
			print("Driving forwards for 2 seconds");
		} else {
			stop(); // stop 
			print("Stoping indefinitely");
		}
	*/
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


