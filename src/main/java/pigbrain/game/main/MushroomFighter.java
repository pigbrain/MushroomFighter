package pigbrain.game.main;

import pigbrain.game.command.CommandTable;
import pigbrain.game.command.CreateCharacterCallBack;
import pigbrain.game.command.CreateGameRoomCallBack;
import pigbrain.game.command.DisconnectCallBack;
import pigbrain.game.command.EnterGameRoomCallBack;
import pigbrain.game.command.GameRoomListCallBack;
import pigbrain.game.command.PlayerBombCallBack;
import pigbrain.game.command.HeartBeatCallBack;
import pigbrain.game.command.PlayerMoveCallBack;
import pigbrain.game.command.PlayerMissileCallBack;
import pigbrain.game.command.model.FighterCreateC2S;
import pigbrain.game.command.model.GameRoomListC2S;
import pigbrain.game.command.model.PlayerBombC2S;
import pigbrain.game.command.model.PlayerMoveC2S;
import pigbrain.game.command.model.GameRoomCreateC2S;
import pigbrain.game.command.model.GameRoomEnterC2S;
import pigbrain.game.command.model.HeartBeatC2S;
import pigbrain.game.command.model.PlayerMissileC2S;
import pigbrain.game.logic.GameSystem;
import pigbrain.game.network.command.CommandContainer;
import pigbrain.game.network.websocket.WebSocketServer;
import pigbrain.game.tick.TickContainer;

public class MushroomFighter {

	private static CommandContainer commandContainer = CommandContainer.getInstance();
	private static TickContainer tickContainer = TickContainer.getInstance();
	
	public static void main(String[] args) {
				
		registerCommand();
		
		registerTick();
		
		start();
	}
	
	private static void registerCommand() {

		commandContainer.putDisconnectCallBack(CommandTable.DISCONNECT, new DisconnectCallBack());
		
		commandContainer.putCallBack(CommandTable.CREATE_CHARACTER_C2S, FighterCreateC2S.class, new CreateCharacterCallBack());
		
		commandContainer.putCallBack(CommandTable.CREATE_GAMEROOM_C2S, GameRoomCreateC2S.class, new CreateGameRoomCallBack());
		
		commandContainer.putCallBack(CommandTable.ENTER_GAMEROOM_C2S, GameRoomEnterC2S.class, new EnterGameRoomCallBack());
		
		commandContainer.putCallBack(CommandTable.PLAYER_MOVE_C2S, PlayerMoveC2S.class, new PlayerMoveCallBack());
		
		commandContainer.putCallBack(CommandTable.PLAYER_BOMB_C2S, PlayerBombC2S.class, new PlayerBombCallBack());
		
		commandContainer.putCallBack(CommandTable.PLAYER_MISSILE_C2S, PlayerMissileC2S.class, new PlayerMissileCallBack());
		
		commandContainer.putCallBack(CommandTable.GAME_ROOM_LIST_C2S, GameRoomListC2S.class, new GameRoomListCallBack());
		
		commandContainer.putCallBack(CommandTable.HEARTBEAT_C2S, HeartBeatC2S.class, new HeartBeatCallBack());
	}
	
	private static void registerTick() {

		tickContainer.addInstance(GameSystem.getInstance());
	}
	
	private static void start() {

		WebSocketServer webSocketServer = new WebSocketServer();

		webSocketServer.setHost("localhost");
		webSocketServer.setPort(8080);
		webSocketServer.setCotextPath("/");
		
		webSocketServer.start();
	}
}
