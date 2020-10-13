// 4. Working with Spring Data Repositories
// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories

// Spring Data Core
// https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html
public interface CrudRepository<T, ID> extends Repository<T, ID> {

    <S extends T> S           save(S entity);
    <S extends T> Iterable<S> saveAll(Iterable<S> entities);

    Optional<T> findById(ID primaryKey);
    Iterable<T> findAll();
    Iterable<T> findAllById(Iterable<ID> ids);

    boolean existsById(ID primaryKey);

    long count();

    void delete(T entity);
    void deleteById(ID id)
    void deleteAll()
    void deleteAll(Iterable<? extends T> entities)

}

// Spring Data Core
// https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html
public interface PagingAndSortingRepository<T, ID> extends CrudRepository<T, ID> {

    Page<T> findAll(Pageable pageable);

    Iterable<T> findAll(Sort sort);

}

// To access the second page of User by a page size of 20,
//      you could do something like the following:
PagingAndSortingRepository<User, Long> repository = // … get access to a bean
Page<User> users = repository.findAll(PageRequest.of(1, 20));

// Spring Data Jpa
// https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html
public interface JpaRepository<T, ID> extends PagingAndSortingRepository<T,ID>, QueryByExampleExecutor<T> {

    <S extends T> List<S>   saveAll(Iterable<S> entities);
    <S extends T> S         saveAndFlush(S entity);

    List<T>                 findAll();
    <S extends T> List<S>   findAll(Example<S> example);
    <S extends T> List<S>   findAll(Example<S> example, Sort sort);
    List<T>                 findAll(Sort sort);
    List<T>                 findAllById(Iterable<ID> ids);

    void                    flush();

    // This is very likely to always return an instance and throw an EntityNotFoundException on first access.
    // Some of them will reject invalid identifiers immediately.
    T                       getOne(ID id);

    void                    deleteAllInBatch();
    void                    deleteInBatch(Iterable<T> entities);
}

