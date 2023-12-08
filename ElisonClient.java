import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ElisonClient {

	public static void main(String[] args) throws UnknownHostException {
        
		// Gets local IP address and initialize client
		final InetAddress ip = InetAddress.getLocalHost();
        
        final int serverPort = 5001; // Uses same port number as the server
         
        
        try( Socket clientSocket = new Socket(ip, serverPort); //Connects it to sever
             BufferedReader clientReader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader serverReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) // Set up input & output streams to communicate
        {
        	
            System.out.print("Connected to Reverse Echo Server. \nType a message: ");

            String userInput;
            
            // Loop allowing user to input messages until terminated 
            while ((userInput = clientReader.readLine()) != null) {
                
            	// Send user input to the server
                clientWriter.println(userInput);

                // Receive and print the reversed message from the server
                String reversedMessage = serverReader.readLine();
                System.out.println("Reversed message from server: " + reversedMessage);

                // Check for termination 'end' -> 'dne'
                if (userInput.equals("end") && reversedMessage.equals("dne")) {
                    System.out.println("\nTerminating client.");
                    break; // Exit the loop and terminate the client
                }

                System.out.print("\nType another message: ");
            }
        
        } 
        // Handles exceptions 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
