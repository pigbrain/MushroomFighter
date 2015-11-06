package pigbrain.game.tick;

public class FrameCalculator {

	private double startTime;
	private double endTime;
	
	
	public FrameCalculator() {
		
	}
	
	
	public void start() {
		startTime = System.currentTimeMillis();
	}
	
	public void end() {
		endTime = System.currentTimeMillis();
	}
	
	public double getElapsedTime() {
		
		return (endTime - startTime) / 1000.0;
	}
}
