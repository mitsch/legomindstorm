package common;

import lejos.nxt.ADSensorPort;
import lejos.nxt.LightSensor;

import common.color.Color;
import common.color.ColorOracle;

/**
 * Uses raw value (0 to 1023) internally.
 * 
 * @author Philipp
 * 
 */
public class ColorSensor {

	private final short _min = 0;
	private final short _max = 1023;

	private LightSensor ls;

	private Color[] possibleColors = { Color.UNDEFINED };

	private ColorOracle.Strength decision = ColorOracle.Strength.WEAK;

	private Color lastColor = Color.UNDEFINED;

	public ColorSensor(ADSensorPort port, boolean floodlight) {
		this.ls = new LightSensor(port, floodlight);

	}

	public ColorSensor(ADSensorPort port) {
		this.ls = new LightSensor(port);
	}

	public final Color[] getPossibleColors() {
		return possibleColors;
	}

	public final void setPossibleColors(final Color[] possibleColors) {
		this.possibleColors = possibleColors;
	}

	public final boolean hasColorChanged(final Color currentColor) {

		boolean changed = (this.lastColor != currentColor);
		this.lastColor = currentColor;

		return changed;

	}

	
	public final Color readColor() {

		return this.internalReadColor(this.possibleColors);
	}

	/**
	 * Nur fuer Thomas ;)
	 * 
	 * @param colors
	 * @return
	 */
	public final Color readColor(final Color... colors) {

		return this.internalReadColor(colors);
	}

	private final Color internalReadColor(final Color[] colors) {

		short value = this.readLightValue();

		Color currentColor = ColorOracle.determineColor(value, this.decision,
				colors);

		if (currentColor != Color.UNDEFINED) {
			this.lastColor = currentColor;
		}

		return currentColor;
	}

	private final short readLightValue() {
		return (short) this.ls.readNormalizedValue();
	}

	
	/**
	 * 
	 * @return the last detected Color. Bevor the first
	 */
	public Color getLastColor() {
		return this.lastColor;
	}

	/**
	 * @return the decision-strength.
	 */
	public ColorOracle.Strength getDecisionStrength() {
		return decision;
	}

	/**
	 * @param strength
	 *            the decision-strength to set.
	 */
	public void setDecisionStrength(ColorOracle.Strength strength) {
		this.decision = strength;
	}

	/**
	 * the internally used high/_max value (normally 1023).
	 * 
	 * @return
	 */
	public int getHigh() {
		return this._max;
	}

	/**
	 * 
	 * @return the internally used low/_min value (normally 0).
	 */
	public int getLow() {
		return this._min;
	}

	/**
	 * @return
	 * @see lejos.nxt.LightSensor#isFloodlightOn()
	 */
	public boolean isFloodlightOn() {
		return ls.isFloodlightOn();
	}

	/**
	 * @param floodlight
	 * @see lejos.nxt.LightSensor#setFloodlight(boolean)
	 */
	public void setFloodlight(boolean floodlight) {
		ls.setFloodlight(floodlight);
	}

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Sensorarm:" + super.toString();
	}

}
