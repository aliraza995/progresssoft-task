package com.progresssoft.dao;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.progresssoft.entity.DealEntity;

@Repository
public class DealDaoImpl implements DealDao {
	private static final Logger LOG = LogManager.getLogger();

	@Autowired
	private SessionUtilityHibernate sessionUtility;

	@Override
	public void addMulti(List<DealEntity> deals) {
		Session session = sessionUtility.getSessionFactory().openSession();
		try {
			LOG.info("Deals to be stored: " + deals.size());
			for (int i = 0; i < deals.size(); i++) {
				DealEntity deal = deals.get(i);
				if (deal == null) {
					LOG.info("Row is null");
					continue;
				}
				session.save(deal);
				if (i % 25000 == 0) {
					session.flush();
					session.clear();
				}
			}
			session.flush();
			session.clear();
		} catch (HibernateException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
}
