package com.oreilly.hh.service;

import java.util.List;

import com.oreilly.hh.data.Album;

public interface AlbumService {
    public Album persist(Album album);
    public void delete (Integer id);
    public List<Album> list();
    public Album get(Integer id);
}
