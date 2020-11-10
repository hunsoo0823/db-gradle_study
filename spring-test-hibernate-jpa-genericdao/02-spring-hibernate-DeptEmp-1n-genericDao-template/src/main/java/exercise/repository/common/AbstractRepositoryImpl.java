package exercise.repository.common;

import java.io.Serializable;
import java.util.List;

import javax.persistence.LockModeType;

import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    // Session API
    // -----------------------------------

    @Autowired
    private SessionFactory sessionFactory;

    protected final Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    // return always a proxy; ObjectNotFoundException if not found
    public T load(Class<T> entityClass, final ID id) {
        return getCurrentSession().load(entityClass, id);
    }

    // returns fully initialized object; null if not found
    public T get(Class<T> entityClass, final ID id) {
        return getCurrentSession().get(entityClass, id);
    }

    public T get(Class<T> entityClass, final ID id, LockOptions lockOptions) {
        return getCurrentSession().get(entityClass, id, lockOptions);
    }

    public T getReference(Class<T> entityClass, final ID id) {
        return getCurrentSession().getReference(entityClass, id);
    }

    public T find(Class<T> entityClass, final ID id) {
        return getCurrentSession().find(entityClass, id);
    }

    public T find(Class<T> entityClass, final ID id, LockModeType lockMode) {
        return getCurrentSession().find(entityClass, id, lockMode);
    }

    // 1: To perform a version check (LockMode.READ),
    // 2: To upgrade to a pessimistic lock (LockMode.PESSIMISTIC_WRITE),
    // 3: To reassociate a transient instance with a session (LockMode.NONE).
    public void lock(final T managedOrDetachedInstance, LockMode lockMode) {
        // Convenient form of:
        //      Session.LockRequest.lock(managedOrDetachedInstance) via buildLockRequest(LockOptions)
        //      getCurrentSession().buildLockRequest(lockOptions).lock(managedOrDetachedInstance);
        getCurrentSession().lock(managedOrDetachedInstance, lockMode);
    }

    public void persist(final T transientInstance) {
        getCurrentSession().persist(transientInstance);
    }

    @SuppressWarnings("unchecked")
    public ID save(final T transientInstance) {
        return (ID) getCurrentSession().save(transientInstance);
    }

    public void saveOrUpdate(final T transientOrDetachedInstance) {
        getCurrentSession().saveOrUpdate(transientOrDetachedInstance);
    }

    public void update(final T modifiedDetachedInstance) { // reattach the detached instance
        getCurrentSession().update(modifiedDetachedInstance);
    }

    @SuppressWarnings("unchecked")
    public T merge(final T valueInstance) {
        return (T) getCurrentSession().merge(valueInstance);
    }

    public void refresh(final T managedInstance) { // re-read the state
        getCurrentSession().refresh(managedInstance);
    }

    public void delete(final T managedInstance) {
        getCurrentSession().delete(managedInstance);
    }

    public void remove(final T managedInstance) {
        getCurrentSession().remove(managedInstance);
    }

    public void evict(final T managedInstance) {
        getCurrentSession().evict(managedInstance);
    }

    public void detach(final T managedInstance) {
        getCurrentSession().detach(managedInstance);
    }

    public boolean contains(final T instance) {
        return getCurrentSession().contains(instance);
    }

    public boolean contains(final String entityName, final T instance) {
        return getCurrentSession().contains(entityName, instance);
    }

    public void flush() {
        getCurrentSession().flush();
    }

    public void clear() {
        getCurrentSession().clear();
    }

    public void close() {
        getCurrentSession().close();
    }

    // Additional API

    public T load(final ID id) {
        return getCurrentSession().load(clazz, id);
    }

    public T get(final ID id) { // returns fully initialized object; null if not found
        return getCurrentSession().get(clazz, id);
    }

    public T get(final ID id, LockOptions lockOptions) {
        return getCurrentSession().get(clazz, id, lockOptions);
    }

    public T getReference(final ID id) {
        return getCurrentSession().getReference(clazz, id);
    }

    public T find(final ID id) {
        return getCurrentSession().find(clazz, id);
    }

    public T find(final ID id, LockModeType lockMode) {
        return getCurrentSession().find(clazz, id, lockMode);
    }

    public void create(final T transientInstance) {
        getCurrentSession().persist(transientInstance);
    }

    public void attach(final T unmodifiedDetachedInstance) {
        // Convenient form of:
        //      Session.LockRequest.lock(Object) via buildLockRequest(LockOptions)
        //      getCurrentSession().buildLockRequest(lockOptions).lock(entity);
        //getCurrentSession().lock(unmodifiedDetachedInstance, LockMode.NONE);
        getCurrentSession().buildLockRequest(LockOptions.NONE).lock(unmodifiedDetachedInstance);
    }

    // User-defined API

    public long count() {
        var qry = String.format("SELECT count(o) FROM %s o", clazz.getSimpleName());
        return getCurrentSession().createQuery(qry, Long.class).getSingleResult();
    }

    public long count(final String qry) {
        return getCurrentSession().createQuery(qry, Long.class).getSingleResult();
    }

    public T findById(final ID id) {
        return getCurrentSession().get(clazz, id);
    }

    public List<T> findAll() {
        var qry = String.format("SELECT o FROM %s o", clazz.getSimpleName());
        return getCurrentSession().createQuery(qry, clazz).getResultList();
    }

    public T findOne(final String qry) {
        return getCurrentSession().createQuery(qry, clazz).getSingleResult();
    }

    public List<T> findMany(final String qry) {
        return getCurrentSession().createQuery(qry, clazz).getResultList();
    }

    public void deleteById(final ID id) {
        final T managedInstance = getCurrentSession().get(clazz, id);
        getCurrentSession().delete(managedInstance);
    }
}

