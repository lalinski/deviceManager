package com.lalin.test.site.blog.mix.one.security;

import com.lalin.test.site.blog.mix.one.security.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frzhao on 2017/4/12.
 */
public class Constants {

	public static final List<User> userList = new ArrayList<User>(){

		private static final long serialVersionUID = 1L;
		{
			User user = new User();
			user.setUsername("user");
			user.setPassword("ss");   // myuserpass
			user.setAuthorities(new ArrayList<SimpleGrantedAuthority>(){
				private static final long serialVersionUID = 1L;
				{
					add(new SimpleGrantedAuthority("ROLE_USER"));
				}
			});

			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword("admin");  // myadminpass
			admin.setAuthorities(new ArrayList<SimpleGrantedAuthority>(){
				private static final long serialVersionUID = 1L;
				{
					add(new SimpleGrantedAuthority("ROLE_ADMIN"));
				}
			});



			add(user);
			add(admin);

		}
	};
}
