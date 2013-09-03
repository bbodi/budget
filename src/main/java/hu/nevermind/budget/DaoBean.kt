package hu.nevermind.budget

import java.io.Serializable
import javax.ejb.Stateless
import javax.persistence.PersistenceContext
import javax.persistence.EntityManager
import hu.nevermind.budget.model.UserInfo
import hu.nevermind.budget.model.MoneyBox
import hu.nevermind.budget.transaction.MoneyFlow
import javax.ejb.LocalBean
import javax.inject.Named

LocalBean
Stateless
//Named
open class DaoBean : Serializable {
	val serialVersionUID: Long = -8977385628111530152

	PersistenceContext(unitName = "persistenceUnit")
	open var em: EntityManager? = null;

    open fun loadUsers(): MutableList<UserInfo> {
        return UserInfo.loadAllUsers(em) as MutableList<UserInfo>;
    }

	open fun persist(final moneyBox: Any) {
		em?.persist(moneyBox);
	}

	open fun existUser(final username: String, final password: String): Boolean {
		return UserInfo.existUser(em, username, password);
	}

	open fun loadUser(final username: String): UserInfo {
		val userInfo = UserInfo.findUser(em, username);
		if (userInfo == null) {
			throw NullPointerException();
		}
		userInfo.getMoneyBoxes()?.forEach { it.setMoneyFlows(loadMoneyFlows(it)) }
		return userInfo
	}

	open fun loadMoneyFlows(mb: MoneyBox): List<MoneyFlow> {
		val flows = MoneyFlow.loadMoneyFlows(em, mb);
		if (flows == null) {
			throw NullPointerException();
		}
		return flows;
	}

	open fun delete(final entity: Any) {
		em?.remove(em?.merge(entity));
	}

	open fun merge(final entity: Any) {
		em?.merge(entity);
	}

}