/**
 * @author: Lina Li
 * Section: B
 * Andrew ID: linal
 * 15-214
 */
package edu.cmu.cs.cs214.hw2.ai;

import edu.cmu.cs.cs214.hw2.actors.Grass;
import edu.cmu.cs.cs214.hw2.actors.RabbitImpl;
import edu.cmu.cs.cs214.hw2.commands.BreedCommand;
import edu.cmu.cs.cs214.hw2.commands.EatCommand;
import edu.cmu.cs.cs214.hw2.commands.MoveCommand;
import edu.cmu.cs.cs214.hw2.staff.interfaces.AI;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Actor;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Animal;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Command;
import edu.cmu.cs.cs214.hw2.staff.interfaces.World;
import edu.cmu.cs.cs214.hw2.staff.util.Direction;
import edu.cmu.cs.cs214.hw2.staff.util.Location;

public class WieldAI extends AbstractAI implements AI {

	public WieldAI() {
	}

	@Override
	protected boolean checkTarget(Object target, int type) {
		// TODO Auto-generated method stub
		if (type == 1 && target instanceof RabbitImpl) {
			return true;
		} else if (type == 0 && target instanceof Grass) {
			return true;
		}
		return false;
	}

	@Override
	public Command act(World world, Actor actor) {
		// TODO Auto-generated method stub
		Command command = null;
		Animal animal = (Animal) actor;
		Location role = world.getLocation(actor);
		Object rabbit = seekClosestTarget(world, actor, 1);
		Object grass = seekClosestTarget(world, actor, 0);
		// first eat rabbit
		if (rabbit != null) {
			Location rabbitLoc = world.getLocation(rabbit);
			int dist = role.distanceTo(rabbitLoc);
			Direction rabbitDir = role.dirTo(rabbitLoc);
			// if the grass is adjacent
			if (dist == 1) {
				command = new EatCommand(rabbitDir);
			} else {
				command = new MoveCommand(rabbitDir);
			}
		}
		if (grass != null) {
			Location grassLoc = world.getLocation(grass);
			int dist = role.distanceTo(grassLoc);
			Direction grassDir = role.dirTo(grassLoc);
			// if the grass is adjacent
			if (dist == 1) {
				command = new EatCommand(grassDir);
			} else {
				command = new MoveCommand(grassDir);
			}
		}
		// breed if possible
		else if (animal.getEnergy() >= animal.getBreedLimit()) {
			Direction dir = checkAdjacent(world, actor);
			if (dir != null) {
				command = new BreedCommand(dir);
			}
		}
		// else walk around to consume some energy
		else {
			Direction[] allDirections = Direction.values();
			int dirIndex = (int) (Math.random() * allDirections.length);
			command = new MoveCommand(allDirections[dirIndex]);
		}
		return command;
	}
}