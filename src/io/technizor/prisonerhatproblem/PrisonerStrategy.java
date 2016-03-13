package io.technizor.prisonerhatproblem;

/**
 * Represents a strategy of a prisoner with a limited information model.
 * 
 * @author Technizor
 *
 */
@FunctionalInterface
public interface PrisonerStrategy {
	/**
	 * Takes the prisoner information model and makes a guess based on the
	 * information known to the prisoner.
	 * 
	 * @param model
	 *            The prisoner information model.
	 * @return The hat value that the prisoner should guess, zero-based. (i.e.
	 *         if there are 1001 hats, valid answers range is [0, 1000]).
	 */
	public int executeStrategy(PrisonerInformationModel model);
}
