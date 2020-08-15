package com.springboot.bcrypt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.bcrypt.dao.EmployeeDao;
import com.springboot.bcrypt.model.Employee;
import com.springboot.bcrypt.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDao employeeDao;

	@Override
	public void insertEmployee(Employee employee) {
		employeeDao.insertEmployee(employee);
	}

	@Override
	public void insertEmployees(List<Employee> employees) {
		employeeDao.insertEmployees(employees);
	}

	public List<Employee> getAllEmployees() {
		return employeeDao.getAllEmployees();
	}

	@Override
	public void getEmployeeById(String empId) {
		Employee employee = employeeDao.getEmployeeById(empId);
		System.out.println(employee);
	}

}
