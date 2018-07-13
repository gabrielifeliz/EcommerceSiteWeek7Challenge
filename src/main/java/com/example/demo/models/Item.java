package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Size(min = 1, max = 255)
    private String itemName;

    @NotEmpty
    @Size(min = 1, max = 255)
    private String description;

    private String image;

    @NotEmpty
    private String itemTags;

    @NotNull
    @Min(1)
    private double price;

    private String publicationDate;

    @ManyToOne
    private AppUser myItem;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getItemTags() {
        return itemTags;
    }

    public void setItemTags(String itemTags) {
        this.itemTags = itemTags;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate() {
        publicationDate = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm:ss"));
    }

    public AppUser getMyItem() {
        return myItem;
    }

    public void setMyItem(AppUser myItem) {
        this.myItem = myItem;
    }


}
