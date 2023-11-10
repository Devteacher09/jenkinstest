package com.streamtest.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.streamtest.model.dao.StudentDao;
import com.streamtest.model.vo.Student;
import static java.util.Map.entry;
public class Collection_api {

	public static void main(String[] args) {
		List<Student> students=StudentDao.getStudents();
		
		//of메소드이용해서 불변의 리스트 만들기
		
		//forEacth메소드 이용해서 전체 출력하기
		students.forEach(s->System.out.println(s));
		//removeIf메소드 이용해서 원하는 항목 한번에 지우기
		students.removeIf(s->s.getAge()>=20);
		System.out.println("삭제후 출력하기");
		students.forEach(System.out::println);
		//replaceAll메소드 이용해서 데이터 변경하기
		System.out.println("변경후 출력하기");
		students.replaceAll(s->{if(s.getAge()<=12)s.setName("초딩");return s;});
		students.forEach(System.out::println);
		
		//map에서 지원하는 메소드
		Map<Integer,Student> studentsMap=StudentDao.getStudentMap();
		System.out.println("map출력하기");
		//Map도 forEach()메소드를 이용해서 전체 출력을 할 수 있다.
		studentsMap.forEach((stuNo,s)->{System.out.print(stuNo);System.out.println(s);});
		
		//map정렬하기
		//map은 key와 value를 기준으로 정렬할 수 있음.
		//map을 stream으로 이용하려면 entryset()으로 이용한다.
		//Entry인터페이스가 제공하는 comparingByKey(),comparingByValue()메소드를 이용해서 정렬도 가능함
		System.out.println("value값을 기준으로 오름차순정렬하기");
		studentsMap.entrySet().stream()
		.sorted(Entry.comparingByValue((s,s1)->s.getAge()-s1.getAge())).forEach((v)->System.out.println(v));
		
	
		Map<String,String> temp=Map.ofEntries(entry("유병승","경기도 시흥시"),entry("홍길동","강원도"),entry("이순신","전라도"));
		
		
	}
	
	
}
