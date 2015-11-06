package pigbrain.game.command;

public class CommandTable {
	
	public static final String CONNECT = "00001";
	public static final String DISCONNECT = "00002";
	
	public static final String CREATE_CHARACTER_C2S = "10001";
	public static final String CREATE_CHARACTER_S2C = "10002";
	
	public static final String CREATE_GAMEROOM_C2S = "10003";
	public static final String CREATE_GAMEROOM_S2C = "10004";

	public static final String ENTER_GAMEROOM_C2S = "10005";
	public static final String ENTER_GAMEROOM_S2C = "10006";
	
	public static final String PLAYER_INFO_S2C = "10008";
	public static final String PLAYER_MOVE_C2S = "10009";
	public static final String PLAYER_MOVE_S2C = "10010";
	public static final String PLAYER_BOMB_C2S = "10011";
	public static final String PLAYER_BOMB_S2C = "10012";
	public static final String BOMB_EXPLOSION_S2C = "10013";

	public static final String PLAYER_MISSILE_C2S = "10014";
	public static final String PLAYER_MISSILE_S2C = "10015";
	public static final String MISSILE_MOVE_S2C = "10015";

	public static final String PLAYER_MOTION_S2C = "10051";
	
	public static final String GAME_STATUS_S2C = "10091";
	public static final String GAME_RESULT_S2C = "10092";
	
	public static final String GAME_ROOM_LIST_C2S = "20001";
	public static final String GAME_ROOM_LIST_S2C = "20002";
	
	public static final String HEARTBEAT_C2S = "80001";
	public static final String HEARTBEAT_S2C = "80002";
	
	
}
