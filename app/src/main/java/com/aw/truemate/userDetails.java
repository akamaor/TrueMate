package com.aw.truemate;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class userDetails {
    public String user_id;
    public String name;
    public String gender;
    public String city;
    public String neighborhood;
    public String roommate_number;
    public Map<String, Boolean> stars = new HashMap<>();

    public userDetails(){
        //default constructor
    }

    public String getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getCity() {
        return city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getRoommate_number() {
        return roommate_number;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void setRoommate_number(String roommate_number) {
        this.roommate_number = roommate_number;
    }


    public userDetails(String user_id, String name, String gender, String city, String neighborhood, String roommate_number){
        this.user_id = user_id;
        this.name = name;
        this.gender = gender;
        this.city = city;
        this.neighborhood = neighborhood;
        this.roommate_number = roommate_number;
    }

    @Exclude
    public Map<String, Object> toMap(){
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("user_id", user_id); // user_id is an email
        userDetails.put("name", name);
        userDetails.put("gender", gender);
        userDetails.put("city", city);
        userDetails.put("neighborhood", neighborhood);
        userDetails.put("roommate_number", roommate_number);

        return userDetails;
    }
}