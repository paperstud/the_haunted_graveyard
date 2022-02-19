package game;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/* Name: Steve Galvan
 * CIN: 304764179
 * Course & Section: CS2012 Sections 01 & 02
 * Description: This class is used to help build the maze itself.
 * It contains methods containGrid which creates an empty GridPane.
 * 
 * addHazards adds objects from the Tile class and the position matches
 * the position of the array.
 * 
 * movement method controls the players movement and add messages to the textbox
 * such as warnings or outcomes.
 * 
 * checkPlayerPosition checks if the player has landed on a tile and returns a Text 
 * of the outcomes.
 * 
 * totalAmmoCheck checks if the player has ammo and if the maze contains any discoverable
 * ammo left
 * 
 * addFog adds the fog that lies on top of the objects to hide them. This class also
 * handles button clicks for when the player throws a vial and updates the game whether a vial
 * lands or not.
 * 
 * removeFog removes the fog
 * 
 * extractBossPosition obtains the position of the mystical creature (Death)
 * 
 * updateBossPosition updates the creatures position for when the player misses a vial throw.
 */

public class MazeBuilder {

	public static void createGrid(GridPane grid, int totalRows, int totalColumns) {
		int cellSize = 0;
		if (totalColumns == 5)
			cellSize = 90;
		else if (totalColumns == 7)
			cellSize = 75;
		else if (totalColumns == 10)
			cellSize = 75;
		for (int i = 0; i < totalRows; i++) {
			RowConstraints row = new RowConstraints(cellSize);
			row.setMaxHeight(cellSize);
			grid.getRowConstraints().add(row);
		}

		for (int i = 0; i < totalColumns; i++) {
			ColumnConstraints column = new ColumnConstraints(cellSize);
			grid.getColumnConstraints().add(column);
		}

	}

	public static GridPane addHazards(Tile[][] list, Player p1, GridPane grid) {
		for (int r = 0; r < list.length; r++) {
			for (int c = 0; c < list[r].length; c++) {
				if (list[r][c] == null)
					continue;
				if (list[r][c].getType() == 1) {
					list[r][c].getGraphic().setFitHeight(grid.getRowConstraints().get(1).getMaxHeight() - 20);
					list[r][c].getGraphic().setFitWidth(grid.getRowConstraints().get(1).getMaxHeight() - 40);
				} else if (list[r][c].getType() == 4) {
					list[r][c].getGraphic().setFitHeight(grid.getRowConstraints().get(1).getMaxHeight());
					list[r][c].getGraphic().setFitWidth(grid.getRowConstraints().get(1).getMaxHeight());
				} else if (list[r][c].getType() == 3) {
					list[r][c].getGraphic().setFitHeight(grid.getRowConstraints().get(1).getMaxHeight());
					list[r][c].getGraphic().setFitWidth(grid.getRowConstraints().get(1).getMaxHeight());
				} else {
					list[r][c].getGraphic().setFitHeight(grid.getRowConstraints().get(1).getMaxHeight());
					list[r][c].getGraphic().setFitWidth(grid.getRowConstraints().get(1).getMaxHeight());
				}

				grid.add(list[r][c].getGraphic(), list[r][c].getXPos(), list[r][c].getYPos());
			}
		}
		return grid;
	}

