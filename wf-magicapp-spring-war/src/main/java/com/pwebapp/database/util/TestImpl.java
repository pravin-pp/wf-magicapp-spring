package com.pwebapp.database.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestImpl implements Test {

    private static final Logger logger = LogManager.getLogger(TestImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    @Override
    public void disp() {
        logger.info("In disp");
        Session session = sessionFactory.getCurrentSession();

    }
}
