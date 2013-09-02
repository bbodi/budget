package hu.nevermind.budget.budget;


import hu.nevermind.budget.transaction.MoneyFlow;
import org.joda.time.LocalDate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Budget {

	private final LocalDate date;
	private final Map<Category, BudgetEntry> entries = new HashMap<>();
	private final Set<MoneyFlow> moneyFlows = new HashSet<>();
	private Budget prevMonthBudget;

	public Budget(final LocalDate date, final Budget prevMonthBudget) {
		this.date = date;
		this.prevMonthBudget = prevMonthBudget;
	}

	public LocalDate getDate() {
		return date;
	}

	public int getBudgeted(final Category category) {
		final BudgetEntry entry = entries.get(category);
		if (entry == null) {
			return 0;
		}
		return entries.get(category).getBudgetedValue();

	}

	public int getOutFlows(final Category category) {
		final BudgetEntry entry = entries.get(category);
		if (entry == null) {
			return 0;
		}
		return entries.get(category).getOutFlows();
	}

	public void setBudgetedValue(final Category category, final int budgetedValue) {
		final BudgetEntry entry = getOrCreate(category);
		entry.setBudgetedValue(budgetedValue);
	}

	private BudgetEntry getOrCreate(final Category category) {
		BudgetEntry entry = entries.get(category);
		if (entry == null) {
			entry = new BudgetEntry(category);
			entries.put(category, entry);
		}
		return entry;
	}

	public void addTransaction(final MoneyFlow tx) {
		final BudgetEntry entry = getOrCreate(tx.getCategory());
		entry.addTrancation(tx);
		moneyFlows.add(tx);
	}

	public int getIncome() {
		int sum = 0;
		for (final MoneyFlow tx : moneyFlows) {
			if (tx.getAmount() > 0) {
				sum += tx.getAmount();
			}
		}
		return 0;
		//return moneyFlows.stream().mapToInt(MoneyFlow::getAmount).filter(amount -> amount > 0).sum();
	}

	public int getBudgeted() {
		//return -(entries.values().stream().mapToInt(BudgetEntry::getBudgetedValue).sum());
		int sum = 0;
		for (final BudgetEntry entry : entries.values()) {
			sum += entry.getBudgetedValue();
		}
		return sum;
	}

	public int getOverSpent() {
		/*final int overspending = entries.keySet().stream().mapToInt(cat -> {
			final int spentInCategory = moneyFlows.stream()
					.filter(tx -> tx.getCategoryName().equals(cat))
					.mapToInt(MoneyFlow::getAmount)
					.filter(amount -> amount < 0)
					.sum();
			final int overSpentInCategory = (-spentInCategory) - entries.get(cat).getBudgetedValue();
			if (overSpentInCategory > 0) {
				return overSpentInCategory;
			}
			return 0;
		}).sum();*/
		int overspending = 0;
		for (final Category cat : entries.keySet()) {
			int spentInCategory = 0;
			for (final MoneyFlow tx : moneyFlows) {
				if (tx.getCategory().equals(cat) == false) {
					continue;
				}
				if (tx.getAmount() < 0) {
					spentInCategory += tx.getAmount();
				}
			}
			final int overSpentInCategory = (-spentInCategory) - entries.get(cat).getBudgetedValue();
			if (overSpentInCategory > 0) {
				overspending += overSpentInCategory;
			}
		}

		return -overspending;
	}

	public int getAllSpent() {
		//return moneyFlows.stream().mapToInt(MoneyFlow::getAmount).filter(amount -> amount < 0).sum();
		int sum = 0;
		for (final MoneyFlow tx : moneyFlows) {
			if (tx.getAmount() < 0) {
				sum += tx.getAmount();
			}
		}
		return sum;
	}

	public int getOverBudgeted() {
		int over = getBudgeted() - getIncome();
		if (prevMonthBudget != null) {
			over += prevMonthBudget.getOverBudgeted();
		}
		return over;
	}

	public void removeTransactions() {
		//entries.values().forEach(BudgetEntry::removeTransactions);
		for (final BudgetEntry entry : entries.values()) {
			entry.removeTransactions();;
		}
		moneyFlows.clear();
	}

	public void setPrevMonthBudget(final Budget prevMonthBudget) {
		this.prevMonthBudget = prevMonthBudget;
	}

	public Budget getPrevMonthBudget() {
		return prevMonthBudget;
	}
}
