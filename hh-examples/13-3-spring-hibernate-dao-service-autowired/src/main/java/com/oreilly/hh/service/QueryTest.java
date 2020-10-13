package com.oreilly.hh.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oreilly.hh.dao.TrackDAO;
import com.oreilly.hh.data.Track;

@Service
@Transactional
public class QueryTest implements Test {

    @Autowired
    private TrackDAO trackDAO;

    public void run() {
        // Print the tracks that will fit in seven minutes
        List<Track> tracks = trackDAO.tracksNoLongerThan(LocalTime.of(00,07,00));
        for (Track track : tracks) {
            System.out.println("Track: \"" + track.getTitle() + "\", " + track.getPlayTime());
        }
    }

}
