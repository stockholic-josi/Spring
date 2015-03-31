package com.taxholic.core.authority;

import java.util.ArrayList;


import java.util.Collection;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthInfo  implements UserDetails {
	
	private AuthDto user;
	private String sessionId; 

	public AuthInfo(AuthDto user) {
		this.user = user;
	}

	public AuthDto getUser() {
		return user;
	}


	public Collection<GrantedAuthority> getAuthorities() {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(int i = 0; i < user.getRoleList().size(); i++){
			 authorities.add( new GrantedAuthorityImpl( "ROLE_" + user.getRoleList().get(i).toString()) );
		}
		return authorities;
	}

	public String getUsername() {
		return user == null ? "" : user.getUserId();
	}
	 
	public String getPassword() {
		return user == null ? "" : user.getPassword();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}
	 
	@Override
	 public int hashCode() {
		return user.getUserId().hashCode();
	 }
	 
	@Override
    public boolean equals(Object rhs) {
        if (rhs instanceof AuthInfo) {
            return user.getUserId().equals(((AuthInfo) rhs).user.getUserId());
        }
        return false;
    }

}
