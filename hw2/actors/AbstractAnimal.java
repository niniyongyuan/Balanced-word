/**
 * @author: Lina Li
 * Section: B
 * Andrew ID: linal
 * 15-214
 */
package edu.cmu.cs.cs214.hw2.actors;

import edu.cmu.cs.cs214.hw2.staff.interfaces.AI;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Animal;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Command;
import edu.cmu.cs.cs214.hw2.staff.interfaces.World;
import edu.cmu.cs.cs214.hw2.staff.util.Direction;
import edu.cmu.cs.cs214.hw2.staff.util.Location;

public abstract class AbstractAnimal implements Animal {

	private int remainEnergy;
	private AI myAI;

	/**
	 * constructor with no arguments
	 */
	public AbstractAnimal() {

	}

	/**
	 * constructor to help build child animal with given energy
	 * 
	 * @param energy
	 */
	public AbstractAnimal(int energy) {
		myAI = getMyAI();
		this.remainEnergy = energy;
	}

	// abstract method to get AI
	protected abstract AI getMyAI();

	// die AI implement here, if the energy is 0, remove it.
	private void checkDie(World world) {
		if (this.getEnergy() == 0) {
			world.remove(this);
		}
	}

	/**
	 * execute the command in the world, after each action check whether it is
	 * dead
	 * 
	 * @param world
	 */
	public void act(World world) {
		remainEnergy--;
		Command c = myAI.act(world, this);
		if (c != null) {
			c.execute(world, this);
		}
		checkDie(world);
	}

	/**
	 * get the energy
	 * 
	 * @return the remainEnergy of actor
	 */
	public int getEnergy() {
		return remainEnergy;
	}

	// set the energy
	protected void setEnergy(int energy) {
		remainEnergy = energy;
	}

	/**
	 * abstract method to getMaxEnergy of abstract animal
	 */
	public abstract int getMaxEnergy();

	/**
	 * abstract method to getBreedLimit of abstract animal
	 */
	public abstract int getBreedLimit();

	// given a direction, get the location of it
	protected Location getLocation(World world, Direction dir) {
		if (world == null) {
			throw new NullPointerException("World must not be null.");
		} else if (dir == null) {
			throw new NullPointerException("Direction must not be null.");
		}

		Location currLoc = world.getLocation(this);
		Location nextLoc = new Location(currLoc, dir);
		return nextLoc;
	}

	// because rabbit and fox eat different things, so we need an abstract
	// method to represent the animalEat process
	protected abstract void animalEat(World world, Object food);

	/**
	 * check whether there is something edible in the direction
	 * 
	 * @param world
	 * @param dir
	 */
	public void eat(World world, Direction dir) {
		//remainEnergy--;
		Location nextLoc = getLocation(world, dir);
		// how to check whether a food is edible or not
		if (world.isValidLocation(nextLoc) && world.getThing(nextLoc) != null) {
			Object food = world.getThing(nextLoc);
			animalEat(world, food);
			world.remove(food);
		}
	}

	/**
	 * check whether abstract animal could move in the direction
	 * 
	 * @param world
	 * @param dir
	 */
	public void move(World world, Direction dir) {
		//remainEnergy--;
		Location nextLoc = getLocation(world, dir);
		if (world.isValidLocation(nextLoc) && world.getThing(nextLoc) == null) {
			// then move to the new location
			world.remove(this);
			world.add(this, nextLoc);
		}
	}

	/**
	 * check whether abstract animal could breed in the direction
	 * 
	 * @param world
	 * @param dir
	 */
	public void breed(World world, Direction dir) {
		//remainEnergy--;
		if (remainEnergy >= this.getBreedLimit()) {
			Location childLoc = getLocation(world, dir);
			if (world.isValidLocation(childLoc)
					&& world.getThing(childLoc) == null) {
				int init = remainEnergy / 2;
				remainEnergy -= init;
				// the initial energy for child is different, so make an
				// abstract function
				AbstractAnimal child = breedChild(init);
				world.add(child, childLoc);
			}
		}
	}

	// abstract method to breed baby
	protected abstract AbstractAnimal breedChild(int energy);
}
