
# Hibernate 4 -> Hibernate 5.2

sed -i -e 's|org.hibernate.Query|org.hibernate.query.Query|g' **/*.java

sed -i -e 's|SessionImplementor|SharedSessionContractImplementor|g' **/*.java

sed -i -e 's|query.setTime|query.setParameter|g' **/*.java
sed -i -e 's|query.setString|query.setParameter|g' **/*.java
sed -i -e 's|query.setInteger|query.setParameter|g' **/*.java

# TrackHibernateDAO of ch13 and ch14
#   Query query --> Query<Track> query


    Query query = session.getNamedQuery("com.oreilly.hh.tracksNoLongerThan");
    -->
    Query<Track> query = session.createNamedQuery("com.oreilly.hh.tracksNoLongerThan", Track.class);




# @Deprecated Criteria createCriteria(Class persistentClass) Deprecated.

List<Category> categories = session.createCriteria(Category.class).list();

# (since 5.2) for Session,
#   use the JPA Criteria Create Criteria instance for the given class
#
# https://docs.oracle.com/cd/E19798-01/821-1841/gjivi/index.html

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

// Create CriteriaBuilder
CriteriaBuilder builder = session.getCriteriaBuilder();

// Create CriteriaQuery
CriteriaQuery<Categroy> criteria = builder.createQuery(Categroy.class);

criteria.from(Category.class);
List<Category> categories = session.createQuery(criteria).getResultList();

