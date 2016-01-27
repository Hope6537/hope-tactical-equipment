package org.hope6537.fastboot.controller;

import org.hope6537.domain.Comic;
import org.hope6537.domain.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@EnableAutoConfiguration
public class SimpleController {

    private Response getData(String query) {
        Response resp = new Response();
        Random random = new Random(System.currentTimeMillis());
        int count = random.nextInt(25);
        List<Comic> comicList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Comic testData = new Comic();
            testData.setId(String.valueOf(i));
            testData.setTitle("漫画名称" + (query == null ? "" : query) + i);
            comicList.add(testData);
        }
        resp.setTotal(count);
        resp.setComics(comicList);
        return resp;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "hello world";
    }

    @RequestMapping(value = "/getComicList/{query}", method = RequestMethod.GET)
    @ResponseBody
    public Response getComicByQuery(@PathVariable String query) {
        return getData(query);
    }

    @RequestMapping(value = "/getComicList", method = RequestMethod.GET)
    @ResponseBody
    public Response getComic() {
        return getData(null);
    }


    public static void main(String[] args) {
        SpringApplication.run(SimpleController.class, args);
    }
}