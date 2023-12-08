import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ElisonServer {
	
	public static void main(String[] args) {
        
		final int portNumber = 5001; // Use port number over 5000
        
		// Sever socket to listen for incoming connections
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) 
        {
            System.out.println("Reverse Echo Server listening on port " + portNumber);
           
            // Accepts incoming client connections 
            while (true) 
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("\nClient connected: " + clientSocket);
                
                // New thread created each new client to handle communication
                new Thread(() -> clientHandling(clientSocket)).start();
            }
        } 
        // Handle exceptions 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

	// Method to handle client communication 
    private static void clientHandling(Socket clientSocket) 
    {
        try (
            BufferedReader severReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter severWriter = new PrintWriter(clientSocket.getOutputStream(), true)
        ) 
        {
            String input;
            while ((input = severReader.readLine()) != null) 
            {
                System.out.println("Received from client: " + input);

                // Check for termination message
                if (input.equals("end")) 
                {
                    severWriter.println("dne"); // Send termination acknowledgment
                    break; // Exit the loop and terminate the thread
                }

                // Reverse the string
                String reversedString = new StringBuilder(input).reverse().toString();

                // Send the reversed string back to the client
                severWriter.println(reversedString);

                System.out.println("Sent to client: " + reversedString);
            }
        } 
        // Handle Exceptions
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        
        finally 
        {
            try 
            {
            	// Closes the socket and disconnects
                clientSocket.close();
                System.out.println("Client disconnected: " + clientSocket);
            } 
            
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }

    }
    
}
