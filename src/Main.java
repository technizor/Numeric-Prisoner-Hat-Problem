import io.technizor.prisonerhatproblem.PrisonerInformationModel;
import io.technizor.prisonerhatproblem.PrisonerNumberedHatContext;

import java.util.List;

public class Main {
	public static void main(final String[] args) {
		PrisonerNumberedHatContext problem = new PrisonerNumberedHatContext(
				1000);
		if (!problem.executeStrategy((PrisonerInformationModel info) -> {
			int cTotal = info.visibleHatValues().stream()
					.mapToInt((Integer value) -> value).sum();
			int cModSum = cTotal % info.numberOfHats();
			return cModSum;
		}))
			System.out.println("A prisoner died.");
		while (problem.hasPrisonersRemaining()) {
			if (!problem
					.executeStrategy((PrisonerInformationModel info) -> {
						int numPrisoners = info.numberOfPrisoners();
						int numHats = info.numberOfHats();
						List<Integer> numsSaid = info.numbersSaid();
						int lastModSum = numsSaid.get(0);
						for (int i = 1; i < numsSaid.size(); i++) {
							lastModSum = ((lastModSum
									- numsSaid.get(info
											.isPrisonerDead(numPrisoners - i) ? 0
											: i) + info.numberOfHats()) % info
									.numberOfHats());
						}

						int cTotal = info.visibleHatValues().stream()
								.mapToInt((Integer value) -> value).sum();
						int cModSum = cTotal % numHats;
						int guess = ((lastModSum - cModSum + info
								.numberOfHats()) % info.numberOfHats());
						if (info.hasSaid(guess)) {
							List<Integer> dummyValues = info.numbersNotSaid();
							dummyValues.removeAll(info.visibleHatValues());
							guess = dummyValues.get(0);
						}
						System.out.println(String.format(
								"Prisoner number %d said %d.",
								info.prisonersRemaining(), guess));
						return guess;
					}))
				System.out.println("A prisoner died.");
		}
		System.out.println(problem.getStatus());
	}
}
