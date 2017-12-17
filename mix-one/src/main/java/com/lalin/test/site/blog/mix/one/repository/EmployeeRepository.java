package com.lalin.test.site.blog.mix.one.repository;

import com.lalin.test.site.blog.mix.one.Dao.User;
import com.lalin.test.site.blog.mix.one.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by frzhao on 2017/4/22.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    public List<Employee> findByUsername(String username);
    @Query("select o from Employee o where display_name = ?1")
    public List<Employee> findByName(String name);
    @Query(nativeQuery = true, value = "select * from employee where email = ?1")
    public Employee findByEmail(String email);
}
