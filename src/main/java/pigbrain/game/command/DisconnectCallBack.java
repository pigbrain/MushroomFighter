package pigbrain.game.command;

import org.apache.commons.lang3.StringUtils;

import pigbrain.game.common.GameRoom;
import pigbrain.game.common.Member;
import pigbrain.game.logic.GameRoomContainer;
import pigbrain.game.logic.GameSystem;
import pigbrain.game.logic.MemberContainer;
import pigbrain.game.network.command.Command;
import pigbrain.game.network.command.CommandCallBack;

public class DisconnectCallBack implements CommandCallBack {

	private GameSystem gameSystem = GameSystem.getInstance();
	private MemberContainer memberContainer = gameSystem.getMemberContainer();
	private GameRoomContainer gameRoomContainer = gameSystem.getGameRoomContainer();
	
	@Override
	public boolean execute(Command command) throws Exception {
		

		Member member = memberContainer.getMember(command.getLink().getSecretKey());

		memberContainer.removeMember(command.getLink().getSecretKey());

		System.out.println("disconnect id : " + command.getLink().getSecretKey());
		
		if (!member.hasGameRoom()) {
			return true;
		}
		
		GameRoom gameRoom = gameRoomContainer.getGameRoom(member.getGameRoomId());
		gameRoom.removeMember(member.getLink().getSecretKey());
		
		if (!gameRoom.hasMember()) {
			gameRoomContainer.removeGameRoom(gameRoom.getId());
			
			System.out.println("remove gameRoom[" + gameRoom.getId() + "]");
		}
		
		
		return true;
	}

}
