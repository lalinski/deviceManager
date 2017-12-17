package com.lalin.test.site.blog.mix.one.service;

import com.lalin.test.site.blog.mix.one.entity.Employee;
import com.lalin.test.site.blog.mix.one.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by frzhao on 2017/12/8.
 */
@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee findByName(String name){
        List<Employee> employeeList = employeeRepository.findByName(name);
        if(employeeList != null){
            return employeeList.get(0);
        }else{
            return null;
        }
    }
    public Employee findbyEmail(String email){
        return employeeRepository.findByEmail(email);
    }
    public Employee existEmployee(String name){
        Employee employee = findByName(name);
        if(employee != null){
                return employee;
        }else{
            return null;
        }

    }
}
