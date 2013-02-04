package common.color;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ColorOracle {

	public static final Color determineColor(final int value) {

		return ColorOracle.determineColor(value, true);

	}

	public static final Color determineColor(final int value, final boolean hard) {

		return ColorOracle.determineColor(value, hard, Color.values());

	}

	public static final Color determineColor(final int value,
			final Color... options) {

		return ColorOracle.determineColor(value, false, options);

	}

	public static final Color determineColor(final int value,
			final boolean hard, final Color... options) {

		if (hard) {
			return ColorOracle.decideHARD(value, options);
		} else {
			return ColorOracle.decideWEAK(value, options);
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