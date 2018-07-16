package com.example.demo.controllers;

import com.cloudinary.utils.ObjectUtils;
import com.example.demo.configurations.CloudinaryConfig;
import com.example.demo.models.Item;
import com.example.demo.repositories.AppRoleRepository;
import com.example.demo.repositories.AppUserRepository;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    ItemService myItems;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String allitems(Model model)
    {
        model.addAttribute("items",items.findAllByListedOrderByPublicationDateDesc(true));
        return "allitems";
    }

    @GetMapping("/additem")
    public String addItem(Model model)
    {
        model.addAttribute("item", new Item());
        return "newitem";
    }

    @PostMapping("/saveitem")
    public String saveItem(@Valid @ModelAttribute("item") Item item,
                                      BindingResult result, Authentication getDetails,
                                      @RequestParam("file")MultipartFile file) {
        if (result.hasErrors()) {
            return "newitem";
        }

        if (file.isEmpty()) {
            item.setPublicationDate();
            myItems.save(item, getDetails);
            return "redirect:/items/";
        }

        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            item.setImage(uploadResult.get("url").toString());
            item.setPublicationDate();
            myItems.save(item, getDetails);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/items/additem";
        }

        return "redirect:/items/myitems";
    }

    @RequestMapping("/myitems")
    public String displayHome(Model model, Authentication authentication) {

        model.addAttribute("items", myItems.orderMyItems(authentication));
        return "myitems";
    }

    @RequestMapping("/update/{id}")
    public String updatePost(@PathVariable("id") long id, Model model){
        model.addAttribute("item", items.findById(id).get());
        return "newitem";
    }

    @RequestMapping("/delete/{id}")
    public  String deletePost(@PathVariable("id") long id){
        items.deleteById(id);
        return "redirect:/items/";
    }

    @RequestMapping("/available/{id}")
    public String availableItem(@PathVariable("id") long id){
        Item item =  items.findById(id).get();
        item.setAvailable(true);
        items.save(item);
        return "redirect:/items/";
    }

    @RequestMapping("/sold/{id}")
    public String soldItem(@PathVariable("id") long id){
        Item item =  items.findById(id).get();
        item.setAvailable(false);
        items.save(item);
        return "redirect:/items/";
    }

    @RequestMapping("/listed/{id}")
    public String listedItem(@PathVariable("id") long id){
        Item item =  items.findById(id).get();
        item.setListed(true);
        items.save(item);
        return "redirect:/items/";
    }

    @RequestMapping("/delisted/{id}")
    public String delistedItem(@PathVariable("id") long id){
        Item item =  items.findById(id).get();
        item.setListed(false);
        items.save(item);
        return "redirect:/items/";
    }


    @RequestMapping("/buyitem/{id}")
    public String buyItem(@PathVariable("id") long id){
        Item item =  items.findById(id).get();
        final double TAX = 1.06;
        item.setItemsSold(item.getItemsSold() + 1);
        item.setTotalEarnedItem(item.getItemsSold() * item.getPrice() * TAX);
        items.save(item);
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

    @RequestMapping("items/search/itemname")
    public String searchByItemName(HttpServletRequest request, Model model)
    {
        //Get the search string from the result form
        String searchString = request.getParameter("search");
        model.addAttribute("search", searchString);
        model.addAttribute("items",
                items.findAllByItemNameContainingIgnoreCase(searchString));
        return "allitems";
    }

    @RequestMapping("items/search/itemtag")
    public String searchByItemTags(HttpServletRequest request, Model model)
    {
        //Get the search string from the result form
        String searchString = request.getParameter("search");
        model.addAttribute("search", searchString);
        model.addAttribute("items",
                items.findAllByItemTagsContainingIgnoreCase(searchString));
        return "allitems";
    }

}
