/**
 * @author: Lina Li
 * Section: B
 * Andrew ID: linal
 * 15-214
 */
package edu.cmu.cs.cs214.hw2.ai;

import edu.cmu.cs.cs214.hw2.actors.Grass;
import edu.cmu.cs.cs214.hw2.commands.BreedCommand;
import edu.cmu.cs.cs214.hw2.commands.EatCommand;
import edu.cmu.cs.cs214.hw2.commands.MoveCommand;
import edu.cmu.cs.cs214.hw2.staff.interfaces.AI;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Actor;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Animal;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Command;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Fox;
import edu.cmu.cs.cs214.hw2.staff.interfaces.World;
import edu.cmu.cs.cs214.hw2.staff.util.Direction;
import edu.cmu.cs.cs214.hw2.staff.util.Location;

public class RabbitAI extends AbstractAI implements AI {

	/**
	 * constructor for RabbitAI
	 */
	public RabbitAI() {
	}

	@Override
	protected boolean checkTarget(Object target, int type) {
		// TODO Auto-generated method stub
		if (type == 0 && target instanceof Grass) {
			return true;
		} else if (type == 2 && target instanceof Fox) {
			return true;
		}
		return false;
	}

	// in order to calculate the path to run away from fox
	private Direction getOppositeDirection(Direction dir) {
		if (dir == Direction.EAST) {
			dir = Direction.WEST;
		} else if (dir == Direction.WEST) {
			dir = Direction.EAST;
		} else if (dir == Direction.NORTH) {
			dir = Direction.SOUTH;
		} else {
			dir = Direction.NORTH;
		}
		return dir;
	}

	@Override
	public Command act(World world, Actor actor) {
		Command command = null;
		Animal animal = (Animal) actor;
		Location role = world.getLocation(actor);
		// if the remain energy if enough, try to breed next generation
		if (animal.getEnergy() >= animal.getBreedLimit()) {
			Direction dir = checkAdjacent(world, actor);
			if (dir != null) {
				command = new BreedCommand(dir);
			}
		}
		// if the energy is not enough, eat grass
		else {
			Object grass = seekClosestTarget(world, actor, 0);
			if (grass != null) {
				Location foodlLoc = world.getLocation(grass);
				int dist = role.distanceTo(foodlLoc);
				Direction foodDir = role.dirTo(foodlLoc);
				// if the grass is adjacent
				if (dist == 1) {
					command = new EatCommand(foodDir);
				} else {
					command = new MoveCommand(foodDir);
				}

			}
		}
		// try to run away from the fox
		Object fox = seekClosestTarget(world, actor, 2);
		if (fox != null) {
			Location foxLoc = world.getLocation(fox);
			int dist = role.distanceTo(foxLoc);
			Direction foxDir = role.dirTo(foxLoc);
			int foxSpeed = ((Actor) fox).getCoolDown();
			int rabSpeed = actor.getCoolDown();
			// if the fox is adjacent, run away
			// need to consider the cool down time. Fox is faster than rabbit
			if (dist < (rabSpeed - foxSpeed)) {
				Direction runDir = getOppositeDirection(foxDir);
				command = new MoveCommand(runDir);
			}
		}
		return command;
	}
}
