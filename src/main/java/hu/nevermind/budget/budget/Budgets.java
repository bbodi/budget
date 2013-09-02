package hu.nevermind.budget.budget;


import hu.nevermind.budget.transaction.MoneyFlow;
import hu.nevermind.budget.transaction.Transactions;
import org.joda.time.LocalDate;

import java.util.HashMap;
import java.util.Map;

public class Budgets {

	private Budget firstBudget;
	private final Map<LocalDate, Budget> budgets = new HashMap<>();

	private Budget get(final MoneyFlow tx) {
		return get(new LocalDate(tx.getDate()));
	}

	private Budget get(final LocalDate date) {
		final LocalDate yearAndMonth = new LocalDate(date.getYear(), date.getMonthOfYear(), 1);
		return budgets.get(yearAndMonth);
	}

	public Budget getPrevMonthBudget(final Budget budget) {
		if (budget.getPrevMonthBudget() == null) {
			budget.setPrevMonthBudget(getBudget(budget.getDate().minusMonths(1)));
		}
		return budget.getPrevMonthBudget();
	}

	public int getSum(final Budget budget) {
		if (budget.equals(firstBudget)) {
			return 0;
		}
		return getSum(getPrevMonthBudget(budget)) + getPrevMonthBudget(budget).getOverSpent() + budget.getIncome() + budget.getBudgeted();
	}

	public Budget getBudget(final LocalDate date) {
		final LocalDate yearAndMonth = new LocalDate(date.getYear(), date.getMonthOfYear(), 1);
		Budget budget = budgets.get(yearAndMonth);
		if (budget == null) {
			budget = new Budget(yearAndMonth, null);
			budgets.put(yearAndMonth, budget);
			if (firstBudget == null || date.isBefore(firstBudget.getDate())) {
				firstBudget = budget;
			}
		}
		return budget;
	}

	private void addBudget(final Budget budget) {
		budgets.put(budget.getDate(), budget);
		if (firstBudget == null || budget.getDate().isBefore(firstBudget.getDate())) {
			firstBudget = budget;
		}
	}

	public void addTransaction(final MoneyFlow tx) {
		final Budget budget = getBudget(new LocalDate(tx.getDate()));
		budget.addTransaction(tx);
	}

	public void reloadTransactions(final Transactions transactions) {
		removeTransactions();
		//moneyFlows.getMoneyFlows().forEach(this::addTransaction);
		for(final MoneyFlow tx : transactions.getMoneyFlows()) {
			addTransaction(tx);
		}
	}

	private void removeTransactions() {
		//budgets.values().forEach(Budget::removeTransactions);
		for(final Budget budget : budgets.values()) {
			budget.removeTransactions();
		}
	}
}
