import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

public class FieldPainter extends BasePainter {
protected FieldInfo fieldInfo;
protected CellPainter cellPainter;
protected AppearanceObject appearance;

public FieldPainter(Graphics frameGraphics, FieldInfo fieldInfo) {
	super(frameGraphics);
	this.fieldInfo = fieldInfo;
	appearance = fieldInfo.getGameManager().getAppearance();
	createCellPainter();
}

public void draw() {
	super.draw();
	drawBackground();
	cellPainter.drawCells();
	drawCellsBorder();
}

@Override
public void refreshGraphics(Graphics g) {
	super.refreshGraphics(g);
	cellPainter.refreshGraphics(g);
}

protected void createCellPainter() {
	cellPainter = new CellPainter(g, fieldInfo);
}

protected void drawBackground() {
	this.fillBackground(fieldInfo.getFieldBounds(), appearance.getFieldColor());
}

protected void drawCellsBorder() {
	Graphics2D g2 = (Graphics2D) g;
	int thickness = fieldInfo.getCellBorderWidth();
	Stroke oldStroke = g2.getStroke();
	g2.setStroke(new BasicStroke(thickness));
	CellInfo[][] cells = fieldInfo.getCellsInfo();
	for(int i = 0; i < fieldInfo.getK(); i++)
		for(int j = 0; j < fieldInfo.getM(); j++) {
			Rectangle r = cells[i][j].getBounds();
			g2.setColor(appearance.getfieldLineColor());
			g2.drawRect(r.x - thickness, r.y - thickness, r.height + thickness * 2, r.width + thickness * 2);
		}
	g2.setStroke(oldStroke);
}

}
