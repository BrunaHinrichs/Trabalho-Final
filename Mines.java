import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Mines extends JFrame {

	private static int FRAME_WIDTH = 250;
	private static int FRAME_HEIGHT = 290;

	private final JLabel statusbar;

	public static void setFrameWidth(int width) {

		FRAME_WIDTH = width;

	}
	public static void setFrameHeight(int height) {

		FRAME_HEIGHT = height;

	}

	public static int getFrameWidth() {
	
		return FRAME_WIDTH;
	
	}

	public static int getFrameHeight() {
	
		return FRAME_HEIGHT;
	
	}

	public Mines() {

		Toolkit kit = Toolkit.getDefaultToolkit();
		Image icon = kit.getImage("13.png");
		setIconImage(icon);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);
		setTitle("Minesweeper");

		statusbar = new JLabel("");
		add(statusbar, BorderLayout.SOUTH);

		Board game = new Board(statusbar);

		add(game);

		game.newGame();

		setResizable(false);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				JFrame ex = new Mines();
				ex.setVisible(true);
			}
		});
	}
}
