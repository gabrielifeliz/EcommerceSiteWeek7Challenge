package com.example.demo.services;

import com.example.demo.models.AppUser;
import com.example.demo.models.Item;
import com.example.demo.repositories.AppUserRepository;
import com.example.demo.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private AppUser user;

    @Autowired
    AppUserRepository users;

    @Autowired
    ItemRepository items;

    public Iterable<Item> getMyItems(Authentication myDetails) {
        return items.findAllByMyItem(users.findByUsername(myDetails.getName()));
    }

    public Iterable<Item> orderMyItems(Authentication myDetails) {
        return items.findAllByMyItemOrderByPublicationDateDesc(users.findByUsername(myDetails.getName()));
    }

    public Item save(Item item, Authentication authentication) {
        user = users.findByUsername(authentication.getName());
        item.setMyItem(user);
        return items.save(item);
    }
}