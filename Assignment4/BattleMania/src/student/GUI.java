package student;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class GUI extends JFrame implements ActionListener {

	private JTable enemyTable;

	// Hero display items
	private JLabel heroNameLbl;
	private JLabel heroStrengthLbl;
	private JLabel heroAgilityLbl;
	private JLabel heroIntelligenceLbl;
	private JLabel heroImg;
	private JLabel numberOfVictoriesLbl;
	private JLabel livesLeftLbl;

	private JButton battleButton = new JButton("Battle");

	// Enemy display items
	private JLabel enemyNameLbl;
	private JLabel enemyStrengthLbl;
	private JLabel enemyAgilityLbl;
	private JLabel enemyIntelligenceLbl;
	private JLabel enemyImg;
	
	private static File[] selectedfiles;
	private Game game;

	public GUI() {
		heroNameLbl = new JLabel(game.getHeroName());
		heroStrengthLbl = new JLabel(Integer.toString(game.getHeroStrength()));
		heroAgilityLbl = new JLabel(Integer.toString(game.getHeroAgility()));
		heroIntelligenceLbl = new JLabel(Integer.toString(game.getHeroIntelligence()));
		ImageIcon image = game.getHeroIcon();
		heroImg = new JLabel(game.getHeroName(), image, JLabel.CENTER);
		
		enemyNameLbl = new JLabel(game.getEnemyName());
		enemyStrengthLbl = new JLabel(Integer.toString(game.getEnemyStrength()));
		enemyAgilityLbl = new JLabel(Integer.toString(game.getEnemyAgility()));
		enemyIntelligenceLbl = new JLabel(Integer.toString(game.getEnemyIntelligence()));
		ImageIcon image2 = game.getEnemyIcon();
		enemyImg = new JLabel(game.getEnemyName(), image2, JLabel.CENTER);
		
		enemyTable = new JTable(game.getEnemyTableModel());
		
		setTitle("Battle Mania!");
		setLayout(new GridLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		add(heroStrengthLbl);
		add(heroAgilityLbl);
		add(heroIntelligenceLbl);
		add(heroImg);
		add(battleButton);
		battleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.battleCharacters();
				new GUI();
			}
		});
		add(enemyStrengthLbl);
		add(enemyAgilityLbl);
		add(enemyIntelligenceLbl);
		add(enemyImg);	
		
		add(enemyTable);
		enemyTable.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				int[] selection = enemyTable.getSelectedRows();
				game.setSelectedEnemy(selection[0]);
				new GUI();					
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub				
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub			
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub	
			}
		});	
		add(numberOfVictoriesLbl);
		add(livesLeftLbl);
	}


	public static void main(String[] args) throws InvalidGameException {
		final JFrame frame = new JFrame("Open");
		final JFileChooser fc = new JFileChooser();

		int retVal = fc.showOpenDialog(frame);
		if (retVal == JFileChooser.APPROVE_OPTION) {
			selectedfiles = fc.getSelectedFiles();
			System.out.println(selectedfiles[1].getName());
			new Game(selectedfiles[1].getName());
		}
		new GUI();
	}
			
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}