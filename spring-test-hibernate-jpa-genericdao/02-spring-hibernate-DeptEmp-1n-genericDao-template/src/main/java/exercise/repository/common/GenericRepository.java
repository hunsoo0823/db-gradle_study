package exercise.repository.common;

import java.io.Serializable;
import java.util.List;

import javax.persistence.LockModeType;

import org.hibernate.LockMode;
import org.hibernate.LockOptions;

public interface GenericRepository<T, ID extends Serializable> {

   T load(Class<T> entityClass, final ID id);

   T get(Class<T> entityClass, final ID id);
   T get(Class<T> entityClass, final ID id, LockOptions lockOptions);

   // Get an instance, whose state may be lazily fetched
   T getReference(Class<T> entityClass, final ID id);

   // Find by primary key
   T find(Class<T> entityClass, final ID id);
   T find(Class<T> entityClass, final ID id, LockModeType lockMode);

   void lock(final T managedOrDetachedInstance, LockMode lockMode);

   // Make an instance managed and persistent.
   void persist(final T transientInstance);

   ID save(final T transientInstance);

   void saveOrUpdate(final T transientOrDetachedInstance);

   // Update the persistent instance with the identifier of
   //   the given detached instance.
   void update(final T modifiedDetachedInstance);

   // Merge the state of the given entity into the current persistence context
   T merge(final T valueInstance);

   // Re-read the state of the given instance from the underlying database
   // This method is, however, useful in certain special circumstances. For example
   //   - where a database trigger alters the object state upon insert or update
   //   - after executing direct SQL (eg. a mass update) in the same session
   //   - after inserting a Blob or Clob
   void refresh(final T managedInstance);

   // Remove the entity instance,
   //   causing the managed entity to become removed
   // The mapped database record doesn't get removed immediately,
   //   but during the next flush operation,
   //   Hibernate will generate an SQL DELETE statement
   //       to remove the record from the database table.
   void delete(final T managedInstance);
   void remove(final T managedInstance);

   // Remove the given entity from the persistence context,
   //   causing a managed entity to become detached
   void evict(final T managedInstance);
   void detach(final T managedInstance);

   // Check if the instance is a managed entity instance
   boolean contains(final T instance);
   boolean contains(final String entityName, final T instance);

   // Synchronize the persistence context to the underlying database
   void flush();

   // Clear the persistence context, causing all managed entities to become detached
   void clear();

   // Close an application-managed entity manager.
   void close();

   // Additional API

   T load(final ID id);

   T get(final ID id);
   T get(final ID id, LockOptions lockOptions);

   T getReference(final ID id);

   T find(final ID id);
   T find(final ID id, LockModeType lockMode);

   void create(final T transientInstance);

   void attach(final T unmodifiedDetachedInstance);

   // User-defined API

   long count();

   // Example: "SELECT count(p) FROM Person p WHERE name LIKE 'T%'"
   long count(final String qry);

   T findById(final ID id);

   List<T> findAll();

   T findOne(final String qry);

   List<T> findMany(final String qry);

   void deleteById(final ID id);

}

