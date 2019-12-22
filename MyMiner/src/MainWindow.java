import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;

public class MainWindow {
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}


	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 703, 460);
		int k = 9;
		int m = 9;
		int mineCount = 10;
		MinerComponent minerComponent = new MinerComponent(setAppearance(), k, m, mineCount);
		frame.getContentPane().add(minerComponent, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	AppearanceObject setAppearance() {
		AppearanceObject appearance = new AppearanceObject();
		//Uncomment to change appearance
		//appearance.setCellColor(Color.blue);
		//appearance.setHoveredCellColor(Color.yellow);
		//appearance.setPanelsColor(Color.DARK_GRAY);
		appearance.setfieldLineColor(Color.GRAY);
		return appearance;
		
	}
}
