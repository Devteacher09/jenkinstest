package com.streamtest.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.streamtest.model.dao.StudentDao;
import com.streamtest.model.vo.Student;

public class Stream01_��Ʈ�������ϱ� {
	//Stream�����͸� ����� ����� ���� �˾ƺ���.
	public static void main(String[] args) {
		System.out.println("===== Stream.of�żҵ��̿��ؼ������ =====");
//		������ ��Ʈ�� �����
//		Stream.of()�żҵ��̿��ϱ� 
//		of�żҵ带 �̿��ؼ� �������͸� stream���� ���� Ȱ���Ҽ��ִ�
		Stream<String> test=Stream.of("Java","Oracle","Html","css","JavaScript");
		//List<String> result=test.filter(s->s.length()>4).collect(Collectors.toList());
//		System.out.println(result);
		test.filter(s->s.length()>4).forEach(System.out::println);
		
		//�迭�� ��Ʈ������ ���鶧�� Arrays.stream()�żҵ带 �̿��Ѵ�.
		String[] data= {"Java","Oracle","Html","css","JavaScript"};
		Arrays.stream(data);
		
		
		//�ټ��� �⺻���� ��Ʈ������ ����� ���͸��ؼ� ����ϱ�
		Stream<Integer> intValue=Stream.of(1,23,4,5,12,44);
		intValue.filter(v->v>20).forEach(System.out::println);
		//�ټ��� ���� ��Ʈ������ ����� �ִ밪����ϱ�
		intValue=Stream.of(1,23,4,5,12,44);
		int maxVal=intValue.max((v,v1)->v-v1).orElse(0);
		System.out.println(maxVal);	
		
		//�����Ͱ� ����(������ ����) ��Ʈ�� �����
		System.out.println("==== Stream.empty() / Stream.ofNullable() ����ִ� ��Ʈ������� ====");
		Stream<Object> emptyStream= Stream.empty();
		Stream<String> emptyStream2= Stream.empty();
		Stream<Integer> emptyStream3= Stream.empty();
		//����ֱ⶧���� �ƹ��͵� ��µ�������, �� �߿��Ѱ��� nullpointer������ �߻����� ����.
		//�ڷ����� ������� ��� �ڷ����� ���� ���� �� ����
		emptyStream.forEach(System.out::println);
		
		//���� ���� Stream�� ������ �� �ִ�. null���� ���� �Ǹ� Stream�żҵ带 �̿��ϸ� nullpointerexception�� �߻���.
		Stream<Student> nullStream=Stream.of(null,null,null);
		//nullStream.forEach(System.out::println);
		//nullStream.filter(s->s.getName().length()>3).forEach(System.out::println);
		
		//��Ʈ���� �̿��Ҷ� ���ԵǴ� ��ü�� ���ϼ��������� stream�� �����ϴ� ���
		//null���� �ִ� ��� ��Stream�� ����� ó���ϱ�
		//���׿����ڸ� �̿��ؼ� ó���ϱ�
		String value=null;
		Stream<String> strStream=value==null?Stream.empty():Stream.of(value);
		//ofNullable�޼ҵ� �̿��ϱ�
		strStream=Stream.ofNullable(value);//value���� null�̸� empty()�� �����ϰ� �ƴϸ� value������ ������.
		strStream.forEach(System.out::println);//null�̱� ������ ��µǴ°��� ����.
		value="test";
		strStream=Stream.ofNullable(value);
		strStream.forEach(System.out::println);//test�� ��µ�.
		
		
		System.out.println("===== Stream.builder�޼ҵ� �̿��ϱ� =====");
		//Stream������ �����ϱ� 
		//builder()�żҵ��̿��ϱ�
		//Stream.BuilderŬ������ stream�� builder�� ������ �� �ִ�. -> Stream.Builder<T>
		//������ Stream.BuilderŬ������ stream�� �����ϴ� Ŭ������ add�޼ҵ带 �̿��ؼ� stream����Ȱ���� �����͸� �߰��� �� ����
		//add�żҵ带 ���� �����͸� �߰��� �� Stream�� build()�żҵ�� �����Ѵ�.
		Stream.Builder<String> builder=Stream.builder();
		Stream<String> strStream1=builder.add("������").add("ȫ�浿").add("�̼���").build();
		strStream1.filter(s->s.contains("��")).forEach(System.out::println);
		
		System.out.println("===== Stream.generate�޼ҵ� �̿��ϱ� =====");
		//Gendrator�̿��ؼ� Stream����� -> �������� �ݺ��Ǳ� ������ limit()�޼ҵ�� ���� ���
		//Stream.generate(Supplier<T>) -> Supplier T get()�żҵ带 ������ ����
		//Stream.generate()�żҵ带 �̿��ϸ� supplier�żҵ忡�� ��ȯ�Ǵ� ���� ������ stream�� �����͸� ���� �� ����
	
		//ȫ�浿 stream�����ͷ� �����
		//�Ʒ��Ͱ��� �����ϸ� ȫ�浿�� �������� ������.
		//Stream.generate(()->"ȫ�浿").forEach(System.out::println);
		//ȫ�浿 ���ϴ� ������ŭ�� �����Ϸ��� limit()�޼ҵ带 �̿��ؼ� ������ �ϸ� �ȴ�.
		Stream.generate(()->"ȫ�浿").limit(5).forEach(System.out::println);
		
		
		System.out.println("===== iterator�޼ҵ� �̿��ϱ� =====");
		//iterate()�޼ҵ带 �̿��ؼ� ���������� ������ Stream�� ���� �� �ִ�. -> �������� �����Ǳ� ������ limit()�޼ҵ�� ���� �����.
		//iterate()�żҵ忡�� �ΰ��� �μ��� ��
		//ù��° : �ʱⰪ
		//�ι�° : �Ѱ��� �Ű�����, ���ϰ��� ���� �Լ����������̽� n -> ������ * n�ǰ��� ù��° ����ÿ��� ù����Ű�������
		//       �����Ŀ��� ���� �������� ��ȯ�� ���� �μ��� �־���. 
		//�ι�° �Ű������� ������ �����ؼ� �����͸� �����Ͽ� Stream�� ���� * �����ϴ� reduce�� ����� ������.
		//�ʱⰪ(ù��°�Ű�����)�� 0��° ��, �ι�° �Ű������� ��ȯ���� 1,2,3... ������ ���� stream�� ������. �� �� �����͸� ��ȯ�Ǵ� ������ �Ѱ����������. 
		Stream.iterate(10, n -> n*2).limit(10).forEach(System.out::println);
		Stream.iterate("��", n->n+"ȣ").limit(5).forEach(System.out::println);
		
		//iterate�� �Ǻ���ġ���� ����ϱ�
		//iterate �Ǻ���ġ���� ���
		Stream.iterate(new int[] {0,1}, n->new int[] {n[1],n[0]+n[1]}).limit(20).forEach(t->{System.out.print(t[0]+t[1]);});
		System.out.println();
		System.out.println("==== �Ǻ���ġ���� �� ����ϱ� ====");
		Stream.iterate(new int[] {0,1}, n->new int[] {n[1],n[0]+n[1]}).limit(20).map(t->t[0]+", ").forEach(System.out::print);
		System.out.println();
		
		//iterate�޼ҵ��̿��Ͽ� ���ǿ� ���缭 �����ϱ�
		//iterate�޼ҵ�� �ΰ��� ������ �Ű������� ���� �������� -> �̸żҵ带 �̿��ϸ� ���� limit() �żҵ带 �̿������ʰ� �ߴ��� �� ����.
		//ù��° : stream�ʱⰪ
		//�ι�° : iterator�� �ߴ��� �� �ִ� ���ǽ� * false�� ������ iterator�� �ߴܵ� -> Predicate�������̽�
		//����° : stream�� ���� �޼ҵ�
		Stream.iterate(0, n->n<100, n->n+1).forEach(System.out::print);//1~99���� ��µ�.
		System.out.println();
		
		
		//��Ʈ����Ŷ �޼ҵ��� takeWhile�� �̿��ص� �ߴܽ�ų �� ���� 
		System.out.println("===== takeWhile�̿��ϱ� ====");
		Stream.iterate(0,n->n+1).takeWhile(n->n<100).forEach(System.out::print);
		System.out.println();
		
		
		
		
		
		System.out.println("===== �迭�� ��Ʈ����ȯ�ϱ� =====");
		//�迭�� Arrays.stream() static�żҵ带 �̿��ؼ� �迭�� ��Ʈ������ ��ȯ�Ͽ� �����͸� ������ �� �ִ�.
		//�⺻�ڷ����� ������ ��Ʈ��Ŭ������ ������ ��. �� �ڷ������� ��Ʈ���� ���������� IntStream, Double, LongStream���� ����
		//�⺻�ڷ����迭�� �� �ش��ڷ�����Ʈ������ ��ȯ�ȴ�. ��ȯ�Ǵ°��� �⺻Stream���� ��ȯ�Ϸ��� boxed()�żҵ带 �̿��ϸ��.
		int[] intArr= {10,220,3,40,50,10,5,4,7,100};
		Stream<Integer> suStream=Arrays.stream(intArr).boxed();
		suStream.forEach(System.out::print);
		System.out.println();
		double[] dArr= {5.12,6.22,6.12,10.55,12.66};
		Stream<Double> dStream=Arrays.stream(dArr).boxed();
		dStream.forEach(System.out::print);
		System.out.println();
		
		//�⺻�ڷ����� �ƴ� ��ü�迭�� boxed()�޼ҵ带 �̿����� �ʰ� ����� �� ����.
		String[] names= {"ȫ�浿","��浿","��浿","�ֱ浿"};
		Stream<String> namesStream=Arrays.stream(names);
		namesStream.forEach(System.out::print);
		System.out.println();
		//Ȱ���� ������ �������� �����ϱ�
		Student[] students= {
					new Student.Builder().stdNo(1).name("test1").age((int)(Math.random()*50+10)).
					gender(1%2==0?Student.Gender.M:Student.Gender.F)
					.height(Math.round(((Math.random()*(190-150))+150)*100/100.0))
					.grade((int)(Math.random()*3+1)).address("�ּ�1").build(), 
					new Student.Builder().stdNo(2).name("test2").age((int)(Math.random()*50+10)).
					gender(2%2==0?Student.Gender.M:Student.Gender.F)
					.height(Math.round(((Math.random()*(190-150))+150)*100/100.0))
					.grade((int)(Math.random()*3+1)).address("�ּ�2").build(), 
					new Student.Builder().stdNo(3).name("test3").age((int)(Math.random()*50+10)).
					gender(3%2==0?Student.Gender.M:Student.Gender.F)
					.height(Math.round(((Math.random()*(190-150))+150)*100/100.0))
					.grade((int)(Math.random()*3+1)).address("�ּ�3").build(),
					new Student.Builder().stdNo(4).name("test4").age((int)(Math.random()*50+10)).
					gender(4%2==0?Student.Gender.M:Student.Gender.F)
					.height(Math.round(((Math.random()*(190-150))+150)*100/100.0))
					.grade((int)(Math.random()*3+1)).address("�ּ�4").build()
					};
		
		Stream<Student> studentStream=Arrays.stream(students);
		studentStream.forEach(System.out::println);
		
		
		System.out.println("===== Collection�� �̿��ؼ� ��Ʈ������� =====");
		//collection�� �ִ� �����͸� ������ stream�����
		//�ڹ�8���� collection��ü�� stream()�޼ҵ带 ������ ������, 
		//�� �żҵ带 �̿��ؼ� �����ϰ� collection�� ������ stream�� ���� �� �ִ�.
		List<Student> studentList=StudentDao.getStudents();
		Set<Student> studentSet=new HashSet(StudentDao.getStudents());
		Map<Integer,Student> studentMap=StudentDao.getStudentMap();
		
		System.out.println("===== List Student =====");
		Stream<Student> listStream=studentList.stream();
		listStream.limit(5).forEach(System.out::println);
		System.out.println("===== Set Student =====");
		Stream<Student> setStream=studentSet.stream();
		setStream.limit(5).forEach(System.out::println);
		System.out.println("===== Map Student =====");
		//map���� stream�� �������� ����. 
		//studentMap.stream().forEach(System.out::println);
		//Map�� Collection��ü�� ��ȯ�Ͽ� ����ؾ���.
		new ArrayList(studentMap.values()).stream().limit(5).forEach(System.out::println);
		
		System.out.println("===== csv����� ������ stream���� �����ϱ� =====");
		//csv������� �Ѿ�� ������ �����ؼ� stream���� �����ϱ�
		String csvData="������,19,��⵵�����,��,180.5,64.5";
		//PatterŬ������ �̿��ؼ� �Ľ��ؼ� ó���� �� �ִ�.
		Stream<String> csvStream=Pattern.compile(",").splitAsStream(csvData);
		csvStream.forEach(s->System.out.print(s+" "));
		System.out.println();
		
		//nio��Ű���� �ִ� FileŬ�������̿��ؼ� ������ ��������
		//���Ͽ� ����� ���ڿ� �����͸� ���ڿ��� ��������
		//noi��Ű���� FilesŬ������ lines�żҵ带 �̿��ϸ� stream<String>Ŭ������ ��ȯ�Ѵ�. 
		try(Stream<String> filestream=Files.lines(Paths.get("filetest.txt"),Charset.defaultCharset());) {
			//������ �������� ���ڿ� ����ϱ�
			//filestream.forEach(s->System.out.println(s));
			//������ ���� ����ϱ�
			long count=filestream.flatMap(s->Arrays.stream(s.split(""))).count();
			System.out.println(count);
					
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
}









