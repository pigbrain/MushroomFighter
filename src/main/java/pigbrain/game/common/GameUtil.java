package pigbrain.game.common;

public class GameUtil {

	public static double calculateDistance(double x1, double x2, double y1, double y2) {

		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}
}
