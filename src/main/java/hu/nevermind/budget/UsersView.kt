package hu.nevermind.budget

import sunw.io.Serializable
import org.primefaces.model.DefaultMenuModel
import org.primefaces.component.submenu.Submenu
import org.primefaces.component.menuitem.MenuItem
import javax.inject.Named
import javax.enterprise.context.SessionScoped

Named
SessionScoped
open class UsersView : Serializable {

	open fun init() {
		val model = DefaultMenuModel();

		//First submenu  
		var submenu = Submenu();
		submenu.setLabel("Dynamic Submenu 1");

		var item = MenuItem();
		item.setValue("Dynamic Menuitem 1.1");
		item.setUrl("#");
		submenu.getChildren()?.add(item);

		model.addSubmenu(submenu);

		//Second submenu  
		submenu = Submenu();
		submenu.setLabel("Dynamic Submenu 2");

		item = MenuItem();
		item.setValue("Dynamic Menuitem 2.1");
		item.setUrl("#");
		submenu.getChildren()?.add(item);

		item = MenuItem();
		item.setValue("Dynamic Menuitem 2.2");
		item.setUrl("#");
		submenu.getChildren()?.add(item);

		model.addSubmenu(submenu);
	}

}