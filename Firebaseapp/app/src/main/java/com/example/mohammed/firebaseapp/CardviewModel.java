package com.example.mohammed.firebaseapp;

import java.util.ArrayList;

/**
 * Created by MOHAMMED on 10/13/2016.
 */
public class CardviewModel {
    String imageId;
    String title;
    String companyName;
    String address ,applyLink;
       public   CardviewModel( String imageId,String title,String companyName,String address,String applyLink){
        this.imageId=imageId;
           this.address=address;
           this.title=title;
           this.companyName=companyName;
           this.applyLink=applyLink;

       }

    public void setDescription(String description) {
        this.companyName = description;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageId() {
        return imageId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApplyLink() {
        return applyLink;
    }

    public void setApplyLink(String applyLink) {
        this.applyLink = applyLink;
    }
}
