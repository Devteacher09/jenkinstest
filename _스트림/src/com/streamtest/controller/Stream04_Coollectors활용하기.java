package com.streamtest.controller;

//Collectors클래스 정적import하기
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

public class Stream04_Coollectors활용하기 {
	//Stream데이터를 데이터 구조에 맞게 변환해주는 클래스를 Collector라고 한다.
	//주로 컬렉션으로 변환하여 많이 사용, toList()메소드를 이용.
	//Stream의 collect()메소드에 매개변수로 Collectors클래스가 제공하는 메소드를 이용한다.
	//Collectors클래스는 Stream이 가지고 있는데이터를 1.하나의 값으로 리듀스(재생성?)하고, 요소 그룹화하고, 요소를 분할한다.
	
	public static void main(String[] args) {
		
		//collect()메소드를 이용해서 reduce하기!
		
		//1. 하나의 값으로 리듀스하기 -> 요소들을 하나의 데이터(값)으로 반환하는 것 예) 합계, 총계, 평균 등
		List<Student> students=StudentDao.getStudents();
		//학생수 구하기
		long totalStudent=students.stream().collect(Collectors.counting());
		System.out.println(totalStudent);
		
		//Collectos클래스가 제공하는 메소드는 정적메소드로 정적 import를 실행하고 하면 편리하게 사용할 수 있다.
		totalStudent=students.stream().collect(counting());
		System.out.println(totalStudent);
			
		//최대값과 최소값출력하기
		//Collectors의 maxBy, minBy매소드를 활용함
		//maxBy(), minBy()매소드는 매개변수로 값을 비교하여처리하는 Comparator를 받는다.
		//학생중 나이가 제일 많은 학생과 적은 학생 구하기
		//반환형은 Optional클래스로 null값에 대해 처리해주기 편하게 만들어져 있다.
		Student s=students.stream().collect(maxBy(Comparator.comparing(Student::getAge))).orElse(new Student());
		System.out.println(s);
		s=students.stream().collect(minBy(Comparator.comparing(Student::getAge))).orElse(new Student());
		System.out.println(s);
		
		//요약연산하기-> 합계 평균을 구하는것.
		//합계구하기
		//Collectors.summingInt, summingDouble, SummingLong을 이용해서 합계 구하기
		int sumAge=students.stream().collect(summingInt(Student::getAge));
		System.out.println(sumAge);
		//평균구하기
		//Collectors.averagingInt, averagingLong, averagingDouble을 이용해서 평균구하기
		double avg=students.stream().collect(averagingDouble(Student::getAge));
		System.out.println(avg);
		
		//합계, 평균, 갯수를 한번에 구하는 메소드 
		//Collectors.sumarizingInt(); -> IntSummaryStatistics클래스를 반환함
		IntSummaryStatistics iss=students.stream().collect(Collectors.summarizingInt(Student::getAge));
		System.out.println(iss);
		//각 값은 getOOO을 이용해서 불러올수 있음
		System.out.println(iss.getCount());
		System.out.println(iss.getAverage());
		
		
		//문자열을 모두 연결해주는 메소드
		//stream데이터가 모두 문자열일때 그 데이터는 한개의 문자열로 합쳐주는 기능
		//Collectors.join() 
		//에러발생 -> 아래 구문은 Student객체를 데이터로 가지고 있는 Stream이기때문에 적용이 안됨.
		//String name=students.stream().collect(Collectors.joining());
		String names=students.stream().map(Student::getName).collect(Collectors.joining());
		System.out.println(names);
		//한개문자열로 연결시 특수 기호 포함시키기.
		names=students.stream().map(Student::getName).collect(Collectors.joining(","));
		System.out.println(names);
		
		
		//범용 reducing메소드를 이용해서 구현하기
		//java에서 구현한 reducing외에 사용자가 정의하여 reducing할 수 있다.
		//Collectors.reducing((연산의 시작||인수가 없을때 반환값->초기값?),적용할내용,연산처리할내용)
		//합계를 구하는 reducing만들기
		int totalSum=students.stream().collect(Collectors.reducing(0,Student::getAge,(pre,next)->pre+next));
		System.out.println(totalSum);
		
		//최대최소구하기(max, min)
		//나이가 제일 많은 학생 구하기!
		Optional<Student> result=students.stream().collect(Collectors.reducing((preStudent,nextStudent)->preStudent.getAge()>nextStudent.getAge()?preStudent:nextStudent));
		Student temp=result.get();
		System.out.println("나이 많은사람(max) : "+temp);
		result=students.stream().collect(Collectors.reducing((preStu,nextStu)->preStu.getAge()<nextStu.getAge()?preStu:nextStu));
		temp=result.get();
		System.out.println("나이 적은사람(min) : "+temp);
		
		
		//전체합계구하기 -> int형으로 바로 구하기
		Optional<Integer> intResult=students.stream().map(Student::getAge).reduce(Integer::sum);
		int intTotal=students.stream().map(Student::getAge).reduce(Integer::sum).get();
		
		//기본 reduce를 이용하면 Optional객체로 반환하기 되는데 바로 기본자료형으로 변경할 수 있다. 
		int totalAge=students.stream().mapToInt(Student::getAge).sum();
		System.out.println("나이 전체 합계구하기 : "+totalAge);
		
		//Student studentResult=students.stream().collect(Collectors.reducing((s1,s2)->s1.getName()+s2.getName())).get();
		
		
		
		//2. 요소 그룹화하기
		//grouping by 메소드를 이용해서 원하는 요소로 그룹화할 수 있다.
		Map<Student.Gender,List<Student>> genderList=students.stream().collect(Collectors.groupingBy(Student::getGender));
		System.out.println(genderList.get(Gender.M));
		System.out.println(genderList.get(Gender.F));
		
		//나이가 30대인 학생을 성별로 구분하기
		//성별에 T타입을 추가하여 그룹으로 묶어서 테스트하기
		students.add(new Student.Builder().stdNo(200).name("test"+200).age(20).
				gender(Gender.T).build());
		students.add(new Student.Builder().stdNo(201).name("test"+201).age(21).
				gender(Gender.T).build());
		
		//Stream의 데이터를 filter 후 그룹으로 묶기 때문에 gender의 T타입은 자체가 사라니는 결과가 나옴
		genderList=students.stream().filter(s1 -> s1.getAge()>=30&&s1.getAge()<40).collect(Collectors.groupingBy(Student::getGender));
		System.out.println(genderList.keySet());
		
		//그룹으로 묶을때 전체 데이터에 대해 하고 없는것 까지 항목으로 포함하려면 Collectors.groupBy메소드내부에서 filter를 해서 적용해야함.
		genderList=students.stream().collect(Collectors.groupingBy(Student::getGender, 
				Collectors.filtering(s1 -> s1.getAge()>=30&&s1.getAge()<40,Collectors.toList())));
		System.out.println(genderList.keySet());
		
		//연결될 데이터 넣기
		Map<String, List<String>> test=new HashMap();
		int count=0;
		for(Student s1 : students) {
			
			test.put(s1.getName(),Arrays.asList(s1.getName()+"_하나_"+count++,s1.getName()+"_둘_"+count++,s1.getName()+"_셋_"+count++));
		}
		
		//이름을 기준으로 성별과 그룹을 만들어서 리스트는 그룹에 연결하는 로직
		//각 이름별 연결된 태그를 이름에 맞는 성별로 그룹지어 조회하는 구문 ?? 뭔소리지? 내가 썼는데 모르겠네...
		//test map의 이름(key)에 연결된 관련 내용을 성별그룹으로 연결하는 로직
		Map<Student.Gender,Set<String>> resultTest=students.stream().collect(Collectors.groupingBy(Student::getGender,
				Collectors.flatMapping(s1->test.get(s1.getName()).stream(),Collectors.toSet())));
		
		System.out.println(resultTest.get(Gender.T));
		
		//두개의 조건으로 분류할때는 groupBy메소드 내부에 groupBy를 하나 더 사용해서 처리한다.
		//성별, 나이대별 학생분류하기
		Map<Student.Gender, Map<String,List<Student>>> testResult=students.stream()
				.collect(Collectors.groupingBy(Student::getGender,Collectors.groupingBy(s1->{
					if(10<=s1.getAge()&&s1.getAge()<20) return "10대";
					else if(20<=s1.getAge()&&s1.getAge()<30) return "20대";
					else if(30<=s1.getAge()&&s1.getAge()<40) return "30대";
					else if(40<=s1.getAge()&&s1.getAge()<50) return "40대";
					else if(50<=s1.getAge()&&s1.getAge()<60) return "50대";
					else return "other";
				})));
		System.out.println(testResult);
		for(Student.Gender g : testResult.keySet()) {
			System.out.println(g+" 성별에 대한 인원수 : "+testResult.get(g).size());//성별 카테고리
			for(String key : testResult.get(g).keySet()) {
				System.out.println(key);//성별내 나이대별 카테고리
				System.out.println(testResult.get(g).get(key));
				System.out.println("인원수 : "+testResult.get(g).get(key).size());
			}
		}
		//성별, 나이대별 나이가 가장 많은 학생 구하기
		//Collectors의 maxBy메소드를 이용하면 반환형이 Optional객체가 된다.
		Map<Student.Gender, Map<Object,Optional<Student>>> testResult2=students.stream().collect(Collectors.groupingBy(Student::getGender,
				Collectors.groupingBy(s1->{
					if(10<=s1.getAge()&&s1.getAge()<20) return "10대";
					else if(20<=s1.getAge()&&s1.getAge()<30) return "20대";
					else if(30<=s1.getAge()&&s1.getAge()<40) return "30대";
					else if(40<=s1.getAge()&&s1.getAge()<50) return "40대";
					else if(50<=s1.getAge()&&s1.getAge()<60) return "50대";
					else return "other";
				},Collectors.maxBy(Comparator.comparingInt(Student::getAge)))));
		
		System.out.println(testResult2);
		
		//반환형을 optional객체가 아닌 값으로 반환하기
		//maxBy()메소드의 두번째 매개변수에 반환할 값을 명시할 수 있다.
		//Collectors.collectingAndThen()메소드를 이용하면된다.
		// 첫번째 매개변수 : 집계할 함수 maxBy, minBy, 
		Map<Student.Gender, Map<Object,Object>> testResult3=students.stream().collect(Collectors.groupingBy(Student::getGender,
				Collectors.groupingBy(s1->{
					if(10<=s1.getAge()&&s1.getAge()<20) return "10대";
					else if(20<=s1.getAge()&&s1.getAge()<30) return "20대";
					else if(30<=s1.getAge()&&s1.getAge()<40) return "30대";
					else if(40<=s1.getAge()&&s1.getAge()<50) return "40대";
					else if(50<=s1.getAge()&&s1.getAge()<60) return "50대";
					else return "other";
				},Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Student::getAge)), s1->s1.get().getAge()))));
		
		System.out.println(testResult3);
		
		// 기본적용이 가능한지 확인 흐미 복잡하네 		
		int tt=students.stream().collect(Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Student::getAge)),o->o.get().getAge()));
		System.out.println(tt);
		
		//IntSummaryStatistics으로 반환하는 collect만들기
		Object count2=students.stream().collect(Collectors.collectingAndThen(Collectors.summarizingInt(Student::getAge),is->is.getMin()));
		System.out.println(count2);
		
		//groupBy메소드 반환형 지정
		Map<String,Set<Student>> set=students.stream().collect(Collectors.groupingBy(s1->{
			if(10<=s1.getAge()&&s1.getAge()<20) return "10대";
			else if(20<=s1.getAge()&&s1.getAge()<30) return "20대";
			else if(30<=s1.getAge()&&s1.getAge()<40) return "30대";
			else if(40<=s1.getAge()&&s1.getAge()<50) return "40대";
			else if(50<=s1.getAge()&&s1.getAge()<60) return "50대";
			else return "other";
		},Collectors.toSet()));
		System.out.println(set);	
		
		//데이터 분할하기
		//데이터의 분할는 Collectors.partitioningBy()메소드를 이용해서 처리할 수 있다. 분할된데이터는 List로 출력됨.
		//컬렉션 데이터를 boolean값의 결과로 분할하기
		Map<Boolean, List<Student>> partitionResult=students.stream().collect(Collectors.partitioningBy(s1->s1.getAge()<30));
		System.out.println(partitionResult);
		System.out.println(partitionResult.keySet());
		
		//학생을 저학년과 고학년으로 분류하기
		//4학년까지 저학년, 5~6학년은 고학년
		partitionResult=students.stream().collect(Collectors.partitioningBy(s1->s1.getGrade()<=4));
		System.out.println(partitionResult);
		System.out.println("저학년");
