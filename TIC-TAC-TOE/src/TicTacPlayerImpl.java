import java.rmi.server.*;
import java.rmi.*;

public class TicTacPlayerImpl extends UnicastRemoteObject implements TicTacPlayer {

	int typeXO;
	boolean bWinning;
	int casesRes;
	public ClientTicTac clientRef = null;
	int[] aiPlay;
	int[] clientPlay;
	
	// Initialise un nouveau joueur-client
	public TicTacPlayerImpl (int xo) throws RemoteException{
		typeXO = xo;
		bWinning = false;
		aiPlay = new int[2];
	}
	
	// Change la valeur 'X' ou 'O' du joueur-client
	@Override
	public void setTypeXO(int xo) throws RemoteException {
		typeXO = xo;
	}

	// Retourne l'issue de la game au joueur-client
	@Override
	public boolean getGameIssue() throws RemoteException {
		return bWinning;
	}
	
	// Renvoie le move de l'IA
	@Override
	public int[] getAIPlay() throws RemoteException {
		return aiPlay;
	}
	
	// Change l'issue de la game du client
	public void setPlayerIssue(boolean issue) {
		bWinning = issue;
	}
	
	// Retourne le type 'X' ou 'O' du joueur-client (serveur side)
	public int getTypeXO() {
		return typeXO;
	}

	// Change le move de l'IA (serveur side)
	public void setAiPlay(int[] play) {
		aiPlay = play;
	}
	
	// Change le move du joueur-client (serveur side)
	public void setClientPlay(int[] play) {
		clientPlay = play;
	}
	
	// Renvoie le move du joueur-client
	@Override
	public int[] getClientPlay() {
		return clientPlay;
	}

	// Renvoie le nombre de cases restantes
	@Override
	public int getCasesRes() throws RemoteException {
		return casesRes;
	}
}
