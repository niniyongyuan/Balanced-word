/**
 * @author: Lina Li
 * Section: B
 * Andrew ID: linal
 * 15-214
 */
package edu.cmu.cs.cs214.hw2.ai;

import edu.cmu.cs.cs214.hw2.staff.interfaces.AI;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Actor;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Command;
import edu.cmu.cs.cs214.hw2.staff.interfaces.World;
import edu.cmu.cs.cs214.hw2.staff.util.Direction;
import edu.cmu.cs.cs214.hw2.staff.util.Location;

public abstract class AbstractAI implements AI {

	/**
	 * constructor for AbstractAI
	 */
	public AbstractAI() {

	}

	// helper function to check whether it is a valid space and whether it
	// is empty
	private boolean checkLocation(World world, Location location) {
		if (world.isValidLocation(location) && world.getThing(location) == null) {
			return true;
		}
		return false;
	}

	// given an actor, use helper function to check whether there is
	// empty space in the adjacent area and move there
	protected Direction checkAdjacent(World world, Actor actor) {
		Location loc = world.getLocation(actor);
		if (loc == null) {
			throw new IllegalArgumentException("Invalid input location");
		}
		int x = loc.getX();
		int y = loc.getY();
		Location north = new Location(x - 1, y);
		Location south = new Location(x + 1, y);
		Location west = new Location(x, y - 1);
		Location east = new Location(x, y + 1);

		if (checkLocation(world, north)) {
			return Direction.NORTH;
		} else if (checkLocation(world, south)) {
			return Direction.SOUTH;
		} else if (checkLocation(world, west)) {
			return Direction.WEST;
		} else if (checkLocation(world, east)) {
			return Direction.EAST;
		}
		return null;
	}

	// helper function to check the target type, we use 0 to represent grass
	// 1 for rabbit, 2 for fox
	protected abstract boolean checkTarget(Object target, int type);

	// in the view range try to find the target with smallest distance
	protected Object seekClosestTarget(World world, Actor actor, int type) {
		Location loc = world.getLocation(actor);
		int x = loc.getX();
		int y = loc.getY();
		Object target = null;
		int viewRange = actor.getViewRange();
		// maxDist is the distance from the actor to the fastest point in the
		// square
		int maxDist = 2 * viewRange;
		// check each position in the view, find the closest target you are
		// looking for
		for (int i = x - viewRange; i < x + viewRange; i++) {
			for (int j = y - viewRange; j < y + viewRange; j++) {
				Location targetLocation = new Location(i, j);
				if (world.isValidLocation(targetLocation)
						&& world.getThing(targetLocation) != null) {
					Object targetObject = world.getThing(targetLocation);
					if (checkTarget(targetObject, type)) {
						// if the target is adjacent
						if (loc.distanceTo(targetLocation) == 1) {
							return targetObject;
						} else if (loc.distanceTo(targetLocation) < maxDist) {
							maxDist = loc.distanceTo(targetLocation);
							target = targetObject;
						}
					}
				}
			}
		}
		return target;
	}

	/**
	 * abstract act command
	 */
	public abstract Command act(World world, Actor actor);
}
