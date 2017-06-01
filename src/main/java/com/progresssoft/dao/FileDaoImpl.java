package com.progresssoft.dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.progresssoft.entity.FileEntity;

@Repository
public class FileDaoImpl implements FileDao {
	private static final Logger LOG = LogManager.getLogger();

	@Autowired
	private SessionUtilityHibernate sessionUtility;
	
	@Override
	public FileEntity add(FileEntity entity) {
		Session session = sessionUtility.getSessionFactory().openSession();
		try {
			entity.setContentHash(calculateHash(entity));
			if (isFileWithSameContent(entity, session)) {
				throw new IllegalStateException("file with same content already exists");
			}
			session.save(entity);
			session.flush();
			session.clear();
			LOG.info("File Saved:" + entity.getId());
		} catch (HibernateException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
		return entity;
	}

	
    @Override
    public FileEntity getById(final String id) {
        if (id == null) {
            throw new IllegalArgumentException(
                    "Unable to get entity. File id is null.");
        }

		Session session = sessionUtility.getSessionFactory().openSession();
		return (FileEntity) session.get(FileEntity.class, id);
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public List<FileEntity> find(final String filename) {
        LOG.debug("Finding File Entity");

		Session session = sessionUtility.getSessionFactory().openSession();
        final Criteria criteria = session.createCriteria(FileEntity.class, "file");
        //Add filters to the criteria
        if (filename != null) {
        	criteria.add(Restrictions.eq("file.fileName", filename));
        }

    	criteria.addOrder(Order.asc("file.fileName"));

        final List<FileEntity> files = criteria.list();
        return files;

    }

	private boolean isFileWithSameContent(FileEntity entity, Session session) {
		final Criteria criteria = session.createCriteria(FileEntity.class, "file");
		criteria.add(Restrictions.eq("file.contentHash", entity.getContentHash()));
		return criteria.list().size() > 0;
	}

	private byte[] calculateHash(FileEntity entity) {
		try {
			final MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			return digest.digest(entity.getContent().getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			// Will only happen in some extreme java environment issue
			LOG.fatal("Invalid hashing algorithm specified", e);
			throw new IllegalStateException("Invalid MessageDigest algorithm specified in code", e);
		}
	}

}
