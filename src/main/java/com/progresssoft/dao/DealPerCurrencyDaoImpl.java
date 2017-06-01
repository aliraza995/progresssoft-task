package com.progresssoft.dao;

import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.progresssoft.entity.DealsPerCurrencyEntity;

@Repository
public class DealPerCurrencyDaoImpl implements DealPerCurrencyDao {

	@Autowired
	private SessionUtilityHibernate sessionUtility;

	@Override
	public void addMulti(List<DealsPerCurrencyEntity> entities) {
		Session session = sessionUtility.getSessionFactory().openSession();
		try {
			entities.forEach(entity -> {
				session.save(entity);
			});
			session.flush();
			session.clear();
		} catch (HibernateException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
}
