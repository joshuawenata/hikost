package com.example.hikost.obj;

public class ObjectSaving {
    protected String title, category, description, savingType;
    protected Long value, pushTime, target;

    public ObjectSaving() { }

    public ObjectSaving(String title, String category, String description, String savingType, Long value, Long target){
        this.title = title;
        this.category = category;
        this.description = description;
        this.savingType = savingType;
        this.value = value;
        this.target = target;
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

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }


    public void setPushTime(Long pushTime) {
        this.pushTime = pushTime;
    }

    public Long getPushTime() {
        return pushTime;
    }

    public void setTarget(Long target) {
        this.target = target;
    }

    public Long getTarget() {
        return target;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public void setSavingType(String savingType) {
        this.savingType = savingType;
    }

    public String getSavingType() {
        return savingType;
    }

    public Long getValue() {
        return value;
    }
}
