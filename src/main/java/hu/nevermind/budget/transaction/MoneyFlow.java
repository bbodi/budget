package hu.nevermind.budget.transaction;


import hu.nevermind.budget.JodaDateTimeConverter;
import hu.nevermind.budget.budget.Category;
import hu.nevermind.budget.model.MoneyBox;
import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Entity
@Converter(name = "jodaConverter", converterClass = JodaDateTimeConverter.class)
@NamedQueries(
		@NamedQuery(name="MoneyFlow.findBy", query = "SELECT mf FROM MoneyFlow mf WHERE mf.moneyBox = :moneyBox")
)
public class MoneyFlow implements Serializable {

	private static final long serialVersionUID = -2687362889126063129L;
	@Column
	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(nullable = false)
    private int amount;

	@Column
    private String memo;

	@JoinColumn(name = "category", nullable = false)
    private Category category;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "MONEYBOX_ID", referencedColumnName = "id", nullable = false)
	private MoneyBox moneyBox;

	public static List<MoneyFlow> loadMoneyFlows(final EntityManager em, final MoneyBox mb) {
		return em.createNamedQuery("MoneyFlow.findBy").setParameter("moneyBox", mb).getResultList();
	}

	public MoneyFlow() {

	}

	public MoneyFlow(final LocalDate date, final int amount, final String memo, final Category category) {
		Objects.requireNonNull(date);
		this.date = new Date(date.toDate().getTime());
		this.amount = amount;
		this.memo = memo;
		this.category = category;
	}

	public MoneyFlow(final LocalDate date, final int amount, final String memo, final Category category, final MoneyBox moneyBox) {
		this(date, amount, memo, category);
		this.moneyBox = moneyBox;
	}


	public Date getDate() {
        return date;
    }


    public int getAmount() {
        return amount;
	}


    public String getMemo() {
        return memo;
    }


    public Category getCategory() {
        return category;
    }

    public long getId() {
        return id;
    }

	public void setDate(final Date date) {
		this.date = date;
	}

	public void setAmount(final int amount) {
		this.amount = amount;
	}

	public void setMemo(final String memo) {
		this.memo = memo;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "MoneyFlow{" +
				"id=" + id +
				", date=" + date +
				", amount=" + amount +
				", memo='" + memo + '\'' +
				", category=" + category +
				'}';
	}

	public MoneyBox getMoneyBox() {
		return moneyBox;
	}

	public void setMoneyBox(final MoneyBox moneyBox) {
		this.moneyBox = moneyBox;
	}
}