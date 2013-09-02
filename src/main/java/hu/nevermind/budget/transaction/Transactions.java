package hu.nevermind.budget.transaction;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Transactions {
	public final Set<MoneyFlow> moneyFlows = new HashSet<>();

	public void add(final MoneyFlow tx) {
		moneyFlows.add(tx);
	}

	public void remove(final MoneyFlow tx) {
		moneyFlows.remove(tx);
	}

	public Set<MoneyFlow> getMoneyFlows() {
		return Collections.unmodifiableSet(moneyFlows);
	}
}
