import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TicTacPlayer extends SwingGUI {

	private char typeXO;
	
	public TicTacPlayer(char type) {
		super("TicToeGame");
		typeXO = type;
	}
	
	public int receiveTurn(int tileX, int tileY){
		return 0;
	}
	
	public void youWin () {
		
	}
	
	public void youLose() {
		
	}

	public static void main(String args[]) {
		TicTacToeGame tictac = null;
		TicTacPlayer player = new TicTacPlayer('X'); // recuperer la valeur du prompt
		try {
			Registry rg =LocateRegistry.getRegistry("localhost",6767);
		    tictac = (TicTacToeGame) rg.lookup("titactoegame");
			 
		} 
		catch (Exception e) {
		     System.out.println("Erreur à l'accès de la game" + e);
		}

		try {
			player = tictac.newPlayer('X'); //Demarre une game (cree une grid sur le serveur)
		} 
		catch (Exception e) {
		     System.out.println("Erreur de création du player " + " : " + e);
		}
	}
}
	
