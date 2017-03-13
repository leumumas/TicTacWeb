import java.rmi.server.*;
import java.rmi.*;

public class TicTacPlayerImpl extends UnicastRemoteObject implements TicTacPlayer {

	int typeXO;
	boolean bWinning;
	public ClientTicTac clientRef = null;
	int[] aiPlay;
	
	public TicTacPlayerImpl (int xo) throws RemoteException{
		typeXO = xo;
		bWinning = false;
		aiPlay = new int[2];
	}
	
	@Override
	public void setTypeXO(int xo) throws RemoteException {
		typeXO = xo;

	}

	@Override
	public boolean getGameIssue() throws RemoteException {
		return bWinning;
	}
	
	@Override
	public int[] receiveTurn() throws RemoteException {
		return aiPlay;
	}
	
	public void setPlayerIssue(boolean issue) {
		bWinning = issue;
	}
	
	public int getTypeXO() {
		return typeXO;
	}

	public void setAiPlay(int[] play) {
		aiPlay = play;
	}
}
