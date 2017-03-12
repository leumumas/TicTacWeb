import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientTicTac extends SwingGUI {

	TicTacToeGame tictac = null;
	public TicTacPlayer player = null;
	
	public ClientTicTac(String title) {
		super(title);
		try {
			Registry rg =LocateRegistry.getRegistry("localhost",6767);
		    tictac = (TicTacToeGame) rg.lookup("titactoegame");	
		} 
		catch (Exception e) {
		     System.out.println("Erreur à l'accès de la game" + e);
		}
	}
	
	public void selectType(char xo) { //player select if playing "x" or "o" do it at start
		try {
			player = tictac.newPlayer(xo);
		}
		catch (Exception e) {
			System.out.println("Erreur à la création d'un Joueur : " + e);
		}
	}
	
	public void play(int X, int Y) { // call it when click in a tile 
		try {
			tictac.playTurn(X, Y);
		}
		catch (Exception e) {
			System.out.println("Player can't play : " + e);
		}
	}
	
	public void checkGameIssue () { // run it to check game issue
		boolean issue;
		while (true) {
			try {
				issue = player.getGameIssue ();
				if(issue)
					;//play win anim
				else
					;//play lose anim
			}
			catch (RemoteException i) {}
		}
	}

	public static void main(String args[]) {
		ClientTicTac client = new ClientTicTac("TicToeGame");
		client.pack ();
		client.checkGameIssue ();
	}
}
	
