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
		
		//of�޼ҵ��̿��ؼ� �Һ��� ����Ʈ �����
		
		//forEacth�޼ҵ� �̿��ؼ� ��ü ����ϱ�
		students.forEach(s->System.out.println(s));
		//removeIf�޼ҵ� �̿��ؼ� ���ϴ� �׸� �ѹ��� �����
		students.removeIf(s->s.getAge()>=20);
		System.out.println("������ ����ϱ�");
		students.forEach(System.out::println);
		//replaceAll�޼ҵ� �̿��ؼ� ������ �����ϱ�
		System.out.println("������ ����ϱ�");
		students.replaceAll(s->{if(s.getAge()<=12)s.setName("�ʵ�");return s;});
		students.forEach(System.out::println);
		
		//map���� �����ϴ� �޼ҵ�
		Map<Integer,Student> studentsMap=StudentDao.getStudentMap();
		System.out.println("map����ϱ�");
		//Map�� forEach()�޼ҵ带 �̿��ؼ� ��ü ����� �� �� �ִ�.
		studentsMap.forEach((stuNo,s)->{System.out.print(stuNo);System.out.println(s);});
		
		//map�����ϱ�
		//map�� key�� value�� �������� ������ �� ����.
		//map�� stream���� �̿��Ϸ��� entryset()���� �̿��Ѵ�.
		//Entry�������̽��� �����ϴ� comparingByKey(),comparingByValue()�޼ҵ带 �̿��ؼ� ���ĵ� ������
		System.out.println("value���� �������� �������������ϱ�");
		studentsMap.entrySet().stream()
		.sorted(Entry.comparingByValue((s,s1)->s.getAge()-s1.getAge())).forEach((v)->System.out.println(v));
		
	
		Map<String,String> temp=Map.ofEntries(entry("������","��⵵ �����"),entry("ȫ�浿","������"),entry("�̼���","����"));
		
		
	}
	
	
}
