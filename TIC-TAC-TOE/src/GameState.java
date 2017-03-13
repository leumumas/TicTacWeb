
public enum GameState {
	ENDED(0), PLAYING(1), CLIENT_TURN(2), AI_TURN(3);
    
    private final int value;
    private GameState(int value) {
        this.value = value;
    }
    
    public String ToString() {
        switch(this.value){
            case 0: return "ENDED";
            case 1: return "PLAYING";
            case 2: return "X_TURN";
            case 3: return "O_TURN";
            default: return "";
        }
    }
    
    public int getValue() {
        return value;
    }
}
