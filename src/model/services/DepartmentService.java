package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	private DepartmentDao dao = DaoFactory.createDepartmentDao();//injetamos a deped�ncia usando o padr�o Factory
	
	public List<Department> findAll(){		
		return dao.findAll();//returns the departments of the database
	}
}
