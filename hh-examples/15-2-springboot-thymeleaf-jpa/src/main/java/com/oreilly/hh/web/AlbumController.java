package com.oreilly.hh.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.oreilly.hh.data.Album;
import com.oreilly.hh.repository.AlbumRepository;

@Controller
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    @GetMapping("/album/new")
    public String newAlbum(Model model) {
        model.addAttribute("album", new Album());
        return "albumform";
    }

    @PostMapping("/album")
    public String createAlbum(Album album, Model model) {
        albumRepository.save(album);
        return "redirect:/album/" + album.getId();
    }

    @GetMapping("/album/{id}")
    public String getAlbumById(@PathVariable Integer id, Model model) {
        model.addAttribute("album", albumRepository.findById(id).orElse(new Album()));
        return "album";
    }

    @GetMapping("/albums")
    public String getAlbums(Model model) {
        model.addAttribute("albums", albumRepository.findAll());
        return "albums";
    }

    @GetMapping("/album/edit/{id}")
    public String editAlbum(@PathVariable Integer id, Model model) {
        model.addAttribute("album", albumRepository.findById(id).orElse(new Album()));
        return "albumform";
    }

    @PostMapping("/album/{id}")
    public String updateAlbum(Album album) {
        albumRepository.save(album);
        return "redirect:/album/" + album.getId();
    }

    @GetMapping("/album/delete/{id}")
    public String deleteAlbum(@PathVariable Integer id) {
        albumRepository.deleteById(id);
        return "redirect:/albums";
    }
}

