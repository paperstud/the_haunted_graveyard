package game;

/* Name: Steve Galvan
 * CIN: 304764179
 * Course & Section: CS2012 Sections 01 & 02
 * Description: This class is made for the player
 * to track it's information such as position, ammo count, 
 * and it's graphic.
 */

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Player extends ImageView {

	private String name;
	private int XPos;
	private int YPos;
	private int XMax;
	private int YMax;
	private int ammoCount;
	private boolean alive;
	private ImageView graphic;

	public Player(String name, ImageView graphic, int XMax, int YMax) {
		this.name = name;
		this.XPos = (int) (Math.random() * XMax);
		this.YPos = (int) (Math.random() * YMax);
		this.XMax = XMax;
		this.YMax = YMax;
		this.ammoCount = 3;
		this.alive = true;
		this.graphic = graphic;
	}

	public String getName() {
		return name;
	}

	public int getXPos() {
		return XPos;
	}

	public int getYPos() {
		return YPos;
	}

	public int getAmmoCount() {
		return ammoCount;
	}

	public ImageView getGraphic() {
		return graphic;
	}

	public int getYMax() {
		return YMax;
	}

	public int getXMax() {
		return XMax;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setXPos(int XPos) {
		if (XPos >= this.XMax - 1) {
			this.XPos = this.XMax - 1;
		} else if (XPos < 0)
			this.XPos = 0;
		else
			this.XPos = XPos;

	}

	public void setYPos(int YPos) {
		if (YPos >= this.YMax - 1) {
			this.YPos = this.YMax - 1;
		} else if (YPos < 0)
			this.YPos = 0;
		else
			this.YPos = YPos;
	}

	public void setAmmoCount(int ammoCount) {
		if (ammoCount <= 0)
			this.ammoCount = 0;
		else
			this.ammoCount = ammoCount;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public void setGraphic(ImageView graphic) {
		this.graphic = graphic;
	}

	public void setYMax(int YMax) {
		this.YMax = YMax;
	}

	public void setXMax(int XMax) {
		this.XMax = XMax;

	}

}
