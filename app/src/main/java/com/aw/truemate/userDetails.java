package com.aw.truemate;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class userDetails {
    private String user_id;
    private String name;
    private String user_email;
    private String gender;
    private Object age;
    private String city;
    private List<String> neighborhood;
    private Object roommate_number;
    private LinkedHashMap<String, userDetails> likedList = new LinkedHashMap<>();

    public userDetails(){
        //default constructor
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_email() {
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

    public LinkedHashMap<String, userDetails> getLikedList() {
        return likedList;
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

    public void setUser_email_email(String user_email) {
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

    public void setLikedList(LinkedHashMap<String, userDetails> likedList) {
        this.likedList = likedList;
    }

    public userDetails(String user_id, String email){
        this.user_id = user_id;
        this.user_email = email;
    }

    public userDetails(Object user_id, Object name, Object email, Object gender, Object age, Object city, Object neighborhood, Object roommate_number){
        this.user_id = (String)user_id;
        this.user_email = (String)email;
        this.name = (String)name;
        this.gender = (String)gender;
        this.age = age;
        this.city = (String)city;
        this.neighborhood = (List<String>)neighborhood;
        this.roommate_number = roommate_number;
    }

    @Exclude
    public Map<String, Object> toMap(){
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("user_id", user_id); // user_id is an email
        userDetails.put("email", user_email);
        userDetails.put("name", name);
        userDetails.put("gender", gender);
        userDetails.put("age", age);
        userDetails.put("city", city);
        userDetails.put("neighborhood", neighborhood);
        userDetails.put("roommate_number", roommate_number);
        userDetails.put("liked_list", likedList);

        return userDetails;
    }
}