	public static void movement(Player p1, GridPane grid, Tile[][] list, BorderPane under) {

		p1.getGraphic().setOnKeyPressed(e -> {
			KeyCode kc = e.getCode();
			VBox messages = new VBox();
			messages.setMinHeight(70);
			switch (e.getCode()) {

			case RIGHT:
				grid.getChildren().remove(p1.getGraphic());
				p1.setXPos(p1.getXPos() + 1);
				grid.add(p1.getGraphic(), p1.getXPos(), p1.getYPos());
				messages.getChildren().addAll(checkPlayerPosition(p1, list));
				under.setBottom(messages);
				break;
			case LEFT:
				grid.getChildren().remove(p1.getGraphic());
				p1.setXPos(p1.getXPos() - 1);
				grid.add(p1.getGraphic(), p1.getXPos(), p1.getYPos());
				messages.getChildren().addAll(checkPlayerPosition(p1, list));
				under.setBottom(messages);
				break;
			case UP:
				grid.getChildren().remove(p1.getGraphic());
				p1.setYPos(p1.getYPos() - 1);
				grid.add(p1.getGraphic(), p1.getXPos(), p1.getYPos());
				messages.getChildren().addAll(checkPlayerPosition(p1, list));
				under.setBottom(messages);
				break;
			case DOWN:
				grid.getChildren().remove(p1.getGraphic());
				p1.setYPos(p1.getYPos() + 1);
				grid.add(p1.getGraphic(), p1.getXPos(), p1.getYPos());
				messages.getChildren().addAll(checkPlayerPosition(p1, list));
				under.setBottom(messages);
				break;
			default:
				break;

			}

			if (list[p1.getYPos()][p1.getXPos()] != null && list[p1.getYPos()][p1.getXPos()].getType() == 4) {
				grid.getChildren().remove(list[p1.getYPos()][p1.getXPos()].getGraphic());
				list[p1.getYPos()][p1.getXPos()] = null;
			}
			totalAmmoCheck(list, p1, grid, under);

		});

	}

	public static ObservableList<Text> checkPlayerPosition(Player p1, Tile[][] list) {
		ObservableList<Text> text = FXCollections.observableArrayList();
		for (int r = 0; r < list.length; r++) {
			for (int c = 0; c < list[r].length; c++) {
				if (list[r][c] == null)
					continue;

				else if (list[r][c].getXPos() == p1.getXPos() && list[r][c].getYPos() == p1.getYPos()) {
					if (list[r][c].getType() == 1 || list[r][c].getType() == 3) {
						p1.getGraphic().setDisable(true);
					} else if (list[r][c].getType() == 2) {
						p1.setAmmoCount(p1.getAmmoCount() - 1);
					} else if (list[r][c].getType() == 4) {
						p1.setAmmoCount(p1.getAmmoCount() + 1);
					}
					text.add(new Text(list[r][c].getOutcomeText()));
					text.add(new Text("Vial Count: " + p1.getAmmoCount()));
					return text;

				} else if ((Math.abs(list[r][c].getXPos() - p1.getXPos()) == 1 && list[r][c].getYPos() == p1.getYPos()
						&& list[r][c].getType() != 4)
						|| (Math.abs(list[r][c].getYPos() - p1.getYPos()) == 1 && list[r][c].getXPos() == p1.getXPos()
								&& list[r][c].getType() != 4)) {
					text.add(new Text(list[r][c].getWarningText()));
				}

			}
		}
		text.add(new Text("Vial Count: " + p1.getAmmoCount()));

		return text;
	}

