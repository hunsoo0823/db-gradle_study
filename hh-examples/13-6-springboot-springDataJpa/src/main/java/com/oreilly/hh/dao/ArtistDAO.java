package com.oreilly.hh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oreilly.hh.data.Artist;

@Repository
public interface ArtistDAO extends JpaRepository<Artist, Integer>, ArtistDAOCustom {

    public Artist findByName(String name);

}

