package edu.cmu.cs.cs214.hw2.staff.world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import edu.cmu.cs.cs214.hw2.actors.Gardener;
import edu.cmu.cs.cs214.hw2.actors.Gnat;
import edu.cmu.cs.cs214.hw2.actors.Grass;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Fox;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Rabbit;
import edu.cmu.cs.cs214.hw2.staff.interfaces.World;
import edu.cmu.cs.cs214.hw2.staff.util.Location;

/**
 * This class represents an element in the GUI for rabbit world. This panel
 * draws the actual world, and also controls running the rabbit world
 * appropriately.
 */
@SuppressWarnings("serial")
public class WorldPanel extends JPanel implements Runnable {

	private final int IMAGE_SIZE = 8;
	private final World world;

	// Load all the icons
	private final ImageIcon gardenerImage = new ImageIcon(getClass()
			.getResource("/icons/gardener.gif"));
	private final ImageIcon gnatImage = new ImageIcon(getClass().getResource(
			"/icons/gnat.gif"));
	private final ImageIcon grassImage = new ImageIcon(getClass().getResource(
			"/icons/grass.gif"));
	private final ImageIcon rabbitImage = new ImageIcon(getClass().getResource(
			"/icons/rabbit.gif"));
	private final ImageIcon foxImage = new ImageIcon(getClass().getResource(
			"/icons/fox.gif"));
	private final ImageIcon unknownImage = new ImageIcon(getClass()
			.getResource("/icons/unknown.gif"));

	private int numSteps;

	public WorldPanel(World w) {
		this.world = w;
		// calculate the preferred width and height of the panel
		int panelWidth = w.getWidth() * IMAGE_SIZE;
		int panelHeight = w.getHeight() * IMAGE_SIZE;
		Dimension preferredSize = new Dimension(panelWidth, panelHeight);
		this.setPreferredSize(preferredSize);
		this.setBackground(Color.WHITE);
	}

	/**
	 * This method paints the graphics of each object in the world.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// iterate over all objects in the world and redraw each object
		synchronized (WorldImpl.class) {
			for (Object thing : world.getAllObjects()) {
				Location loc = world.getLocation(thing);
				int x = loc.getX() * IMAGE_SIZE;
				int y = loc.getY() * IMAGE_SIZE;

				// determine which icon to draw
				if (thing instanceof Gardener) {
					gardenerImage.paintIcon(this, g, x, y);
				} else if (thing instanceof Grass) {
					grassImage.paintIcon(this, g, x, y);
				} else if (thing instanceof Rabbit) {
					rabbitImage.paintIcon(this, g, x, y);
				} else if (thing instanceof Fox) {
					foxImage.paintIcon(this, g, x, y);
				} else if (thing instanceof Gnat) {
					gnatImage.paintIcon(this, g, x, y);
				} else {
					unknownImage.paintIcon(this, g, x, y);
				}
			}
		}
	}

	/**
	 * This method steps the world once and then updates the UI. This method can
	 * be safely called from a separate thread.
	 */
	public boolean step() {
		boolean ret = world.step();
		repaint();
		sleep();
		return ret;
	}

	/**
	 * This method steps the world n times and updates the UI.
	 *
	 * @param num
	 *            The number of steps to run
	 */
	public void step(int num) {
		numSteps = num;
		new Thread(this).start();
	}

	/**
	 * This method will run the world forever.
	 */
	public void stepForever() {
		numSteps = -1;
		new Thread(this).start();
	}

	/**
	 * This method tells the thread how to run.
	 */
	@Override
	public void run() {
		if (numSteps == -1) {
			while (numSteps == -1) {
				if (!step()) {
					break;
				}
			}
		} else {
			for (int i = 0; i < numSteps; i++) {
				step();
			}
		}
	}

	public void stop() {
		// value breaks concurrent loop in run()
		numSteps = 0;
	}

	/**
	 * this method will cause the current thread to sleep for 100 milliseconds
	 */
	private static void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
