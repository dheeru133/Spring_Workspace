/**
 * Copyright (c)
 * @author TCS
 *
 */
package com.example.security.model;

public class Employee {

	private String	empId;
	private String	empName;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + this.empId + ", empName=" + this.empName + "]";
	}

}