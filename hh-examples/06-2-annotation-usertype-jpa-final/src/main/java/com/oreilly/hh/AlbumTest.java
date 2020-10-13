package com.oreilly.hh;

import javax.persistence.*;

import com.oreilly.hh.data.*;

import java.time.LocalTime;
import java.util.*;

/**
 * Create sample album data, letting Hibernate persist it for us.
 */
public class AlbumTest {

    /**
     * Quick and dirty helper method to handle repetitive portion of creating
     * album tracks. A real implementation would have much more flexibility.
     */
    private static void addAlbumTrack(Album album, String title, String file,
                                      LocalTime length, Artist artist, int disc,
                                      int positionOnDisc, EntityManager em) {
        Track track = new Track(title, file, length, new HashSet<Artist>(),
                                new StereoVolume(), SourceMedia.CD,
                                new HashSet<String>());
        track.getArtists().add(artist);
        // em.persist(track);
        album.getTracks().add(new AlbumTrack(track, disc, positionOnDisc));
    }

    public static void main(String args[]) throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager        em  = emf.createEntityManager();
        EntityTransaction    tx  = em.getTransaction();

        try {
            // Create some data and persist it
            tx.begin();

            Artist artist = CreateTest.getArtist("Martin L. Gore", true, em);
            Album album = new Album("Counterfeit e.p.", 1,
                                    new HashSet<Artist>(), new HashSet<String>(),
                                    new ArrayList<AlbumTrack>(5));
            album.getArtists().add(artist);
            em.persist(album);

            addAlbumTrack(album, "Compulsion",
                          "vol1/album83/track01.mp3", LocalTime.of(00,05,29),
                          artist, 1, 1, em);
            addAlbumTrack(album, "In a Manner of Speaking",
                          "vol1/album83/track02.mp3", LocalTime.of(00,04,21),
                          artist, 1, 2, em);
            addAlbumTrack(album, "Smile in the Crowd",
                          "vol1/album83/track03.mp3", LocalTime.of(00,05,06),
                          artist, 1, 3, em);
            addAlbumTrack(album, "Gone",
                          "vol1/album83/track04.mp3", LocalTime.of(00,03,32),
                          artist, 1, 4, em);
            addAlbumTrack(album, "Never Turn Your Back on Mother Earth",
                          "vol1/album83/track05.mp3", LocalTime.of(00,03,07),
                          artist, 1, 5, em);
            addAlbumTrack(album, "Motherless Child",
                          "vol1/album83/track06.mp3", LocalTime.of(00,03,32),
                          artist, 1, 6, em);

            System.out.println(album);

            // We're done; make our changes permanent
            tx.commit();

            // This commented out section is for experimenting with deletions.
            //tx = em.beginTransaction();
            //album.getTracks().remove(1);
            //em.update(album);
            //tx.commit();

            //tx = em.beginTransaction();
            //em.delete(album);
            //tx.commit();

        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }

        emf.close();
    }
}
