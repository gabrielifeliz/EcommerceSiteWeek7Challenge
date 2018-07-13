package com.example.demo.controllers;

import com.cloudinary.utils.ObjectUtils;
import com.example.demo.configurations.CloudinaryConfig;
import com.example.demo.models.Item;
import com.example.demo.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    ItemRepository items;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String index(Model model)
    {
        model.addAttribute("items",items.findAll());
        return "index";
    }

    @GetMapping("/additem")
    public String addPersonColorInfo(Model model)
    {
        model.addAttribute("item", new Item());
        return "newitem";
    }

    @PostMapping("/saveitem")
    public String savePersonColorInfo(@Valid @ModelAttribute("item") Item item,
                                      BindingResult result,
                                      @RequestParam("file")MultipartFile file) {
        if (result.hasErrors()) {
            return "newitem";
        }


        if (file.isEmpty()) {
            item.setPublicationDate();
            items.save(item);
            return "redirect:/items/";
        }

        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            item.setImage(uploadResult.get("url").toString());
            item.setPublicationDate();
            items.save(item);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/items/additem";
        }

        return "redirect:/items/";
    }


    @RequestMapping("/update/{id}")
    public String updatePost(@PathVariable("id") long id, Model model){
        model.addAttribute("item", items.findById(id).get());
        return "newitem";
    }

    @RequestMapping("/delete/{id}")
    public  String deletePost(@PathVariable("id") long id){
        items.deleteById(id);
        return "redirect:/";
    }

    /*@PostConstruct
    public void fillTables()
    {
        ItemTag c;
        ArrayList<String> someColors = new ArrayList<>(Arrays
                .asList("White", "Black", "Grey", "Yellow", "Red",
                        "Blue", "Green", "Brown", "Pink", "Orange", "Purple"));

        for (String s: someColors) {
            c = new ItemTag();
            c.setTagName(s);
            tags.save(c);
        }
    }*/

    @RequestMapping("/search")
    public String showSearchResults(HttpServletRequest request, Model model)
    {
        //Get the search string from the result form
        String searchString = request.getParameter("search");
        model.addAttribute("search", searchString);
        model.addAttribute("items",
                items.findAllByItemTagsContainingIgnoreCase(searchString));
        return "index";
    }

}
