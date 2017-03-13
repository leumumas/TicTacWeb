import java.rmi.RemoteException;
import java.util.*;

public class AIPlayerMinimax extends TicTacPlayerImpl {
	private int[][] grid;
	private int me;
	private int enemy;
	
	public AIPlayerMinimax(int[][] board, int index) throws RemoteException {
		super(index);
		grid = board;
		SetIndex(index);
	}
	
	public void SetIndex(int index) {
		me = index;
		enemy = (me == 1) ? 2 : 1;
	}
	
	public void UpdateGrid(int[][] g) {
		grid = g;
	}
	
	//Va chercher le meilleur Move possible selon l'algo MiniMax
	int[] Move() {
		int[] result = Minimax(3, me);
		return new int[] { result[1], result[2] }; 
	}
	
	//MiniMax : trouve le Move qui avantage le plus l'IA ET d�savantage le plus le joueur
	//  depth : � quelle profondeur d'analyse on est rendu
	//  player : id du joueur � qui c'est le tour dans l'analyse
	private int[] Minimax(int depth, int player) {
		List<int[]> possibleMoves = GetPossibleMoves();
		depth--;
		
		int bestScore = (player == me) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		int score;
		int bestRow = -1;
		int bestCol = -1;
		
		if(possibleMoves.isEmpty() || depth <= 0) //On calcule le score de ce Move quand on atteint le bout.
			bestScore = CalculateScore();
		else {
			for(int[] move : possibleMoves) {
				grid[move[0]][move[1]] = player;
				
				if(player == me) {  //Il faut maximiser le score du tour de l'IA
					score = Minimax(depth, enemy)[0];
					
					if(score > bestScore) {         
						bestScore = score;
						bestRow = move[0];
						bestCol = move[1];
					}
				}
				else { // Il faut minimiser le score du tour du joueur
					score = Minimax(depth, enemy)[0];
					
					if(score < bestScore) {
						bestScore = score;
						bestRow = move[0];
						bestCol = move[1];
					}
				}
				
				grid[move[0]][move[1]] = 0;
			}
		}
		
		return new int[] {bestScore, bestRow, bestCol }; //� travers la r�cursion on passe ces variables
	}
	
	//On va chercher toutes les cases vides
	private List<int[]> GetPossibleMoves() {
		List<int[]> possibleMoves = new ArrayList<int[]>();
		
		if(Ended(me) || Ended(enemy)) //Si la simulation est termin�e, on ne retourne aucun Move possibles
			return possibleMoves;
		
		for(int row = 0; row < 3; row++) 
			for(int col = 0; col <3; col++)
				if(grid[row][col] == 0)
					possibleMoves.add(new int[] {row, col});
		
		return possibleMoves;
	}
	
	//Le score de toutes les 8 lignes est calcul� pour d�terminer le score de la meilleure simulation
	private int CalculateScore() {
		int score = 0;
		
		score += LineScore(0, 0, 0, 1, 0, 2);
		score += LineScore(1, 0, 1, 1, 1, 2);
		score += LineScore(2, 0, 2, 1, 2, 2);
		score += LineScore(0, 0, 1, 0, 2, 0);
		score += LineScore(0, 1, 1, 1, 2, 1);
		score += LineScore(0, 2, 1, 2, 2, 2);
		score += LineScore(0, 0, 1, 1, 2, 2);
		score += LineScore(0, 2, 1, 1, 2, 0);

		return score;
	}
	
	//Selon les arrangements de chaque ligne, on lui attrribue un score
	private int LineScore(int r1, int c1, int r2, int c2, int r3, int c3) {
		int score = 0;
		
		if(grid[r1][c1] == me) 
			score = 1;             //Si l'IA a la premi�re case
		else if(grid[r1][c1] == enemy) 
			score = -1;            //Si le joueur a la premi�re case
		
	    if (grid[r2][c2] == me) {           
	        if (score == 1)
	            score = 10;        //Si l'IA a les deux premi�res cases
	        else if (score == -1)
	            return 0;          //Si l'IA et le joueur ont chacun une case
	        else 
	            score = 1;         //Si l'IA a seulement la deuxi�me case
	    }
        else if (grid[r2][c2] == enemy) {
	        if (score == -1)
	            score = -10;       //Si le joueur a les deux premi�res cases
	        else if (score == 1)
	            return 0;          //Si l'IA et le joueur ont chacun une case
	        else 
	            score = -1;        //Si le joueur a seulement la deuxi�me case
	    }
	    
	    if (grid[r3][c3] == me) {
	        if (score > 0)
	            score *= 10;       //Si l'IA a la troisi�me case et au moins une des deux autres
	        else if (score < 0)
	            return 0;          //Si l'IA a la troisi�me case et le joueur en a au moins une autre
	        else 
	            score = 1;         //Si l'IA a seulement la troisi�me case
	    } 
	    else if (grid[r3][c3] == enemy) {
	        if (score < 0)
	            score *= 10;       //Si le joueur a la troisi�me case et au moins une des deux autres
	        else if (score > 1)
	            return 0;          //Si le joueur a la troisi�me case et l'IA en a au moins une autre
	        else 
	            score = -1;        //Si le joueur a seulement la troisi�me case
	    }
	    
	    return score;
	}
	
	//V�rification de victoire � la sauvage
	private boolean Ended(int player) {
		if((grid[0][0] == player && grid[0][1] == player && grid[0][2] == player)
			||	(grid[1][0] == player && grid[1][1] == player && grid[1][2] == player)
			||	(grid[2][0] == player && grid[2][1] == player && grid[2][2] == player)
			||	(grid[0][0] == player && grid[1][0] == player && grid[2][0] == player)
			||	(grid[0][1] == player && grid[1][1] == player && grid[2][1] == player)
			||	(grid[0][2] == player && grid[1][2] == player && grid[2][2] == player)
			||	(grid[0][0] == player && grid[1][1] == player && grid[2][2] == player)
			||	(grid[0][2] == player && grid[1][1] == player && grid[2][0] == player))
			return true;
		else
			return false;
	}
}
