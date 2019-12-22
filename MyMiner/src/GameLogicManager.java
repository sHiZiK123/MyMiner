import javax.swing.JOptionPane;

public class GameLogicManager {
private FieldInfo fieldInfo;
private int tmpI; 
private int tmpJ;
private GameManager gameManager;

public GameLogicManager(GameManager gameManager, FieldInfo fieldInfo) {
	this.fieldInfo = fieldInfo;
	this.gameManager = gameManager; 
}

public void PerformCellRightClick(CellInfo info) {
	if(info == null || info.getCellState() == CellState.Opened) return;
	
    if(info.getCellState() == CellState.Hovered) 
    {
    	 switch (info.getCellObjectState()) 
    	 {
    	 	case Empty: info.setCellObjectState(CellObjectState.WithFlag);
    	 	break;
    	 	case WithFlag: info.setCellObjectState(CellObjectState.Unknown);
    	 	break;
    	 	case Unknown: info.setCellObjectState(CellObjectState.Empty);
    	 	break;
    	 }
    	
    	gameManager.refreshField();
    }
}

public void PerformCellClick(CellInfo info) {
    if(info == null || info.getCellState() == CellState.Opened || 
    		info.getCellObjectState() == CellObjectState.WithFlag || info.getCellObjectState() == CellObjectState.Unknown) 
    	return;
		
    if(info.getCellState() == CellState.Hovered) 
    	if(checkMine(info)) 
    		endGame(false);
		  else 
		  {
			info.setCellState(CellState.Opened);
			if(!isGameFinished())
				openNearbyCells(info);
			else
				endGame(true);
		  }
		 
}

private boolean isGameFinished() {
	CellInfo[][] cells = fieldInfo.getCellsInfo();
	for(int i = 0; i < fieldInfo.getK(); i++)
		for(int j = 0; j < fieldInfo.getM(); j++)
			if (cells[i][j].getCellState() == CellState.Hidden  
			  	&& !cells[i][j].getIsMine())
				return false;
	return true;
} 

protected boolean checkMine(CellInfo info) {
	return info.getIsMine();
}

protected void openNearbyCells(CellInfo info) {
	findCellIndex(info);
	info.setMineCount(adjacentMines(tmpI, tmpJ));
	FFuncover(tmpI, tmpJ, new boolean[fieldInfo.getK()][fieldInfo.getM()]);
	gameManager.refreshField();
}

private void findCellIndex(CellInfo info) {
	CellInfo[][] arr = fieldInfo.getCellsInfo();
	for (int i = 0; i < arr.length; i++) {
	    for (int j = 0; j < arr[i].length; j++) {
	        if (arr[i][j] == info) {
	            tmpI = i; tmpJ = j;
	            return;
	        }
	    }
	}
}

public void FFuncover(int i, int j, boolean[][] visited) {
    if (i >= 0 && j >= 0 && i < fieldInfo.getK() && j < fieldInfo.getM()) {
        if (visited[i][j])
            return;
        
        if(fieldInfo.getCellsInfo()[i][j].getCellObjectState() == CellObjectState.Empty)
        {
        	fieldInfo.getCellsInfo()[i][j].setMineCount(adjacentMines(i, j));
        	fieldInfo.getCellsInfo()[i][j].setCellState(CellState.Opened);
        }

        if (adjacentMines(i, j) > 0)
            return;

        visited[i][j] = true;

        FFuncover(i-1, j, visited);
        FFuncover(i+1, j, visited);
        FFuncover(i, j-1, visited);
        FFuncover(i, j+1, visited);
    }
}

private boolean mineAt(int i, int j) {
	if (i >= 0 && j >= 0 && i < fieldInfo.getK() && j < fieldInfo.getM()) 
		return fieldInfo.getCellsInfo()[i][j].getIsMine();
	return false;
}

protected int adjacentMines(int i, int j) {
    int count = 0;
    if (mineAt(i,   j+1)) ++count; // top
    if (mineAt(i+1, j+1)) ++count; // top-right
    if (mineAt(i+1, j  )) ++count; // right
    if (mineAt(i+1, j-1)) ++count; // bottom-right
    if (mineAt(i,   j-1)) ++count; // bottom
    if (mineAt(i-1, j-1)) ++count; // bottom-left
    if (mineAt(i-1, j  )) ++count; // left
    if (mineAt(i-1, j+1)) ++count; // top-left

    return count;
}

protected void endGame(boolean isPlayerWin) {
	fieldInfo.setIsGameEnd(true);
	gameManager.refreshField();
	
	int result = -1;
	if(!isPlayerWin)
		result = JOptionPane.showConfirmDialog(gameManager.getOwnerControl(), "You lose. Restart?", null, 0);
	else
	{
		int time = ((MinerComponent)fieldInfo.getGameManager().getOwnerControl().getParent()).getTime();
		result = JOptionPane.showConfirmDialog(gameManager.getOwnerControl(), "You win! Time: " + time + "s. Restart?", null, 0);
	}
	
	if(result == 0) 
		gameManager.restartGame();
	else
		Runtime.getRuntime().exit(0);

}
}
