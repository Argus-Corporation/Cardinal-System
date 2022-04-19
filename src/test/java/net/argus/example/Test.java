package net.argus.example;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JFrame;

public class Test {
	
	public static void main(String[] args) throws IOException {
		ServerSocket serv = new ServerSocket(11066);
		serv.accept();
	}
	
}
