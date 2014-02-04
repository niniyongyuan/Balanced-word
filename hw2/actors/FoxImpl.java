package edu.cmu.cs.cs214.hw2.actors;

import edu.cmu.cs.cs214.hw2.ai.FoxAI;
import edu.cmu.cs.cs214.hw2.staff.interfaces.AI;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Fox;
import edu.cmu.cs.cs214.hw2.staff.interfaces.World;

public class FoxImpl extends AbstractAnimal implements Fox {
	private static final int FOX_MAX_ENERGY = 160;
	private static final int FOX_VIEW_RANGE = 5;
	private static final int FOX_BREED_LIMIT = FOX_MAX_ENERGY * 3 / 4;
	private static final int FOX_COOL_DOWN = 2;
	private static final int FOX_INITAL_ENERGY = FOX_MAX_ENERGY * 1 / 2;

	/**
	 * constructor for FoxImpl
	 */
	public FoxImpl() {
		super(FOX_INITAL_ENERGY);
	}

	/**
	 * constructor for child with initial energy
	 * 
	 * @param energy
	 */
	public FoxImpl(int energy) {
		super(energy);
	}

	@Override
	public int getViewRange() {
		// TODO Auto-generated method stub
		return FOX_VIEW_RANGE;
	}

	@Override
	public int getCoolDown() {
		// TODO Auto-generated method stub
		return FOX_COOL_DOWN;
	}

	@Override
	protected AI getMyAI() {
		// TODO Auto-generated method stub
		return new FoxAI();
	}

	@Override
	public int getMaxEnergy() {
		// TODO Auto-generated method stub
		return FOX_MAX_ENERGY;
	}

	@Override
	public int getBreedLimit() {
		// TODO Auto-generated method stub
		return FOX_BREED_LIMIT;
	}

	@Override
	protected void animalEat(World world, Object food) {
		// TODO Auto-generated method stub
		if (food instanceof RabbitImpl) {
			int refillEnergy = ((RabbitImpl) food).getEnergyValue();
			if (getEnergy() + refillEnergy > FOX_MAX_ENERGY) {
				setEnergy(FOX_MAX_ENERGY);
			} else {
				setEnergy(getEnergy() + refillEnergy);
			}
		}
	}

	@Override
	protected AbstractAnimal breedChild(int energy) {
		// TODO Auto-generated method stub
		return new FoxImpl(energy);
	}

}
