package pigbrain.game.network.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import pigbrain.game.network.common.CommandConstant;
import pigbrain.game.network.exception.CommandBuildException;
import pigbrain.game.network.link.Link;

public class CommandBuilder {
	
	private static CommandContainer commandContainer = CommandContainer.getInstance();
	
	public static Command createCommand(Link link, String data) throws CommandBuildException {
	
		try {
			String commandId = data.substring(0, CommandConstant.COMMAND_ID_FIELD_LENGTH);
			String commandBody = data.substring(CommandConstant.COMMAND_ID_FIELD_LENGTH);
			
			CommandDesc commandDesc = commandContainer.getCommandDesc(commandId);
			
			ObjectMapper objectMapper = new ObjectMapper();
			Object object = objectMapper.readValue(commandBody, commandDesc.getParameterClass());
			
			Command command = new Command();
			command.setLink(link);
			command.setObject(object);
			command.setCommandId(commandId);
			
			return command;
			
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new CommandBuildException("Building Command is failed [" + e.getMessage() + "]");
		}
	}
	
	public static String createPacket(String commandId, Object object) throws CommandBuildException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			return String.format("%s%s", StringUtils.rightPad(commandId, CommandConstant.COMMAND_ID_FIELD_LENGTH), objectMapper.writeValueAsString(object));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			throw new CommandBuildException("Creating Row Packet is failed");
		}
	}
}
