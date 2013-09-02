package hu.nevermind.budget.model;

import hu.nevermind.budget.budget.Category;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="USERINFO")
@NamedQueries({
		@NamedQuery(name="UserInfo.findUser", query = "SELECT user FROM UserInfo user WHERE user.name = :name"),
		@NamedQuery(name="UserInfo.findUserByPassword", query = "SELECT user FROM UserInfo user WHERE user.name = :name AND user.password = :password")
})
public class UserInfo implements Serializable {

	private static final long serialVersionUID = -2448706711016608746L;

	@Id
	@Column
	private String name;

	@Column(nullable = false)
	private String password;

	@OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<MoneyBox> moneyBoxes;

	@OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Category> categories;

	public Role getRole() {
		return role;
	}

	public void setRole(final Role role) {
		this.role = role;
	}

	public enum Role {
		Normal, Admin
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@NotNull
	public static UserInfo findUser(final EntityManager em, final String userName) {
		return em.find(UserInfo.class, userName);
		//return em.createNamedQuery("UserInfo.existUser", UserInfo.class).setParameter("name", userName).getSingleResult();
	}

	public static boolean existUser(final EntityManager em, final String userName, final String password) {
		return !em.createNamedQuery("UserInfo.findUserByPassword", UserInfo.class).setParameter("name", userName).setParameter("password", password).getResultList().isEmpty();
	}

	@Override
	public String toString() {
		return "UserInfo{" +
				"name='" + name + '\'' +
				", password='" + password + '\'' +
				'}';
	}

	public List<MoneyBox> getMoneyBoxes() {
		return moneyBoxes;
	}

	public void setMoneyBoxes(final List<MoneyBox> moneyBoxes) {
		this.moneyBoxes = moneyBoxes;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(final List<Category> categories) {
		this.categories = categories;
	}
}
