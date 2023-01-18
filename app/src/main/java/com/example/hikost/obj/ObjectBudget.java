package com.example.hikost.obj;

public class ObjectBudget {
    protected String title, description, budgetType;
    protected Long value, pushTime;
    protected int userId;

    public ObjectBudget(String title, String description, String budgetType, Long value, int userId){
        this.title = title;
        this.description = description;
        this.budgetType = budgetType;
        this.value = value;
        this.userId = userId;
    }
    public ObjectBudget() { }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    public String getBudgetType() {
        return budgetType;
    }

    public void setPushTime(Long pushTime) {
        this.pushTime = pushTime;
    }

    public Long getPushTime() {
        return pushTime;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
