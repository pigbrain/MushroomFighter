package pigbrain.game.command.model;

public class GameStatusS2C {

	private String status;
	private int time;

	public GameStatusS2C(String status, int time) {
		this.status = status;
		this.time = time;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
