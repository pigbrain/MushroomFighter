package pigbrain.game.common;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;


public class GameConstant {

	public static final int READY_TIME = 3;

	public static final int FIGHT_TIME = 90;

	public static final int ENERGY = 100;
	
	public static final int ROOM_MEMBER_COUNT = 2;
		
	public static enum GameStatus {
		WAIT("wait"),	// 유저 접속 대기
		READY("ready"),	// 싸움준비
		FIGHT("fight"),	// 싸움
		END	("end");	// 끝
		
		private String id;
		
		private GameStatus(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}
	};
	
	public static enum MotionStatus {
		NONE("NONE"), 
		UP("UP"), 
		RIGHT("RIGHT"), 
		DOWN("DOWN"), 
		LEFT("LEFT"), 
		TALENT("TALENT"), 
		DIE("DIE");
		
		private String id;
		
		private MotionStatus(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}
	};
	
	public static enum Talent {
		BOMB("BOMB"),
		SHOOT("MISSILE");
		
		private String id;
				
		private Talent(String id) {
			this.id = id;	
		}

		public String getId() {
			return id;
		}
	}
	
	public static enum Direction {
		UP(1), RIGHT(2), DOWN(3), LEFT(4);
		
		private int id;
		
		private Direction(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}
	
	public static final String PLYAER_POSITION_1 = "1p";
	
	public static final int PLAYER_SPEED = 15;
	
	public static final int MISSILE_SPEED = 14;
	
	public static final String PLYAER_POSITION_2 = "2p";
	
	public static final int MAX_X_POSITION = 770;
	
	public static final int MIN_X_POSITION = 30;
	
	public static final int MIN_Y_POSITION = 50;
	
	public static final int MAX_Y_POSITION = 400;
	
	public static final int DEFAULT_Y_POSITION = 200;
	
	public static final int PLAYER_COLLISION_THRESHOLD = 45;
	
	public static final int BOMB_COLLISION_THRESHOLD = 110;
	
	public static final int BOMB_REFESH_TIME = 1;
	
	public static final int MIN_RANDOM_NUMBER = 0;
	
	public static final int MAX_RANDOM_NUMBER = 7;
	
}