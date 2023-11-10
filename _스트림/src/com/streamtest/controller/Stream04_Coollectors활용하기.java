package com.streamtest.controller;

//CollectorsŬ���� ����import�ϱ�
import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;
import static java.util.stream.Collectors.summingInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.streamtest.functional.ToMyListCollect;
import com.streamtest.model.dao.StudentDao;
import com.streamtest.model.vo.Student;
import com.streamtest.model.vo.Student.Gender;

public class Stream04_CoollectorsȰ���ϱ� {
	//Stream�����͸� ������ ������ �°� ��ȯ���ִ� Ŭ������ Collector��� �Ѵ�.
	//�ַ� �÷������� ��ȯ�Ͽ� ���� ���, toList()�޼ҵ带 �̿�.
	//Stream�� collect()�޼ҵ忡 �Ű������� CollectorsŬ������ �����ϴ� �޼ҵ带 �̿��Ѵ�.
	//CollectorsŬ������ Stream�� ������ �ִµ����͸� 1.�ϳ��� ������ ���ེ(�����?)�ϰ�, ��� �׷�ȭ�ϰ�, ��Ҹ� �����Ѵ�.
	
	public static void main(String[] args) {
		
		//collect()�޼ҵ带 �̿��ؼ� reduce�ϱ�!
		
		//1. �ϳ��� ������ ���ེ�ϱ� -> ��ҵ��� �ϳ��� ������(��)���� ��ȯ�ϴ� �� ��) �հ�, �Ѱ�, ��� ��
		List<Student> students=StudentDao.getStudents();
		//�л��� ���ϱ�
		long totalStudent=students.stream().collect(Collectors.counting());
		System.out.println(totalStudent);
		
		//CollectosŬ������ �����ϴ� �޼ҵ�� �����޼ҵ�� ���� import�� �����ϰ� �ϸ� ���ϰ� ����� �� �ִ�.
		totalStudent=students.stream().collect(counting());
		System.out.println(totalStudent);
			
		//�ִ밪�� �ּҰ�����ϱ�
		//Collectors�� maxBy, minBy�żҵ带 Ȱ����
		//maxBy(), minBy()�żҵ�� �Ű������� ���� ���Ͽ�ó���ϴ� Comparator�� �޴´�.
		//�л��� ���̰� ���� ���� �л��� ���� �л� ���ϱ�
		//��ȯ���� OptionalŬ������ null���� ���� ó�����ֱ� ���ϰ� ������� �ִ�.
		Student s=students.stream().collect(maxBy(Comparator.comparing(Student::getAge))).orElse(new Student());
		System.out.println(s);
		s=students.stream().collect(minBy(Comparator.comparing(Student::getAge))).orElse(new Student());
		System.out.println(s);
		
		//��࿬���ϱ�-> �հ� ����� ���ϴ°�.
		//�հ豸�ϱ�
		//Collectors.summingInt, summingDouble, SummingLong�� �̿��ؼ� �հ� ���ϱ�
		int sumAge=students.stream().collect(summingInt(Student::getAge));
		System.out.println(sumAge);
		//��ձ��ϱ�
		//Collectors.averagingInt, averagingLong, averagingDouble�� �̿��ؼ� ��ձ��ϱ�
		double avg=students.stream().collect(averagingDouble(Student::getAge));
		System.out.println(avg);
		
		//�հ�, ���, ������ �ѹ��� ���ϴ� �޼ҵ� 
		//Collectors.sumarizingInt(); -> IntSummaryStatisticsŬ������ ��ȯ��
		IntSummaryStatistics iss=students.stream().collect(Collectors.summarizingInt(Student::getAge));
		System.out.println(iss);
		//�� ���� getOOO�� �̿��ؼ� �ҷ��ü� ����
		System.out.println(iss.getCount());
		System.out.println(iss.getAverage());
		
		
		//���ڿ��� ��� �������ִ� �޼ҵ�
		//stream�����Ͱ� ��� ���ڿ��϶� �� �����ʹ� �Ѱ��� ���ڿ��� �����ִ� ���
		//Collectors.join() 
		//�����߻� -> �Ʒ� ������ Student��ü�� �����ͷ� ������ �ִ� Stream�̱⶧���� ������ �ȵ�.
		//String name=students.stream().collect(Collectors.joining());
		String names=students.stream().map(Student::getName).collect(Collectors.joining());
		System.out.println(names);
		//�Ѱ����ڿ��� ����� Ư�� ��ȣ ���Խ�Ű��.
		names=students.stream().map(Student::getName).collect(Collectors.joining(","));
		System.out.println(names);
		
		
		//���� reducing�޼ҵ带 �̿��ؼ� �����ϱ�
		//java���� ������ reducing�ܿ� ����ڰ� �����Ͽ� reducing�� �� �ִ�.
		//Collectors.reducing((������ ����||�μ��� ������ ��ȯ��->�ʱⰪ?),�����ҳ���,����ó���ҳ���)
		//�հ踦 ���ϴ� reducing�����
		int totalSum=students.stream().collect(Collectors.reducing(0,Student::getAge,(pre,next)->pre+next));
		System.out.println(totalSum);
		
		//�ִ��ּұ��ϱ�(max, min)
		//���̰� ���� ���� �л� ���ϱ�!
		Optional<Student> result=students.stream().collect(Collectors.reducing((preStudent,nextStudent)->preStudent.getAge()>nextStudent.getAge()?preStudent:nextStudent));
		Student temp=result.get();
		System.out.println("���� �������(max) : "+temp);
		result=students.stream().collect(Collectors.reducing((preStu,nextStu)->preStu.getAge()<nextStu.getAge()?preStu:nextStu));
		temp=result.get();
		System.out.println("���� �������(min) : "+temp);
		
		
		//��ü�հ豸�ϱ� -> int������ �ٷ� ���ϱ�
		Optional<Integer> intResult=students.stream().map(Student::getAge).reduce(Integer::sum);
		int intTotal=students.stream().map(Student::getAge).reduce(Integer::sum).get();
		
		//�⺻ reduce�� �̿��ϸ� Optional��ü�� ��ȯ�ϱ� �Ǵµ� �ٷ� �⺻�ڷ������� ������ �� �ִ�. 
		int totalAge=students.stream().mapToInt(Student::getAge).sum();
		System.out.println("���� ��ü �հ豸�ϱ� : "+totalAge);
		
		//Student studentResult=students.stream().collect(Collectors.reducing((s1,s2)->s1.getName()+s2.getName())).get();
		
		
		
		//2. ��� �׷�ȭ�ϱ�
		//grouping by �޼ҵ带 �̿��ؼ� ���ϴ� ��ҷ� �׷�ȭ�� �� �ִ�.
		Map<Student.Gender,List<Student>> genderList=students.stream().collect(Collectors.groupingBy(Student::getGender));
		System.out.println(genderList.get(Gender.M));
		System.out.println(genderList.get(Gender.F));
		
		//���̰� 30���� �л��� ������ �����ϱ�
		//������ TŸ���� �߰��Ͽ� �׷����� ��� �׽�Ʈ�ϱ�
		students.add(new Student.Builder().stdNo(200).name("test"+200).age(20).
				gender(Gender.T).build());
		students.add(new Student.Builder().stdNo(201).name("test"+201).age(21).
				gender(Gender.T).build());
		
		//Stream�� �����͸� filter �� �׷����� ���� ������ gender�� TŸ���� ��ü�� ���ϴ� ����� ����
		genderList=students.stream().filter(s1 -> s1.getAge()>=30&&s1.getAge()<40).collect(Collectors.groupingBy(Student::getGender));
		System.out.println(genderList.keySet());
		
		//�׷����� ������ ��ü �����Ϳ� ���� �ϰ� ���°� ���� �׸����� �����Ϸ��� Collectors.groupBy�޼ҵ峻�ο��� filter�� �ؼ� �����ؾ���.
		genderList=students.stream().collect(Collectors.groupingBy(Student::getGender, 
				Collectors.filtering(s1 -> s1.getAge()>=30&&s1.getAge()<40,Collectors.toList())));
		System.out.println(genderList.keySet());
		
		//����� ������ �ֱ�
		Map<String, List<String>> test=new HashMap();
		int count=0;
		for(Student s1 : students) {
			
			test.put(s1.getName(),Arrays.asList(s1.getName()+"_�ϳ�_"+count++,s1.getName()+"_��_"+count++,s1.getName()+"_��_"+count++));
		}
		
		//�̸��� �������� ������ �׷��� ���� ����Ʈ�� �׷쿡 �����ϴ� ����
		//�� �̸��� ����� �±׸� �̸��� �´� ������ �׷����� ��ȸ�ϴ� ���� ?? ���Ҹ���? ���� ��µ� �𸣰ڳ�...
		//test map�� �̸�(key)�� ����� ���� ������ �����׷����� �����ϴ� ����
		Map<Student.Gender,Set<String>> resultTest=students.stream().collect(Collectors.groupingBy(Student::getGender,
				Collectors.flatMapping(s1->test.get(s1.getName()).stream(),Collectors.toSet())));
		
		System.out.println(resultTest.get(Gender.T));
		
		//�ΰ��� �������� �з��Ҷ��� groupBy�޼ҵ� ���ο� groupBy�� �ϳ� �� ����ؼ� ó���Ѵ�.
		//����, ���̴뺰 �л��з��ϱ�
		Map<Student.Gender, Map<String,List<Student>>> testResult=students.stream()
				.collect(Collectors.groupingBy(Student::getGender,Collectors.groupingBy(s1->{
					if(10<=s1.getAge()&&s1.getAge()<20) return "10��";
					else if(20<=s1.getAge()&&s1.getAge()<30) return "20��";
					else if(30<=s1.getAge()&&s1.getAge()<40) return "30��";
					else if(40<=s1.getAge()&&s1.getAge()<50) return "40��";
					else if(50<=s1.getAge()&&s1.getAge()<60) return "50��";
					else return "other";
				})));
		System.out.println(testResult);
		for(Student.Gender g : testResult.keySet()) {
			System.out.println(g+" ������ ���� �ο��� : "+testResult.get(g).size());//���� ī�װ�
			for(String key : testResult.get(g).keySet()) {
				System.out.println(key);//������ ���̴뺰 ī�װ�
				System.out.println(testResult.get(g).get(key));
				System.out.println("�ο��� : "+testResult.get(g).get(key).size());
			}
		}
		//����, ���̴뺰 ���̰� ���� ���� �л� ���ϱ�
		//Collectors�� maxBy�޼ҵ带 �̿��ϸ� ��ȯ���� Optional��ü�� �ȴ�.
		Map<Student.Gender, Map<Object,Optional<Student>>> testResult2=students.stream().collect(Collectors.groupingBy(Student::getGender,
				Collectors.groupingBy(s1->{
					if(10<=s1.getAge()&&s1.getAge()<20) return "10��";
					else if(20<=s1.getAge()&&s1.getAge()<30) return "20��";
					else if(30<=s1.getAge()&&s1.getAge()<40) return "30��";
					else if(40<=s1.getAge()&&s1.getAge()<50) return "40��";
					else if(50<=s1.getAge()&&s1.getAge()<60) return "50��";
					else return "other";
				},Collectors.maxBy(Comparator.comparingInt(Student::getAge)))));
		
		System.out.println(testResult2);
		
		//��ȯ���� optional��ü�� �ƴ� ������ ��ȯ�ϱ�
		//maxBy()�޼ҵ��� �ι�° �Ű������� ��ȯ�� ���� ����� �� �ִ�.
		//Collectors.collectingAndThen()�޼ҵ带 �̿��ϸ�ȴ�.
		// ù��° �Ű����� : ������ �Լ� maxBy, minBy, 
		Map<Student.Gender, Map<Object,Object>> testResult3=students.stream().collect(Collectors.groupingBy(Student::getGender,
				Collectors.groupingBy(s1->{
					if(10<=s1.getAge()&&s1.getAge()<20) return "10��";
					else if(20<=s1.getAge()&&s1.getAge()<30) return "20��";
					else if(30<=s1.getAge()&&s1.getAge()<40) return "30��";
					else if(40<=s1.getAge()&&s1.getAge()<50) return "40��";
					else if(50<=s1.getAge()&&s1.getAge()<60) return "50��";
					else return "other";
				},Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Student::getAge)), s1->s1.get().getAge()))));
		
		System.out.println(testResult3);
		
		// �⺻������ �������� Ȯ�� ��� �����ϳ� 		
		int tt=students.stream().collect(Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Student::getAge)),o->o.get().getAge()));
		System.out.println(tt);
		
		//IntSummaryStatistics���� ��ȯ�ϴ� collect�����
		Object count2=students.stream().collect(Collectors.collectingAndThen(Collectors.summarizingInt(Student::getAge),is->is.getMin()));
		System.out.println(count2);
		
		//groupBy�޼ҵ� ��ȯ�� ����
		Map<String,Set<Student>> set=students.stream().collect(Collectors.groupingBy(s1->{
			if(10<=s1.getAge()&&s1.getAge()<20) return "10��";
			else if(20<=s1.getAge()&&s1.getAge()<30) return "20��";
			else if(30<=s1.getAge()&&s1.getAge()<40) return "30��";
			else if(40<=s1.getAge()&&s1.getAge()<50) return "40��";
			else if(50<=s1.getAge()&&s1.getAge()<60) return "50��";
			else return "other";
		},Collectors.toSet()));
		System.out.println(set);	
		
		//������ �����ϱ�
		//�������� ���Ҵ� Collectors.partitioningBy()�޼ҵ带 �̿��ؼ� ó���� �� �ִ�. ���ҵȵ����ʹ� List�� ��µ�.
		//�÷��� �����͸� boolean���� ����� �����ϱ�
		Map<Boolean, List<Student>> partitionResult=students.stream().collect(Collectors.partitioningBy(s1->s1.getAge()<30));
		System.out.println(partitionResult);
		System.out.println(partitionResult.keySet());
		
		//�л��� ���г�� ���г����� �з��ϱ�
		//4�г���� ���г�, 5~6�г��� ���г�
		partitionResult=students.stream().collect(Collectors.partitioningBy(s1->s1.getGrade()<=4));
		System.out.println(partitionResult);
		System.out.println("���г�");
