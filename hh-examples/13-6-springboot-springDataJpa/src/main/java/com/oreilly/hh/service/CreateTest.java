package com.oreilly.hh.service;

import java.time.LocalTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oreilly.hh.dao.ArtistDAO;
import com.oreilly.hh.dao.TrackDAO;

import com.oreilly.hh.data.*;

@Service
public class CreateTest implements Test {

    @Autowired
    private ArtistDAO artistDAO;

    @Autowired
    private TrackDAO trackDAO;

    private static void addTrackArtist(Track track, Artist artist) {
        track.getArtists().add(artist);
    }

    public void run() {
        StereoVolume fullVolume = new StereoVolume();

        Track track = new Track("Russian Trance",
                                "vol2/album610/track02.mp3",
                                LocalTime.of(00,03,30), new HashSet<Artist>(),
                                fullVolume, SourceMedia.CD,
                                new HashSet<String>());
        addTrackArtist(track, artistDAO.getArtist("PPK", true));
        trackDAO.save(track);

        track = new Track("Video Killed the Radio Star",
                          "vol2/album611/track12.mp3",
                          LocalTime.of(00,03,49), new HashSet<Artist>(),
                          fullVolume, SourceMedia.VHS,
                          new HashSet<String>());
        addTrackArtist(track, artistDAO.getArtist("The Buggles", true));
        trackDAO.save(track);

        track = new Track("Gravity's Angel",
                          "vol2/album175/track03.mp3",
                          LocalTime.of(00,06,06), new HashSet<Artist>(),
                          fullVolume, SourceMedia.CD,
                          new HashSet<String>());
        addTrackArtist(track, artistDAO.getArtist("Laurie Anderson", true));
        trackDAO.save(track);

        track = new Track("Adagio for Strings (Ferry Corsten Remix)",
                          "vol2/album972/track01.mp3",
                          LocalTime.of(00,06,35), new HashSet<Artist>(),
                          fullVolume, SourceMedia.CD,
                          new HashSet<String>());
        addTrackArtist(track, artistDAO.getArtist("William Orbit", true));
        addTrackArtist(track, artistDAO.getArtist("Ferry Corsten", true));
        addTrackArtist(track, artistDAO.getArtist("Samuel Barber", true));
        trackDAO.save(track);

        track = new Track("Adagio for Strings (ATB Remix)",
                          "vol2/album972/track02.mp3",
                          LocalTime.of(00,07,39), new HashSet<Artist>(),
                          fullVolume, SourceMedia.CD,
                          new HashSet<String>());
        addTrackArtist(track, artistDAO.getArtist("William Orbit", true));
        addTrackArtist(track, artistDAO.getArtist("ATB", true));
        addTrackArtist(track, artistDAO.getArtist("Samuel Barber", true));
        trackDAO.save(track);

        track = new Track("The World '99",
                          "vol2/singles/pvw99.mp3",
                          LocalTime.of(00,07,05), new HashSet<Artist>(),
                          fullVolume, SourceMedia.STREAM,
                          new HashSet<String>());
        addTrackArtist(track, artistDAO.getArtist("Pulp Victim", true));
        addTrackArtist(track, artistDAO.getArtist("Ferry Corsten", true));
        trackDAO.save(track);

        track = new Track("Test Tone 1",
                          "vol2/singles/test01.mp3",
                          LocalTime.of(00,00,10), new HashSet<Artist>(),
                          new StereoVolume((short) 50, (short) 75), null,
                          new HashSet<String>());
        track.getComments().add("Pink noise to test equalization");
        trackDAO.save(track);

    }

    public ArtistDAO getArtistDAO() {
        return artistDAO;
    }

    public void setArtistDAO(ArtistDAO artistDAO) {
        this.artistDAO = artistDAO;
    }

    public TrackDAO getTrackDAO() {
        return trackDAO;
    }

    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

}
