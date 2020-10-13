package com.oreilly.hh;

import javax.persistence.*;

import com.oreilly.hh.data.*;

import java.time.LocalTime;
import java.util.*;

import com.querydsl.core.Tuple;      // javax.persistence.Tuple
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.oreilly.hh.data.QAlbum;
import com.oreilly.hh.data.QTrack;
import com.oreilly.hh.data.QArtist;

/**
 * Retrieve data as objects
 */
public class QueryTest {

    /**
     * Retrieve any tracks that fit in the specified amount of time.
     *
     * @param length the maximum playing time for tracks to be returned.
     * @param em the Jpa EntityManager that can retrieve data.
     * @return a list of {@link Track}s meeting the length restriction.
     */
    public static List<Track> tracksNoLongerThan(LocalTime length, EntityManager em) {
        QTrack track = QTrack.track;
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<Track> tracks = queryFactory.selectFrom(track)
            .where(track.playTime.loe(length).or(track.title.like("%A%")))
            .orderBy(track.title.asc())
            .fetch();

        return tracks;
    }

    /**
     * Retrieve any tracks associated with artists whose name matches a SQL
     * string pattern.
     *
     * @param namePattern the pattern which an artist's name must match
     * @param entityManager the Hibernate entityManager that can retrieve data.
     * @return a list of {@link Track}s meeting the artist name restriction.
     */
    public static List<Track> tracksWithArtistLike(String namePattern, EntityManager entityManager) {
        QTrack track = QTrack.track;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<Track> tracks = queryFactory.selectFrom(track)
            .where(track.artists.any().name.like(namePattern))
            .orderBy(track.title.asc())
            .fetch();

        return tracks;
    }

    /**
     * Retrieve the titles of any tracks that contain a particular text string.
     *
     * @param text the text to be matched, ignoring case, anywhere in the title.
     * @param entityManager the Hibernate entityManager that can retrieve data.
     * @return the matching titles, as strings.
     */
    public static List<String> titlesContainingText(String text, EntityManager entityManager) {
        QTrack track = QTrack.track;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<String> titles = queryFactory
            .select(track.title)
            .from(track)
            .where(track.title.like("%"+text+"%"))
            .fetch();

        return titles;
    }

    /**
     * Retrieve the titles and play times of any tracks that contain a
     * particular text string.
     *
     * @param text the text to be matched, ignoring case, anywhere in the title.
     * @param entityManager the Hibernate entityManager that can retrieve data.
     * @return the matching titles and times wrapped in object arrays.
     */
    public static List<Tuple> titlesContainingTextWithPlayTimes(String text, EntityManager entityManager) {
        QTrack track = QTrack.track;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<Tuple> titles = queryFactory
            .select(track.title, track.playTime)
            .from(track)
            .where(track.title.like("%"+text+"%"))
            .fetch();

        return titles;
    }

    /**
     * Print statistics about various media types.
     *
     * @param entityManager the Hibernate entityManager that can retrieve data.
     */
    public static void printMediaStatistics(EntityManager entityManager) {
        /*
        Criteria criteria = entityManager.createCriteria(Track.class);
        criteria.setProjection(Projections.projectionList().
                               add(Projections.groupProperty("sourceMedia").as("media")).
                               add(Projections.rowCount()).
                               add(Projections.max("playTime")));
        criteria.addOrder(Order.asc("media"));
        List tracks = criteria.list();
        */

        NumberPath<Long> count = Expressions.numberPath(Long.class, "count");
        NumberPath<Integer> max = Expressions.numberPath(Integer.class, "max");

        QTrack track = QTrack.track;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<Tuple> tuples = queryFactory
            .select(track.sourceMedia, track.id.count().as(count), track.id.max().as(max))
            // FIXME
            // variable playTime of type TimePath<Date>
            //.select(track.sourceMedia, track.id.count().as(count), track.playTime.max().as(max))
            .from(track)
            .groupBy(track.sourceMedia)
            .orderBy(track.sourceMedia.desc())
            .fetch();

        for (Tuple row : tuples) {
            System.out.println(row.get(track.sourceMedia) + " track count: " + row.get(count) +
                               "; max play time: " + row.get(max));
        }
        /*
        for (Tuple row : tuples) {
            System.out.println(row.get(0, SourceMedia.class) + " track count: " + row.get(1, Long.class) +
                               "; max play time: " + row.get(2, Long.class));
        }
        */
        System.out.println();
    }

    /**
     * Retrieve any tracks that were obtained from a particular source media
     * type.
     *
     * @param media the media type of interest.
     * @param entityManager the Hibernate entityManager that can retrieve data.
     * @return a list of {@link Track}s meeting the media restriction.
     */
    public static List<Track> tracksFromMedia(SourceMedia media, EntityManager entityManager) {
        QTrack track = QTrack.track;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        JPAQuery<Track> query = queryFactory.selectFrom(track)
            .where(track.sourceMedia.eq(media))
            .orderBy(track.title.asc());

        return query.fetch();
    }

    /**
     * Build a parenthetical, comma-separated list of artist names.
     *
     * @param artists the artists whose names are to be displayed.
     * @return the formatted list, or an empty string if the set was empty.
     */
    public static String listArtistNames(Set<Artist> artists) {
        StringBuilder result = new StringBuilder();
        for (Artist artist : artists) {
            result.append((result.length() == 0) ? "(" : ", ");
            result.append(artist.getName());
        }
        if (result.length() > 0) {
            result.append(") ");
        }
        return result.toString();
    }

    /**
     * Look up and print some tracks when invoked from the command line.
     */
    public static void main(String args[]) throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager        em  = emf.createEntityManager();
        EntityTransaction    tx  = em.getTransaction();

        try {
            tx.begin();

            System.out.println(tracksWithArtistLike("Samuel %", em));
            System.out.println();

            // Print track titles that contain the letter "v"
            //System.out.println(titlesContainingText("v", em));

            // Print track titles and lengths for "v"-containing tracks.
            //for (Object o : titlesContainingTextWithPlayTimes("v", em)) {
            //    Object[] array = (Object[])o;
            //    System.out.println("Title: " + array[0] +
            //            " (Play Time: " + array[1] + ')');
            //}

            // Print information about tracks from different media
            printMediaStatistics(em);

            // Print tracks associated with an artist whose name ends with "n"
            List<Track> tracks = tracksFromMedia(SourceMedia.CD, em);

            for (Track aTrack : tracks) {
                String mediaInfo = "";
                if (aTrack.getSourceMedia() != null) {
                    mediaInfo = "from " + aTrack.getSourceMedia().getDescription();
                }
                System.out.printf("Track: \"%s\" %s %s, %s\n",
                                  aTrack.getTitle(),
                                  listArtistNames(aTrack.getArtists()),
                                  aTrack.getPlayTime(),
                                  mediaInfo);
                for (String comment : aTrack.getComments()) {
                    System.out.println("  Comment: " + comment);
                }
            }

            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }

        emf.close();
    }
}
