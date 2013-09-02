package hu.nevermind.budget.budget;

import hu.nevermind.budget.model.UserInfo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Entity
public class Category implements Serializable {

	private static final long serialVersionUID = 3361792878433382037L;
	@Id
	@Column(nullable = false)
	private String name;

	@Column
    private Character shortCut;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "username", referencedColumnName = "name", nullable = false)
	private UserInfo userInfo;

	public Category() {

	}

    public Category(final String name, final Character shortCut) {
        this.name = name;
        this.shortCut = shortCut;
    }

	public Category(final Category other) {
		this(other.name, other.shortCut);
	}

	public Category(final String categoryName, final Character shortCut, final UserInfo userInfo) {
		this(categoryName, shortCut);
		this.userInfo = userInfo;
	}


	@Override
	public String toString() {
		return "Category{" +
				"name='" + name + '\'' +
				", shortCut='" + shortCut + '\'' +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Character getShortCut() {
		return shortCut;
	}

	public void setShortCut(final Character shortCut) {
		this.shortCut = shortCut;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(final UserInfo userInfo) {
		this.userInfo = userInfo;
	}
}
