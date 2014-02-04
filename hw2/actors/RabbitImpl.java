package edu.cmu.cs.cs214.hw2.actors;

import edu.cmu.cs.cs214.hw2.ai.RabbitAI;
import edu.cmu.cs.cs214.hw2.staff.interfaces.AI;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Rabbit;
import edu.cmu.cs.cs214.hw2.staff.interfaces.World;

public class RabbitImpl extends AbstractAnimal implements Rabbit {
	private static final int RABBIT_MAX_ENERGY = 20;
	private static final int RABBIT_VIEW_RANGE = 3;
	private static final int RABBIT_BREED_LIMIT = RABBIT_MAX_ENERGY * 2 / 4;
	private static final int RABBIT_ENERGY_VALUE = 20;
	private static final int RABBIT_COOL_DOWN = 4;
	private static final int RABBIT_INITAL_ENERGY = RABBIT_MAX_ENERGY * 1 / 2;

	/**
	 * constructor for RabbitImpl
	 */
	public RabbitImpl() {
		super(RABBIT_INITAL_ENERGY);
	}

	/**
	 * constructor of baby rabbit with half inital_energy
	 * 
	 * @param energy
	 */
	public RabbitImpl(int energy) {
		super(energy);
	}

	@Override
	public int getViewRange() {
		// TODO Auto-generated method stub
		return RABBIT_VIEW_RANGE;
	}

	@Override
	public int getCoolDown() {
		// TODO Auto-generated method stub
		return RABBIT_COOL_DOWN;
	}

	@Override
	public int getEnergyValue() {
		// TODO Auto-generated method stub
		return RABBIT_ENERGY_VALUE;
	}

	@Override
	protected AI getMyAI() {
		// TODO Auto-generated method stub
		return new RabbitAI();
	}

	@Override
	protected void animalEat(World world, Object food) {
		// TODO Auto-generated method stub
		if (food instanceof Grass) {
			int refillEnergy = ((Grass) food).getEnergyValue();
			if (getEnergy() + refillEnergy > RABBIT_MAX_ENERGY) {
				setEnergy(RABBIT_MAX_ENERGY);
			} else {
				setEnergy(getEnergy() + refillEnergy);
			}
		}
	}

	@Override
	protected RabbitImpl breedChild(int energy) {
		// TODO Auto-generated method stub
		return new RabbitImpl(energy);
	}

	@Override
	public int getMaxEnergy() {
		// TODO Auto-generated method stub
		return RABBIT_MAX_ENERGY;
	}

	@Override
	public int getBreedLimit() {
		// TODO Auto-generated method stub
		return RABBIT_BREED_LIMIT;
	}
}
