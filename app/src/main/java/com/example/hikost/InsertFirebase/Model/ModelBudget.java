package com.example.hikost.InsertFirebase.Model;

import java.util.HashMap;
import java.util.Map;

public class ModelBudget {
    protected String key;
    protected String title;
    protected String description;
    protected Integer value;

    public ModelBudget(String key, String title, String description, Integer value){
        this.key = key;
        this.title = title;
        this.description = description;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> data = new HashMap<>();
        data.put("key", key);
        data.put("title", title);
        data.put("description", description);
        data.put("value", value);
        return data;
    }
}