//		for(Student s1 : partitionResult.get(true)) {
//			System.out.println(s1);
//		}
		//저학년의 나이 평균
		double ageAvg=partitionResult.get(true).stream().collect(Collectors.averagingDouble(Student::getAge));
		System.out.println(ageAvg);
		
		System.out.println("고학년");
//		for(Student s1 : partitionResult.get(false)) {
//			System.out.println(s1);
//		}
		ageAvg=partitionResult.get(false).stream().collect(Collectors.averagingDouble(Student::getAge));
		System.out.println(ageAvg);
		
		//고학년 저학년으로 분할하고 내부에서 다시 성별로 구분하기
		Map<Boolean,Map<Student.Gender,List<Student>>> partiGroup=students.stream().collect(Collectors.partitioningBy(s1->s1.getGrade()<=4,Collectors.groupingBy(Student::getGender)));
		System.out.println(partiGroup);
		
		int data=9;
		boolean flag=IntStream.range(2, data).noneMatch(i->data%i==0);
		System.out.println(flag);
		
		
//		Collect커스터마이징하기
//		Collect인터페이스를 구현해보자
//		Collect인터페이스는 네개의 메소드가 선언되어있고, 제네릭으로 세개의 타입을 받는다
//		메소드
//		supplier : 새로운 결과를 만들어 저장할 컨테이너 생성(컬렉션 프레임워크, stream의 값을 새로 저장시킬 객체)하는 매소드 -> 수집데이터 저장소를반환하는 메소드 반환 
//		accumulator : supplier매소드에서 생성한 컨테이너에 데이터(값)을 저장시키는 메소드 -> 데이터 수집 방법을 반환하는 메소드 반환
//		finisher : 최종결과를 반환하는 메소드 * 항등함수(자신을 반환하는 함수)를 호출함 -> 
//		combiner : 병렬화 처리에서 sub파트에서 수집한 리스트와 합치는 기능 -> 병렬처리할 메소드 반환
//		characteristics :
		//UNORDERED : 리듀스 결과는 스트림 요소의 방문 순서나 누적 순서에 영향을 받지 않는다.
		//CONCURRENT : 다중스레드에서 accumulator함수를 동시에 호출할 수 있으며 이 컬렉터는 스트림의 병렬 리듀싱을 수행할 수 있음.
		//IDENTITY_FINISH : finisher메소드가 반환하는 함수는 단순히 identity를 적용할 뿐이므로 이를 생략할 수 있다.

		
		//나만의 Collect만들기
		//List로 종합하는 Collect만들기
		//Stream데이터를 List로 만들어주는 collect작성
		List<String> myList=students.stream().map(s1->s1.getName()).collect(new ToMyListCollect<String>());
		System.out.println(myList);
		List<Student> myList2=students.stream().filter(s1->s1.getAge()>=50).collect(new ToMyListCollect<Student>());
		System.out.println(myList2);
		//메소드를 구현하지 않고도 collect를 커스터마이징하여 이용할 수 있다. 
		myList2=students.stream().filter(s1->s1.getHeight()>180).collect(ArrayList::new, List::add, List::addAll);
		System.out.println(myList2);
		
		
		System.out.println(List.of(1,2,3,4,5,6,7,8,9,10).stream().takeWhile(i->i<=5).collect(Collectors.toList()));
		
		
	}
	
}

