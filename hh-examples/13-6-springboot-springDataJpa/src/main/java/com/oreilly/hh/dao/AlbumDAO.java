package com.oreilly.hh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oreilly.hh.data.Album;

@Repository
public interface AlbumDAO extends JpaRepository<Album, Integer> {

}
