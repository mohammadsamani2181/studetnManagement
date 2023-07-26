package com.studentManagement.repository;

import com.studentManagement.model.Lesson;
import com.studentManagement.model.LessonType;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LessonRepositoryManualTransaction {

    private final PlatformTransactionManager platformTransactionManager;
    private final SessionFactory sessionFactory;


    public void save(Lesson lesson) {
//        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
//        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);

        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            session.persist(lesson);

            session.getTransaction().commit();
//            platformTransactionManager.commit(transactionStatus);
        } catch (Exception ex) {
//            platformTransactionManager.rollback(transactionStatus);
            throw ex;
        }
    }

    @Transactional(value = "projectTransactionManager", rollbackFor = Exception.class)
    public List<Lesson> getLessonsByType(LessonType lessonType) {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select lesson " +
                                "from Lesson lesson " +
                                "where " +
                                "lesson.type = :lessonType "
                        , Lesson.class)
                .setParameter("lessonType", lessonType)
                .getResultList();
    }
}
