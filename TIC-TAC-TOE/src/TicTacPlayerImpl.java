import java.rmi.server.*;
import java.rmi.*;

public class TicTacPlayerImpl extends UnicastRemoteObject implements TicTacPlayer {

	char typeXO;
	boolean bWinning;
	
	public TicTacPlayerImpl (char xo) throws RemoteException{
		typeXO = xo;
		bWinning = false;
	}
	
	@Override
	public void setTypeXO(char xo) throws RemoteException {
		typeXO = xo;

	}

	@Override
	public boolean getGameIssue() throws RemoteException {
		return bWinning;
	}
	
	public void setPlayerIssue(boolean issue) {
		bWinning = issue;
	}

}
