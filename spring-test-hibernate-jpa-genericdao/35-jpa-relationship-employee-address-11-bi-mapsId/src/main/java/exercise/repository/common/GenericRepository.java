package exercise.repository.common;

import java.io.Serializable;
import java.util.List;

import javax.persistence.LockModeType;

public interface GenericRepository<T, ID extends Serializable> {

   // Get an instance, whose state may be lazily fetched
   T getReference(Class<T> entityClass, final ID id);

   // Find by primary key
   T find(Class<T> entityClass, final ID id);
   T find(Class<T> entityClass, final ID id, LockModeType lockMode);

   // Lock an entity instance that is contained in the persistence context
   //   with the specified lock mode type.
   void lock(final T managedInstance, LockModeType lockMode);

   // Make an instance managed and persistent.
   void persist(final T transientInstance);

   // Merge the state of the given entity into the current persistence context
   T merge(final T valueInstance);

   // Refresh the state of the instance from the database,
   //   overwriting changes made to the entity, if any.
   void refresh(final T managedInstance);

   // Remove the entity instance,
   //   causing the managed entity to become removed
   // The mapped database record doesn't get removed immediately,
   //   but during the next flush operation,
   //   Hibernate will generate an SQL DELETE statement
   //       to remove the record from the database table.
   void remove(final T managedInstance);

   // Remove the given entity from the persistence context,
   //   causing a managed entity to become detached
   void detach(final T managedInstance);

   // Check if the instance is a managed entity instance
   boolean contains(final T instance);

   // Synchronize the persistence context to the underlying database
   void flush();

   // Clear the persistence context, causing all managed entities to become detached
   void clear();

   // Close an application-managed entity manager.
   void close();

   // Additional API

   T getReference(final ID id);

   T find(final ID id);
   T find(final ID id, LockModeType lockMode);

   void save(final T transientInstance);

   void create(final T transientInstance);

   void delete(final T managedInstance);

   void evict(final T managedInstance);

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

