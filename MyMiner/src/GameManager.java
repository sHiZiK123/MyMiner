import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;
import javax.swing.SwingUtilities;

public class GameManager {
private Graphics frameGraphics; 	
private GameField ownerControl;
private FieldPainter fieldPainter;
private FieldInfo fieldInfo;
private int mineCount;
private int userMineCount;
private HighlightHelper highlightHelper;
private Random rnd = new Random();
private GameLogicManager logicManager;
private AppearanceObject appearance;
private int k;
private int m;

public GameManager(GameField ownerControl, AppearanceObject appearance, int k, int m, int mineCount) {
	this.ownerControl = ownerControl;	
	this.mineCount = mineCount;
	userMineCount = mineCount;
	this.appearance = appearance;
	this.m = m;
	this.k = k;
	initializeManager();
	handleEvents();
}
public FieldInfo getFieldInfo() { return fieldInfo; } 
public GameField getOwnerControl() { return ownerControl; } 
public void setMineCount(int mineCount) { this.mineCount = mineCount; }
public int getMineCount() { return userMineCount; }
public AppearanceObject getAppearance() { return appearance; }

private void initializeManager() {
	createFieldInfo();
	createFieldPainter();
	createGameLogicManager();
	createHighlightedCellsHelper();
	generateMines();
} 

public void restartGame() {	
	fieldPainter = null;
	fieldInfo = null;
	highlightHelper = null;
	logicManager = null;
	userMineCount = mineCount;
	getOwnerControl().updateMinesCount();
	getOwnerControl().updateTime();
	initializeManager();
	refreshField();
}


protected void handleEvents() {
	getOwnerControl().addMouseMotionListener(new MouseMotionAdapter() {
		@Override
		public void mouseMoved(MouseEvent arg0) {
			highlightHoverCell();
		}
	});
	
	
	getOwnerControl().addMouseListener(new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			if(fieldInfo.getIsGameEnd())
				return;
				
			if(e.getButton() == MouseEvent.BUTTON1)
				cellPressed();
			if(e.getButton() == MouseEvent.BUTTON3)
				setFlag();
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			
		}
	});
}

private void refreshFieldCore() {
	Rectangle r = new Rectangle(0, 0, (int)getOwnerControl().getWidth(), (int)getOwnerControl().getHeight()); 
	fieldInfo.refreshInfo(r);
	fieldPainter.draw();
}

public void refreshField() {
	getOwnerControl().repaint();
	refreshFieldCore();
}

public void updateGraphics(Graphics g) {
	frameGraphics = g;
	fieldPainter.refreshGraphics(g);
	refreshFieldCore();
}

protected CellInfo getActiveCellInfo() {
	Point p = MouseInfo.getPointerInfo().getLocation();
	SwingUtilities.convertPointFromScreen(p, getOwnerControl());
	CellInfo info = fieldInfo.getCellByPoint(p);
	return info;
}

protected void setFlag() {
	CellInfo info = getActiveCellInfo();
	if(info!=null && info.getCellState() == CellState.Hovered)
		switch (info.getCellObjectState()) 
		{
	 		case Empty: userMineCount--;
	 		break;
	 		case WithFlag: userMineCount++;
	 		break;
	 		case Unknown: break;
		}
	getOwnerControl().updateMinesCount();
	logicManager.PerformCellRightClick(info);
}

protected void cellPressed() {
	CellInfo info = getActiveCellInfo();
	logicManager.PerformCellClick(info);
}

protected void highlightHoverCell() {
	CellInfo info = getActiveCellInfo();
	highlightHelper.HighlightCell(info);
	refreshField();
} 

protected void createGameLogicManager() {
	logicManager = new GameLogicManager(this, fieldInfo);
}

protected void createHighlightedCellsHelper() {
	highlightHelper = new HighlightHelper();
}

protected void createFieldInfo() {
	Rectangle r = new Rectangle(0, 0, (int)getOwnerControl().getWidth(), (int)getOwnerControl().getHeight()); 
	fieldInfo = new FieldInfo(r, this, k, m);
	fieldInfo.calcInfo();
}

protected void createFieldPainter() {
	fieldPainter = new FieldPainter(frameGraphics, fieldInfo);
}


private void generateMines() {
	CellInfo[][] cellsInfo = fieldInfo.getCellsInfo();
	int counter = 0;
	while(counter < mineCount) {
		int i = rnd.nextInt(fieldInfo.getK());
		int j = rnd.nextInt(fieldInfo.getM());
		if(!cellsInfo[i][j].getIsMine()) {
			cellsInfo[i][j].setIsMine(true);
			counter++;
		}
	}
}
}
