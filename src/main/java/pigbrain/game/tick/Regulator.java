package pigbrain.game.tick;

public class Regulator {

	private double elapsedTime;
	private double updateTick;
	private boolean active;

	public Regulator(double updateTick) {
		this.updateTick = updateTick;
		
		elapsedTime = 0.0;
		active = true;
	}
	
	public double getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(double elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public double getUpdateTick() {
		return updateTick;
	}

	public void setUpdateTick(double updateTick) {
		this.updateTick = updateTick;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isReady(double delta) {
		if (!active) {
			return false;
		}
		
		elapsedTime += delta;
		
		
		if (elapsedTime > updateTick) {
			elapsedTime = 0.0;
			return true;
		}
		
		return false;
	}
	
	public double getRemainTick() {
		return updateTick - elapsedTime;
	}
	
	public void stop() {
		active = false;
	}
	
	public void start() {
		active = true;
	}
}
