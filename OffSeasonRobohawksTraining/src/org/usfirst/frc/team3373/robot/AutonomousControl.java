package org.usfirst.frc.team3373.robot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AutonomousControl {
	
	SuperAHRS ahrs;
	SwerveControl swerve;
	Ultrasonic ultra;
	Object auto;
	
	//Initializes self and defines AHRS, swerve, and ultrasonic sensor
	public AutonomousControl(SuperAHRS ah, SwerveControl swer, Ultrasonic ult) {
		ahrs = ah;
		swerve = swer;
		ultra = ult;
	}
	
	//Initializes auto by file name and passes in itself as a parameter
	public boolean start(String au) {
		String aut = "org.usfirst.frc.team3373.autonomous." + au;
		try {
			auto = Class.forName(aut).getConstructor(AutonomousControl.class).newInstance(this);
		}  catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException ex) {
			System.out.println(ex);
			return false;
		}
		
		return true;
	}
	
	//Starts the autonomous running
	public void run() {
		try {
			Method run = auto.getClass().getMethod("run");
			run.invoke(auto);
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException | IllegalAccessException ex) {
			System.out.println(ex);
		}
	}
	
	//Drives for x milliseconds at y angle and z speed
	public void driveAtAngle(long milliseconds, float angle, double speed) {
		swerve.drive(speed, angle);
		Thread.sleep(milliseconds);
		swerve.drive(0, angle);
	}
	
	//Calls rotateRelative(angle, speed) until finished
	public void rotateRelative(float angle, double speed) {
		boolean atPosition = false;
		while (!atPosition)
			atPosition = swerve.rotateRelative(angle, speed);
	}
	
	//Calls rotateAbsolute(angle, speed) until finished
	public void rotateAbsolute(float angle, double speed) {
		boolean atPosition = false;
		while (!atPosition)
			atPosition = swerve.rotateAbsolute(angle, speed);
	}
	
	//Calls rotateroundObject(angle, distance, speed) until finished
	public void rotateAroundObject(float angle, float distance, double speed) {
		boolean atPosition = false;
		while (!atPosition)
			atPosition = swerve.rotateAroundObject(angle, distance, speed);
	}
	
	public double getUltrasonicDistance() {
		return ultra.getDistance();
	}
}
