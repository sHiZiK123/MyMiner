import java.awt.Rectangle;

public class CellInfo {
private Rectangle cellBounds;
private CellState cellState;
private int mineCount = 0;
private CellObjectState cellObjectState;
private boolean isMine;

public CellInfo() {
	cellState = CellState.Hidden;
	isMine = false;
	cellObjectState = CellObjectState.Empty;
}

public void setBounds(Rectangle cellBounds) {
	this.cellBounds = cellBounds;
}

public int getMineCount() { return mineCount; }
public void setMineCount(int mineCount) { this.mineCount = mineCount; }

public CellObjectState getCellObjectState() { return cellObjectState; }
public void setCellObjectState(CellObjectState cellObjectState) { this.cellObjectState = cellObjectState; }

public CellState getCellState() { return cellState; }
public void setCellState(CellState state) { cellState = state; }

public boolean getIsMine() { return isMine; }
public void setIsMine(boolean isMine) { this.isMine = isMine; }

public Rectangle getBounds() { return cellBounds; }
}

enum CellState{ Hidden, Opened, Hovered }
enum CellObjectState { Empty, Unknown, WithFlag }
 