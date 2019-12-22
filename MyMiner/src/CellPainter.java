import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

public class CellPainter extends BasePainter {
	protected FieldInfo fieldInfo;
	protected AppearanceObject appearance;
	public CellPainter(Graphics frameGraphics, FieldInfo fieldInfo) {
		super(frameGraphics);
		this.fieldInfo = fieldInfo;
		appearance = fieldInfo.getGameManager().getAppearance();
	}
	
public void draw() {
	super.draw();
	drawCells();
}

protected void drawCells() {
	CellInfo[][] cellsInfo = fieldInfo.getCellsInfo();
	for(int i = 0; i< fieldInfo.getK(); i++)
		for(int j = 0; j< fieldInfo.getM(); j++)
			drawCell(cellsInfo[i][j]);
}

protected void drawMine(CellInfo cellInfo) {
	super.fillBackground(cellInfo.getBounds(), appearance.getMineColor());
} 

protected void drawCellContent(CellInfo cellInfo) {
	if(cellInfo.getCellObjectState() == CellObjectState.Unknown) 
	{
		drawUnknownSymbol(cellInfo);
	}
	if(cellInfo.getCellObjectState() == CellObjectState.WithFlag) 
	{
		drawFlag(cellInfo);
	}
}

protected void drawUnknownSymbol(CellInfo cellInfo) {
	DrawText(cellInfo, "?", Color.black);
}

protected void drawFlag(CellInfo cellInfo) {
	int offset = 2;
	int circleOffset = 4;
	Rectangle r = cellInfo.getBounds();
	super.drawLine(r.x + r.width/2, r.y + offset, r.x + r.width/2, r.y + r.height - offset, Color.black, 2);
	super.drawLine(r.x + offset, r.y + r.height/2, r.x + r.width - offset, r.y + r.height/2, Color.black, 2);
	g.drawOval(r.x + circleOffset, r.y + circleOffset, r.width - circleOffset*2, r.height - circleOffset*2); 
}

protected void drawCell(CellInfo cellInfo) {
	if(cellInfo.getCellState() == CellState.Opened)
		super.fillBackground(cellInfo.getBounds(), appearance.getOpenedCellColor());
	if(cellInfo.getCellState() == CellState.Hidden)
	{
		super.fillBackground(cellInfo.getBounds(), appearance.getCellColor());
		drawCellContent(cellInfo);
	}
	if(cellInfo.getCellState() == CellState.Hovered)
	{
		super.fillBackground(cellInfo.getBounds(), appearance.getHoveredCellColor());
		drawCellContent(cellInfo);
	}
	//if(cellInfo.getIsMine()) //shows all mines; for test
	if(fieldInfo.getIsGameEnd() && cellInfo.getIsMine())
	{
		drawMine(cellInfo);
		drawCellContent(cellInfo);
	}
	int mineCount = cellInfo.getMineCount(); 
	if(cellInfo.getMineCount() != 0 && cellInfo.getCellState() == CellState.Opened)
		DrawText(cellInfo, Integer.toString(mineCount), getMineCountColor(mineCount));
} 

protected Color getMineCountColor(int mineCount) {
	if(mineCount == 1)
		return appearance.getOneMineColor();
	if(mineCount == 2)
		return appearance.getTwoMineColor();
	if(mineCount == 3)
		return appearance.getThreeMineColor();
	if(mineCount == 4)
		return appearance.getFourMineColor();
	if(mineCount == 5)
		return appearance.getFiveMineColor();
	if(mineCount == 6)
		return appearance.getSixMineColor();
	if(mineCount == 7)
		return appearance.getSevenMineColor();
	return Color.black;
}


protected void DrawText(CellInfo cellInfo, String text, Color color) {
	 Font font = new Font("Serif", Font.BOLD, (int)cellInfo.getBounds().getHeight());
	 drawCenteredString(g, text, cellInfo.getBounds(), font, color);
	 font = null;
} 

private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font, Color color) {
    FontMetrics metrics = g.getFontMetrics(font);
    g.setColor(color);
    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
    g.setFont(font);
    g.drawString(text, x, y);
}
}
