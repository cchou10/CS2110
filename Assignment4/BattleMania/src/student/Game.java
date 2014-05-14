package student;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.table.TableModel;

public class Game {
	
	private Random rng;
	
 	//the starting number of lives of the hero
 	private static final int NUMBER_LIVES = 5;
	
	private Hero hero;
	private Enemy selectedEnemy;
	
	private EnemyTableModel enemyTableModel;

	public Game(String fileName) throws InvalidGameException {
		rng = new Random(123456789);
		initializeGame(fileName);
	}
	
	/**
	 * Initializes the game using a given text file which lists the hero and
	 * enemies of the game. Throws an InvalidGameException if the given board
	 * file name is invalid
	 * @param gameFileName The file name that will initialize the game
	 * @throws InvalidGameException
	 * @throws  
	 */
	private void initializeGame(String gameFileName) throws InvalidGameException {
		List<Enemy> enemies = new ArrayList<Enemy>();
		String nxt;
		FileReader file = null;
		try {
			file = new FileReader(gameFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner console = new Scanner(file);
		while (console.hasNext()){
			nxt = console.next();
			String[] inputs = nxt.split(";");
			if (inputs.length != 7)
				return;
			String type = inputs[0];
			String name = inputs[1];
			int strength = Integer.parseInt(inputs[2]);
			int intelligence = Integer.parseInt(inputs[3]);
			int agility = Integer.parseInt(inputs[4]);
			String taunt = inputs[5];
			String imgName = inputs[6];
			if (type.equals("Hero")) {
				hero = new Hero(name, strength, intelligence, agility, taunt, imgName, NUMBER_LIVES);
			}
			if (type.equals("Enemy")) {
				enemies.add(new Enemy(name, strength, intelligence, agility, taunt, imgName));
			}
		}
		enemyTableModel = new EnemyTableModel(enemies, hero);			
	}
	
	/**
	 * Gets the table model associated with the enemies
	 * @return The TableModel object that holds the enemies to be displayed in
	 * a GUI's JTable
	 */
	public TableModel getEnemyTableModel() {
		return enemyTableModel;
	}
	
	/**
	 * Finds the selected Enemy object based on an index that was selected
	 * within the JTable
	 * @param index The index of the row selected within the JTable
	 */
	public void setSelectedEnemy(int index) {
		selectedEnemy = enemyTableModel.getValueAt(index);

	}
	
	/**
	 * Battles the hero with the selected enemy. If the hero wins, the selected
	 * enemy is removed from the table model, the hero's number of victories
	 * is incremented and received the number of attributes for beating the
	 * selected enemy, the enemies are resorted in the table model, and the
	 * selected enemy is removed. If the enemy wins, the hero loses a life.
	 * @return Battle.HERO_WON if the hero wins, Battle.ENEMY_WON if the
	 * enemy wins, or -1 if there is no enemy to battle
	 */
	public int battleCharacters() {
		if (selectedEnemy == null)
			return -1;
		Battle battle = new Battle(hero, selectedEnemy);
		if (battle.getOutcome() == 1) {
			hero.decrementLives();
			return Battle.ENEMY_WON;
		}
		if (battle.getOutcome() == 0) {
			hero.incrementVictories();
			enemyTableModel.removeEnemy(selectedEnemy);
			Sorter.heapSort(enemyTableModel.getBattleList());
			addAttributes(battle.getPointsHeroWin());
			return Battle.HERO_WON;
			
		}
		return -1;
	}
	

	/**
	 * Adds <i>pointAwarded</i> points that are randomly allocated to the hero's
	 * attributes
	 * @param pointsAwarded The amount of points allocated to the hero
	 */
	private void addAttributes(int pointsAwarded) {
		for (int i = 0; i < pointsAwarded; i++) {
			int numSelected = rng.nextInt(3);
			switch (numSelected) {
				case 0:
					hero.incrementStrength();
					break;
				case 1:
					hero.incrementAgility();
					break;
				case 2:
					hero.incrementIntelligence();
					break;
				default:
					break;
			}
		}
	}
	
	/**
	 * @return True if the hero has no more lives left to play, false otherwise
	 */
	public boolean heroLostGame() {
		if (hero.getNumberLives() > 0)
			return true;
		else
			return false;
	}

	/**
	 * @return True if the hero beat all the enemies, false otherwise
	 */
	public boolean heroWonGame() {
		if (!heroLostGame() && enemyTableModel.getNumberOfEnemies() == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * @return True if there is an enemy that is selected, false otherwise
	 */
	public boolean isEnemySelected() {
		if (selectedEnemy != null)
			return true;
		else
			return false;
	}
	
	/**
	 * @return The name of the hero
	 */
	public String getHeroName() {
		return hero.getName();
	}
	
	/**
	 * @return The ImageIcon representing an image for the hero that will be
	 * displayed in the GUI
	 */
	public ImageIcon getHeroIcon() {
		ImageIcon pic = hero.getIcon();
		return pic;
	}
	
	/**
	 * @return The hero's strength ranking
	 */
	public int getHeroStrength() {
		return hero.getStrength();
	}
	
	/**
	 * @return The hero's agility ranking
	 */
	public int getHeroAgility() {
		return hero.getAgility();
	}
	
	/**
	 * @return The hero's intelligence ranking
	 */
	public int getHeroIntelligence() {
		return hero.getIntelligence();
	}
	
	/**
	 * @return The taunt that will be displayed in a window if the hero wins
	 */
	public String getHeroTaunt() {
		return hero.getTaunt();
	}
	
	/**
	 * @return The name of the selected enemy. Returns null if there is no
	 * selected enemy.
	 */
	public String getEnemyName() {
		if (!isEnemySelected())
			return null;
		else
			return selectedEnemy.getName();
	}
	
	/**
	 * @return The ImageIcon representing an image for the selected enemy that 
	 * will be displayed in the GUI
	 */
	public ImageIcon getEnemyIcon() {
		if (!isEnemySelected())
			return null;
		else
			return selectedEnemy.getIcon();
	}
	
	/**
	 * @return The selected enemy's strength ranking. Returns 0 if there is no
	 * selected enemy.
	 */
	public int getEnemyStrength() {
		if (!isEnemySelected())
			return 0;
		else
			return selectedEnemy.getStrength();
	}
	
	/**
	 * @return The selected enemy's agility ranking. Returns 0 if there is no
	 * selected enemy.
	 */
	public int getEnemyAgility() {
		if (!isEnemySelected())
			return 0;
		else
			return selectedEnemy.getAgility();
	}
	
	/**
	 * @return The selected enemy's intelligence ranking. Returns 0 if there is
	 * no selected enemy.
	 */
	public int getEnemyIntelligence() {
		if (!isEnemySelected())
			return 0;
		else
			return selectedEnemy.getIntelligence();
	}
	
	/**
	 * @return The taunt that will be displayed in a window if the selected
	 * enemy wins. Returns null if there is no selected enemy.
	 */
	public String getEnemyTaunt() {
		if (!isEnemySelected())
			return null;
		else
			return selectedEnemy.getTaunt();
	}
	
	/**
	 * @return The number of victories associated with the hero in this game.
	 */
	public int getNumberOfVictories() {
		return hero.getNumberOfVictories();
	}
	
	/**
	 * @return The number of victories associated with the hero in this game.
	 */
	public int getNumberOfLives() {
		return hero.getNumberLives();
	}
}
