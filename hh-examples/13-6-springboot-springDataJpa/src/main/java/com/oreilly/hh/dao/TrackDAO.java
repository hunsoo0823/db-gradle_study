package com.oreilly.hh.dao;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.oreilly.hh.data.Track;

@Repository
public interface TrackDAO extends JpaRepository<Track, Integer> {

    @Query("select t from Track t where t.playTime <= :length")
    public List<Track> tracksNoLongerThan(@Param("length") LocalTime length);

    public List<Track> findByPlayTimeLessThan(LocalTime playTime);

}
