package com.qa.intergration;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.qa.persistence.domain.Account;
import com.qa.persistence.domain.Transaction;
import com.qa.persistence.repository.AccountDBRepository;
import com.qa.util.JSONUtil;



@RunWith(MockitoJUnitRunner.class)
public class AccountDBRepositoryTest {

	private static final String MOCK_VALUE2 = "test_value2";
	
	private static final String MOCK_VALUE = "test_value";
	
	@InjectMocks
	private AccountDBRepository dbRepo;
	
	@Mock
	private EntityManager em;
	
	@Mock
	private Query query;
	
	private JSONUtil util;
	
	private static final String MOCK_DATA_ARRAY = "[{\"firstName\":\"Michael\",\"secondName\":\"Massey\",\"accountNumber\":\"A12345\",\"transaction\":[]}]";
	
	private static final String MOCK_OBJECT = "{\"firstName\":\"Michael\",\"secondName\":\"Massey\",\"accountNumber\":\"A12345\",\"transaction\":[]}";
	
	@Before
	public void setup() {
		dbRepo.setManager(em);
		util = new JSONUtil();
		dbRepo.setUtil(util);
	}
	
	@Test
	public void testGetAllAccount() {
		Mockito.when(em.createQuery(Mockito.anyString())).thenReturn(query);
		List<Account> accounts = new ArrayList<Account>();
		List<Transaction> transactions = new ArrayList<Transaction>();
		accounts.add(new Account("Michael", "Massey", "A12345", transactions));
		Mockito.when(query.getResultList()).thenReturn(accounts);
		Assert.assertEquals(MOCK_DATA_ARRAY, dbRepo.getAllAccounts());
	}
	
	@Test
	public void testCreateAccount() {
		String reply = dbRepo.createAccount(MOCK_OBJECT);
		Assert.assertEquals(reply, "{\"message\": \"account has been sucessfully added\"}");
	}
	
	@Test
	public void testUpdateAccount() {
		String reply = dbRepo.updateAccount(1L, MOCK_OBJECT);
		Assert.assertEquals(reply, "{\"message\": \"account sucessfully updated\"}");
	}
	
	@Test
	public void testDeleteAccount() {
		String reply = dbRepo.deleteAccount(1L);
		Assert.assertEquals(reply, "{\"message\": \"account sucessfully deleted\"}");
	}
	
}
