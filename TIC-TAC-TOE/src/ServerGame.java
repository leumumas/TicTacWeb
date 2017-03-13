import java.util.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerGame {
	public static void main (String args []) {
		// Envoie un objet TicTacToeGame sur le serveur
		try {
			TicTacToeGameImpl tictac = new TicTacToeGameImpl ();
			Registry rg= LocateRegistry.createRegistry(6767);
			rg.bind ("titactoegame", tictac);
			System.out.println("Server waiting.....");
		}
		catch (Exception e) {
		      System.out.println(e.getMessage());
	    }
	}
}
