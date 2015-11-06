package pigbrain.game.tick;

import java.util.ArrayList;
import java.util.List;

public class TickContainer {

	private static TickContainer tickContainer;
	
	private List<Tick> tickInstanceList = new ArrayList<Tick>();
	
	private TickContainer() {
		
	}
	
	public static TickContainer getInstance() {
		if (tickContainer == null) {
			tickContainer = new TickContainer();
		}
		
		return tickContainer;
	}
	
	public void addInstance(Tick tickInstance) {
		tickInstanceList.add(tickInstance);
	}
	
	public List<Tick> getTickInstanceList() {
		return tickInstanceList;
	}
}
