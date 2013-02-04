package common.color;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ColorOracle {

	public enum Strength {
		WEAK, HARD;
	}

	/**
	 * Determine the Color of the given lightvalue.
	 * 
	 * @param value
	 * @return
	 */
	public static final Color determineColor(final int value) {

		return ColorOracle.determineColor(value, Strength.HARD);

	}

	public static final Color determineColor(final int value, final Strength strength) {

		return ColorOracle.determineColor(value, strength, Color.values());

	}

	public static final Color determineColor(final int value,
			final Color... options) {

		return ColorOracle.determineColor(value, Strength.HARD, options);

	}

	public static final Color determineColor(final int value,
			final Strength strength, final Color... options) {

		switch (strength) {

		case WEAK:
			return ColorOracle.decideWEAK(value, options);

		default:
		case HARD:
			return ColorOracle.decideHARD(value, options);
		}
	}

	private static final Color decideHARD(int value, Color[] options) {

		Color outcome = Color.UNDEFINED;

		List<Color> colors = new LinkedList<Color>();

		for (Color color : options) {
			if (color.isComprised(value)) {
				colors.add(color);
			}
		}

		if (colors.size() == 1) {
			outcome = colors.get(0);
		}

		return outcome;
	}

	private static final Color decideWEAK(int value, Color[] options) {

		Color outcomeHARD = decideHARD(value, options);

		if (outcomeHARD != Color.UNDEFINED) {

			return outcomeHARD;

		} else {

			// WEAK

			List<Integer> distMIN = new LinkedList<Integer>();
			List<Integer> distMAX = new LinkedList<Integer>();

			List<Color> colors = new LinkedList<Color>();

			for (Color color : options) {
				if (color.isComprised(value)) {
					distMIN.add(Math.abs(color.min() - value));
					distMAX.add(Math.abs(color.max() - value));
					colors.add(color);
				}
			}

			int min = Collections.min(distMIN);
			int max = Collections.min(distMAX);

			if (min != max) {

				List<Integer> direction = null;
				Integer distance = 0;

				if (min < max) {
					direction = distMIN;
					distance = min;
				} else {
					// max < min
					direction = distMAX;
					distance = max;
				}

				if (Collections.frequency(direction, distance) == 1) {

					return colors.get(direction.indexOf(direction));
				}

			}

			return Color.UNDEFINED;

		}
	}

}