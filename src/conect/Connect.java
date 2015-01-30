package conect;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import console.Console;

public class Connect extends Thread {
	public static String mode;
	static Socket socket;
	static ServerSocket server;
	static OutputStream streamOut;
	static InputStream streamIn;
	static DataOutputStream dataOut;
	static DataInputStream dataIn;
	static String packageIn;
	static String packageOut;
	public static String ip = "localhost";
//	public static boolean isServerDone = false;
//	public static boolean isClientDone = false;

	public void run() {
		if (mode.equals("server")) {
			try {
				startAsServer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (mode.equals("client")) {
			try {
				startAsClient();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void startAsServer() throws IOException {
		server = new ServerSocket(7770);
		socket = server.accept();
		getStream();
		sendMap();
		Console.mode = "server";
		Console.start();
	}

	private static void startAsClient() throws IOException {
		socket = new Socket(ip, 7770);
		getStream();
		getMap();
		Console.mode = "client";
		Console.start();
	}

	public static void addToPackage(String out) {
		packageOut = packageOut + out + ";";
	}


	public static String handshakeAsClient() {
		packageOut = "c"+packageOut;
		sendPackage();
		getPackage();
		return packageIn;
	}

	public static String handshakeAsServer() {
		packageOut = "s"+packageOut;
		getPackage();
		sendPackage();
		return packageIn;
	}

	private static void sendPackage() {

		try {
			dataOut.writeUTF( packageOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
		packageOut = "";
	}

	private static void getPackage() {
		try {
			packageIn = dataIn.readUTF().substring(1);
//			if(packageIn.charAt(0)=='c')
//				isClientDone = true;
//			else if (packageIn.charAt(0)=='s')
//				isServerDone = true;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
//		return packageIn;
	}

	private static void sendMap() throws IOException {
		dataOut.writeUTF(Console.map.length + " " + Console.map[0].length);
		packageOut = "";// char[][] map
		for (int i = 0; i < Console.map.length; i += 1)
			for (int j = 0; j < Console.map[0].length; j += 1)
				packageOut += Console.map[i][j];
		dataOut.writeUTF(packageOut);

		packageOut = "";// goldMap
		for (int i = 0; i < Console.map.length; i += 1)
			for (int j = 0; j < Console.map[0].length; j += 1)
				packageOut += Console.goldMap[i][j];
		dataOut.writeUTF(packageOut);

		packageOut = "";// stoneMap
		for (int i = 0; i < Console.map.length; i += 1)
			for (int j = 0; j < Console.map[0].length; j += 1)
				packageOut += Console.stoneMap[i][j];
		dataOut.writeUTF(packageOut);

		packageOut = "";// lumberMap
		for (int i = 0; i < Console.map.length; i += 1)
			for (int j = 0; j < Console.map[0].length; j += 1)
				packageOut += Console.lumberMap[i][j];
		dataOut.writeUTF(packageOut);

		packageOut = "";// foodMap
		for (int i = 0; i < Console.map.length; i += 1)
			for (int j = 0; j < Console.map[0].length; j += 1)
				packageOut += Console.foodMap[i][j];
		dataOut.writeUTF(packageOut);

		packageOut = "";

	}

	private static void getMap() throws IOException {
		packageIn = dataIn.readUTF();
		int row = Integer.parseInt(packageIn.split(" ")[0]);
		int col = Integer.parseInt(packageIn.split(" ")[1]);
		char[][] map = new char[row][col];
		int[][] goldMap = new int[row][col];
		int[][] stoneMap = new int[row][col];
		int[][] lumberMap = new int[row][col];
		int[][] foodMap = new int[row][col];
		packageIn = dataIn.readUTF();
		for (int i = 0; i < map.length; i += 1)
			for (int j = 0; j < map[0].length; j += 1)
				map[i][j] = packageIn.charAt((map[0].length * i) + j);

		packageIn = dataIn.readUTF();
		for (int i = 0; i < map.length; i += 1)
			for (int j = 0; j < map[0].length; j += 1)
				goldMap[i][j] = Integer.parseInt(""
						+ packageIn.charAt((map[0].length * i) + j));

		packageIn = dataIn.readUTF();
		for (int i = 0; i < map.length; i += 1)
			for (int j = 0; j < map[0].length; j += 1)
				stoneMap[i][j] = Integer.parseInt(""
						+ packageIn.charAt((map[0].length * i) + j));

		packageIn = dataIn.readUTF();
		for (int i = 0; i < map.length; i += 1)
			for (int j = 0; j < map[0].length; j += 1)
				lumberMap[i][j] = Integer.parseInt(""
						+ packageIn.charAt((map[0].length * i) + j));

		packageIn = dataIn.readUTF();
		for (int i = 0; i < map.length; i += 1)
			for (int j = 0; j < map[0].length; j += 1)
				foodMap[i][j] = Integer.parseInt(""
						+ packageIn.charAt((map[0].length * i) + j));

		Console.map = map;
		Console.foodMap = foodMap;
		Console.goldMap = goldMap;
		Console.lumberMap = lumberMap;
		Console.stoneMap = stoneMap;
		packageOut = "";
	}

	private static void getStream() throws IOException {
		streamOut = socket.getOutputStream();
		dataOut = new DataOutputStream(streamOut);
		streamIn = socket.getInputStream();
		dataIn = new DataInputStream(streamIn);
	}

	public static void disconnect() throws IOException {
		streamOut.close();
		streamIn.close();
		dataOut.close();
		dataIn.close();
		socket.close();
		if (server != null)
			server.close();
	}
}