	public static GridPane addFog(GridPane grid, Tile[][] list, Player p1, ImageView blocks[][], BorderPane under) {

		for (int r = 0; r < blocks.length; r++) {
			for (int c = 0; c < blocks[r].length; c++) {
				blocks[r][c] = new ImageView(new Image("/images/fogbackground.png"));
				blocks[r][c].setFitHeight(grid.getRowConstraints().get(1).getMaxHeight());
				blocks[r][c].setFitWidth(grid.getRowConstraints().get(1).getMaxHeight());
			}

		}

		for (int r = 0; r < blocks.length; r++) {
			for (int c = 0; c < blocks[r].length; c++) {

				if (list[r][c] != null && list[r][c].getType() == 3) {
					Tile temp = list[r][c];
					grid.add(blocks[r][c], c, r);
					int x = c;
					int y = r;
					blocks[r][c].setOnMouseClicked(e -> {
						if (p1.getAmmoCount() > 0 && ((Math.abs(x - p1.getXPos()) == 1 && y == p1.getYPos())
								|| (Math.abs(y - p1.getYPos()) == 1 && x == p1.getXPos()))) {
							temp.setGameOver(true);
							p1.getGraphic().setDisable(true);
							p1.setAmmoCount(p1.getAmmoCount() - 1);
							VBox messages = new VBox();
							messages.setMinHeight(70);
							Text win = new Text("YOU WIN!");
							win.setFont(new Font(20));
							messages.getChildren().add(win);
							under.setBottom(messages);
							removeFog(blocks, grid);
							grid.getChildren().remove(p1.getGraphic());
							ImageView winner = new ImageView("/images/somawing.gif");
							winner.setFitHeight(grid.getRowConstraints().get(1).getMaxHeight() * 1.5);
							winner.setFitWidth(grid.getRowConstraints().get(1).getMaxHeight() * 1.5);
							grid.add(winner, p1.getXPos(), p1.getYPos());

						} else if (p1.getAmmoCount() <= 0) {
							VBox messages = new VBox();
							messages.setMinHeight(70);
							messages.getChildren().add(new Text("No vials left"));
							under.setBottom(messages);
							totalAmmoCheck(list, p1, grid, under);
							p1.getGraphic().requestFocus();
							if (p1.getAmmoCount() <= 0) {
								totalAmmoCheck(list, p1, grid, under);
							}
						}
					});
					continue;
				}
				grid.add(blocks[r][c], c, r);
				int x = c;
				int y = r;
				blocks[r][c].setOnMouseClicked(e -> {
					if (p1.getAmmoCount() > 0 && ((Math.abs(x - p1.getXPos()) == 1 && y == p1.getYPos())
							|| (Math.abs(y - p1.getYPos()) == 1 && x == p1.getXPos()))) {
						p1.setAmmoCount(p1.getAmmoCount() - 1);
						VBox messages = new VBox();
						messages.setMinHeight(70);
						messages.getChildren().addAll(new Text("Missed!"));
						under.setBottom(messages);
						updateBossPosition(list, grid, blocks, p1, under);
						grid.getChildren().remove(p1.getGraphic());
						grid.add(p1.getGraphic(), p1.getXPos(), p1.getYPos());
						messages.getChildren().addAll(checkPlayerPosition(p1, list));
						p1.getGraphic().requestFocus();
						if (p1.getAmmoCount() <= 0) {
							totalAmmoCheck(list, p1, grid, under);
						}

					} else if (p1.getAmmoCount() <= 0) {
						VBox messages = new VBox();
						messages.setMinHeight(70);
						messages.getChildren().add(new Text("No vials left"));
						under.setBottom(messages);
						totalAmmoCheck(list, p1, grid, under);
						p1.getGraphic().requestFocus();

					}

				});

			}
		}
		return grid;
	}

	public static GridPane removeFog(ImageView[][] blocks, GridPane grid) {
		for (int r = 0; r < blocks.length; r++) {
			for (int c = 0; c < blocks[r].length; c++) {
				grid.getChildren().removeAll(blocks[r][c]);
			}
		}
		return grid;
	}

	public static int[] extractBossPosition(Tile[][] list, int bossCount) {
		int size = 2 * bossCount;
		int[] positions = new int[size];
		int count = 0;

		while (count < size) {
			for (int r = 0; r < list.length; r++) {
				for (int c = 0; c < list[r].length; c++) {
					if (list[r][c] != null && list[r][c].getType() == 3) {
						positions[count] = list[r][c].getXPos();
						positions[count + 1] = list[r][c].getYPos();
						count += 2;
					}
				}
			}
		}
		return positions;
	}

