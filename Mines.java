import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

//Estive aqui e dei uma cagadinha

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

	public Mines() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);
		setTitle("Minesweeper");

		statusbar = new JLabel("");
		add(statusbar, BorderLayout.SOUTH);

		add(new Board(statusbar));

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
