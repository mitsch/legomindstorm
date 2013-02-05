package common.color;

import java.util.LinkedList;
import java.util.List;

public class ColorOracle {
	
	/**
	 * 
	 * @author HM42
	 *
	 */
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

	public static final Color determineColor(final int value,
			final Strength strength) {

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
	
	/**
	 * Decides if a given value is comprised in a (single) Color-Range.
	 * If its not or if its comprised in more than one there will be no decision (Color.UNDEFINED).
	 * 
	 * @param value
	 * @param options
	 * @return
	 */
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

			for (Color color : options) {
				distMIN.add(Math.abs(color.min() - value));
				distMAX.add(Math.abs(color.max() - value));
			}

			int min = SimpleCollections.min(distMIN);
			int max = SimpleCollections.min(distMAX);

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

				if (SimpleCollections.frequency(direction, distance) == 1) {
					return options[direction.indexOf(distance)];
				}

			}
			
			return Color.UNDEFINED;
		}
	}

	private static class SimpleCollections {

		private static final int min(final List<Integer> list) {

			int min = Integer.MAX_VALUE;

			for (Integer v : list) {
				if (v < min) {
					min = v;
				}
			}
			return min;

		}

		@SuppressWarnings("unused")
		private static int max(final List<Integer> list) {

			int max = Integer.MIN_VALUE;

			for (Integer v : list) {
				if (v > max) {
					max = v;
				}
			}

			return max;
		}

		private static int frequency(final List<Integer> list, final int v2) {

			int cnt = 0;

			for (Integer v : list) {

				if (v == v2) {
					cnt++;
				}
			}

			return cnt;
		}

	}

}