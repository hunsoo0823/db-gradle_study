package com.oreilly.hh.dao;

import com.oreilly.hh.data.Artist;

public interface ArtistDAO {

    Artist persist(Artist artist);

    void delete (Artist artist);

    Artist uniqueByName(String name);

    Artist getArtist(String name, boolean create);

}
