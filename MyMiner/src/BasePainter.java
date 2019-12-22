import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

public class BasePainter {
protected Graphics g;
	
public BasePainter(Graphics frameGraphics) {
	this.g = frameGraphics;
}

public void refreshGraphics(Graphics g) {
	this.g = g;
}

public void draw() { 
	if(g == null) {
		throw new NullPointerException("Graphics is null");
	}
	
}

protected void fillBackground(Rectangle r, Color color) {	
	 Graphics2D g2 = (Graphics2D) g;
     g2.setColor(color);
     g2.fill(r);
}

protected void drawLine(int x1, int y1, int x2, int y2, Color color, int lineWidth) {
	g.setColor(color);
	Graphics2D g2 = (Graphics2D) g;
    g2.setStroke(new BasicStroke(lineWidth));
    g2.draw(new Line2D.Float(x1, y1, x2, y2));
}
}
