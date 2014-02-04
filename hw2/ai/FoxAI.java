/**
 * @author: Lina Li
 * Section: B
 * Andrew ID: linal
 * 15-214
 */
package edu.cmu.cs.cs214.hw2.ai;

import edu.cmu.cs.cs214.hw2.commands.BreedCommand;
import edu.cmu.cs.cs214.hw2.commands.EatCommand;
import edu.cmu.cs.cs214.hw2.commands.MoveCommand;
import edu.cmu.cs.cs214.hw2.staff.interfaces.AI;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Actor;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Animal;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Command;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Rabbit;
import edu.cmu.cs.cs214.hw2.staff.interfaces.World;
import edu.cmu.cs.cs214.hw2.staff.util.Direction;
import edu.cmu.cs.cs214.hw2.staff.util.Location;

public class FoxAI extends AbstractAI implements AI {

	/**
	 * constructor for FoxAI
	 */
	public FoxAI() {
	}

	@Override
	protected boolean checkTarget(Object target, int type) {
		// TODO Auto-generated method stub
		if (type == 1 && target instanceof Rabbit) {
			return true;
		}
		return false;
	}

	@Override
	public Command act(World world, Actor actor) {
		Command command = null;
		Animal animal = (Animal) actor;
		Location role = world.getLocation(actor);
		Object rabbit = seekClosestTarget(world, actor, 1);
		// if the remain energy if enough, try to breed next generation
		if (animal.getEnergy() >= animal.getBreedLimit()) {
			Direction dir = checkAdjacent(world, actor);
			if (dir != null) {
				command = new BreedCommand(dir);
			}
		}
		// then the priority for fox is chasing rabbits, eat them
		else if (rabbit != null) {
			Location foodLoc = world.getLocation(rabbit);
			int dist = role.distanceTo(foodLoc);
			Direction foodDir = role.dirTo(foodLoc);
			// if the grass is adjacent
			if (dist == 1) {
				command = new EatCommand(foodDir);
			} else {
				command = new MoveCommand(foodDir);
			}

		}
		// else do nothing to save energy
		return command;
	}

}
