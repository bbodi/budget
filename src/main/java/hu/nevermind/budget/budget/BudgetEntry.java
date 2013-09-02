package hu.nevermind.budget.budget;

import hu.nevermind.budget.transaction.MoneyFlow;

import java.util.HashSet;
import java.util.Set;

public class BudgetEntry {
	private final Category category;
	private final Set<MoneyFlow> moneyFlows = new HashSet<>();
	private int budgetedValue;

	public BudgetEntry(final Category category) {
		this.category = category;
	}

	public int getBudgetedValue() {
		return budgetedValue;
	}

	public void setBudgetedValue(final int budgetedValue) {
		this.budgetedValue = budgetedValue;
	}

	public int getOutFlows() {
		//return moneyFlows.stream().mapToInt(MoneyFlow::getAmount).sum();
		int sum = 0;
		for (final MoneyFlow tx : moneyFlows) {
			sum += tx.getAmount();
		}
		return sum;
	}

	public void addTrancation(final MoneyFlow tx) {
		moneyFlows.add(tx);
	}

	public Category getCategory() {
		return category;
	}

	public void removeTransactions() {
		moneyFlows.clear();
	}
}
