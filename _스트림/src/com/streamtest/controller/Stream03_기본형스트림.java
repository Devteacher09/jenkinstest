package com.streamtest.controller;

import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.streamtest.model.dao.StudentDao;
import com.streamtest.model.vo.Student;

public class Stream03_�⺻����Ʈ�� {
	//�������� �⺻��Ưȭ ��Ʈ���� �����Ѵ�. 
	//��Ʈ�� API�� �ڽ� ����� ���� �� �ֵ��� �� �⺻�ڷ����� ��Ʈ���� �����Ѵ�. -> ���� �����͸� �����Ҵ� ����ȭ�� �� �ִ�.
	//int ->  IntStream
	//double -> DoubleStream
	//loing -> longStream
	//�� ��Ʈ�� �������̽����� �� ���� ����� �� �ִ� ������ �޼ҵ带 ������ ����
	// sum, max, reduce ��
	private static List<Student> list=StudentDao.getStudents();
	
	public static void main(String[] args) {
		//randomŬ���� �̿��Ͽ� stream����� 
		//�⺻���� ����� inits()�żҵ带 �̿��ϸ� �������� �������� ������. 
		//limit()�޼ҵ带 �̿��ؼ� ������.
		//1~10 �������� 5�� ����ϱ� *�ζǹ�ȣ����ϱ�
		IntStream is=new Random().ints(1,45).distinct().limit(6);
		is.forEach(s->System.out.print(s+" "));
		System.out.println();
		
		System.out.println("===== range()�޼ҵ��̿��ϱ� =====");
		//range()�żҵ带 �̿��ؼ� �⺻�ڷ��� stream �����ϱ�
		//������� ���ڸ� �����ؼ� stream�� �����ϴ� �żҵ�
		//range(ù��°����, ����������)
		IntStream.range(10,20).forEach(s->System.out.print(s+" "));
		System.out.println();
		LongStream.range(100, 110).forEach(s->System.out.print(s+" "));
		System.out.println();
		
		//������ �����ϴ� 000Stream���� ��ȯ�Ҷ��� �⺻ Stream���� mapToInt, mapToDouble, mapToLong�� �̿��Ѵ�.
		IntStream intstream=list.stream().mapToInt(Student::getAge);
		System.out.println(intstream);
		System.out.println(intstream.count());
		//��Ʈ���� �ѹ�����ϸ� �������� ����.
		intstream=list.stream().mapToInt(Student::getAge);
		System.out.println(intstream.distinct().count());
		
		//�л��� ���̰� ���� ���� �л��� ���� ���ϱ�
		//max()�޼ҵ� �̿� : max�żҵ�� optionalŬ������ ��ȯ�ϱ� ������ ����Ҷ� OptionalIntŬ������ �����ϴ� �޼ҵ带 �̿��ؼ� �����.
		OptionalInt opint=list.stream().mapToInt(Student::getAge).max();
		int maxAge=opint.orElse(1);
		System.out.println(maxAge);
		
		//Ŭ������ �ڷ����� �ٸ��⶧���� ������ �Ұ�����.
		//�⺻��Ưȭ stream�� �Ϲ� stream���� �ٽ� �����Ϸ��� boxed()�żҵ带 �̿��ϸ��.
		//Stream<Integer> ageStream=list.stream().mapToInt(Student::getAge);//�����߻�
		Stream<Integer> ageStream=list.stream().mapToInt(Student::getAge).boxed();
		
		//System.out.println(maxAge);
		
		//IntStreamŬ������ �̿��ؼ� Ư�������� ���ڰ�������
		//range, rangeClosed�żҵ带 �̿��ؼ� Ư�������� ���� ��ü�� �����ü� �ִ�
		//range()�� ���ᰪ�� ���Ե�������
		//rangeClosed() ���ᰪ�� ���Ե�.
		IntStream rangeint=IntStream.range(1, 100);//1~99������ �� ��������
		rangeint.forEach(System.out::println);
		rangeint=IntStream.rangeClosed(1, 100);
		rangeint.forEach(System.out::println);
		
		//doubleStream�̿��ϱ�
		DoubleStream dstream=list.stream().mapToDouble(Student::getHeight);
		dstream.limit(5).forEach(System.out::println);
		dstream=list.stream().mapToDouble(Student::getHeight).distinct();
		//�л��� ���� ūŰ�� ��cm�ΰ�?
		OptionalDouble opDouble=dstream.max();
		System.out.println(opDouble.orElse(160.8));
		
		
		
		
		
	}
}







