package pigbrain.game.logic;

import java.util.HashMap;
import java.util.Map;

import pigbrain.game.common.GameRoom;
import pigbrain.game.common.Member;
import pigbrain.game.network.command.CommandCommunicator;
import pigbrain.game.network.exception.InvalidLinkException;
import pigbrain.game.tick.Regulator;
import pigbrain.game.tick.Tick;

public class GameRoomContainer implements Tick {

	private Map<String, GameRoom> gameRoomMap = new HashMap<String, GameRoom>();
	
	@Override
	public void update(double delta) {
	
		for( Map.Entry<String, GameRoom> element : gameRoomMap.entrySet() ){
            element.getValue().update(delta);
        }
	}
	
	public boolean putGameRoom(String key, GameRoom gameRoom) {
		if (gameRoomMap.put(key, gameRoom) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void removeGameRoom(String key) {
		gameRoomMap.remove(key);
	}
	
	public GameRoom getGameRoom(String key) throws InvalidLinkException {
		GameRoom gameRoom = gameRoomMap.get(key);
		if (gameRoom == null) {
			throw new InvalidLinkException("GameRoom Key[" + key + "] is not found");
		}
		return gameRoom;
	}
	
	public Map<String, GameRoom> getGameRooms() {
		return gameRoomMap;
	}

}
