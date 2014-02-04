package edu.cmu.cs.cs214.hw2.ai;

import edu.cmu.cs.cs214.hw2.commands.MoveCommand;
import edu.cmu.cs.cs214.hw2.staff.interfaces.AI;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Actor;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Command;
import edu.cmu.cs.cs214.hw2.staff.interfaces.World;
import edu.cmu.cs.cs214.hw2.staff.util.Direction;

/**
 * The AI for a Gnat. This AI will pick a random direction and then return a
 * command which moves in that direction.
 *
 * This class serves as a simple example for how other AIs should be
 * implemented.
 */
public class GnatAI implements AI {

	/*
	 * Your AI implementation must provide a public default constructor so that
	 * the it can be initialized outside of the package.
	 */
	public GnatAI() {
	}

	/*
	 * GnatAI is dumb. It disregards its surroundings and simply tells the Gnat
	 * to move in a random direction.
	 */
	@Override
	public Command act(World world, Actor actor) {
		Direction[] allDirections = Direction.values();
		int dir = (int) (Math.random() * allDirections.length);
		return new MoveCommand(allDirections[dir]);
	}
}
