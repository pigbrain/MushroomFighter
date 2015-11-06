package pigbrain.game.logic;

import java.util.Map;

import pigbrain.game.command.CommandTable;
import pigbrain.game.common.Member;
import pigbrain.game.network.command.CommandCommunicator;
import pigbrain.game.network.exception.CommandBuildException;
import pigbrain.game.network.link.Link;
import pigbrain.game.tick.Tick;

public class GameSystem implements Tick {

	private static GameSystem gameSystem;
	
	private MemberContainer memberContainer = new MemberContainer();
	
	private GameRoomContainer gameRoomContainer = new GameRoomContainer();
	
	private GameSystem() {
		
	}
	
	public static GameSystem getInstance() {
		if (gameSystem == null) {
			gameSystem = new GameSystem();
		}
		
		return gameSystem;
	}
	
	@Override
	public void update(double delta) {
		
		memberContainer.update(delta);
		
		gameRoomContainer.update(delta);
	}
	
	public MemberContainer getMemberContainer() {
		return memberContainer;
	}

	public GameRoomContainer getGameRoomContainer() {
		return gameRoomContainer;
	}

	public void post(Link link, String commandId, Object object) throws CommandBuildException {
        CommandCommunicator.getInstance().post(link, commandId, object);
	}
	
}
