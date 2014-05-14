package student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;	

import javax.swing.table.AbstractTableModel;

public class EnemyTableModel extends AbstractTableModel{
		
	private Hero hero;
	
	private List<Enemy> enemies;
	private List<Battle> battles;
	
	public EnemyTableModel(List<Enemy> enemies, Hero hero) {
		this.enemies = enemies;
		this.hero = hero;
	}
	/**
	 * Returns the name of the column to be displayed on the JTable given the 
	 * column index
	 * @param column The column index whose name should be returned
	 * @return The name of the column associated with the given index
	 */
	public String getColumnName(int column) {
		switch (column) {
		case 1: return "Name";
		case 2: return "Strength";
		case 3: return "Agility";
		case 4: return "Intelligence";
		case 5: return "Likelihood of Winning";
		case 6: return "Points Awarded";
		}
		return null;
	}
	
	/**
	 * Returns the Enemy object associated with the given index
	 * @param rowIndex The index which the user selected
	 * @return The Enemy object contained the given row. If rowIndex is an
	 * invalid index for the enemies list, return null.
	 */
	public Enemy getValueAt(int rowIndex) {
		if (rowIndex > this.getRowCount() || rowIndex < 0)
			return null;
		else {
			Enemy value = this.getValueAt(rowIndex);
			return value;
		}
	}

	/**
	 * @return The number of enemies that are contained within this table model
	 */
	public int getNumberOfEnemies() {
		return this.getRowCount() - 1;
	}
	
	/**
	 * Returns the number of enemies contained within this table model
	 */
	@Override
	public int getRowCount() {
		return this.getRowCount();
	}

	/**
	 * Returns the number of columns in this table model
	 */
	@Override
	public int getColumnCount() {
		return this.getColumnCount();
	}
	
	/**
	 * Returns the value to be displayed in a JTable given a row index and
	 * column index in the table
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex > this.getRowCount() || columnIndex > this.getColumnCount() || columnIndex < 0 || rowIndex < 0)
			return null;
		else 
			return this.getValueAt(rowIndex, columnIndex);
	}
	
	/**
	 * Removes a given enemy, which is called after the Hero defeats the enemy
	 * @param enemy The enemy that was defeated and that will be removed from
	 * the list of available enemies
	 */
	public void removeEnemy(Enemy enemy) {
		for (int i = 0; i <= enemies.size(); i++) {
			if (enemies.get(i).equals(enemy)) 
				enemies.remove(i);
		}
	}
	
	/**
	 * Sorts the enemies in descending order based on how likely they will beat
	 * the hero. This method should call <i>fireTableDataChanged</i> to
	 * indicate that the enemies are sorted in this table model
	 */
	public void sortEnemies() {
		fireTableDataChanged();
		Sorter.heapSort(battles);
	}
	public List<Battle> getBattleList() {
		for (int i = 0; i < enemies.size(); i++) {
			battles.set(i, new Battle(hero, enemies.get(i)));
		}
		return battles;
	}
}
