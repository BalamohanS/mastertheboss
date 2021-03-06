package com.mastertheboss.jaxws.ws;

import javax.ejb.Stateless;

 
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class AccountManager {
	@PersistenceContext 
	private EntityManager em;	


	public Account findAccount(String name)  
	{
		Query query = em.createQuery("FROM Account WHERE name= :name");
		query.setParameter("name", name);
		Account account = (Account)query.getSingleResult();
		System.out.println("Found Account "+account);
		return account;	}


	public void createAccount(String name) {
		Account account = new Account();
		account.setName(name);
		account.setAmount(0l);
		em.persist(account);
	 
	}

	public void deposit( 
			String name,

			long amount)  
	{
		Account account = findAccount(name);
		long currentAmount = account.getAmount();
		account.setAmount(currentAmount + amount);
		System.out.println("Updated Account "+account);
		em.persist(account);
	}
	public void withdraw(String name, long amount) {
		Account account = findAccount(name);	
		long currentAmount = account.getAmount();
		long newAmount = currentAmount - amount;
		if (newAmount < 0) {
			throw new RuntimeException("Unsufficient funds for account "+ account.getName());

		}
		account.setAmount(newAmount);
		em.persist(account);

	}
}
