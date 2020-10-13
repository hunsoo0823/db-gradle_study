package com.oreilly.hh.dao;

import java.util.List;

import com.oreilly.hh.data.Album;

public interface AlbumDAO {

    Album persist(Album album);

    void delete (Album album);

    List<Album> list();

    Album get(Integer id);

}
