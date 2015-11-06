package pigbrain.game.command;

import java.util.Map;
import java.util.UUID;

import pigbrain.game.command.model.FighterCreateC2S;
import pigbrain.game.command.model.FighterCreateS2C;
import pigbrain.game.command.model.GameRoomCreateC2S;
import pigbrain.game.command.model.GameRoomCreateS2C;
import pigbrain.game.command.model.GameRoomListC2S;
import pigbrain.game.command.model.GameRoomListS2C;
import pigbrain.game.common.GameConstant;
import pigbrain.game.common.GameConstant.Direction;
import pigbrain.game.common.GameRoom;
import pigbrain.game.common.Member;
import pigbrain.game.logic.GameRoomContainer;
import pigbrain.game.logic.GameSystem;
import pigbrain.game.logic.MemberContainer;
import pigbrain.game.network.command.Command;
import pigbrain.game.network.command.CommandCallBack;

public class GameRoomListCallBack implements CommandCallBack {

	private GameSystem gameSystem = GameSystem.getInstance();
	private MemberContainer memberContainer = gameSystem.getMemberContainer();
	private GameRoomContainer gameRoomContainer = gameSystem.getGameRoomContainer();
	
	@Override
	public boolean execute(Command command) throws Exception {
		
		GameRoomListC2S request = (GameRoomListC2S) command.getObject();
		
		Map<String, GameRoom> gameRoomMap = gameRoomContainer.getGameRooms();
		
		GameRoomListS2C response = new GameRoomListS2C();
		response.setGameRoomMap(gameRoomMap);
		
		gameSystem.post(command.getLink(), CommandTable.GAME_ROOM_LIST_S2C, response);
		
		return true;
	}

}
