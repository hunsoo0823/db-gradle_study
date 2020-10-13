package com.oreilly.hh.repository;

import com.oreilly.hh.data.Album;

import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends CrudRepository<Album, Integer> {
}

