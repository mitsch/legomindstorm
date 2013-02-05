package common;

import lejos.nxt.ADSensorPort;
import lejos.nxt.LightSensor;

import common.color.Color;
import common.color.ColorOracle;

public class ColorSensor extends LightSensor {

	private Color[] possibleColors = { Color.UNDEFINED };
	private Color lastColor = Color.UNDEFINED;
	private ColorOracle.Strength strength = ColorOracle.Strength.WEAK;

	public ColorSensor(ADSensorPort port, boolean floodlight) {
		super(port, floodlight);
	}

	public ColorSensor(ADSensorPort port) {
		super(port);
	}

	public Color[] getPossibleColors() {
		return possibleColors;
	}

	public void setPossibleColors(Color[] possibleColors) {
		this.possibleColors = possibleColors;
	}

	public boolean hasColorChanged(Color currentColor) {

		boolean changed = (this.lastColor != currentColor);
		this.lastColor = currentColor;

		return changed;

	}

	private int getColorValue() {
		return super.readValue();
	}
	
	/**
	 * Nur fuer Thomas ;)
	 * @param colors
	 * @return
	 */
	public Color getColor(Color... colors) {

		int value = this.getColorValue();

		Color currentColor = ColorOracle.determineColor(value, this.strength,
				colors);

		if (currentColor != Color.UNDEFINED) {
			this.lastColor = currentColor;
		}

		return currentColor;

	}

	public Color getColor() {

		int value = this.getColorValue();

		Color currentColor = ColorOracle.determineColor(value, this.strength,
				this.possibleColors);

		if (currentColor != Color.UNDEFINED) {
			this.lastColor = currentColor;
		}

		return currentColor;

	}

	public Color getLastColor() {
		return this.lastColor;
	}

	public ColorOracle.Strength getStrength() {
		return strength;
	}

	public void setStrength(ColorOracle.Strength strength) {
		this.strength = strength;
	}

}
