import java.rmi.*;

public interface TicTacPlayer extends Remote {
	void setTypeXO(char xo) throws RemoteException;
	boolean getGameIssue() throws RemoteException;
}