//		for(Student s1 : partitionResult.get(true)) {
//			System.out.println(s1);
//		}
		//���г��� ���� ���
		double ageAvg=partitionResult.get(true).stream().collect(Collectors.averagingDouble(Student::getAge));
		System.out.println(ageAvg);
		
		System.out.println("���г�");
//		for(Student s1 : partitionResult.get(false)) {
//			System.out.println(s1);
//		}
		ageAvg=partitionResult.get(false).stream().collect(Collectors.averagingDouble(Student::getAge));
		System.out.println(ageAvg);
		
		//���г� ���г����� �����ϰ� ���ο��� �ٽ� ������ �����ϱ�
		Map<Boolean,Map<Student.Gender,List<Student>>> partiGroup=students.stream().collect(Collectors.partitioningBy(s1->s1.getGrade()<=4,Collectors.groupingBy(Student::getGender)));
		System.out.println(partiGroup);
		
		int data=9;
		boolean flag=IntStream.range(2, data).noneMatch(i->data%i==0);
		System.out.println(flag);
		
		
//		CollectĿ���͸���¡�ϱ�
//		Collect�������̽��� �����غ���
//		Collect�������̽��� �װ��� �޼ҵ尡 ����Ǿ��ְ�, ���׸����� ������ Ÿ���� �޴´�
//		�޼ҵ�
//		supplier : ���ο� ����� ����� ������ �����̳� ����(�÷��� �����ӿ�ũ, stream�� ���� ���� �����ų ��ü)�ϴ� �żҵ� -> ���������� ����Ҹ���ȯ�ϴ� �޼ҵ� ��ȯ 
//		accumulator : supplier�żҵ忡�� ������ �����̳ʿ� ������(��)�� �����Ű�� �޼ҵ� -> ������ ���� ����� ��ȯ�ϴ� �޼ҵ� ��ȯ
//		finisher : ��������� ��ȯ�ϴ� �޼ҵ� * �׵��Լ�(�ڽ��� ��ȯ�ϴ� �Լ�)�� ȣ���� -> 
//		combiner : ����ȭ ó������ sub��Ʈ���� ������ ����Ʈ�� ��ġ�� ��� -> ����ó���� �޼ҵ� ��ȯ
//		characteristics :
		//UNORDERED : ���ེ ����� ��Ʈ�� ����� �湮 ������ ���� ������ ������ ���� �ʴ´�.
		//CONCURRENT : ���߽����忡�� accumulator�Լ��� ���ÿ� ȣ���� �� ������ �� �÷��ʹ� ��Ʈ���� ���� ������� ������ �� ����.
		//IDENTITY_FINISH : finisher�޼ҵ尡 ��ȯ�ϴ� �Լ��� �ܼ��� identity�� ������ ���̹Ƿ� �̸� ������ �� �ִ�.

		
		//������ Collect�����
		//List�� �����ϴ� Collect�����
		//Stream�����͸� List�� ������ִ� collect�ۼ�
		List<String> myList=students.stream().map(s1->s1.getName()).collect(new ToMyListCollect<String>());
		System.out.println(myList);
		List<Student> myList2=students.stream().filter(s1->s1.getAge()>=50).collect(new ToMyListCollect<Student>());
		System.out.println(myList2);
		//�޼ҵ带 �������� �ʰ� collect�� Ŀ���͸���¡�Ͽ� �̿��� �� �ִ�. 
		myList2=students.stream().filter(s1->s1.getHeight()>180).collect(ArrayList::new, List::add, List::addAll);
		System.out.println(myList2);
		
		
		System.out.println(List.of(1,2,3,4,5,6,7,8,9,10).stream().takeWhile(i->i<=5).collect(Collectors.toList()));
		
		
	}
	
}

