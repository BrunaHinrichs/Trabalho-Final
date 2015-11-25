/**
    Importações devido ao uso de eventos.
*/
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Random;

/**
    Importações devido ao uso das interfaces.
*/
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JOptionPane;

/**
    Importações devido ao uso de arquivo.
*/
import java.io.*;
import sun.audio.*;
import java.applet.*;
import java.net.*;

public class Board extends JPanel implements ActionListener{
	
	private final File games = new File("Games.dat");
	
	private final int NUM_IMAGES = 13;
	private final int CELL_SIZE = 15;

	private final int COVER_FOR_CELL = 10;
	private final int MARK_FOR_CELL = 10;
	private final int EMPTY_CELL = 0;
	private final int MINE_CELL = 9;
	private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
	private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

	private final int DRAW_MINE = 9;
	private final int DRAW_COVER = 10;
	private final int DRAW_MARK = 11;
	private final int DRAW_WRONG_MARK = 12;

	//	Deixara de ser final ou sera inicializado posteriormente.
	//	Novos valores conforme o nivel de dificuldade:
	// 		Iniciante:		9x9;	10 minas
	// 		Intermediario:	16x16;	40 minas
	// 		Expert:			30x16;	99 minas
	private int N_MINES = 40;
	private int N_ROWS = 16;
	private int N_COLS = 16;
	
	// E necessario um timer para atualizar o relogio em intervalos
	// regulares de tempo.
	private final int DELAY = 25;
	private Timer timer;
	private long beginTime=0;
	private long time;

	private String nickname;

	private int[] field;
	private boolean inGame;
	private int mines_left;
	private Image[] img;

	private int all_cells;
	private JLabel statusbar;
	    
	//ArrayList de jogadores para definir o ranking
	ArrayList<Player> ranking = new ArrayList<Player>();
      
	public Board(JLabel statusbar) {

		this.statusbar = statusbar;

		img = new Image[NUM_IMAGES];

		for (int i = 0; i < NUM_IMAGES; i++) {
			img[i] = (new ImageIcon(i + ".png")).getImage();
		}

		setBackground(Color.BLACK);
		setDoubleBuffered(true);

		timer = new Timer(DELAY, this);
		timer.start();

		addMouseListener(new MinesAdapter());
		
		//		inGame=true;
	}

	// atualiza o cronometro acada intervalo de tempo
	public void actionPerformed(ActionEvent e) {
		setBackground(Color.BLACK);


		if(inGame){
			if(beginTime==0)
				beginTime=System.currentTimeMillis();

			time=System.currentTimeMillis()-beginTime;
			statusbar.setText(Integer.toString(mines_left) + " - " + (time/1000.0) + "s");
		}

	}

	public void newGame() {

		// Antes de qualquer inicializacao, devemos obter o nome do
		// jogador e o nivel de dificuldade.

		nickname = JOptionPane.showInputDialog(this,
				"Nickname: ",
				"Minesweeper",
				JOptionPane.PLAIN_MESSAGE);
		if(nickname==null){

			System.exit(0);

		}

		Object[] levels = {"Beginner", "Intermediate", "Expert"};

		String level = String.valueOf(JOptionPane.showInputDialog(this,
					"Level: ",
					"Minesweeper",
					JOptionPane.PLAIN_MESSAGE,
					null,
					levels,
					levels[0]));

		if (level.equals("Beginner")) {

			N_MINES = 10;
			N_ROWS = 9;
			N_COLS = 9;
			Mines.setFrameWidth(140);
			Mines.setFrameHeight(192);

		} else if (level.equals("Intermediate")) {

			N_MINES = 40;
			N_ROWS = 16;
			N_COLS = 16;
			Mines.setFrameWidth(245);
			Mines.setFrameHeight(300);

		} else if (level.equals("Expert")) {

			N_MINES = 99;
			N_ROWS = 16;
			N_COLS = 30;
			Mines.setFrameWidth(455);
			Mines.setFrameHeight(300);

		} else {

			System.exit(0);

		}
		
		this.readGames(games);
		
		if(getTopLevelAncestor()!=null)
			getTopLevelAncestor().setSize(Mines.getFrameWidth(), Mines.getFrameHeight());

		Random random;
		int current_col;

		int i = 0;
		int position = 0;
		int cell = 0;

		random = new Random();
		mines_left = N_MINES;

		all_cells = N_ROWS * N_COLS;
		field = new int[all_cells];

		for (i = 0; i < all_cells; i++)
			field[i] = COVER_FOR_CELL;

		statusbar.setText(Integer.toString(mines_left) + " - " + (time/1000.0) + "s");

		i = 0;
		while (i < N_MINES) {

			position = (int) (all_cells * random.nextDouble());

			if ((position < all_cells) && (field[position] != COVERED_MINE_CELL)) {
				current_col = position % N_COLS;
				field[position] = COVERED_MINE_CELL;
				i++;

				if (current_col > 0) {
					cell = position - 1 - N_COLS;
					if (cell >= 0)
						if (field[cell] != COVERED_MINE_CELL)
							field[cell] += 1;
					cell = position - 1;
					if (cell >= 0)
						if (field[cell] != COVERED_MINE_CELL)
							field[cell] += 1;

					cell = position + N_COLS - 1;
					if (cell < all_cells)
						if (field[cell] != COVERED_MINE_CELL)
							field[cell] += 1;
				}

				cell = position - N_COLS;
				if (cell >= 0)
					if (field[cell] != COVERED_MINE_CELL)
						field[cell] += 1;
				cell = position + N_COLS;
				if (cell < all_cells)
					if (field[cell] != COVERED_MINE_CELL)
						field[cell] += 1;

				if (current_col < (N_COLS - 1)) {
					cell = position - N_COLS + 1;
					if (cell >= 0)
						if (field[cell] != COVERED_MINE_CELL)
							field[cell] += 1;
					cell = position + N_COLS + 1;
					if (cell < all_cells)
						if (field[cell] != COVERED_MINE_CELL)
							field[cell] += 1;
					cell = position + 1;
					if (cell < all_cells)
						if (field[cell] != COVERED_MINE_CELL)
							field[cell] += 1;
				}
			}
		}

		inGame = true;

	}


