import io.technizor.prisonerhatproblem.PrisonerInformationModel;
import io.technizor.prisonerhatproblem.PrisonerNumberedHatContext;

import java.util.List;

/**
 * 
 * @author Technizor
 *
 */
public class Main {
	public static void main(final String[] args) {
		PrisonerNumberedHatContext problem = new PrisonerNumberedHatContext(
				true, 1000);
		problem.executeStrategy((PrisonerInformationModel info) -> {
			int cTotal = info.visibleHatValues().stream()
					.mapToInt((Integer value) -> value).sum();
			int cModSum = cTotal % info.numberOfHats();
			return cModSum;
		});
		while (problem.hasPrisonersRemaining()) {
			problem.executeStrategy((PrisonerInformationModel info) -> {
				int numPrisoners = info.numberOfPrisoners();
				int numHats = info.numberOfHats();
				List<Integer> numsSaid = info.numbersSaid();
				int lastModSum = numsSaid.get(0);
				for (int i = 1; i < numsSaid.size(); i++) {
					switch (info.getPrisonerStatus(numPrisoners - 1 - i)) {
					case SAVED: {
						lastModSum = ((lastModSum - numsSaid.get(i) + info
								.numberOfHats()) % info.numberOfHats());
						break;
					}
					case LOST: {
						lastModSum = ((lastModSum - numsSaid.get(0) + info
								.numberOfHats()) % info.numberOfHats());
						break;
					}
					case UNKNOWN:
					case LIMBO:
					default: {
						throw new IllegalStateException("Should not occur.");
					}
					}
				}

				int cTotal = info.visibleHatValues().stream()
						.mapToInt((Integer value) -> value).sum();
				int cModSum = cTotal % numHats;
				int guess = ((lastModSum - cModSum + info.numberOfHats()) % info
						.numberOfHats());
				if (info.hasSaid(guess)) {
					List<Integer> dummyValues = info.numbersNotSaid();
					dummyValues.removeAll(info.visibleHatValues());
					guess = dummyValues.get(0);
				}
				System.out.println(String.format("Prisoner number %d said %d.",
						info.prisonersRemaining(), guess));
				return guess;
			});
		}
		System.out.println(problem.getStatus());
	}
}
