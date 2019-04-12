/**
 * Copyright (c)
 * @author TCS
 *
 */
package com.example.security.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.security.model.Employee;

@Controller
@RequestMapping("/user")
public class EmployeeController {

	@RequestMapping(value = "/getEmployeesList", produces = "application/json")
	@ResponseBody
	public List<Employee> getEmployeesList() {
		final List<Employee> employees = new ArrayList<>();
		final Employee emp = new Employee();
		emp.setEmpId("678632");
		emp.setEmpName("Dheeraj");
		employees.add(emp);
		return employees;

	}

}