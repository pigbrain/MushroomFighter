package pigbrain.game.logic;

import java.util.HashMap;
import java.util.Map;

import pigbrain.game.common.Member;
import pigbrain.game.network.command.CommandCommunicator;
import pigbrain.game.network.exception.InvalidLinkException;
import pigbrain.game.tick.Regulator;
import pigbrain.game.tick.Tick;

public class MemberContainer implements Tick {

	private Map<String, Member> memberMap = new HashMap<String, Member>();
	
			
	@Override
	public void update(double delta) {
	
		for( Map.Entry<String, Member> element : memberMap.entrySet() ){
            element.getValue().update(delta);
        }
	}
	
	public boolean putMember(String key, Member member) {
		if (memberMap.put(key, member) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void removeMember(String key) {
		memberMap.remove(key);
	}
	
	public Member getMember(String key) throws InvalidLinkException {
		Member member = memberMap.get(key);
		if (member == null) {
			throw new InvalidLinkException("Member Key[" + key + "] is not found");
		}
		return member;
	}
	
	public Map<String, Member> getMembers() {
		return memberMap;
	}

}
