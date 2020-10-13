// How to write this query using JPA Criteria query?
// https://stackoverflow.com/questions/9926363/how-to-write-this-query-using-jpa-criteria-query

// SELECT p, l
// FROM Person p LEFT JOIN Language l ON (p.language = l.language)
// WHERE l.locale like :locale
//   AND p.name like :name
//   AND p.time BETWEEN :startDate AND :endDate
// ORDER BY name ASC

// Older Hibernate

{
    Criteria criteria = entityManager.createCriteria(Person.class);
    Criteria languageCriteria = criteria.createCriteria("language");

    languageCriteria.add(Restrictions.like("locale", locale));

    criteria.add(Restrictions.like("name", name));
    criteria.add(Restrictions.between("time", startDate, endDate));

    criteria.addOrder(Order.asc("name"));
}

// JPA 2.0

{
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();

    CriteriaQuery<Person> criteria = builder.createCriteria(Person.class);
    Root<Person> pRoot = criteria.from(Person.class);
    Join<Person, Language> langJoin = criteria.join("language", JoinType.LEFT);

    Predicate conjunction = builder.conjunction();

    criteria.where(builder.and(
                       builder.like(langJoin.get(Language_.locale), locale),
                       builder.like(pRoot.get(Person_.name), name),
                       builder.between(pRoot.get(Person_.time), startDate, endDate));

                   criteria.orderBy(builder.asc(pRoot.get(Person_.name)));
}

//

{
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();

    CriteriaQuery<Person> criteria = builder.createCriteria(Person.class);
    Root<Person> pRoot = criteria.from(Person.class);
    Join<Person, Language> langJoin = criteria.join("language", JoinType.LEFT);

    Predicate[] restrictions = new Predicate[] {
        builder.like(langJoin.get(Language_.locale), locale),
        builder.like(pRoot.get(Person_.name), name),
        builder.between(pRoot.get(Person_.time), startDate, endDate)
    };

    criteria.where(builder.and(restrictions));

    criteria.orderBy(builder.asc(pRoot.get(Person_.name)));
}


// QueryDSL
// https://leanpub.com/opinionatedjpa/read#ch-querydls

// Simple example with Querydsl

{
    List<Dog> dogs = new JPAQuery<>(em)
    .select(QDog.dog)
    .from(QDog.dog)
    .where(QDog.dog.name.like("Re%"))
    //.where(QDog.dog.name.startsWith("Re")) // alternative
    .fetch();
}

// Comparison with Criteria API
{
    CriteriaBuilder cb = em.getCriteriaBuilder();

    CriteriaQuery<Dog> query = cb.createQuery(Dog.class);
    Root<Dog> dog = query.from(Dog.class);
    query.select(dog)
    // this is the only place where we can use metamodel in this example
    .where(cb.like(dog.get(Dog_.name), "Re%"));
    // without metamodel it would be:
    //.where(cb.like(dog.<String>get("name"), "Re%"));
    List<Dog> dogs = em.createQuery(query)
    .getResultList();
}

// JPQL simple example

{
    List<Dog> dogs = em.createQuery("select d from Dog d where d.name like :name", Dog.class)
    .setParameter("name", "Re%")
    .getResultList();
}

// Querydsl simple example with alias

{
    QDog d = new QDog("d1");
    List<Dog> dogs = new JPAQuery<>(em)
    .select(d)
    .from(d)
    .where(d.name.startsWith("Re"))
    .fetch();
}
// Generated JPQL: select d1 from Dog d1 where d1.name like ?1 escape '!'
