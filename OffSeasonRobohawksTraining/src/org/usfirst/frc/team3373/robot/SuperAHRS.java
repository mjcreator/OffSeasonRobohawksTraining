package org.usfirst.frc.team3373.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

public class SuperAHRS extends AHRS {
	// Reset function, get 0-360 rotation, bump detection (get bump), get altitude (starts at zero, positive numbers up)
	
	private double previousAccelerationZ;
	private boolean hasBumped;
	
	SuperAHRS (SPI.Port port) {
		super(port);
		previousAccelerationZ = super.getWorldLinearAccelZ();
		hasBumped = false;
	}
	
	//Gets absolute yaw rotation from 0-360*
	public float getRotation() {
		float rotation;
		if (super.getYaw() >= 0) {
			rotation = super.getYaw();
		} else {
			rotation = (180 - Math.abs(super.getYaw())) + 180;
		}
		return rotation;
	}
	
	//Gets change of acceleration in z direction
	private double getZJerk() {
		double currentAccel = super.getWorldLinearAccelZ();
		double deltaAccel = currentAccel - previousAccelerationZ;
		previousAccelerationZ = currentAccel;
		return deltaAccel/.01;
	}
	
	//Checks if bumb is over a certain size and returns if it has bumped
	public boolean hasHitBump (double amount) {
		if(Math.abs(this.getZJerk()) > amount)
			hasBumped = true;
		return hasBumped;
	}
	
	//Resets hasBumped to false
	public void resetBump() {
		hasBumped = false;
	}
}
