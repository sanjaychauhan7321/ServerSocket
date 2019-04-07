package com.nokia.sanjay;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.nokia.sanjay.exceptions.ServerSocketException;

public class Server {

	public static void main(String[] args) throws ServerSocketException {

		ServerSocket serverSocket = null;
		Socket socket = null;
		Scanner scanner = null;
		InputStream socketInputStream = null;
		OutputStream socketOutputStream = null;
		PrintStream printStream = null;
		String dataRecievedFromClient = null;

		try {

			// Creating a server socket on defined port
			try {
				serverSocket = new ServerSocket(1234);
			} catch (IOException e) {
				System.out.println("An I/O error occurs when opening the socket.! Reason : " + e.getMessage());
				throw new ServerSocketException("Error_Custom",
						"An I/O error occurs when opening the socket.! Reason : " + e.getMessage());

			} catch (SecurityException e) {

				System.out.println(
						"A SecurityException occured! A security manager exists and its checkListenmethod doesn't allow the operation. Reason : "
								+ e.getMessage());
				throw new ServerSocketException("Error_Custom",
						"A SecurityException occured! A security manager exists and its checkListenmethod doesn't allow the operation. Reason : "
								+ e.getMessage());

			} catch (IllegalArgumentException e) {

				System.out.println(
						" An IllegalArgumentException occured! The port parameter is outside the specified range of valid port values, which is between 0 and 65535, inclusive. Reson : "
								+ e.getMessage());
				throw new ServerSocketException("Error_Custom",
						" An IllegalArgumentException occured! The port parameter is outside the specified range of valid port values, which is between 0 and 65535, inclusive. Reson : "
								+ e.getMessage());

			}

			// A server side Socket on ServerSocket, It waits till the connection is
			// established!

			System.out.println("Server socket on port 1234 waiting for client to connect...");
			try {
				socket = serverSocket.accept();

			} catch (IOException ex) {
				throw new ServerSocketException("Error_Custom", ex.getMessage());

			} catch (SecurityException ex) {
				throw new ServerSocketException("Error_Custom", ex.getMessage());

			} catch (java.nio.channels.IllegalBlockingModeException ex) {
				throw new ServerSocketException("Error_Custom", ex.getMessage());

			}

			System.out.println("A client just connected to the server socket! Remote socket address : "
					+ socket.getRemoteSocketAddress());

			try {
				socketInputStream = socket.getInputStream();
			} catch (IOException ex) {
				System.out.println(
						"An I/O error occured when creating the input stream, the socket is closed, the socket is not connected, or the socket input has been shut down using shutdownInput(). Reason : "
								+ ex.getMessage());
				throw new ServerSocketException("Error_Custom",
						"An I/O error occured when creating the input stream, the socket is closed, the socket is not connected, or the socket input has been shut down using shutdownInput(). Reason : "
								+ ex.getMessage());

			}

			// Scanner for the input stream of the socket
			scanner = new Scanner(socketInputStream);

			// Let server wait for the input
			System.out.println("Waiting for the client to send some data...");
			scanner.hasNextLine();

			try {
				// Data received!
				System.out.println();
				System.out.println("Data received from client!");
				dataRecievedFromClient = scanner.nextLine();
				System.out.println();
				System.out.println("---------------------Start-------------------------------");
				System.out.println();
				System.out.println(dataRecievedFromClient);
				System.out.println();
				System.out.println("---------------------------End-------------------------------------");
			} catch (NoSuchElementException e) {
				System.out
						.println("NoSuchElementException : Client has disconnected the socket unexpectedly!. Reason : "
								+ e.getMessage());
				throw new ServerSocketException("Error_Custom",
						"NoSuchElementException : Client has disconnected the socket unexpectedly!. Reason : "
								+ e.getMessage());
			}

			try {
				socketOutputStream = socket.getOutputStream();
			} catch (IOException ex) {
				System.out.println(
						"An I/O error occurs when creating the output stream or if the socket is not connected. Reason : "
								+ ex.getMessage());
				throw new ServerSocketException("Error_Custom",
						"An I/O error occurs when creating the output stream or if the socket is not connected. Reason : "
								+ ex.getMessage());

			}

			printStream = new PrintStream(socketOutputStream);

			String serverValue = "Got server acknowledgement! Your data has been processed.";
			printStream.println(serverValue + ". For Data : \n\n" + dataRecievedFromClient);

		} finally {

			if (null != printStream) {

				printStream.close();
			}

			if (null != socketInputStream) {

				try {
					socketInputStream.close();
				} catch (IOException e) {
					System.out.println("Could not close the socket input stream. Reason : " + e.getMessage());
				}
			}
			if (null != socket) {
				try {
					socket.close();
				} catch (IOException e) {
					System.out.println("Could not close the socket. Reason : " + e.getMessage());
				}
			}

			if (null != scanner) {
				scanner.close();
			}
			if (null != serverSocket) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					System.out.println("Could not close the server socket. Reason : " + e.getMessage());
				}
			}
		}

	}

}
