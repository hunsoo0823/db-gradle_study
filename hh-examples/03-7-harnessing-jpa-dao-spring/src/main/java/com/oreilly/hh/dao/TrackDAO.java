package com.oreilly.hh.dao;

import java.time.LocalTime;
import java.util.List;

import com.oreilly.hh.data.Track;

public interface TrackDAO {

    Track persist(Track track);

    void delete (Track track);

    Track findById(int id);

    List<Track> findAll();

    List<Track> findByNameLike(String namePattern);

    List<Track> tracksNoLongerThan(LocalTime length);

}
