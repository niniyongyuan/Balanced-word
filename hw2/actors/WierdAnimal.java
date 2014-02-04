/**
 * @author: Lina Li
 * Section: B
 * Andrew ID: linal
 * 15-214
 */
package edu.cmu.cs.cs214.hw2.actors;

import edu.cmu.cs.cs214.hw2.ai.WieldAI;
import edu.cmu.cs.cs214.hw2.staff.interfaces.AI;
import edu.cmu.cs.cs214.hw2.staff.interfaces.Wield;
import edu.cmu.cs.cs214.hw2.staff.interfaces.World;

public class WierdAnimal extends AbstractAnimal implements Wield {

	private static final int WIELD_MAX_ENERGY = 300;
	private static final int WIELD_VIEW_RANGE = 8;
	private static final int WIELD_BREED_LIMIT = WIELD_MAX_ENERGY * 3 / 4;
	private static final int WIELD_COOL_DOWN = 4;
	private static final int WIELD_INITAL_ENERGY = WIELD_MAX_ENERGY * 1 / 2;

	/**
	 * constructor for Wield who eat grass and rabbit
	 */
	public WierdAnimal() {
		super(WIELD_INITAL_ENERGY);

	}

	/**
	 * constructor for baby wield with initial energy
	 * 
	 * @param energy
	 */
	public WierdAnimal(int energy) {
		super(energy);
	}

	@Override
	public int getViewRange() {
		// TODO Auto-generated method stub
		return WIELD_VIEW_RANGE;
	}

	@Override
	public int getCoolDown() {
		// TODO Auto-generated method stub
		return WIELD_COOL_DOWN;
	}

	@Override
	protected AI getMyAI() {
		// TODO Auto-generated method stub
		return new WieldAI();
	}

	@Override
	public int getMaxEnergy() {
		// TODO Auto-generated method stub
		return WIELD_MAX_ENERGY;
	}

	@Override
	public int getBreedLimit() {
		// TODO Auto-generated method stub
		return WIELD_BREED_LIMIT;
	}

	@Override
	protected void animalEat(World world, Object food) {
		// TODO Auto-generated method stub
		if (food instanceof RabbitImpl) {
			int inputEnergy = ((RabbitImpl) food).getEnergyValue();
			if (this.getEnergy() + inputEnergy > WIELD_MAX_ENERGY) {
				setEnergy(WIELD_MAX_ENERGY);
			} else {
				setEnergy(this.getEnergy() + inputEnergy);
			}
		}

		if (food instanceof Grass) {
			int inputEnergy = ((Grass) food).getEnergyValue();
			if (this.getEnergy() + inputEnergy > WIELD_MAX_ENERGY) {
				setEnergy(WIELD_MAX_ENERGY);
			} else {
				setEnergy(this.getEnergy() + inputEnergy);
			}
		}

	}

	@Override
	protected AbstractAnimal breedChild(int energy) {
		// TODO Auto-generated method stub
		return new WierdAnimal(energy);
	}

}
