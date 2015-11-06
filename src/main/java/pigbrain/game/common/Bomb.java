package pigbrain.game.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import pigbrain.game.tick.Regulator;
import pigbrain.game.tick.Tick;

public class Bomb implements Tick {

	private String id;
	private double xPosition;
	private double yPosition;
	private boolean explosion;

	@JsonIgnore
	private Regulator explosionRegulator;

	public Bomb(String id, double countDown, double xPosition, double yPosition) {
		this.id = id;
		this.explosion = false;
		this.explosionRegulator = new Regulator(countDown);
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}

	public String getId() {
		return id;
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

	public Regulator getExplosionRegulator() {
		return explosionRegulator;
	}

	public void setExplosionRegulator(Regulator explosionRegulator) {
		this.explosionRegulator = explosionRegulator;
	}

	public boolean isActive() {
		return explosionRegulator.isActive();
	}

	public boolean isExplostion() {
		return explosion;
	}

	@Override
	public void update(double delta) {
		// TODO Auto-generated method stub
		if (!explosionRegulator.isReady(delta)) {
			return;
		}

		explosion = true;
	}

}
