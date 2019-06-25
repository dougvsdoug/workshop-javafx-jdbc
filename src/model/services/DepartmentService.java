package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	private DepartmentDao dao = DaoFactory.createDepartmentDao();//injetamos a depedência usando o padrão Factory
	
	public List<Department> findAll(){		
		return dao.findAll();//returns the departments of the database
	}
	
	public void saveOrUpdate(Department obj) {// it resolves if the department must be save in the database or must
		// update a department that already exists in the database
		
		if( obj.getId() == null ) {// the department doesn't exist in the database
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
}
