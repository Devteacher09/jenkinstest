package com.streamtest.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.streamtest.model.dao.TransactionDao;
import com.streamtest.model.vo.Trader;
import com.streamtest.model.vo.Transaction;

public class Stream_ex_����������_�ǽ����� {
	//1. 2022�� �Ͼ ��� Ʈ�������� ���� ���������� �����Ͽ� ����ϱ�
	//2.�ŷ��ڰ� �ٹ��ϴ� ��絵�ø� �ߺ����� �����ϱ�
	//3. ���￡�� �ٹ��ϴ� ����� ���ã�Ƽ� �̸������� �����ϱ�
	//4. ������ �ŷ��ڰ��ִ��� Ȯ���ϱ�
	//5. �������� �ٹ��ϴ� �ŷ����� ��� �ŷ����� ����ϱ�
	//6. ��� �ŷ��ڸ� �̸������� �����Ͽ� ����ϱ�
	//7. ��ü�ŷ��� �ִ밪�� ����ϱ�
	//8. ��ü�ŷ��� �ּҰ��� ����ϱ�
	
	public static void main(String[] args) {
		List<Transaction> list=TransactionDao.getList();
		//1. 2022�� �Ͼ ��� Ʈ�������� ���� ���������� �����Ͽ� ����ϱ�
		List<Transaction> result=list.stream().filter(t->t.getYear()==2022)
								.sorted(Comparator.comparing(Transaction::getValue).reversed())
								.collect(Collectors.toList());
		printResult(result);
		
		//2.�ŷ��ڰ� �ٹ��ϴ� ��絵�ø� �ߺ����� �����ϱ�
		List<String> resultStr=list.stream().map(t->t.getTrader().getCity())
								.distinct().collect(Collectors.toList());
		Set<String>resultSet=list.stream().map(t->t.getTrader().getCity())
								.collect(Collectors.toSet());
		printResult(resultStr);
		
		//3. ���￡�� �ٹ��ϴ� ����� ���ã�Ƽ� �̸������� �����ϱ�
		List<Trader> resultTrader=list.stream().map(t->t.getTrader()).filter(t->t.getCity().equals("����"))
				.sorted(Comparator.comparing(Trader::getName)).distinct().collect(Collectors.toList());
		printResult(resultTrader);
		
		//4. ������ �ŷ��ڰ��ִ��� Ȯ���ϱ�
		System.out.println(list.stream().anyMatch(t->t.getTrader().getCity().equals("����")));
		
		//5. �������� �ٹ��ϴ� �ŷ����� ��� �ŷ����� ����ϱ�
		List<Integer> resultInt=list.stream().filter(t->t.getTrader().getCity().equals("����"))
								.map(t->t.getValue()).collect(Collectors.toList());
		printResult(resultInt);
		
		//6. ��� �ŷ��ڸ� �̸������� �����Ͽ� ����ϱ�
		resultTrader=list.stream().map(t->t.getTrader()).sorted(Comparator.comparing(Trader::getName)).distinct().collect(Collectors.toList());
		printResult(resultTrader);
		
		//7. ��ü�ŷ��� �ִ밪�� ����ϱ�
		list.stream().max(Comparator.comparing(Transaction::getValue)).ifPresent(t->System.out.println(t.getValue()));
		//8. ��ü�ŷ��� �ּҰ��� ����ϱ�
		list.stream().min(Comparator.comparing(Transaction::getValue)).ifPresent(t->System.out.println(t.getValue()));
		
		
	}
	private static <T> void printResult(List<T> result) {
		for(T t : result) {
			System.out.println(t);
		}
	}

}
