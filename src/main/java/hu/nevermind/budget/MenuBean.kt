package hu.nevermind.budget

import java.io.Serializable
import javax.faces.application.FacesMessage
import javax.faces.context.FacesContext
import hu.nevermind.budget.model.MoneyBox
import org.primefaces.component.menuitem.MenuItem
import org.primefaces.model.DefaultMenuModel
import hu.nevermind.budget.model.UserInfo
import javax.faces.bean.ManagedProperty
import javax.annotation.PostConstruct
import javax.inject.Named
import javax.enterprise.context.SessionScoped
import javax.inject.Inject
import javax.validation.constraints.Digits
import javax.faces.event.ActionEvent
import javax.faces.event.ActionListener

Named
SessionScoped
open public class MenuBean : Serializable {

	//ManagedProperty(value = "#{userSession}")
	open Inject var userSession: UserSession? = null

	Digits(integer=10, fraction = 0)
	open var newMoneyBoxAmount: Int = 3

	open var newMoneyBoxName: String = ""

	open var menuModel: DefaultMenuModel = DefaultMenuModel()

	PostConstruct open fun initMainMenu() {
		if (userSession == null) {
			throw NullPointerException()
		}
		val user = userSession?.getUserInfo()
		if (user?.getRole() == UserInfo.Role.Admin) {
			menuModel.addMenuItem(createAdnimMenu())
		}
		val budgetMenuItem = MenuItem()
		budgetMenuItem.setValue("Budget")
		budgetMenuItem.setUrl("/moneyFlows.xhtml")
		budgetMenuItem.setIcon("ui-icon-cart")
		menuModel.addMenuItem(budgetMenuItem)

		val logoutMenuItem = MenuItem()
		logoutMenuItem.setValue("Logout")
		//logoutMenuItem.addActionListener(
		//		ActionListener({event -> userSession?.doLogout()})
		//)
		logoutMenuItem.setIcon("ui-icon-circlesmall-close")
		menuModel.addMenuItem(logoutMenuItem)
	}

	private fun fakk(event: ActionEvent): Unit {
		userSession?.doLogout()
	}

	open fun createAdnimMenu(): MenuItem {
		var item = MenuItem();
		item.setValue("Users");
		item.setIcon("ui-icon-person")
		item.setUrl("/users.xhtml");
		return item;
	}

	open public fun clickAccount(): Unit {
		val msg: FacesMessage? = FacesMessage("Selected", "asd")
		FacesContext.getCurrentInstance()?.addMessage(null, msg)
	}

	open public fun getMoneyBoxes(): List<MoneyBox?>? {
		return userSession?.getMoneyBoxes()
	}

	open public fun addMoneyBox(): Unit {
		userSession?.addMoneyBox(newMoneyBoxName, newMoneyBoxAmount)
	}
}
