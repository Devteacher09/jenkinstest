package com.streamtest.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.streamtest.model.vo.Trader;
import com.streamtest.model.vo.Transaction;

public class TransactionDao {

	private static List<Transaction> list=new ArrayList();
	
	static {
		list=List.of(
			new Transaction(new Trader("유병승","대전"),2022,300),
			new Transaction(new Trader("홍길동","서울"),2022,500),
			new Transaction(new Trader("김길동","부산"),2021,200),
			new Transaction(new Trader("이순신","서울"),2020,100),
			new Transaction(new Trader("둘리","대전"),2022,800),
			new Transaction(new Trader("고길동","부산"),2021,600),
			new Transaction(new Trader("김인천","인천"),2022,400),
			new Transaction(new Trader("고제주","제주"),2020,1000)
			);
	}

	public static List<Transaction> getList() {
		return list;
	}
	
}
