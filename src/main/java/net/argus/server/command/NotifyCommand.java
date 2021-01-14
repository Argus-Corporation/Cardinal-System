package net.argus.server.command;

import java.io.IOException;

import net.argus.exception.SecurityException;
import net.argus.server.Regular;
import net.argus.server.ServerSocketClient;
import net.argus.server.Users;
import net.argus.util.ArrayManager;
import net.argus.util.debug.Debug;
import net.argus.util.pack.Package;
import net.argus.util.pack.PackageBuilder;
import net.argus.util.pack.PackageType;

public class NotifyCommand extends Command {

	public NotifyCommand(String commandName, int rank) {
		super(commandName, rank);
	}
	
	@Override
	protected void run(String[] com, ServerSocketClient client) throws IOException, SecurityException {
		ServerSocketClient target = null;
		
		if(Regular.isExist(com[1])) {
			switch(Regular.getRegular(com[1])) {
				case a:
					client.getProcessServer().sendMessage("You can't use \"@a\" for this command");
					Debug.log("Error: @a doesn't work with this command");
					return;
					
				case s:
					target = client;
					break;
			}
		}else {
			try {target = Users.getServerSocketClient(com[1]);}
			catch(NullPointerException e) {
				client.getProcessServer().sendMessage("Target client does existed");
				Debug.log("Error: this client does existed");
				return;
			}
		}
		String message = "";
		
		if(ArrayManager.isExist(com, 2)) {
			for(int i = 2; i < com.length; i++)
				message += com[i] + " ";
			
			message = message.substring(0, message.length() - 1);
		}
		
		target.sendPackage(new Package(new PackageBuilder(PackageType.NOTIFY).addValue("message", message)));
	}

}
