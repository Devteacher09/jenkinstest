package com.streamtest.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.streamtest.model.dao.TransactionDao;
import com.streamtest.model.vo.Trader;
import com.streamtest.model.vo.Transaction;

public class Stream_ex_데이터조작_실습문제 {
	//1. 2022년 일어난 모든 트렌젝션을 값이 높은순으로 정렬하여 출력하기
	//2.거래자가 근무하는 모든도시를 중복없이 나열하기
	//3. 서울에서 근무하는 사람을 모두찾아서 이름순으로 정렬하기
	//4. 제주의 거래자가있는지 확인하기
	//5. 대전에서 근무하는 거래자의 모든 거래값을 출력하기
	//6. 모든 거래자를 이름순으로 정렬하여 출력하기
	//7. 전체거래의 최대값을 출력하기
	//8. 전체거래의 최소값을 출력하기
	
	public static void main(String[] args) {
		List<Transaction> list=TransactionDao.getList();
		//1. 2022년 일어난 모든 트렌젝션을 값이 높은순으로 정렬하여 출력하기
		List<Transaction> result=list.stream().filter(t->t.getYear()==2022)
								.sorted(Comparator.comparing(Transaction::getValue).reversed())
								.collect(Collectors.toList());
		printResult(result);
		
		//2.거래자가 근무하는 모든도시를 중복없이 나열하기
		List<String> resultStr=list.stream().map(t->t.getTrader().getCity())
								.distinct().collect(Collectors.toList());
		Set<String>resultSet=list.stream().map(t->t.getTrader().getCity())
								.collect(Collectors.toSet());
		printResult(resultStr);
		
		//3. 서울에서 근무하는 사람을 모두찾아서 이름순으로 정렬하기
		List<Trader> resultTrader=list.stream().map(t->t.getTrader()).filter(t->t.getCity().equals("서울"))
				.sorted(Comparator.comparing(Trader::getName)).distinct().collect(Collectors.toList());
		printResult(resultTrader);
		
		//4. 제주의 거래자가있는지 확인하기
		System.out.println(list.stream().anyMatch(t->t.getTrader().getCity().equals("제주")));
		
		//5. 대전에서 근무하는 거래자의 모든 거래값을 출력하기
		List<Integer> resultInt=list.stream().filter(t->t.getTrader().getCity().equals("대전"))
								.map(t->t.getValue()).collect(Collectors.toList());
		printResult(resultInt);
		
		//6. 모든 거래자를 이름순으로 정렬하여 출력하기
		resultTrader=list.stream().map(t->t.getTrader()).sorted(Comparator.comparing(Trader::getName)).distinct().collect(Collectors.toList());
		printResult(resultTrader);
		
		//7. 전체거래의 최대값을 출력하기
		list.stream().max(Comparator.comparing(Transaction::getValue)).ifPresent(t->System.out.println(t.getValue()));
		//8. 전체거래의 최소값을 출력하기
		list.stream().min(Comparator.comparing(Transaction::getValue)).ifPresent(t->System.out.println(t.getValue()));
		
		
	}
	private static <T> void printResult(List<T> result) {
		for(T t : result) {
			System.out.println(t);
		}
	}

}
