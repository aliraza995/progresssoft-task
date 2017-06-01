package com.progresssoft.dao;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.progresssoft.entity.InvalidDealEntity;

@Repository
public class InvalidDealDaoImpl implements InvalidDealDao {
	private static final Logger LOG = LogManager.getLogger();

	@Autowired
	private SessionUtilityHibernate sessionUtility;

	@PostConstruct
	public void start() {
	}

	@Override
	public void addMulti(List<InvalidDealEntity> deals) {
		Session session = sessionUtility.getSessionFactory().openSession();
		try {
			for (int i = 0; i < deals.size(); i++) {
				session.save(deals.get(i));
				if (i % 1000 == 0) {
					session.flush();
					session.clear();
				}
			}
		} catch (HibernateException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
}
