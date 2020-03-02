package com.hyf.backend.film.controller;

import com.hyf.backend.film.controller.vo.ActorVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Elvis on 2020/2/15
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
@RequestMapping("/meetingfilm/filmapi")
public class FilmController {

    @GetMapping("/films/actors")
    public ActorVO actorList() {
        return null;
    }

}
