package edu.cmu.cs.cs214.hw2.actors;

import edu.cmu.cs.cs214.hw2.staff.interfaces.Actor;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Edible;

/**
 * Grass is an {@link Edible} {@link Actor}. Grass never moves. It simply sits
 * at the same location until it is eaten.
 */
public class Grass implements Edible {

	private static final int GRASS_ENERGY_VALUE = 5;

	@Override
	public int getEnergyValue() {
		return GRASS_ENERGY_VALUE;
	}
}
