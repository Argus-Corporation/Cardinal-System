package net.argus.net.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.BindException;
import java.net.ServerSocket;

import net.argus.event.net.server.EventServer;
import net.argus.event.net.server.ServerEvent;
import net.argus.event.net.server.ServerListener;
import net.argus.instance.Instance;
import net.argus.net.BanRegister;
import net.argus.net.server.room.Room;
import net.argus.net.server.room.RoomRegister;
import net.argus.net.socket.CardinalSocket;
import net.argus.util.Version;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class Server {
	
	public static final Version VERSION = new Version("1.0");
	
	private BanRegister serverBanRegister;
	private ServerConnector connector;
	private ServerSocket server;
	private Room mainRoom;
	
	private SystemUser systemUser;
	
	private EventServer event = new EventServer();
	
	private boolean stop;
	
	private int port;
	
	public Server(int max, int port) {
		this.port = port;
		
		server = createSocket(port);
		if(server == null)
			return;
		
		serverBanRegister = new BanRegister(Instance.SYSTEM);
		
		mainRoom = new Room("main", max, this);
		connector = new ServerConnector(server, mainRoom, serverBanRegister);
		
		systemUser = new SystemUser();
	}
		
	public Server(int port) {
		this(10, port);
	}
	
	private ServerSocket createSocket(int port) {
		try {return new ServerSocket(port);}
		catch(BindException e) {Debug.log("Port " + port + " is not available", Info.ERROR);}
		catch (IOException e) {e.printStackTrace();}
		
		return null;
	}
	
	public void open(CardinalSocket socket) {
		if(server != null) {
			connector.open(socket);
			
			systemUser.start(System.in, System.out);

			event.startEvent(EventServer.OPEN, new ServerEvent(this));
		}else {
			Debug.log("The operation could not be completed", Info.ERROR);
			event.startEvent(EventServer.ERROR, new ServerEvent(this));
		}
	}
	
	public void stop(String arg) {
		if(!stop) {
			stop = true;
			try {
				connector.close();
				Room[] rooms = (Room[]) RoomRegister.getRooms().toArray(new Room[RoomRegister.getRooms().size()]);
				for(Room room : rooms)
					room.close(arg);
			}catch(IOException e) {Debug.log("Error when closing rooms", Info.ERROR);}
			
			Debug.log("Server stoped: " + arg);
			event.startEvent(EventServer.STOP, new ServerEvent(this));
		}
	}
	
	public void setMonitoring(InputStream in, PrintStream out) {new SystemUser().start(in, out);}
	
	public void addServerListener(ServerListener listener) {event.addListener(listener);}
	
	public BanRegister getServerBanRegister() {return serverBanRegister;}
	public ServerSocket getServerSocket() {return server;}
	public Room getMainRoom() {return mainRoom;}
	public int getPort() {return port;}
	
	public boolean isStoped() {return stop;}
	
	public EventServer getEvent() {return event;}
	
}
