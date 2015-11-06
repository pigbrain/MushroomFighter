package pigbrain.game.command.model;

public class PlayerMoveS2C {

	private String id;
	private double move;
	private double moveX;
	private double moveY;
	private int direction;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getMove() {
		return move;
	}

	public void setMove(double move) {
		this.move = move;
	}

	public double getMoveX() {
		return moveX;
	}

	public void setMoveX(double moveX) {
		this.moveX = moveX;
	}

	public double getMoveY() {
		return moveY;
	}

	public void setMoveY(double moveY) {
		this.moveY = moveY;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

}
