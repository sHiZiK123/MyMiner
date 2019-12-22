import java.awt.Point;
import java.awt.Rectangle;

public class FieldInfo {
private int k = 9;
private int m = 9;
private int cellSize;
private CellInfo[][] cells;
private Rectangle fieldBounds;
private int cellBorderWidth = 2;
private boolean isGameEnd;
private GameManager manager;

public FieldInfo(Rectangle fieldSize, GameManager manager, int k, int m) {
	this.manager = manager;
	this.k = k;
	this.m = m;
	cells = new CellInfo[getK()][getM()];
	this.fieldBounds = fieldSize;
	isGameEnd = false;
}

public int getK() { return k; }
public void setK(int k) { this.k = k; }
public int getM() { return m; }
public void setM(int m) { this.m = m;}
public boolean getIsGameEnd() { return isGameEnd; }
public void setIsGameEnd(boolean isGameEnd) {this.isGameEnd = isGameEnd;}
public int getCellBorderWidth() { return cellBorderWidth; }

public GameManager getGameManager() { return manager; }

public CellInfo[][] getCellsInfo() {
	return cells;
}

public Rectangle getFieldBounds() {
	return fieldBounds;
}

private void calcCellSize() {
	cellSize = fieldBounds.width/getK();
}


public void calcInfo() {
	calcCellSize();
	for(int i = 0; i < getK(); i++)
		for(int j = 0; j < getM(); j++)
			createCellInfo(i, j);
}

public void refreshInfo(Rectangle r) {
	if(r.width == fieldBounds.width && r.height == fieldBounds.height)
		return;
	fieldBounds = r;
	calcCellSize();
	for(int i = 0; i < getK(); i++)
		for(int j = 0; j < getM(); j++)
			cells[i][j].setBounds(calcCellBounds(i, j));
}

public CellInfo getCellByPoint(Point p) {
	for(int i = 0; i < getK(); i++)
		for(int j = 0; j < getM(); j++)
			if(cells[i][j].getBounds().contains(p))
				return cells[i][j];
	return null;
}

private void createCellInfo(int i, int j) {
	CellInfo cellInfo = new CellInfo();
	Rectangle  r = calcCellBounds(i, j);
	cellInfo.setBounds(r);
	cells[i][j] = cellInfo;
}

protected Rectangle calcCellBounds(int i, int j) {
	Rectangle  r = new Rectangle(fieldBounds.x + i * cellSize + getCellBorderWidth(), fieldBounds.y + j * cellSize + getCellBorderWidth(), cellSize - getCellBorderWidth() * 2, cellSize - getCellBorderWidth() * 2);
	return r;
}
}
