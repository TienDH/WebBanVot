package com.thunga.web.entity;

import javax.persistence.*;
import com.thunga.web.form.AccountForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String username;
	private String password;

	@Column(name = "decryptedpasword") // 🔹 giữ nguyên như trong DB
	private String decryptedPassword;

	private String email;
	private String role;
	private String created_at;
	private String updated_at;

	public Account(AccountForm accountForm) {
		this.username = accountForm.getUserName();
		this.decryptedPassword = accountForm.getPassword();
		this.password = accountForm.getPassword();
		this.email = accountForm.getEmail();
		this.role = "ROLE_USER";
	}
}
