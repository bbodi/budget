package hu.nevermind.budget

import sunw.io.Serializable
import org.primefaces.model.DefaultMenuModel
import org.primefaces.component.submenu.Submenu
import org.primefaces.component.menuitem.MenuItem
import javax.inject.Named
import javax.enterprise.context.SessionScoped
import hu.nevermind.budget.model.UserInfo
import java.util.ArrayList
import org.primefaces.event.CellEditEvent
import javax.inject.Inject
import javax.annotation.PostConstruct

Named
SessionScoped
open class UsersView : Serializable {

    Inject public open var dao : DaoBean? = null

    open public var selectedUser: UserInfo? =  null

    open public var users: MutableList<UserInfo> = ArrayList<UserInfo>()

    open public var role: String = ""
    open public var name: String = ""
    open public var password: String = ""

    PostConstruct
    open fun init() {
        users = dao?.loadUsers() as MutableList<UserInfo>;
    }

    open fun deleteRow() {

    }

    open fun onCellEdit(event: CellEditEvent) {

    }

    open fun addNewUser() {
        val newUser = UserInfo(name, password, UserInfo.Role.valueOf(role));
        dao?.persist(newUser)
        users.add(newUser)
    }

}