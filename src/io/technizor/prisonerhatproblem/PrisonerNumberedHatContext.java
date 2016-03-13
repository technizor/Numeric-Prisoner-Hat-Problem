package io.technizor.prisonerhatproblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A model of the Prisoners with Numbered Hats problem. Models the lack of
 * information of the prisoners.
 * 
 * @author Technizor
 *
 */
public class PrisonerNumberedHatContext {
	private final boolean statusKnown;
	private final ArrayList<Integer> hats;
	private final ArrayList<Integer> numbersSaid;
	private final boolean[] prisonerSurvived;
	private final boolean[] isNumberSaid;
	private final int numPrisoners;
	private final int numHats;

	private int remainingPrisoners;
	private int survivalCount;
	private int deathCount;

	/**
	 * Creates a new prisoner puzzle with the specified number of prisoners. The
	 * number of hats will be 1 more than the number of prisoners specified.
	 * Whether the prisoners know the status of another prisoner after they have
	 * guessed can be set.
	 * 
	 * @param statusKnown
	 *            Whether the prisoners should know the status of a prisoner
	 *            after they have guessed (i.e. right or wrong).
	 * @param numPrisoners
	 *            The number of prisoners to line up.
	 */
	public PrisonerNumberedHatContext(boolean statusKnown, int numPrisoners) {
		this.statusKnown = statusKnown;
		this.numHats = numPrisoners + 1;
		this.numPrisoners = numPrisoners;
		prisonerSurvived = new boolean[numPrisoners];
		numbersSaid = new ArrayList<>(numPrisoners);
		isNumberSaid = new boolean[numHats];
		this.remainingPrisoners = numPrisoners - 1;
		this.survivalCount = 0;
		this.deathCount = 0;

		hats = new ArrayList<>(numHats);
		for (int v = 0; v < numHats; v++)
			hats.add(v);
		Collections.shuffle(this.hats);
	}

	/**
	 * Executes the next prisoner's strategy for guessing their hat number.
	 * 
	 * @param strategy
	 *            The strategy to use with the information known to the
	 *            prisoner.
	 */
	public void executeStrategy(PrisonerStrategy strategy) {
		int nextValue = strategy.executeStrategy(new PrisonerInformationModel(
				this.statusKnown, this.hats, numbersSaid, prisonerSurvived,
				isNumberSaid, numPrisoners, numHats, remainingPrisoners,
				survivalCount, deathCount));
		this.checkNext(nextValue);
	}

	/**
	 * Declares the final answer for the next prisoner, and advances to the next
	 * prisoner.
	 * 
	 * @param number
	 *            The value to declare as their hat guess.
	 * @return The result of their guess (true = correct, false = incorrect).
	 */
	private boolean checkNext(int number) {
		if (!hasPrisonersRemaining())
			throw new IllegalStateException("Already finished all prisoners.");
		if (number >= numberOfHats())
			throw new IllegalArgumentException("Hat value is out of bounds.");
		numbersSaid.add(number);
		boolean result;
		if (isNumberSaid[number]) {
			result = false;
		} else {
			isNumberSaid[number] = true;
			result = hats.get(remainingPrisoners) == number;
		}
		prisonerSurvived[remainingPrisoners] = result;
		if (result)
			survivalCount++;
		else
			deathCount++;
		remainingPrisoners--;
		return result;
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
	 * Summarizes the current status of the prisoners.
	 * 
	 * @return
	 */
	public String getStatus() {
		return String.format("%d saved, %d lost, %d remaining. %d total.",
				prisonersSaved(), prisonersLost(), prisonersRemaining(),
				numberOfPrisoners());
	}
}
