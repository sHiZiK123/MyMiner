import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class MinerComponent extends JComponent {
	private JPanel leftPanel;
	private JPanel rightdPanel;
	private JPanel middlePanel;
	private JLabel timeLabel;
	private JLabel timeValueLabel;
	private JLabel mineCountTextLabel;
	private JLabel mineCountLabel;
	private GameField fieldPanel;
	private Timer timer;
	private int time = 0;
	private int fontSize = 25;
	private int k;
	private int m;
	private int mineCount;
	
	protected AppearanceObject appearance;
	
	public MinerComponent(AppearanceObject appearance, int k, int m, int mineCount) {
		this.appearance = appearance;
		this.mineCount = mineCount;
		this.m = m;
		this.k = k;
		this.addAncestorListener(new AncestorListener ()
	    {
			@Override
	        public void ancestorAdded ( AncestorEvent event )
	        {
				updateMineCountText();
	        }
			
			@Override
	        public void ancestorRemoved ( AncestorEvent event )
	        {
				
	        }
			
			@Override
	        public void ancestorMoved ( AncestorEvent event )
	        {
	            
	        }
	    } );
		
		initialize();
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
		    @Override
		    public void run() {
		    	if(fieldPanel.getGameManager().getFieldInfo().getIsGameEnd())
		    		return;
		        time += 1;
		        timeValueLabel.setText(Integer.toString(time));
		    }

		}, 1000, 1000);
	}
	
	public AppearanceObject getAppereance() {
		return appearance;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setMineCount(int count) {
		fieldPanel.getGameManager().setMineCount(count);
	}
	
	public void updateMineCountText() {
		mineCountLabel.setText(Integer.toString(fieldPanel.getGameManager().getMineCount()));
	}
	
	public void resetTime() {
		time = 0;
		timeValueLabel.setText("0");
	}
	
	protected GameField createGameField() {
		return new GameField(appearance, k, m, mineCount);
	} 
	
	
	private void initialize() {
		this.setSize(300, 500);
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		GridBagConstraints c =  new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH; 
		c.fill   = GridBagConstraints.BOTH;  
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.gridx = 0; 
		c.gridy = 0; 
		c.weightx = 0.2;
		c.weighty = 0.2;
		leftPanel = new JPanel();
		gbl.setConstraints(leftPanel, c);
		leftPanel.setBackground(appearance.getPanelsColor());
		add(leftPanel);
		
		c =  new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH; 
		c.fill   = GridBagConstraints.BOTH;  
		c.gridheight = 1;
		c.gridwidth  = 3;
		c.gridx = 1; 
		c.gridy = 0; 
		c.weightx = 0.6;
		c.weighty = 0.6;
		fieldPanel = createGameField();
		gbl.setConstraints(fieldPanel, c);
		fieldPanel.setBackground(Color.RED);
		add(fieldPanel);
		
		c =  new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH; 
		c.fill   = GridBagConstraints.BOTH;  
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.gridx = 4; 
		c.gridy = 0; 
		c.weightx = 0.2;
		c.weighty = 0.2;
		rightdPanel = new JPanel();
		gbl.setConstraints(rightdPanel, c);
		rightdPanel.setBackground(appearance.getPanelsColor());
		add(rightdPanel);
		
		c.anchor = GridBagConstraints.NORTH; 
		c.fill   = GridBagConstraints.NONE;  
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.gridx = 0; 
		c.gridy = 1; 
		c.weightx = 0.5;
		c.weighty = 0.0;
		timeLabel = new JLabel("Time:", SwingConstants.CENTER);
		timeLabel.setFont(new Font(timeLabel.getName(), Font.BOLD, fontSize));
		gbl.setConstraints(timeLabel, c);
		add(timeLabel);
		
		c.anchor = GridBagConstraints.NORTH; 
		c.fill   = GridBagConstraints.NONE;  
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.gridx = 1; 
		c.gridy = 1; 
		c.weightx = 0.2;
		c.weighty = 0.0;
		timeValueLabel = new JLabel("0", SwingConstants.CENTER);
		timeValueLabel.setPreferredSize(new Dimension(40, 40));
		timeValueLabel.setFont(new Font(timeValueLabel.getName(), Font.PLAIN, fontSize));
		gbl.setConstraints(timeValueLabel, c);
		add(timeValueLabel);
		
		c.anchor = GridBagConstraints.NORTH; 
		c.fill   = GridBagConstraints.BOTH;  
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.gridx = 2; 
		c.gridy = 1; 
		c.weightx = 1.0;
		c.weighty = 0.0;
		middlePanel = new JPanel();
		gbl.setConstraints(middlePanel, c);
		middlePanel.setBackground(appearance.getPanelsColor());
		add(middlePanel);
		
		c.anchor = GridBagConstraints.NORTH; 
		c.fill   = GridBagConstraints.NONE;  
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.gridx = 3; 
		c.gridy = 1; 
		c.weightx = 0.2;
		c.weighty = 0.0;
		mineCountTextLabel = new JLabel("Mines:", SwingConstants.CENTER);
		mineCountTextLabel.setFont(new Font(mineCountTextLabel.getName(), Font.BOLD, fontSize));
		gbl.setConstraints(mineCountTextLabel, c);
		add(mineCountTextLabel);
		
		c.anchor = GridBagConstraints.NORTH; 
		c.fill   = GridBagConstraints.NONE;  
		c.gridheight = 1;
		c.gridwidth  = 1;
		c.gridx = 4; 
		c.gridy = 1; 
		c.weightx = 0.5;
		c.weighty = 0.0;
		mineCountLabel = new JLabel("", SwingConstants.CENTER);
		mineCountLabel.setPreferredSize(new Dimension(40, 40));
		mineCountLabel.setFont(new Font(mineCountLabel.getName(), Font.PLAIN, fontSize));
		gbl.setConstraints(mineCountLabel, c);
		add(mineCountLabel);
	}
}
