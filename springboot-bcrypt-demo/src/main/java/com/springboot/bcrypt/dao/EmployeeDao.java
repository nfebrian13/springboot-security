package com.springboot.bcrypt.dao;

import java.util.List;

import com.springboot.bcrypt.model.Employee;

public interface EmployeeDao {
	void insertEmployee(Employee cus);
	void insertEmployees(List<Employee> employees);
	List<Employee> getAllEmployees();
	Employee getEmployeeById(String empId);
}
