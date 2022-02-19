package game;

/* Name: Steve Galvan
 * CIN: 304764179
 * Course & Section: CS2012 Sections 01 & 02
 * Description: This class is for all the objects in the maze that can
 * affect the player (Holy Water vials, Witches, Flame Traps, and Death).
 * There is a copy constructor and a method used to help create a 
 * randomized Tile array used for creating the Maze and a method
 * to check to ensure the Tile gets placed in a unique position.
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile extends ImageView {

	private int XPos;
	private int YPos;
	private String warningText;
	private String outcomeText;
	private ImageView graphic;
	boolean gameOver;
	private int XMax;
	private int YMax;
	// Type (1, 2, 3, 4) = ( Trap, Creature, Boss, Ammo) respectively
	private int type;

	public Tile(String outcomeText, String warningText, ImageView graphic, int type) {

		this.outcomeText = outcomeText;
		this.warningText = warningText;
		this.graphic = graphic;
		this.type = type;
		this.gameOver = false;
		this.XMax = 5;
		this.YMax = 5;
	}

	public Tile(Tile h2) {

		this.warningText = h2.getWarningText();
		this.outcomeText = h2.getOutcomeText();
		Image temp = h2.getGraphic().getImage();
		this.graphic = new ImageView(temp);
		this.type = h2.getType();
	}

	public int getXPos() {
		return XPos;
	}

	public int getYPos() {
		return YPos;
	}

	public String getWarningText() {
		return warningText;
	}

	public String getOutcomeText() {
		return outcomeText;
	}

	public ImageView getGraphic() {
		return graphic;
	}

	public int getType() {
		return type;
	}

	public boolean getGameOver() {
		return gameOver;
	}

	public void setXPos(int XPos) {
		this.XPos = XPos;
	}

	public void setYPos(int YPos) {
		this.YPos = YPos;
	}

	public void setWarningText(String warningText) {
		this.warningText = warningText;
	}

	public void setOutcomeText(String outcomeText) {
		this.outcomeText = outcomeText;
	}

	public void setGraphic(ImageView graphic) {
		this.graphic = graphic;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public static Tile[][] createHazardList(Tile[][] list, Player p1, Tile hazard, int totalHazards) {

		int count = 0;
		while (count != totalHazards) {
			int x = (int) (Math.random() * list[0].length);
			int y = (int) (Math.random() * list.length);
			if (!isRepeat(p1, list, x, y)) {
				Tile temp = new Tile(hazard);
				list[y][x] = temp;
				temp.setXPos(x);
				temp.setYPos(y);
				count++;
			}
		}

		return list;
	}

	public static boolean isRepeat(Player p1, Tile[][] list, int x, int y) {
		if (p1.getXPos() == x && p1.getYPos() == y)
			return true;

		for (int r = 0; r < list.length; r++) {
			for (int c = 0; c < list[r].length; c++) {
				if (list[r][c] != null && r == y && c == x)
					return true;
			}
		}
		return false;
	}

}
