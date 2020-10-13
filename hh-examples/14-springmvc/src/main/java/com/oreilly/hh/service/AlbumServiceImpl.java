package com.oreilly.hh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.oreilly.hh.dao.AlbumDAO;
import com.oreilly.hh.data.Album;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumDAO albumDAO;

    public void setAlbumDAO(AlbumDAO albumDAO) {
        this.albumDAO = albumDAO;
    }

    @Override
    @Transactional
    public Album persist(Album album) {
        return this.albumDAO.persist(album);
    }

    @Override
    @Transactional
    public void delete (Integer id) {
        Album album = get(id);
        this.albumDAO.delete(album);
    }
    @Override
    @Transactional
    public List<Album> list() {
        return this.albumDAO.list();
    }

    @Override
    @Transactional
    public Album get(Integer id) {
        return this.albumDAO.get(id);
    }

}
