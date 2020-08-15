package com.springboot.bcrypt.service;

import java.util.List;

import com.springboot.bcrypt.model.Employee;

public interface EmployeeService {
	void insertEmployee(Employee emp);
	void insertEmployees(List<Employee> employees);
	List<Employee> getAllEmployees();
	void getEmployeeById(String empid);
}
