package com.oreilly.hh.dao;

import java.time.LocalTime;
import java.util.List;

import com.oreilly.hh.data.Track;

public interface TrackDAO {

    Track persist(Track track);

    void delete (Track track);

    List<Track> tracksNoLongerThan(LocalTime length);

}
