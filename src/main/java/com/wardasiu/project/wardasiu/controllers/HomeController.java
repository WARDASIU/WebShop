package com.wardasiu.project.wardasiu.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class HomeController {
    @RequestMapping("/")
    public ModelAndView returnHomePage() {

        return new ModelAndView("index");
    }

    @GetMapping("/api/images/home/{filename}")
    @ResponseBody
    public byte[] getImage(@PathVariable String filename) throws IOException {
        String filePath = Paths.get("src/main/resources/img/home", filename).toString();
        return Files.readAllBytes(Paths.get(filePath));
    }
}
