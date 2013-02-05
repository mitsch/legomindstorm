package testing;

import lejos.nxt.*;
import lejos.util.Delay;

import java.io.*;

public class UltraSonicTester {

	/**
	 * Every second the ultasonic sensor at port S1 detects features and saves
	 * them into log.dat
	 * @param args
	 */
	public static void main(String[] args) {
		File data = new File("log.dat");
		
		//Prepare the file
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(data);
		} catch (IOException e) {
			System.err.println("Failed to create output stream");
			Button.waitForAnyPress();
			System.exit(1);
		}
		DataOutputStream dos = new DataOutputStream(out);
		
		//Prepare the sensor
		UltrasonicSensor sonar = new UltrasonicSensor(SensorPort.S2);	
		
		//Take measurements and save them until we press ESCAPE
		do {
			Delay.msDelay(1000);
			
			//save the measurement
			try {
				dos.writeUTF(String.valueOf(sonar.getDistance()) + '\n');
			} catch (IOException e) {
				System.err.println("Failed to write to output stream");
			}
			Sound.beep();
		} while (Button.readButtons() != Button.ID_ESCAPE);
		
		try {
			out.close();
		} catch (IOException e) {
			System.err.println("Failed to write to output stream");
		}
	}
}
