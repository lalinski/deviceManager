package com.lalin.test.site.blog.mix.one.Dao;

import com.lalin.test.site.blog.mix.one.entity.Employee;
import com.lalin.test.site.blog.mix.one.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frzhao on 2017/4/12.
 */
@Service
public class MyUserDetailService implements UserDetailsService {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		List<Employee> list = new ArrayList<Employee>();
		list = employeeRepository.findByUsername(name);
		System.out.println("List: " + list);
		if(list != null && list.size() > 0) {
			System.out.println(list.get(0).getUsername() + " : " + list.get(0).getPassword());
	//	return list.get(0).toString();
			return list.get(0);
		}
		throw new UsernameNotFoundException("can not find the registered user");
	}

}
