
package com.example.demo.mapper;
import java.util.List;
import java.util.Map;

import com.example.demo.vo.Heros;

public interface HeroMapper {    	
	public void insertHero(Map<String, Object> map);		
	public List<Heros> selectHeros(Map<String, Object> map);
}



