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
	public static enum PrisonerStatus {
		LIMBO, UNKNOWN, LOST, SAVED
	}

	private final boolean statusKnown;
	private final List<Integer> hats;
	private final List<Integer> numbersSaid;
	private final boolean[] prisonerSurvived;
	private final boolean[] isNumberSaid;
	private final int numPrisoners;
	private final int numHats;

	private final int remainingPrisoners;
	private final int survivalCount;
	private final int deathCount;

	protected PrisonerInformationModel(boolean statusKnown,
			List<Integer> hats2, List<Integer> numbersSaid2,
			boolean[] prisonerSurvived2, boolean[] isNumberSaid2,
			int numPrisoners2, int numHats2, int remainingPrisoners2,
			int survivalCount2, int deathCount2) {
		this.statusKnown = statusKnown;
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

	/**
	 * Gets the status of the prisoner at the specified position.
	 * 
	 * @param index
	 *            The zero-based index of the prisoner. i.e. the front prisoner
	 *            (going last) is numbered 0, while the back prisoner (going
	 *            first) is numbered one less than the total number of
	 *            prisoners.
	 * @return
	 */
	public PrisonerStatus getPrisonerStatus(int index) {
		if (index < 0 || index >= numberOfPrisoners()) {
			throw new IllegalArgumentException(
					"No such prisoner with the index " + index);
		}
		if (index < prisonersRemaining())
			return PrisonerStatus.LIMBO;
		if (this.statusKnown) {

			return this.prisonerSurvived[index] ? PrisonerStatus.SAVED
					: PrisonerStatus.LOST;
		}
		return PrisonerStatus.UNKNOWN;
	}
}
