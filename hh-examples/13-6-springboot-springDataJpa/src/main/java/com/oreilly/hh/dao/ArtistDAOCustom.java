package com.oreilly.hh.dao;

import com.oreilly.hh.data.Artist;

public interface ArtistDAOCustom {

    public Artist getArtist(String name, boolean create);

}
