package pigbrain.game.command;

import java.util.UUID;

import pigbrain.game.command.model.FighterCreateC2S;
import pigbrain.game.command.model.FighterCreateS2C;
import pigbrain.game.command.model.GameRoomCreateC2S;
import pigbrain.game.command.model.GameRoomCreateS2C;
import pigbrain.game.common.GameConstant;
import pigbrain.game.common.GameConstant.Direction;
import pigbrain.game.common.GameRoom;
import pigbrain.game.common.Member;
import pigbrain.game.logic.GameRoomContainer;
import pigbrain.game.logic.GameSystem;
import pigbrain.game.logic.MemberContainer;
import pigbrain.game.network.command.Command;
import pigbrain.game.network.command.CommandCallBack;

public class CreateGameRoomCallBack implements CommandCallBack {

	private GameSystem gameSystem = GameSystem.getInstance();
	private MemberContainer memberContainer = gameSystem.getMemberContainer();
	private GameRoomContainer gameRoomContainer = gameSystem.getGameRoomContainer();
	
	@Override
	public boolean execute(Command command) throws Exception {
		
		GameRoomCreateC2S request = (GameRoomCreateC2S) command.getObject();
	
		String gameRoomId = UUID.randomUUID().toString();
		GameRoom gameRoom = new GameRoom(gameRoomId, request.getRoomName());
		
		Member member = memberContainer.getMember(command.getLink().getSecretKey());
		member.init();
		member.setGameRoomId(gameRoomId);
		member.setxPosition(GameConstant.MIN_X_POSITION);
		member.setyPosition(GameConstant.DEFAULT_Y_POSITION);
		member.setDirection(Direction.RIGHT.getId());
		
		gameRoom.setMember1p(member);
		
		gameRoomContainer.putGameRoom(gameRoomId, gameRoom);

		GameRoomCreateS2C response = new GameRoomCreateS2C();
		response.setId(gameRoomId);
		response.setPlayerPosition(GameConstant.PLYAER_POSITION_1);
		response.setxPosition(member.getxPosition());
		response.setDirection(member.getDirection());
		
		gameSystem.post(command.getLink(), CommandTable.CREATE_GAMEROOM_S2C, response);
		
		return true;
	}

}
