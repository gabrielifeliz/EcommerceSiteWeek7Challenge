package com.example.demo.repositories;

import com.example.demo.models.AppUser;
import com.example.demo.models.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
    Iterable<Item> findAllByMyItem(AppUser appUser);
    Iterable<Item> findAllByListedOrderByPublicationDateDesc(boolean isDislisted);
    Iterable<Item> findAllByMyItemOrderByPublicationDateDesc(AppUser appUser);
    Iterable<Item> findAllByItemTagsContainingIgnoreCase(String s);
    Iterable<Item> findAllByItemNameContainingIgnoreCase(String s);
    Iterable<Item> findAllByOrderByPublicationDateDesc();
}
