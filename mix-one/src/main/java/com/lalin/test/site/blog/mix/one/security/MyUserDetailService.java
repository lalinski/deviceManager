package com.lalin.test.site.blog.mix.one.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by frzhao on 2017/4/12.
 */
//@Service
public class MyUserDetailService implements UserDetailsService {
	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		List<User> userList = Constants.userList;
		for (int i = 0, len = userList.size(); i < len; i++) {
			User user = userList.get(i);
			if (user.getUsername().equals(s)) {
				return user;
			}
		}
		throw new UsernameNotFoundException("??????");
	}

}
