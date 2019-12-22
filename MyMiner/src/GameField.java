import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class GameField extends JPanel {
	private GameManager manager;
	protected AppearanceObject appearance;
	private int k;
	private int m;
	private int mineCount;
	
	public GameField(AppearanceObject appearance, int k, int m, int mineCount) {
		this.appearance = appearance;
		this.mineCount = mineCount;
		this.m = m;
		this.k = k;
		this.addAncestorListener(new AncestorListener ()
	    {
			@Override
	        public void ancestorAdded ( AncestorEvent event )
	        {
				ñreateGameManager();
	            // Component added somewhere
	        }
			
			@Override
	        public void ancestorRemoved ( AncestorEvent event )
	        {
	            // Component removed from container
	        }
			
			@Override
	        public void ancestorMoved ( AncestorEvent event )
	        {
	            // Component container moved
	        }
	    } );
	}
	
	public GameManager getGameManager() { return manager; }
	
	protected void ñreateGameManager() {
		manager = new GameManager(this, appearance, k, m, mineCount);
	}
	
	public void updateMinesCount() {
		((MinerComponent)this.getParent()).updateMineCountText();
	}
	
	public void updateTime() {
		((MinerComponent)this.getParent()).resetTime();
	} 
	
	@Override
	 public void paint(Graphics g) {
	    	super.paint(g);
	    	if(manager != null)
	    		manager.updateGraphics(g);
	    	
	 }
	
	
}
