package exercise.repository.common;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// The Repository with Spring and Hibernate
//     Last modified: December 31, 2019
//     by Eugen Paraschiv
//     https://www.baeldung.com/persistence-layer-with-spring-and-hibernate

// Simplify the Repository with Spring and Java Generics
//     Last modified: January 18, 2020
//     by Eugen Paraschiv
//     https://www.baeldung.com/simplifying-the-data-access-layer-with-spring-and-java-generics

// propagation=Propagation.REQUIRED  (default)
// Propagation.REQUIRES_NEW
// Propagation.NESTED
// Propagation.SUPPORTS :
//     if the method  is called from within a transaction,
//         it supports the transaction.
//     if no transaction exists,
//         the method is executed non-transactionally.

@Repository
@Transactional
public abstract class AbstractRepositoryImpl<T, ID extends Serializable> implements GenericRepository<T, ID> {

    private Class<T> clazz;

    @SuppressWarnings("unchecked")
    protected AbstractRepositoryImpl() {
        if (clazz == null) {
            this.clazz = (Class<T>) GenericTypeResolver.resolveTypeArguments(getClass(), AbstractRepositoryImpl.class)[0];
        }
    }

    // -----------------------------------
    // Jpa Api
    // -----------------------------------

    @PersistenceContext
    private EntityManager entityManager;

    public T getReference(Class<T> entityClass, final ID id) {
        return entityManager.getReference(entityClass, id);
    }

    public T find(Class<T> entityClass, final ID id) {
        return entityManager.find(entityClass, id);
    }

    public T find(Class<T> entityClass, final ID id, LockModeType lockMode) {
        return entityManager.find(entityClass, id, lockMode);
    }

    // Optimistic locks are specified using LockModeType.OPTIMISTIC and LockModeType.OPTIMISTIC_FORCE_INCREMENT.
    // The lock mode type values LockModeType.READ and LockModeType.WRITE are
    //   synonyms of OPTIMISTIC and OPTIMISTIC_FORCE_INCREMENT respectively.
    // The latter are to be preferred for new applications.
    public void lock(final T managedInstance, LockModeType lockMode) {
        entityManager.lock(managedInstance, lockMode);
    }

    public void persist(final T transientInstance) {
        entityManager.persist(transientInstance);
    }

    public T merge(final T valueInstance) {
        return entityManager.merge(valueInstance);
    }

    public void refresh(final T managedInstance) {
        entityManager.refresh(managedInstance);
    }

    public void remove(final T managedInstance) {
        entityManager.remove(managedInstance);
    }

    public void detach(final T managedInstance) {
        entityManager.detach(managedInstance);
    }

    public boolean contains(final T instance) {
        return entityManager.contains(instance);
    }

    public void flush() {
        entityManager.flush();
    }

    public void clear() {
        entityManager.clear();
    }

    public void close() {
        entityManager.close();
    }

    // Additional API

    public T getReference(final ID id) {
        return entityManager.getReference(clazz, id);
    }

    public T find(final ID id) {
        return entityManager.find(clazz, id);
    }

    public T find(final ID id, LockModeType lockMode) {
        return entityManager.find(clazz, id, lockMode);
    }

    public void save(final T transientInstance) {
        entityManager.persist(transientInstance);
    }

    public void create(final T transientInstance) {
        entityManager.persist(transientInstance);
    }

    public void delete(final T managedInstance) {
        entityManager.remove(managedInstance);
    }

    public void evict(final T managedInstance) {
        entityManager.detach(managedInstance);
    }

    // User-defined API

    public long count() {
        var qry = String.format("SELECT count(o) FROM %s o", clazz.getSimpleName());
        return entityManager.createQuery(qry, Long.class).getSingleResult();
    }

    public long count(final String qry) {
        return entityManager.createQuery(qry, Long.class).getSingleResult();
    }

    public T findById(final ID id) {
        return entityManager.find(clazz, id);
    }

    public List<T> findAll() {
        var qry = String.format("SELECT o FROM %s o", clazz.getSimpleName());
        return entityManager.createQuery(qry, clazz).getResultList();
    }

    public T findOne(final String qry) {
        return entityManager.createQuery(qry, clazz).getSingleResult();
    }

    public List<T> findMany(final String qry) {
        return entityManager.createQuery(qry, clazz).getResultList();
    }

    public void deleteById(final ID id) {
        final T managedInstance = entityManager.find(clazz, id);
        entityManager.remove(managedInstance);
    }
}

