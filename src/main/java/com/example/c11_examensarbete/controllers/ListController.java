package com.example.c11_examensarbete.controllers;

import com.example.c11_examensarbete.dtos.ListDto;
import com.example.c11_examensarbete.dtos.SavedMangaDto;
import com.example.c11_examensarbete.services.ListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ListController {

    ListService listService;

    public ListController(ListService listService) {
        this.listService = listService;
    }

    //TODO: Works in bruno but no exeption handling
    @GetMapping("/user/{userid}/lists")
    public List<ListDto> getAllListsByUser(@PathVariable int userid){
        return listService.getAllListsByUser(userid);
    }

    //TODO: Works in bruno but no exeption handling
    @GetMapping("/user/list/{id}")
    public List<SavedMangaDto> getAllSavedMangasInList(@PathVariable int id) {
        return listService.getAllSavedMangasInList(id);
    }

    //TODO: Works in bruno but no exeption handling
    @PostMapping("/user/list/add/{listid}")
    public ResponseEntity<Void> addToList(@PathVariable int listid, @RequestBody List<Integer> savedMangaIds) {
        if (savedMangaIds == null || savedMangaIds.isEmpty()) {
            throw new IllegalArgumentException("No manga ID provided");
        }

        savedMangaIds.forEach(id -> listService.addSavedMangaToList(listid, id));

        return ResponseEntity.created(URI.create("/user/list/" + listid)).build();
    }

    //TODO: Works in bruno but no exeption handling
    @PostMapping("/user/list")
    public ResponseEntity<Void> addList(@RequestBody ListDto listDto) {
        int id = listService.addList(listDto);
        return ResponseEntity.created(URI.create("/user/list/" + id)).build();
    }

    @DeleteMapping("/user/list/{listid}")
    public ResponseEntity<Void> deleteList(@PathVariable int listid) {
        listService.deleteList(listid);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user/list/{listid}/{mangaid}")
    public ResponseEntity<Void> deleteFromList(@PathVariable int listid, @PathVariable int mangaid) {
        listService.deleteSavedMangaFromList(listid, mangaid);
        return ResponseEntity.noContent().build();
    }
}
