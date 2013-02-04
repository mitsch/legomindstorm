package common.color;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ColorOracle {

	private static final Color decide(int value, Color[] options, boolean force) {

		Color outcome = Color.UNDEFINED;

		List<Integer> distances = new LinkedList<Integer>();
		List<Color> colors = new LinkedList<Color>();

		for (Color color : options) {
			if (color.isComprised(value)) {

				distances.add(Math.abs(color.avg() - value));
				colors.add(color);
			}
		}

		if (distances.size() > 0) {

			
			
			
			if (distances.size() == 1) {
				// TODO
			} else {

				Collections.min(distances);

				Collections.max(distances);

			}

		}else{
			
			// Keine Entscheidung.
			
			
		}

		return outcome;
	}

	public static final Color determineColor(int value) {

		return ColorOracle.decide(value, Color.values(), false);

	}

	public static final Color determineColor(int value, boolean force) {

		return ColorOracle.decide(value, Color.values(), force);

	}

	public static final Color determineColor(int value, Color... options) {

		return ColorOracle.decide(value, options, false);
	}

	public static final Color determineColor(int value, boolean force,
			Color... options) {
		
		return ColorOracle.decide(value, options, force);
	}

}
