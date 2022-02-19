package game;

/* Name: Steve Galvan
 * CIN: 304764179
 * Course & Section: CS2012 Sections 01 & 02
 * Description: This class runs the game and has methods for saving, loading
 * and debugging the game. The lamda expressions handle the button action events
 * to start up whichever maze type you decide to load. Inside the expressions contains
 * the methods that are called to make the maze function.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		BorderPane under = new BorderPane();
		VBox menu = new VBox();
		Button easy = new Button("Easy");
		Button medium = new Button("Medium");
		Button hard = new Button("Hard");
		HBox loader = new HBox();
		Button loadBtn = new Button("Load");
		TextField load = new TextField();
		ImageView logo = new ImageView(new Image("/images/Logo.png"));

		load.setPromptText("Enter file path.txt");
		loader.getChildren().addAll(loadBtn, load);
		logo.setFitHeight(380);
		logo.setFitWidth(500);
		loader.setAlignment(Pos.CENTER);

		menu.getChildren().addAll(logo, loader, easy, medium, hard);
		menu.setAlignment(Pos.CENTER);
		menu.setPadding(new Insets(290));
		menu.setMargin(easy, new Insets(20));
		menu.setMargin(medium, new Insets(20));
		menu.setMargin(hard, new Insets(20));
		menu.setMargin(loader, new Insets(20));
		menu.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		Scene s = new Scene(menu);
		primaryStage.setTitle("The Haunted Grave");


		// Objects
		ImageView playerGraphic = new ImageView(new Image("/images/soma.gif"));
		playerGraphic.setFitHeight(85);
		playerGraphic.setFitWidth(85);
		ImageView trapGraphic = new ImageView(new Image("/images/flametrap.png"));
		Tile flameTrap = new Tile("You've been burned to death.", "It smells like something is cooking nearby.",
				trapGraphic, 1);
		ImageView witchGraphic = new ImageView(new Image("/images/witch.gif"));
		Tile witch = new Tile("The Witch evaporates some of your Holy Water.", "A black cat sprints past you.",
				witchGraphic, 2);
		ImageView deathGraphic = new ImageView(new Image("/images/deathfinal.gif"));
		Tile death = new Tile("Death came for you.", "You feel a dark presence nearby.", deathGraphic, 3);
		ImageView holyWaterGraphic = new ImageView(new Image("/images/holywaterfinal.png"));
		Tile holyWater = new Tile("You found some Holy Water!", "", holyWaterGraphic, 4);

		GridPane grid = new GridPane();
		grid.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		grid.setGridLinesVisible(true);
		under.setCenter(grid);

		TextField save = new TextField();
		Button saveBtn = new Button("Save Game");
		CheckBox debugMode = new CheckBox("Debug Mode");
		save.setFocusTraversable(false);
		saveBtn.setFocusTraversable(false);
		save.setPromptText("Enter file path.txt");

		debugMode.setPadding(new Insets(10));
		debugMode.setLayoutX(400);
		save.setPadding(new Insets(10));
		saveBtn.setPadding(new Insets(10));

		HBox top = new HBox();
		top.setPadding(new Insets(10));
		top.getChildren().addAll(debugMode, save, saveBtn);
		VBox msgs = new VBox();
		msgs.setMinHeight(70);
		under.setBottom(msgs);
		under.setTop(top);
		debugMode.setFocusTraversable(false);
		Scene scene = new Scene(under, 1000, 700, Color.ALICEBLUE);

		easy.setOnAction(e -> {
			MazeBuilder.createGrid(grid, 5, 5);
			Player p1 = new Player("Soma Cruz", playerGraphic, 5, 5);
			Tile[][] l1 = new Tile[5][5];
			l1 = Tile.createHazardList(l1, p1, death, 1);
			l1 = Tile.createHazardList(l1, p1, witch, 3);
			l1 = Tile.createHazardList(l1, p1, holyWater, 2);
			Tile[][] list = Tile.createHazardList(l1, p1, flameTrap, 3);
			MazeBuilder.addHazards(list, p1, grid);
			ImageView[][] blocks = new ImageView[5][5];
			MazeBuilder.addFog(grid, list, p1, blocks, under);
			grid.add(p1.getGraphic(), p1.getXPos(), p1.getYPos());
			debugger(debugMode, blocks);
			MazeBuilder.movement(p1, grid, list, under);
			save(saveBtn, save, p1, list);
			p1.getGraphic().setFocusTraversable(true);
			p1.getGraphic().requestFocus();
			primaryStage.setScene(scene);
		});

		medium.setOnAction(e -> {
			MazeBuilder.createGrid(grid, 7, 7);
			Player p1 = new Player("Soma Cruz", playerGraphic, 7, 7);
			Tile[][] l1 = new Tile[7][7];
			l1 = Tile.createHazardList(l1, p1, death, 1);
			l1 = Tile.createHazardList(l1, p1, witch, 5);
			l1 = Tile.createHazardList(l1, p1, holyWater, 4);
			Tile[][] list = Tile.createHazardList(l1, p1, flameTrap, 5);
			MazeBuilder.addHazards(list, p1, grid);
			ImageView[][] blocks = new ImageView[7][7];
			MazeBuilder.addFog(grid, list, p1, blocks, under);
			grid.add(p1.getGraphic(), p1.getXPos(), p1.getYPos());
			debugger(debugMode, blocks);
			MazeBuilder.checkPlayerPosition(p1, list);
			MazeBuilder.movement(p1, grid, list, under);
			save(saveBtn, save, p1, list);
			p1.getGraphic().setFocusTraversable(true);
			p1.getGraphic().requestFocus();
			primaryStage.setScene(scene);
		});

		hard.setOnAction(e -> {

			MazeBuilder.createGrid(grid, 7, 10);
			Player p1 = new Player("Soma Cruz", playerGraphic, 10, 7);
			Tile[][] l1 = new Tile[7][10];
			l1 = Tile.createHazardList(l1, p1, death, 1);
			l1 = Tile.createHazardList(l1, p1, witch, 9);
			l1 = Tile.createHazardList(l1, p1, holyWater, 5);
			Tile[][] list = Tile.createHazardList(l1, p1, flameTrap, 9);
			debugMode.setFocusTraversable(false);
			MazeBuilder.addHazards(list, p1, grid);
			ImageView[][] blocks = new ImageView[7][10];
			MazeBuilder.addFog(grid, list, p1, blocks, under);
			grid.add(p1.getGraphic(), p1.getXPos(), p1.getYPos());
			debugger(debugMode, blocks);
			MazeBuilder.checkPlayerPosition(p1, list);
			MazeBuilder.movement(p1, grid, list, under);
			save(saveBtn, save, p1, list);
			p1.getGraphic().setFocusTraversable(true);
			p1.getGraphic().requestFocus();
			primaryStage.setScene(scene);
		});

		loadBtn.setOnAction(e -> {
			Player p1 = new Player("Soma Cruz", playerGraphic, 9, 9);
			Tile[][] list = load(loadBtn, load, witch, flameTrap, death, holyWater, p1);
			MazeBuilder.createGrid(grid, p1.getYMax(), p1.getXMax());
			MazeBuilder.addHazards(list, p1, grid);
			ImageView[][] blocks = new ImageView[p1.getYMax()][p1.getXMax()];
			MazeBuilder.addFog(grid, list, p1, blocks, under);
			grid.add(p1.getGraphic(), p1.getXPos(), p1.getYPos());
			debugger(debugMode, blocks);
			MazeBuilder.checkPlayerPosition(p1, list);
			MazeBuilder.movement(p1, grid, list, under);
			save(saveBtn, save, p1, list);
			p1.getGraphic().setFocusTraversable(true);
			p1.getGraphic().requestFocus();
			primaryStage.setScene(scene);
		});

		primaryStage.setScene(s);
		primaryStage.show();

	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public void save(Button btn, TextField save, Player p1, Tile[][] list) {
		btn.setOnAction(e -> {
			PrintWriter writer = null;
			String filePath = null;
			try {
				filePath = save.getText();
				writer = new PrintWriter(filePath);
				int count = 0;
				for (int r = 0; r < list.length; r++) {
					for (int c = 0; c < list[r].length; c++) {

						if (c == p1.getXPos() && r == p1.getYPos()) {
							writer.print(6 + " ");
							count++;
						}

						else if (list[r][c] == null) {
							writer.print(0 + " ");
							count++;
						} else if (list[r][c].getType() == 1) {
							writer.print(1 + " ");
							count++;
						}

						else if (list[r][c].getType() == 2) {
							writer.print(2 + " ");
							count++;

						}

						else if (list[r][c].getType() == 3) {
							writer.print(3 + " ");
							count++;
						}

						else if (list[r][c].getType() == 4) {
							writer.print(4 + " ");
							count++;
						}

					}
					if (count == list[r].length) {
						writer.print("\n");
						count = 0;
					}

				}

				writer.print(p1.getAmmoCount() + " ");
				writer.print(p1.getXMax() + " ");
				writer.print(p1.getYMax() + " ");

				writer.flush();
				writer.close();

			}

			catch (FileNotFoundException ex) {
				save.setPromptText("File not found");
			}
		});
		p1.getGraphic().setOnMouseClicked(e -> {
			p1.getGraphic().requestFocus();
		});
	}

	public Tile[][] load(Button loadBtn, TextField load, Tile witch, Tile flameTrap, Tile death, Tile holyWater,
			Player p1) {

		try {
			String location = load.getText();
			File savedFile = new File(location);
			Scanner fReader = new Scanner(savedFile);
			ArrayList<Integer> nums = new ArrayList<>();

			while (fReader.hasNextInt()) {
				int number = fReader.nextInt();
				nums.add(number);
			}

			int r = nums.get(nums.size() - 1);
			int c = nums.get(nums.size() - 2);
			p1.setXMax(c);
			p1.setYMax(r);
			Tile[][] list = new Tile[r][c];
			int i = 0;

			for (int row = 0; row < r; row++) {
				for (int col = 0; col < c; col++) {
					if (nums.get(i) == 0) {
					} else if (nums.get(i) == 6) {
						p1.setXPos(col);
						p1.setYPos(row);
					} else if (nums.get(i) == 1) {
						list[row][col] = new Tile(flameTrap);
						list[row][col].setXPos(col);
						list[row][col].setYPos(row);
					} else if (nums.get(i) == 2) {
						list[row][col] = new Tile(witch);
						list[row][col].setXPos(col);
						list[row][col].setYPos(row);
					} else if (nums.get(i) == 3) {
						list[row][col] = new Tile(death);
						list[row][col].setXPos(col);
						list[row][col].setYPos(row);
					} else if (nums.get(i) == 4) {
						list[row][col] = new Tile(holyWater);
						list[row][col].setXPos(col);
						list[row][col].setYPos(row);
					}
					i++;
				}
			}

			return list;
		}

		catch (FileNotFoundException ex) {
			load.setPromptText("File not found");
		} catch (NullPointerException ex) {
			load.setPromptText("File not found");
		}
		return null;
	}

	public void debugger(CheckBox debugMode, ImageView[][] blocks) {
		debugMode.setOnAction(e -> {
			for (int r = 0; r < blocks.length; r++) {
				for (int c = 0; c < blocks[r].length; c++) {
					if (debugMode.isSelected()) {
						blocks[r][c].setVisible(false);
					} else
						blocks[r][c].setVisible(true);
				}
			}
		});
	}

}
