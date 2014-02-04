package edu.cmu.cs.cs214.hw2.staff.world;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.cmu.cs.cs214.hw2.staff.interfaces.World;

//import java.util.HashMap;
//import java.util.Map;
//import edu.cmu.cs.cs214.hw2.staff.util.Location;

/**
 * This class represents the GUI for the rabbit world simulation.
 */
@SuppressWarnings("serial")
public class WorldUI extends JPanel {

	private final int X_DIM = 40;
	private final int Y_DIM = 40;

	private final World w;

	private final WorldPanel worldPanel;
	private final JButton step;
	private final JButton run;

	public WorldUI() {
		// set the layout of the UI
		setLayout(new BorderLayout());

		// setup/add the world
		w = new WorldImpl(X_DIM, Y_DIM);
		worldPanel = new WorldPanel(w);

		// worldPanel.setBorder( BorderFactory.createLineBorder(
		// Color.LIGHT_GRAY ) );

		add(worldPanel, BorderLayout.CENTER);

		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());

		step = new JButton("Step");
		bottom.add(step, BorderLayout.EAST);

		run = new JButton("Start");
		bottom.add(run, BorderLayout.WEST);

		add(bottom, BorderLayout.SOUTH);

		// add functionality to the step button.
		step.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				worldPanel.step();
			}
		});

		// add functionality to the run button
		run.addActionListener(new ActionListener() {
			boolean toggle = true;

			@Override
			public void actionPerformed(ActionEvent evt) {
				if (toggle) {
					worldPanel.stepForever();
					step.setEnabled(false);
					run.setText("Stop");
				} else {
					worldPanel.stop();
					step.setEnabled(true);
					run.setText("Start");
				}
				toggle = !toggle;
			}
		});

		// initialize the world
		WorldLoader wl = new WorldLoader(w);

		// Map<Class, String> aiToName = new HashMap<Class, String>();
		// ArenaLoader al = new ArenaLoader(w, aiToName);

		wl.initializeWorld();

		// ((WorldImpl)w).setMap(aiToName);

		// draw the world
		worldPanel.setVisible(true);
	}

	/**
	 * The programs entry point to have a nice UI.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame f = new JFrame("World 15-214");
				WorldUI gui = new WorldUI();
				f.add(gui);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.pack();
				f.setResizable(false);
				f.setVisible(true);
			}
		});
	}
}
