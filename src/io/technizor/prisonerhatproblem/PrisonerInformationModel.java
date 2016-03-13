package io.technizor.prisonerhatproblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The information model of a prisoner.
 * 
 * @author Technizor
 *
 */
public final class PrisonerInformationModel {
	private final List<Integer> hats;
	private final List<Integer> numbersSaid;
	private final boolean[] prisonerSurvived;
	private final boolean[] isNumberSaid;
	private final int numPrisoners;
	private final int numHats;

	private final int remainingPrisoners;
	private final int survivalCount;
	private final int deathCount;

	protected PrisonerInformationModel(List<Integer> hats2,
			List<Integer> numbersSaid2, boolean[] prisonerSurvived2,
			boolean[] isNumberSaid2, int numPrisoners2, int numHats2,
			int remainingPrisoners2, int survivalCount2, int deathCount2) {
		this.hats = Collections.unmodifiableList(hats2);
		this.numbersSaid = Collections.unmodifiableList(numbersSaid2);
		this.prisonerSurvived = Arrays.copyOf(prisonerSurvived2,
				prisonerSurvived2.length);
		this.isNumberSaid = Arrays.copyOf(isNumberSaid2, isNumberSaid2.length);
		this.numPrisoners = numPrisoners2;
		this.numHats = numHats2;
		this.remainingPrisoners = remainingPrisoners2;
		this.survivalCount = survivalCount2;
		this.deathCount = deathCount2;
	}

	/**
	 * Gets a list of visible hat values for the next prisoner, ordered from
	 * front to back.
	 * 
	 * @return
	 */
	public List<Integer> visibleHatValues() {
		return hats.subList(0, remainingPrisoners);
	}

	public boolean hasSaid(int value) {
		return this.isNumberSaid[value];
	}

	/**
	 * Gets a list of the numbers said, in the order they were said.
	 * 
	 * @return
	 */
	public List<Integer> numbersSaid() {
		ArrayList<Integer> clone = new ArrayList<>(this.numbersSaid);
		return clone;
	}

	/**
	 * Gets a list of the numbers not said, in order of ascending value.
	 * 
	 * @return
	 */
	public List<Integer> numbersNotSaid() {
		ArrayList<Integer> clone = new ArrayList<>(this.hats);
		clone.removeAll(this.numbersSaid);
		Collections.sort(clone);
		return clone;
	}

	public int prisonersSaved() {
		return survivalCount;
	}

	public int prisonersLost() {
		return deathCount;
	}

	public int prisonersRemaining() {
		return remainingPrisoners + 1;
	}

	public int numberOfPrisoners() {
		return numPrisoners;
	}

	public int numberOfHats() {
		return numHats;
	}

	public boolean hasPrisonersRemaining() {
		return prisonersRemaining() > 0;
	}

	public boolean isPrisonerDead(int number) {
		if (number <= prisonersRemaining())
			return false;
		return !this.prisonerSurvived[number - 1];
	}
}
