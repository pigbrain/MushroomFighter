package pigbrain.game.command.model;

import java.util.Map;

import pigbrain.game.common.GameRoom;

public class GameRoomListS2C {

	private Map<String, GameRoom> gameRoomMap;

	public Map<String, GameRoom> getGameRoomMap() {
		return gameRoomMap;
	}

	public void setGameRoomMap(Map<String, GameRoom> gameRoomMap) {
		this.gameRoomMap = gameRoomMap;
	}

}
