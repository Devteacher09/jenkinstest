package com.streamtest.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.streamtest.model.vo.Student;

public class StudentDao2 {

	private static List<Student> students=new ArrayList();
	private static Map<Integer,Student> studentMap=new HashMap();
	static {
		for(int i=0;i<100;i++) {
			students.add(new Student.Builder().stdNo(i).name(""+(char)(Math.random()*'힣'+'가')+(char)(Math.random()*'힣'+'가')+(char)(Math.random()*'힣'+'가'))
					.age((int)(Math.random()*50+10)).
					gender(i%2==0?Student.Gender.M:Student.Gender.F)
					.height(Math.round(((Math.random()*(190-150))+150)*100/100.0))
			.grade((int)(Math.random()*6+1)).address("주소"+i).build());
			
			studentMap.put(i,new Student.Builder().stdNo(i).name("test"+i).age((int)(Math.random()*50+10)).
					gender(i%2==0?Student.Gender.M:Student.Gender.F)
			.grade((int)(Math.random()*6+1)).address("주소"+i).build());
		}
	}
	public static List<Student> getStudents(){
		return students;
	}
	public static Map<Integer,Student> getStudentMap(){
		return studentMap;
	}
	
}
