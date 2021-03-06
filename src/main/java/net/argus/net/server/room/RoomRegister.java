package net.argus.net.server.room;

import java.util.ArrayList;
import java.util.List;

import net.argus.net.server.ServerProcess;

public class RoomRegister {
	
	private static List<Room> rooms = new ArrayList<Room>();
	
	public static void addRoom(Room room) {
		rooms.add(room);
	}
	
	public static int indexOf(Room room) {return rooms.indexOf(room);}
	
	public static Room getRoom(int index) {
		return rooms.get(index);
	}
	
	public static Room getRoom(String name) {
		for(Room r : rooms)
			if(r.getName().equals(name))
				return r;
		return null;
	}
	
	public static List<Room> getRooms() {return rooms;}
	
	public static void remove(Room room) {
		rooms.remove(room);
		if(rooms.size() < 1)
			room.getParent().stop("no room avaliable");
	}
	
	public static boolean isExist(String name) {
		for(Room room : rooms)
			if(room.getName().toUpperCase().equals(name.toUpperCase()))
				return true;
		return false;
	}
	
	public static boolean isUserExist(String name) {
		for(Room room : rooms)
			for(ServerProcess pro : room.getClients())
				if(pro.getCardinalSocket().getProfile().getName().equals(name))
					return true;
		return false;
	}
	
	public static int length() {return rooms.size();}

}
