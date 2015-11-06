package pigbrain.game.tick;

import java.util.List;

public class TickUpdater extends Thread {

	private int tickFrame;
	private double timePerFrame;
	private double elapsedTime;
	private FrameCalculator frameCalculator;
	
	public TickUpdater(int tickFrame) {
		this.tickFrame = tickFrame;
		
		elapsedTime = timePerFrame = 1.0 / tickFrame;
		
		frameCalculator = new FrameCalculator();
	}
	
	@Override
	public void run() {
		
		while (true) {
			update();
		}
	}
	
	private void update() {

		List<Tick> tickInstanceList = TickContainer.getInstance().getTickInstanceList();
		
		frameCalculator.start();
		
		for (Tick tick : tickInstanceList) {
			tick.update(elapsedTime);
		}
		
		frameCalculator.end();
		
		elapsedTime = frameCalculator.getElapsedTime();
		
		if (elapsedTime > timePerFrame) {
			System.err.println("@Row frame :" + elapsedTime);
		} else {
			try {
				Thread.sleep((long) ((timePerFrame - elapsedTime) * 1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			elapsedTime = timePerFrame;
		}
	}
}