	public void find_empty_cells(int j) {

		int current_col = j % N_COLS;
		int cell;

		if (current_col > 0) { 
			cell = j - N_COLS - 1;
			if (cell >= 0)
				if (field[cell] > MINE_CELL) {
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL)
						find_empty_cells(cell);
				}

			cell = j - 1;
			if (cell >= 0)
				if (field[cell] > MINE_CELL) {
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL)
						find_empty_cells(cell);
				}

			cell = j + N_COLS - 1;
			if (cell < all_cells)
				if (field[cell] > MINE_CELL) {
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL)
						find_empty_cells(cell);
				}
		}

		cell = j - N_COLS;
		if (cell >= 0)
			if (field[cell] > MINE_CELL) {
				field[cell] -= COVER_FOR_CELL;
				if (field[cell] == EMPTY_CELL)
					find_empty_cells(cell);
			}

		cell = j + N_COLS;
		if (cell < all_cells)
			if (field[cell] > MINE_CELL) {
				field[cell] -= COVER_FOR_CELL;
				if (field[cell] == EMPTY_CELL)
					find_empty_cells(cell);
			}

		if (current_col < (N_COLS - 1)) {
			cell = j - N_COLS + 1;
			if (cell >= 0)
				if (field[cell] > MINE_CELL) {
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL)
						find_empty_cells(cell);
				}

			cell = j + N_COLS + 1;
			if (cell < all_cells)
				if (field[cell] > MINE_CELL) {
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL)
						find_empty_cells(cell);
				}

			cell = j + 1;
			if (cell < all_cells)
				if (field[cell] > MINE_CELL) {
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL)
						find_empty_cells(cell);
				}
		}
	}

	@Override
	public void paintComponent(Graphics g) {

		// Nesta funcao podemos usar statusbar.setText() para
		// escrever o tempo corrido de jogo a cada atualizacao.
		// Como tambem e aqui onde o programa detecta se o jogo
		// terminou, essa funcao tambem ficaria responsavel por
		// chamar a funcao que ira colher os dados do final da 
		// partida e escrever no .dat

		int cell = 0;
		int uncover = 0;

		for (int i = 0; i < N_ROWS; i++) {
			for (int j = 0; j < N_COLS; j++) {

				cell = field[(i * N_COLS) + j];

				if (inGame && cell == MINE_CELL)
					inGame = false;

				if (!inGame) {
					if (cell == COVERED_MINE_CELL) {
						cell = DRAW_MINE;
					} else if (cell == MARKED_MINE_CELL) {
						cell = DRAW_MARK;
					} else if (cell > COVERED_MINE_CELL) {
						cell = DRAW_WRONG_MARK;
					} else if (cell > MINE_CELL) {
						cell = DRAW_COVER;
					}


				} else {
					if (cell > COVERED_MINE_CELL)
						cell = DRAW_MARK;
					else if (cell > MINE_CELL) {
						cell = DRAW_COVER;
						uncover++;
					}
				}

				g.drawImage(img[cell], (j * CELL_SIZE),
					(i * CELL_SIZE), this);
			}
		}

		if (uncover == 0 && inGame) {
			inGame = false;
			statusbar.setText("Game won: " + (time/1000.0) + "s");
			addInArrayList(ranking,true);
			savingGame(ranking,games);
			/*			
					JOptionPane.showMessageDialog(
					this,
					"Congratulations, " + nickname + "! You won.\n" +
					"Time:\t\t"+ (time/1000.0) + "s",
					"You won",
					JOptionPane.PLAIN_MESSAGE);*/

		} else if (!inGame){
			soundLost();
			statusbar.setText("Game lost: " + (time/1000.0) + "s");
			addInArrayList(ranking,false);
			savingGame(ranking,games);
		}

	}


	class MinesAdapter extends MouseAdapter {
		
		@Override
		public void mousePressed(MouseEvent e) {

			if (!inGame) {

				newGame();
				inGame = true;
				// Coleta o tempo inicial do jogo
				beginTime = System.currentTimeMillis();
				repaint();
				return;

			}

			int x = e.getX();
			int y = e.getY();

			int cCol = x / CELL_SIZE;
			int cRow = y / CELL_SIZE;

			boolean rep = false;


			if ((x < N_COLS * CELL_SIZE) && (y < N_ROWS * CELL_SIZE)) {

				if (e.getButton() == MouseEvent.BUTTON3) {

					if (field[(cRow * N_COLS) + cCol] > MINE_CELL) {
						rep = true;

						if (field[(cRow * N_COLS) + cCol] <= COVERED_MINE_CELL) {
							if (mines_left > 0) {
								field[(cRow * N_COLS) + cCol] += MARK_FOR_CELL;
								mines_left--;
								statusbar.setText(Integer.toString(mines_left) + " - " + (time/1000.0) + "s");
							} else
								statusbar.setText("No marks left");
						} else {

							field[(cRow * N_COLS) + cCol] -= MARK_FOR_CELL;
							mines_left++;
							statusbar.setText(Integer.toString(mines_left) + " - " + (time/1000.0) + "s");
						}
					}

				} else {

					if (field[(cRow * N_COLS) + cCol] > COVERED_MINE_CELL) {
						return;
					}

					if ((field[(cRow * N_COLS) + cCol] > MINE_CELL) &&
						(field[(cRow * N_COLS) + cCol] < MARKED_MINE_CELL)) {

						field[(cRow * N_COLS) + cCol] -= COVER_FOR_CELL;
						rep = true;

						if (field[(cRow * N_COLS) + cCol] == MINE_CELL)
							inGame = false;
						if (field[(cRow * N_COLS) + cCol] == EMPTY_CELL)
							find_empty_cells((cRow * N_COLS) + cCol);
					}
				}

				if (rep)
					repaint();

			}
		}
	}
	
	public void savingGame(ArrayList<Player> list, File arq){
		try{
			FileOutputStream fout = new FileOutputStream(arq);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			
			//Gravando a lista de jogadores dentro do arquivo.
			oos.writeObject(list);
			oos.flush();
			oos.close();
			fout.close();
			System.out.println("Game Saved");
		}catch(Exception ex){
			System.out.println("Game no Saved");
		}
	}
	
	public ArrayList<Player> readGames(File arq){
		ArrayList<Player> list = null;
		try{
			FileInputStream fin = new FileInputStream(arq);
			ObjectInputStream oin = new ObjectInputStream(fin);
			
			//Lendo o arquivos dos jogos passados.
			list = (ArrayList<Player>) oin.readObject();
			oin.close();
			fin.close();
			
			for(Player n : list)
				System.out.println(n.toString());
		}catch(Exception ex){
			System.out.println("Não rolou, mas pode rolar.");
		}
		
		return list;
	}
	
	public void addInArrayList(ArrayList<Player> list, boolean win){
	    if(N_MINES == 10)
		list.add(new Player(time,nickname,"Beginner",N_MINES - mines_left,win));
	    
	    if(N_MINES == 40)
		list.add(new Player(time,nickname,"Intermediate",N_MINES - mines_left,win));
	    if(N_MINES == 99)
		list.add(new Player(time,nickname,"Expert",N_MINES - mines_left,win));
	}
	
	public void soundLost(){
		try {
			AudioClip clip = Applet.newAudioClip(new File("errow.wav").toURL());
			clip.play();
		} catch (MalformedURLException murle) {
			System.out.println(murle.toString());
		}
	}
	
	public void soundWin(){
		try{
			AudioClip clip = Applet.newAudioClip(new File("aplausos.wav").toURL());
			clip.play();
		}catch(MalformedURLException murle){
			System.out.println(murle.toString());
		}
	
	}

}
