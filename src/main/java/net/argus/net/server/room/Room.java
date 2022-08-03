package net.argus.net.server.room;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import net.argus.event.net.server.EventServer;
import net.argus.event.net.server.ServerEvent;
import net.argus.instance.Instance;
import net.argus.instance.InstanceRegister;
import net.argus.net.BanRegister;
import net.argus.net.StatusConnection;
import net.argus.net.pack.Package;
import net.argus.net.pack.PackagePrefab;
import net.argus.net.server.Server;
import net.argus.net.server.ServerProcess;
import net.argus.net.server.role.Role;
import net.argus.net.socket.CardinalSocket;
import net.argus.util.debug.Debug;

public class Room {
	
	public static final int DEFUALT_ROOM_SIZE = 10;
	
	private int size;
	
	private String password;
	
	private Instance roomInstance;
		
	private Server parent;
	
	private ServerProcess[] clients;
	private String name;
	
	private boolean closed;
		
	private BanRegister ban;
	
	public Room(String name, int size, Server parent) {	
		this(name, size, null, parent);
	}
	
	public Room(String name, int size, String password, Server parent) {	
		if(size <= 0)
			size = DEFUALT_ROOM_SIZE;
		
		clients = new ServerProcess[size];
		this.name = name;
		this.size = size;
		this.parent = parent;
		this.password = password;
		
		roomInstance = new Instance("room/" + name);
		
		ban = new BanRegister(roomInstance);
		
		RoomRegister.addRoom(this);

		parent.getEvent().startEvent(EventServer.ROOM_CREATE, new ServerEvent(this, parent));
	}
	
	private void add(ServerProcess client) {
		if(countClientInRoom() < clients.length)
			clients[firstPlace()] = client;

		try {sendToAll(PackagePrefab.genSystemPackage(client.getCardinalSocket().getProfile().getName() + " join the room"), client.getCardinalSocket());}
		catch(IOException e) {}
	}
	
	public int countClientInRoom() {
		int count = 0;
		for(ServerProcess sock : clients)
			if(sock != null)
				count++;
		return count;
	}
	
	private int firstPlace() {
		for(int i = 0; i < clients.length; i++)
			if(clients[i] == null)
				return i;
		return -1;
	}
	
	public StatusConnection join(ServerProcess process, String password) {
		StatusConnection status = checkConnection(process, password);
		if(status.isConnected()) {
			process.setRoom(this);
			add(process);
			
			try {process.send(PackagePrefab.genSystemPackage("You have joined room \"" + name + "\""));
			}catch(IOException e) {e.printStackTrace();}

			return status;
		}else {
			Debug.log("Error on connection: " + status.getArgument());
			try {process.send(PackagePrefab.genSystemPackage("Error on connection: " + status.getArgument()));}
			catch(IOException e) {}
			
			return status;
		}
	}
	
	public void sendToAll(Package pack, CardinalSocket sender) throws IOException {
		for(ServerProcess socket : getClients())
			if(!socket.getCardinalSocket().equals(sender))
				socket.send(pack);
	}
	
	public void logOut(ServerProcess client, String arg) {
		boolean connected = false;
		for(int i = 0; i < clients.length; i++)
			if(clients[i] != null && clients[i].equals(client)) {
				clients[i] = null;
				connected = true;
			}

		try {
			client.getCardinalSocket().logOut(arg);
			if(connected)
				sendToAll(PackagePrefab.genSystemPackage(client.getCardinalSocket().getProfile().getName() + " leave the room"), client.getCardinalSocket());
		}catch(IOException e) {}
	}
	
	public void move(ServerProcess process, String password, Room room) {
		try {
			StatusConnection status = room.join(process, password);
			if(status.isConnected()) {
				for(int i = 0; i < clients.length; i++)
					if(clients[i] != null && clients[i].equals(process))
						clients[i] = null;
				sendToAll(PackagePrefab.genSystemPackage(process.getCardinalSocket().getProfile().getName() + " leave the room"), process.getCardinalSocket());
			}
		}catch(IOException e) {}
	}
	
	public void close(String arg) throws IOException {
		closed = true;
		
		if(RoomRegister.indexOf(this) <= 0)
			for(ServerProcess sock : getClients())
				sock.logOut(arg);
		else {
			Room mainRoom = RoomRegister.getRoom(0);
			for(ServerProcess sock : getClients())
				move(sock, null, mainRoom);
		}
		
		Debug.log("Room \"" + name + "\" is closed");
		
		InstanceRegister.remove(roomInstance);
		RoomRegister.remove(this);
		
		parent.getEvent().startEvent(EventServer.ROOM_REMOVE, new ServerEvent(this, parent));
	}
	
	public StatusConnection checkConnection(ServerProcess client, String password) {
		if(client.getCardinalSocket().getProfile().getRole().equals(Role.SYSTEM))
			return new StatusConnection(true, "connected");
		
		if(isFull())
			return new StatusConnection(false, "room full");
		
		InetAddress address = client.getCardinalSocket().getInetAddress();
		if(address != null)
			if(getBanRegister().isBanned(address.getHostAddress()))
				return new StatusConnection(false, "user banned");
		
		if(this.password != null)
			if(password == null)
				return new StatusConnection(false, "invalid password");
			else
				if(!this.password.equals(password))
					return new StatusConnection(false, "invalid password");
		
		if(client.getRoom() != null && client.getRoom().equals(this))
			return new StatusConnection(false, "already connected");
		
		return new StatusConnection(true, "connected");
	}
	
	public void ban(ServerProcess client, String arg) throws IOException {
		ban.ban(client.getCardinalSocket());
		logOut(client, arg);
	}
	
	public void unban(String ip) throws IOException {
		ban.unban(ip);
	}
	
	public List<ServerProcess> getClients() {
		List<ServerProcess> clients = new ArrayList<ServerProcess>();
		for(ServerProcess sock : this.clients)
			if(sock != null)
				clients.add(sock);
			
		return clients;
	}
	
	public ServerProcess getClientByName(String userName) {
		for(ServerProcess sock : getClients())
			if(sock.getCardinalSocket().getProfile().getName().equals(userName))
				return sock;
		return null;
	}
	
	public boolean isFull() {
		for(ServerProcess sock : clients)
			if(sock == null)
				return false;
		return true;
	}
	
	public BanRegister getBanRegister() {return ban;}
	
	public int getSize() {return size;}
	public String getName() {return name;}
	public Server getParent() {return parent;}
	public String getPassword() {return password;}
	
	public boolean isPrivate() {return password!=null;}
	
	public boolean isClosed() {return closed;}
	
	@Override
	public String toString() {
		return "Room@" + name;
	}
	
}
