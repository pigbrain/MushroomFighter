package pigbrain.game.command.model;

import pigbrain.game.common.GameConstant.Talent;


public class PlayerMissileS2C {

	private String id;
	private Talent talent;
	private int direction;
	private double xPosition;
	private double yPosition;
	private boolean disappear = false;
	
	public PlayerMissileS2C() {
		
	}
	
	public PlayerMissileS2C(String id, int direction, double xPosition, double yPosition) {
		this.id = id;
		this.direction = direction;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}
	
	public PlayerMissileS2C(String id, boolean disappear) {
		this.id = id;
		this.disappear = disappear;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Talent getTalent() {
		return talent;
	}

	public void setTalent(Talent talent) {
		this.talent = talent;
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

	public boolean isDisappear() {
		return disappear;
	}

	public void setDisappear(boolean disappear) {
		this.disappear = disappear;
	}

	
}
