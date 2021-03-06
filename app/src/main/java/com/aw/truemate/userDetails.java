package com.aw.truemate;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.*;

@IgnoreExtraProperties
public class userDetails {
    private String user_id;
    private String name;
    private Object user_email;
    private String gender;
    private Object age;
    private String city;
    private List<String> neighborhood;
    private Object roommate_number;
    private List<String> liked_list = new ArrayList<>();

    public userDetails(){
        //default constructor
    }

    public String getUser_id() {
        return user_id;
    }

    public Object getUser_email() {
        return user_email;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public Object getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public List<String> getNeighborhood() {
        return neighborhood;
    }

    public Object getRoommate_number() {
        return roommate_number;
    }

    public List<String> getLikedList() {
        return liked_list;
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

    public void setUser_email(String user_email) {
        this.gender = user_email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setNeighborhood(List<String> neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void setRoommate_number(int roommate_number) {
        this.roommate_number = roommate_number;
    }

    public void setLikedList(List<String> likedList) {
        this.liked_list = likedList;
    }

    public userDetails(String user_id, String email){
        this.user_id = user_id;
        this.user_email = email;
    }

    public userDetails(Object user_id, Object name, Object email, Object gender, Object age, Object city, Object neighborhood, Object roommate_number, Object likedList){
        this.user_id = (String)user_id;
        this.user_email = email;
        this.name = (String)name;
        this.gender = (String)gender;
        this.age = age;
        this.city = (String)city;
        this.neighborhood = Arrays.asList(neighborhood.toString().split(","));
        this.roommate_number = roommate_number;
        this.liked_list = (List<String>) likedList;
    }

    @Exclude
    public Map<String, Object> toMap(){
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("user_id", user_id);
        userDetails.put("email", user_email);
        userDetails.put("name", name);
        userDetails.put("gender", gender);
        userDetails.put("age", age);
        userDetails.put("city", city);
        userDetails.put("neighborhood", neighborhood);
        userDetails.put("roommate_number", roommate_number);
        userDetails.put("liked_list", liked_list);

        return userDetails;
    }
}