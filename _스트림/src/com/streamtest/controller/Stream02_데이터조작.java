package com.streamtest.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.streamtest.model.dao.StudentDao;
import com.streamtest.model.vo.Student;

public class Stream02_���������� {

	public static void main(String[] args) {
		List<Student> students=StudentDao.getStudents();
		//��Ʈ���� collection�� �����͸� �����ݺ����� Ȱ���ؼ� ����ȭ�� �������� collection �����͸� filter(������ȸ), map(Ư�������� ���), 
		//reduce(����), match(��ġ�ϴ� �� ã��)���� �����ϴ� api�̴�. 
		//collection �����Ϳ� ���� filter, map, reduce, match���� ������ �� ����ó����ɱ��� ���߰� ����.
		//���η���(�ݺ����� ���� ���ϴ� ���� ã�� ��)�� �����ϴ°� �ƴ϶� SQL����ó�� ������������ ȣ���ؼ� ����ϴ� ������ �ۼ��� �� �ְ� ��.
		
		//streamAPI�� �̿����� �ʰ� �����л�ã��
		//�ܺιݺ��� �̿��ؼ� for������ �����ؼ� ����ؾ���
		List<Student> result=new ArrayList();
		for(Student s : students) {
			if(s.getGender()==Student.Gender.M) {
				result.add(s);
			}
		}
		printStudent(result);		
		//streamAPI�̿��ϱ�
		//java8�������� collection�� streamŬ������ �����ϰ� stream�� ���� collection�� �����͸� �ٷ���ִ� �޼ҵ带 ������.
		//filter(Predicate) collection�����͸� ���ϴ°��� ����س��±��
		//for���� ������� �ʰ� ���ٷ� ���������� ǥ���� �� �ִ�. ���� ���ٿ� �ͼ��ؾ��Ѵ�.
		//result=students.stream().filter(s->s.getGender()==Student.Gender.M).collect(Collectors.toList());
		result=students.stream().filter(s->s.getGender()==Student.Gender.M).collect(Collectors.toCollection(ArrayList::new));
		printStudent(result);
		
		//collect()�� ��ȸ�� ����� Stream���� �����µ� �� ����� �ٽ� collection��ü�� ��ȯ���ִ� ����� �Ѵ�.
		//collect()�Ű������� �μ��� CollectorsŬ������ �����ϴ� �żҵ带 �̿��Ѵ�.
		//CollectorsŬ���� �޼ҵ峻�� �����ϱ� **
		
		//stream�� �Ѱ��� �����ϴ°��� �ƴϰ� �������� �������������� ���� streamAPI�Լ��� �ٿ��� ������ �����ϴ�.
		//���л��� ���̰� ���� 3�� ��ȸ�ϰ���� ���
		System.out.println("==== stream �̿�� ====");
		result=students.stream().filter(s->s.getGender()==Student.Gender.M)
				.sorted(Comparator.comparing(Student::getAge).reversed()).limit(3)
				.collect(Collectors.toList());//sql��ó�� �����ؼ� ������ ������ �� ����.
		printStudent(result);
		//streamAPI�� ������� �ʰ� ������ȸ�ϱ�
		result.clear();
		//���ڸ� ��ȸ
		for(Student s:students) {
			if(s.getGender()==Student.Gender.M) {
				result.add(s);
			}
		}
		//��ȸ�Ȱ���� sort
		//sort���� �����Ϸ���.. �����.. �����ϰ���.����
		result.sort(Comparator.comparing(Student::getAge).reversed());
		Iterator<Student> it=result.iterator();
		//�տ� 3�� ����ϴ� ����
		int count=0;
		while(it.hasNext()) {
			it.next();
			if(count>=3) {
				it.remove();
			}
			count++;
		}
		System.out.println("==== stream ���̿�� ====");
		printStudent(result);
		
		//stream �����̽��̿��ϱ�
		//takeWhile : filter���� ù��° false�� ������ ���ιݺ��� �ߴܽ�Ű�� ���������� ����� ��ȯ 
		//dropWhile : false�� ���� �ε������� �������ε������� ��� ��ȯ�ϴ� �޼ҵ�
		printStudent(students);
		System.out.println("======== filter�̿� ==========");
		result=students.stream().filter(s->s.getAge()>10).collect(Collectors.toList());
		printStudent(result);
		System.out.println("========= takeWhile�̿� ==========");
		result=students.stream().takeWhile(s->s.getAge()>10).collect(Collectors.toList());
		printStudent(result);	
		System.out.println("=========== dropWhile�̿� ===========");
		result=students.stream().dropWhile(s->s.getAge()>10).collect(Collectors.toList());
		printStudent(result);
		
		//limit() : collection�� �տ������� ���ϴ� �ε������� ��ȯ���ִ� ��� -> sort�� ���� ����Ұ����� �����.
		//sort�� top-n�ڷḦ �����ö� �����Ұ����� ������.
		System.out.println("===== ���� ��л� 3�� ��ȸ�ϱ� =====");
		result=students.stream().sorted(Comparator.comparing(Student::getAge)).limit(3).collect(Collectors.toList());
		printStudent(result);
		
		//��Ұǳʶٱ�
		//skip() : ���ε������� �μ��ǰ���ŭ �����ϰ� �������� ����ϴ� �޼ҵ�
		System.out.println("===== skip()�޼ҵ� �̿��ϱ� ====== ");
		result=students.stream().skip(4).collect(Collectors.toList());
		printStudent(result);
		
		
		//limit()�� skip()�����ϱ�, 4��°���� 10��°�л�����ϱ�
		System.out.println("====== 4��°���� 10��°�л�����ϱ� ======");
		result=students.stream().skip(3).limit(10).collect(Collectors.toList());
		printStudent(result);
		
		//hasMap�� List�� ��ȯ�ؼ� ���������Ѵ�.
		result=new ArrayList(StudentDao.getStudentMap().values());
		
		 
		
		//map()�żҵ� �̿��ϱ�
		//Ư�� �����͸� �����ϴ� �޼ҵ�
		List<String> studentNames=students.stream().map(Student::getName).collect(Collectors.toList());
		System.out.println(studentNames);
		
		//�ּҰ� ��⵵ �ΰ͸� ����ϱ�
		List<String> studentAddr=students.stream().map(Student::getAddress).filter(s->s.contains("��⵵"))
				.collect(Collectors.toList());
		System.out.println(studentAddr);
		
		//�� �ε����� ���ڿ��� ���� �Ѱ��� �迭�� �����
		List<String> strList=List.of("�ȳ��ϼ���","���³�����","�ȳ�ȣȣȣ");
		List<String[]> resultStringArr=strList.stream().map(s->s.split("")).collect(Collectors.toList());
		for(String[] strArr : resultStringArr) {
			for(String t: strArr) {
				System.out.println(t);
			}
		}
		System.out.println("Stream���� ����ϱ�");
		strList.stream().map(s->s.split("")).forEach(s->Arrays.stream(s).forEach(System.out::print));
		System.out.println();
		//�� �������� split�� �迭�� ��ȯ�ϱ� ������ collect �޼ҵ�� ����� ����Ʈ�� ����� List<String[]> Ÿ���� ������ �ȴ�.
		//������ List<String>���� ����Ϸ��� flatMap(Arrays::stream)�żҵ带 �̿��ؼ� ó���ؾ��Ѵ�.
		
		
		//���ڿ����� ����� ����(���ĺ�) �ߺ��� ���� �˻��ϱ�
		//��Ʈ�� �迭�� ��ȯ�Ǵ� ������ flatMap�޼ҵ带 �̿��ؼ� ó�� -> �������� ������ ��Ʈ���� �ϳ��� ��Ʈ������ ������ִ� �żҵ�!
		//�迭�������� ��ȯ�Ǵ� �������� stream���� �ϳ��� ��Ʈ������ ������ִ� ��� 
		
		List<String> resultStr=strList.stream().map(s->s.split("")).flatMap(s->Arrays.stream(s)).distinct().collect(Collectors.toList());
		
		//map�� flatMap�� ��°������ Ȯ��
		System.out.println("�Ϲ�Map���� : "+strList.stream().map(s->s.split("")).collect(Collectors.toList()));
		
		//flatMap������ -> List<String>���� ��ȯ�Ǳ� ������ �⺻ stream�� �̿��ؼ� filter, distinct���� �żҵ带 �̿��Ͽ� ó���� �� ����
		//flatMap�� �ַ� �迭�� �����Ͽ� �����͸� �����Ҷ� �����.
		System.out.println("flatMap ���� : "+strList.stream().map(s->s.split("")).flatMap(s->Arrays.stream(s)).collect(Collectors.toList()));
		//List<String> resultStr=strList.stream().map(s->s.split("")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
		System.out.println(resultStr);
		
		List<Integer> targetNum=List.of(1,2,3,4,5);
		//����� ���� �������� ����ϴ°� 
		List<Integer> numResult=targetNum.stream().map(s->s*s).collect(Collectors.toList());
		System.out.println(numResult);
		
		//�ΰ��� ����Ʈ ���ļ� ����ϱ� -> �����ϱ�
		//streamapi�żҵ峻�ο��� stream�� �ٽ� ȣ���Ͽ� ������ �� ����.
		List<Integer> su1=List.of(1,2,3);
		List<Integer> su2=List.of(3,4);
		List<int[]> r=su1.stream().flatMap(i-> su2.stream().map(j->new int[] {i,j})).collect(Collectors.toList());
		//����ϱ�
		for(int[] v:r) {
			System.out.print("(");
			for(int i:v) {
				System.out.print(i);
			}
			System.out.println(")");
		}
		
		
		//���͸��� student��ü ��ȯ�ϱ� -> map�� �̿�
		//���� ���縦 �ϱ� ���� clone�� �����.  
		result=students.stream().filter(s->s.getAge()<20).map(s->s.clone()).collect(Collectors.toList());
		printStudent(result);
		
		
		
		
		//�˻��� ��Ī�ϱ�
		//anyMatch : collection���� Predicate�� ����� true�� �����°� �ϳ��� ������ true�� ��ȯ����
		if(students.stream().anyMatch(s->s.getAge()==23)) {
			System.out.println("�������� �ֳ�!!");
		}
		//allMatch : collection���� predicate�� ����� ��� true�� ��ȯ�ϸ� true�� ��ȯ���ִ� �޼ҵ�
		if(students.stream().allMatch(s->s.getAddress().length()>=3)) {
			System.out.println("�ּҰ� ��� 4���� �̻��̴�!");
		}
		
		//noneMatch : collection�� predicate�� ����� ��� false�� ���ð�� true�� ��ȯ�ϴ� �޼ҵ�
		if(students.stream().noneMatch(s->s.getName().equals("������"))) {
			System.out.println("�������� �����ϴ�");
		}
		
		
		
		//collection�� �˻���� ��ȯ�ϱ�
		//findAny()�żҵ� : filter���� true���� ��ȯ�ϴ� ����� ������ �ߴ��ϰ� �� ���� Optional��ü�� ��ȯ��.
		//OptionalŬ������ ��ȯ��.
		Optional<Student> searchResult=students.stream().filter(s->s.getAge()>5).findAny();
		
		//Optional null��ó���� ���� wapperŬ������ null���� ��ȯ�ɶ� �߻��ϴ� ������ �ٿ��� �� ����
		System.out.println(searchResult);
		//get : optional�� ���� ����ϴ� �żҵ� *optional�� ���� ������ NoSearchElementException�� �߻���.
		//searchResult.get();
		//isPresent : Optional��ü�� ���� �ִ��� ������ Ȯ���ϴ� �żҵ� ������ true, ������ false�� ��µ�.
		System.out.println(searchResult.isPresent());
		//ifPresent : ���� ������ �Ű�����(Consumer�������̽�) ������ �����ϴ� �Լ�, ������ ������.
		searchResult.ifPresent(s->System.out.println(s));
		//orElse : ���� ������ ���� ����ϰ� ������ �Ű������� �Ѿ�� ���� ����ϴ� ���.
		System.out.println(searchResult.orElse(new Student()));
		
		
		//reduce�԰質 ������ ����ϴ� �Լ�
		//reduce�� �ΰ��� �μ��� �޴´�
		//1 : �Ź� ����Ǵ� ���(��ȯ��)�� �����ϴ� ����
		//2 : collection�� ���� �Ѱ��� ���ԵǴ� ����
		List<Integer> counter=List.of(5,6,8,1,24,5,6,712,4,5);
		//counter�� �հ豸�ϱ�
		Optional<Integer> resultSum=counter.stream().reduce((t,v)->t+v);
		System.out.println(resultSum.get());
		
		
		//���̰� ���� ��� 5��
		System.out.println(students.stream().sorted(Comparator.comparing(Student::getAge).reversed()).limit(5)
				.map(Student::getAge).collect(Collectors.toList()));
		
		//���̰� ���� ��� 5���� ��ճ��̸� ���ϴ� stream
		students.stream().sorted(Comparator.comparing(Student::getAge).reversed()).limit(5)
		.map(Student::getAge).reduce((total,v)->total+v).ifPresent(totalVal->System.out.println(totalVal/5.0));
		
		
		Stream<Student> s=students.stream();
		s.filter(t->t.getAge()>30).collect(Collectors.toList());
		//streamŬ������ �ѹ��� ����� �����ϴ�. ������ �Ұ�����.
		s.map(Student::getAddress).collect(Collectors.toList());//�����߻�!!
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	private static void printStudent(List<Student> list) {
		System.out.println(list.size());
		System.out.println(list);		
	}
	
}
