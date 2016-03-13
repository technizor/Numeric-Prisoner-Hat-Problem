package io.technizor.prisonerhatproblem;

/**
 * Represents a strategy of a prisoner with a limited information model.
 * 
 * @author Technizor
 *
 */
@FunctionalInterface
public interface PrisonerStrategy {
	public int executeStrategy(PrisonerInformationModel model);
}
