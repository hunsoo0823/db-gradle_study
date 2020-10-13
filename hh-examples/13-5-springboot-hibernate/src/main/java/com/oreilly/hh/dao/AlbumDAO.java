package com.oreilly.hh.dao;

import com.oreilly.hh.data.Album;

public interface AlbumDAO {

    Album persist(Album album);

    void delete (Album album);

}
