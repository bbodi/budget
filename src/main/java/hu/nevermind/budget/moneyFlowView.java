package hu.nevermind.budget;

import hu.nevermind.budget.budget.Category;
import hu.nevermind.budget.model.MoneyBox;
import hu.nevermind.budget.transaction.MoneyFlow;
import org.joda.time.LocalDate;
import org.primefaces.event.CellEditEvent;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SessionScoped
@Named
public class MoneyFlowView implements Serializable {

	private static final long serialVersionUID = -5625531407107057419L;

	private List<MoneyFlow> moneyFlows;

	@Inject
	private DaoBean dao;

	private MoneyFlow selectedMoneyFlow;
	private Date date = new Date();
	private String memo;

	@Digits(integer = 10, fraction = 0)
	private String amount;

	private String categoryName;
	private List<Category> categories;

	//@ManagedProperty("#{menuBean.userSession}")
	@Inject
	private UserSession userSession;

	private MoneyBox moneyBox;

	public String show(final MoneyBox moneyBox) {
		this.moneyBox = moneyBox;
        this.moneyBox.setMoneyFlows(dao.loadMoneyFlows(moneyBox));
		moneyFlows = moneyBox.getMoneyFlows();
		categories = moneyBox.getUserInfo().getCategories();
		return "/moneyFlows.xhtml?faces-redirect=true";
	}

	public void addNewMoneyFlow() {
		Category selectedCategory = null;
		for (final Category cat : categories) {
			if (cat.getName().equals(categoryName)) {
				selectedCategory = cat;
				break;
			}
		}
		if (selectedCategory == null) {
			selectedCategory = new Category(categoryName, null, userSession.getUserInfo());
			dao.persist(selectedCategory);
			categories.add(selectedCategory);
		}
		final MoneyFlow moneyFlow = new MoneyFlow(new LocalDate(date), Integer.parseInt(amount), memo, selectedCategory, moneyBox);
		dao.persist(moneyFlow);
		moneyBox.getMoneyFlows().add(moneyFlow);
	}

	public void onCellEdit(final CellEditEvent event) {
		final Object oldValue = event.getOldValue();
		final Object newValue = event.getNewValue();

		final boolean isChanged = newValue != null && !newValue.equals(oldValue);
		if(isChanged) {
			dao.merge(moneyBox.getMoneyFlows().get(event.getRowIndex()));
			sendNotification("Change savned", null);
		}
	}

	private void sendNotification(final String mainMsg, final String detailMsg) {
		final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, mainMsg, detailMsg);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void deleteRow() {
		dao.delete(selectedMoneyFlow);
		moneyBox.getMoneyFlows().remove(selectedMoneyFlow);
	}

	public List<MoneyFlow> getMoneyFlows() {
		return moneyFlows;
	}

	public void setMoneyFlows(final List<MoneyFlow> moneyFlows) {
		this.moneyFlows = moneyFlows;
	}

	public MoneyFlow getSelectedMoneyFlow() {
		return selectedMoneyFlow;
	}

	public Date getDate() {
		return date;
	}

	public String getMemo() {
		return memo;
	}

	public String getAmount() {
		return amount;
	}

	public void setSelectedMoneyFlow(final MoneyFlow selectedMoneyFlow) {
		this.selectedMoneyFlow = selectedMoneyFlow;
	}

	public void setDate(final Date  date) {
		this.date = date;
	}

	public void setMemo(final String memo) {
		this.memo = memo;
	}

	public void setAmount(final String amount) {
		this.amount = amount;
	}

	public String  getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(final String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(final List<Category> categories) {
		this.categories = categories;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(final UserSession userSession) {
		this.userSession = userSession;
	}
}
