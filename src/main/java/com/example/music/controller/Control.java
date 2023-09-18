package com.example.music.controller;

import com.example.music.dto.Track;
import com.example.music.entity.Music;
import com.example.music.entity.UserInfo;
import com.example.music.service.AppService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class Control {

    @Autowired
    private AppService appService;

    //홈 화면
    @GetMapping("/home")
    public String home(){
        return "home";
    }

    //검색화면 구현
    @PostMapping("/search")
    public String searchMusic(UserInfo userInfo){

        appService.storeUser(userInfo);

        return "search";
    }

    //검색리스트 구현
    @GetMapping("/search-music")
    public String searchList(@RequestParam String track, Model model){
        List<Track> list;
        try{
            list = appService.searchmusic(track);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        UserInfo userInfo = appService.getUser();
        List<Music> musicList = appService.makeMusic(list, userInfo);
        model.addAttribute("musics", musicList);

        return "searchlist";
    }

    //음악을 클릭하면 자신의 플리에 넣어지는 메소드
    @PostMapping("/user-music")
    public String userMusic(Music musicplaylist, Model model){


        appService.store(musicplaylist);

        model.addAttribute("musics", appService.getMusicList());

        return "mylist";
    }


}
