package com.aw.truemate;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class detail {
    public String user_id;
    public String name;
    public String gender;
    public String city;
    public String neighborhood;
    public String roommate_number;
    public Map<String, Boolean> stars = new HashMap<>();

    public detail(){
		//defualt constractor
    }

    public detail(String user_id,String name,String gender,String city,String neighborhood,String roommate_number){
        this.user_id = user_id;
        this.name = name;
        this.gender = gender;
        this.city = city;
        this.neighborhood = neighborhood;
        this.roommate_number = roommate_number;
    }

    @Exclude
    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("user_id",user_id);
        result.put("name",name);
        result.put("gender",gender);
        result.put("city",city);
        result.put("neighborhood",neighborhood);
        result.put("roommate_number",roommate_number);

        return result;
    }

}