package com.taxholic.core.authority;


import org.springframework.dao.DataAccessException;

import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class DaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	
	private UserDetailsService userDetailsService;


	@Override
	protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken arg1)throws AuthenticationException {

		// TODO Auto-generated method stub
		UserDetails loadedUser;

		try {
			loadedUser = getUserDetailsService().loadUserByUsername(userName);
		} catch (DataAccessException repositoryProblem) {
			throw new AuthorizationServiceException(repositoryProblem.getMessage(), repositoryProblem);
		}

		if (loadedUser == null) {
			throw new AuthorizationServiceException("UserDetailsService returned null, which is an interface contract violation");
		}

		return loadedUser;
	} 

	/**
	* @return the userDetailsService
	*/
	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	/**
	* @param userDetailsService the userDetailsService to set
	*/
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails arg0,
			UsernamePasswordAuthenticationToken arg1)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		
	}
}
