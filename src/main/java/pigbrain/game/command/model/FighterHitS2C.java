package pigbrain.game.command.model;

public class FighterHitS2C {

	private String id;
	private double energy;

	public FighterHitS2C(String id, double energy) {
		this.id = id;
		this.energy = energy;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}
}
