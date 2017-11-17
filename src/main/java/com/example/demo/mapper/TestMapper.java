
package com.example.demo.mapper;
import java.util.List;
import java.util.Map;


public interface TestMapper {    	
	public void insertUser(Map<String, Object> map);		
	public List<Map<String, Object>> selectUserByLogin(Map<String, Object> map);
	public List<Map<String, Object>> selectUser(Map<String, Object> map);
}



