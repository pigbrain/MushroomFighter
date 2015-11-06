package pigbrain.game.command;

import java.util.UUID;

import pigbrain.game.command.model.FighterCreateC2S;
import pigbrain.game.command.model.FighterCreateS2C;
import pigbrain.game.command.model.GameRoomCreateC2S;
import pigbrain.game.command.model.GameRoomCreateS2C;
import pigbrain.game.command.model.GameRoomEnterC2S;
import pigbrain.game.command.model.GameRoomEnterS2C;
import pigbrain.game.common.Bomb;
import pigbrain.game.common.GameConstant;
import pigbrain.game.common.GameRoom;
import pigbrain.game.common.Member;
import pigbrain.game.common.GameConstant.Direction;
import pigbrain.game.common.GameConstant.MotionStatus;
import pigbrain.game.logic.GameRoomContainer;
import pigbrain.game.logic.GameSystem;
import pigbrain.game.logic.MemberContainer;
import pigbrain.game.network.command.Command;
import pigbrain.game.network.command.CommandCallBack;

public class EnterGameRoomCallBack implements CommandCallBack {

	private GameSystem gameSystem = GameSystem.getInstance();
	private MemberContainer memberContainer = gameSystem.getMemberContainer();
	private GameRoomContainer gameRoomContainer = gameSystem.getGameRoomContainer();
	
	@Override
	public boolean execute(Command command) throws Exception {
		
		GameRoomEnterC2S request = (GameRoomEnterC2S) command.getObject();
	
		GameRoom gameRoom = gameRoomContainer.getGameRoom(request.getId());
		
		if (gameRoom.getMemberCount() == GameConstant.ROOM_MEMBER_COUNT) {
			gameSystem.post(command.getLink(), CommandTable.ENTER_GAMEROOM_S2C, new GameRoomEnterS2C());
			return true;
		}
		
		Member member = memberContainer.getMember(command.getLink().getSecretKey());
		member.init();
		member.setGameRoomId(gameRoom.getId());
		member.setyPosition(GameConstant.DEFAULT_Y_POSITION);
		
		String playerPosition = gameRoom.setMember(member);
		if (GameConstant.PLYAER_POSITION_1.equals(playerPosition)) {
			member.setxPosition(GameConstant.MIN_X_POSITION);
			member.setDirection(Direction.RIGHT.getId());
			member.setMotionStatus(MotionStatus.RIGHT);
		} else {
			member.setxPosition(GameConstant.MAX_X_POSITION);
			member.setDirection(Direction.LEFT.getId());
			member.setMotionStatus(MotionStatus.LEFT);
		}
		
		GameRoomEnterS2C response = new GameRoomEnterS2C();
		response.setId(gameRoom.getId());
		response.setPlayerPosition(playerPosition);
		response.setxPosition(member.getxPosition());
		response.setDirection(member.getDirection());
		
		gameSystem.post(command.getLink(), CommandTable.ENTER_GAMEROOM_S2C, response);
		
		return true;
	}

}
