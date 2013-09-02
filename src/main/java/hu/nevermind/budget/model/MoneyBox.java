package hu.nevermind.budget.model;

import hu.nevermind.budget.transaction.MoneyFlow;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedQueries({
		@NamedQuery(name = "MoneyBox.find", query = "SELECT mb FROM MoneyBox mb WHERE mb.userInfo = :userInfo"),
})
public class MoneyBox implements Serializable {

	private static final long serialVersionUID = 4382925020053675457L;
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "username", referencedColumnName = "name", nullable = false)
	private UserInfo userInfo;
	@Column
	private String name;
	@Column
	private int amount;
	@OneToMany(mappedBy = "moneyBox", fetch = FetchType.EAGER)
	private List<MoneyFlow> moneyFlows;

	public MoneyBox() {
	}

	public MoneyBox(final String name) {
		this.name = name;
	}

	public MoneyBox(final String name, final int initialValue, final UserInfo userInfo) {
		this.name = name;
		this.amount = initialValue;
		this.userInfo = userInfo;
	}

	public static List<MoneyBox> findForUser(final EntityManager em, final UserInfo userInfo) {
		return em.createNamedQuery("MoneyBox.find", MoneyBox.class).setParameter("userInfo", userInfo).getResultList();
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(final int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "MoneyBox{" +
				"name='" + name + '\'' +
				", amount=" + amount +
				'}';
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(final UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public List<MoneyFlow> getMoneyFlows() {
		return moneyFlows;
	}

	public void setMoneyFlows(final List<MoneyFlow> moneyFlows) {
		this.moneyFlows = moneyFlows;
	}
}
