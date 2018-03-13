package com.demo.utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtility {
	private static final SessionFactory sessionFactory;
	private static final ServiceRegistry serviceRegistry;

	private static Logger logger = LogManager.getLogger(HibernateUtility.class);

	static {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration cfg = new Configuration();
			cfg.configure();
			serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).getBootstrapServiceRegistry();
			sessionFactory = cfg.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			// Make sure you log the exception, as it might be swallowed
			logger.error("Initial SessionFactory creation failed." + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void closeSessionFactory() {
		if (sessionFactory != null)
			sessionFactory.close();
	}
}