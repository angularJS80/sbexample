package com.example.demo.security.model;

import lombok.Data;

import javax.validation.constraints.Size;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @modify Yongbeom Cho
 */
public class UserDto {

    @Data
    public static class Create {
        private Long id;
        private String login;
        private String password;
        private String email;
        private String name;
        private Set<Authority> authorities;
        private boolean activated = false;
        private String createdBy;
        private Date createdDate;
        private String lastModifiedBy;
        private Date lastModifiedDate;
		public String getPassword() {
			// TODO Auto-generated method stub
			return this.password;
		}
		public String getEmail() {
			// TODO Auto-generated method stub
			return this.email;
		}
		public String getLogin() {
			// TODO Auto-generated method stub
			return this.login;
		}
		public String getName() {
			// TODO Auto-generated method stub
			return this.name;
		}
    }

    @Data
    public static class Update {
        private Long id;
        private String login;
        private String email;
        private String name;
        private Set<String> authorities;
        private boolean activated;
		public Long getId() {
			// TODO Auto-generated method stub
			return this.id;
		}
		public Set<String> getAuthorities() {
			// TODO Auto-generated method stub
			return this.authorities;
		}
		public boolean isActivated() {
			// TODO Auto-generated method stub
			return this.activated;
		}
    }

    @Data
    public static class Response {
        private Long id;
        private String login;
        private String email;
        private String name;
        private Set<Authority> authorities;
        private boolean activated;

      

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Set<Authority> getAuthorities() {
			return authorities;
		}

		public void setAuthorities(Set<Authority> authorities) {
			this.authorities = authorities;
		}

		public boolean isActivated() {
			return activated;
		}

		public void setActivated(boolean activated) {
			this.activated = activated;
		}

    }

    @Data
    public static class Login {
        private String login;
        @Size(min = 4, max = 12)
        private String password;
		public String getPassword() {
			// TODO Auto-generated method stub
			return this.password;
		}
		public String getLogin() {
			// TODO Auto-generated method stub
			return this.login;
		}
    }
}