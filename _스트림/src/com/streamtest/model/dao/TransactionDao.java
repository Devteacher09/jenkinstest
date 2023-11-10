package com.streamtest.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.streamtest.model.vo.Trader;
import com.streamtest.model.vo.Transaction;

public class TransactionDao {

	private static List<Transaction> list=new ArrayList();
	
	static {
		list=List.of(
			new Transaction(new Trader("������","����"),2022,300),
			new Transaction(new Trader("ȫ�浿","����"),2022,500),
			new Transaction(new Trader("��浿","�λ�"),2021,200),
			new Transaction(new Trader("�̼���","����"),2020,100),
			new Transaction(new Trader("�Ѹ�","����"),2022,800),
			new Transaction(new Trader("��浿","�λ�"),2021,600),
			new Transaction(new Trader("����õ","��õ"),2022,400),
			new Transaction(new Trader("������","����"),2020,1000)
			);
	}

	public static List<Transaction> getList() {
		return list;
	}
	
}
