package pigbrain.game.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import pigbrain.game.tick.Regulator;
import pigbrain.game.tick.Tick;

public class Missile implements Tick {

	private String id;
	private int direction;
	private double xPosition;
	private double yPosition;

	public Missile(String id, int direction, double xPosition, double yPosition) {
		this.id = id;
		this.direction = direction;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}

	public String getId() {
		return id;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public double getxPosition() {
		return xPosition;
	}

	public void setxPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	public double getyPosition() {
		return yPosition;
	}

	public void setyPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	@Override
	public void update(double delta) {
		// TODO Auto-generated method stub

	}

}
