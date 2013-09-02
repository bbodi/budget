package hu.nevermind.budget;

import org.primefaces.event.SelectEvent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ManagedBean
@SessionScoped
public class TimelineBean implements Serializable {

	private static final long serialVersionUID = -8200569421492618338L;
	private List<String> selectedMonth;
	private final String[] monthNames = {"jan", "feb", "már", "ápr", "máj", "jún", "júl", "aug", "sze", "okt", "nov", "dec"};

	private int value;

	public List<String> getSelectedMonth() {
		return selectedMonth;
	}
	public void setSelectedMonth(final List<String> selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	public String[] getMonthNames() {
		return monthNames;
	}

	public void updateValue() {
		value += 1;
	}

	public int getValue() {
		return value;
	}

	public void setValue(final int value) {
		this.value = value;
	}

	public List<String> complete(final String input) {
		final List<String> result = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			result.add(input + i);
		}
		return result;
	}

	public void handleSelect(final SelectEvent event) {
		final Object selectedObject = event.getObject();
		final FacesMessage msg = new FacesMessage("Selected", selectedObject.toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
