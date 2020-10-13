package com.oreilly.hh.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.oreilly.hh.data.Album;
import com.oreilly.hh.service.AlbumService;

@Controller
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    public void setAlbumService(AlbumService albumService) {
        this.albumService = albumService;
    }

    /*
    @InitBinder
    private void dateBinder(WebDataBinder binder) {
    //The date format to parse or output your dates
    //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    //Create a new CustomDateEditor
    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
    //Register it as custom editor for the Date type
    binder.registerCustomEditor(Date.class, editor);
    }
    */

    @RequestMapping(value = "/albums", method = RequestMethod.GET)
    public String listAlbums(Model model) {
        model.addAttribute("album", new Album());
        model.addAttribute("listAlbums", this.albumService.list());
        return "album";
    }

    //For add and update album both
    @RequestMapping(value = "/album/add", method = RequestMethod.POST)
    public String persist(@ModelAttribute("album") Album album) {
        this.albumService.persist(album);
        return "redirect:/albums";
    }

    @RequestMapping("/remove/{id}")
    public String delete (@PathVariable("id") Integer id) {
        this.albumService.delete(id);
        return "redirect:/albums";
    }

    @RequestMapping("/edit/{id}")
    public String editAlbum(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("album", this.albumService.get(id));
        model.addAttribute("listAlbums", this.albumService.list());
        return "album";
    }

}
