package GameState;


public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	public static final int NUMGAMESTATES = 21;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
    public static final int LEVEL2STATE = 2;
	public static final int LEVEL3STATE = 3;
	public static final int LEVEL4STATE = 4;
	public static final int LEVEL5STATE = 5;
    public static final int WINNINGSTATE = 6;
    public static final int GAMEOVERSTATE = 20;


    public GameStateManager() {
		
		gameStates = new GameState[NUMGAMESTATES];
		
		currentState = MENUSTATE;
		loadState(currentState);
		
	}
	
	private void loadState(int state) {
        if(state == MENUSTATE)
            gameStates[state] = new MenuState(this);
        if(state == LEVEL1STATE)
            gameStates[state] = new Level1State(this);
        if(state == LEVEL2STATE)
            gameStates[state] = new Level2State(this);
		if(state == LEVEL3STATE)
			gameStates[state] = new Level3State(this);
		if(state == LEVEL4STATE)
			gameStates[state] = new Level4State(this);
		if(state == LEVEL5STATE)
			gameStates[state] = new Level5State(this);
        if (state == WINNINGSTATE)
            gameStates[state] = new WinningState(this);
        if(state == GAMEOVERSTATE)
            gameStates[state] = new GameOverState(this);

    }
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		gameStates[currentState].init();
	}

	
	public void update() {
		try {
			gameStates[currentState].update();
		} catch(Exception e) {}
	}
	
	public void draw(java.awt.Graphics2D g) {
		try {
			gameStates[currentState].draw(g);
		} catch(Exception e) {}
	}
	
	public void keyPressed(int k) throws NullPointerException {
		gameStates[currentState].keyPressed(k);
	}
	
	public void keyReleased(int k) throws NullPointerException {
		gameStates[currentState].keyReleased(k);
	}
	
}