	public static void updateBossPosition(Tile[][] list, GridPane grid, ImageView[][] blocks, Player p1,
			BorderPane under) {

		int x = extractBossPosition(list, 1)[0];
		int y = extractBossPosition(list, 1)[1];
		grid.getChildren().remove(list[y][x].getGraphic());
		boolean notUpdated = true;
		int count = 0;
		while (notUpdated) {
			int d = (int) (Math.random() * 4);
			if (d == 0 && x + 1 < list[0].length && list[y][x + 1] == null && p1.getAmmoCount() >= 0) {
				Tile temp = new Tile(list[y][x]);
				temp.getGraphic().setFitHeight(grid.getRowConstraints().get(1).getMaxHeight());
				temp.getGraphic().setFitWidth(grid.getRowConstraints().get(1).getMaxHeight());
				list[y][x] = null;
				list[y][x + 1] = temp;
				temp.setXPos(x + 1);
				temp.setYPos(y);
				grid.add(temp.getGraphic(), x + 1, y);
				removeFog(blocks, grid);
				addFog(grid, list, p1, blocks, under);
				notUpdated = false;
			} else if (d == 1 && x - 1 >= 0 && list[y][x - 1] == null && p1.getAmmoCount() >= 0) {
				Tile temp = new Tile(list[y][x]);
				temp.getGraphic().setFitHeight(grid.getRowConstraints().get(1).getMaxHeight());
				temp.getGraphic().setFitWidth(grid.getRowConstraints().get(1).getMaxHeight());
				list[y][x] = null;
				list[y][x - 1] = temp;
				temp.setXPos(x - 1);
				temp.setYPos(y);
				grid.add(temp.getGraphic(), x - 1, y);
				removeFog(blocks, grid);
				addFog(grid, list, p1, blocks, under);
				notUpdated = false;
			} else if (d == 2 && y - 1 >= 0 && list[y - 1][x] == null && p1.getAmmoCount() >= 0) {
				Tile temp = new Tile(list[y][x]);
				temp.getGraphic().setFitHeight(grid.getRowConstraints().get(1).getMaxHeight());
				temp.getGraphic().setFitWidth(grid.getRowConstraints().get(1).getMaxHeight());
				list[y][x] = null;
				list[y - 1][x] = temp;
				temp.setXPos(x);
				temp.setYPos(y - 1);
				grid.add(temp.getGraphic(), x, y - 1);
				removeFog(blocks, grid);
				addFog(grid, list, p1, blocks, under);
				notUpdated = false;
			}

			else if (d == 3 && y + 1 < list.length && list[y + 1][x] == null && p1.getAmmoCount() >= 0) {
				Tile temp = new Tile(list[y][x]);
				temp.getGraphic().setFitHeight(grid.getRowConstraints().get(1).getMaxHeight());
				temp.getGraphic().setFitWidth(grid.getRowConstraints().get(1).getMaxHeight());
				list[y][x] = null;
				list[y + 1][x] = temp;
				temp.setXPos(x);
				temp.setYPos(y + 1);
				grid.add(temp.getGraphic(), x, y + 1);
				removeFog(blocks, grid);
				addFog(grid, list, p1, blocks, under);
				notUpdated = false;

			} else if (count > 40) {
				grid.add(list[y][x].getGraphic(), x, y);
				removeFog(blocks, grid);
				addFog(grid, list, p1, blocks, under);
				notUpdated = false;

			}

			count++;
		}

	}

	public static void totalAmmoCheck(Tile[][] list, Player p1, GridPane grid, BorderPane under) {
		int count = 0;
		Text text = new Text();
		for (int r = 0; r < list.length; r++) {
			for (int c = 0; c < list[r].length; c++) {
				if (list[r][c] == null)
					continue;
				else if (list[r][c].getType() == 4)
					count++;
			}
		}
		if (count == 0 && p1.getAmmoCount() == 0) {
			text.setText("You've run out of Holy Water completely. "
					+ list[extractBossPosition(list, 1)[1]][extractBossPosition(list, 1)[0]].getOutcomeText());
			VBox msgs = new VBox();
			msgs.setMinHeight(70);
			msgs.getChildren().add(text);
			under.setBottom(msgs);
			p1.getGraphic().setDisable(true);
		}
	}

}
