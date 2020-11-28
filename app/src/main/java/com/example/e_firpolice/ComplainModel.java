package com.example.e_firpolice;

public class ComplainModel {
    String name;
    String father;
    String location;
    String category;
    String description;
    String number;
    String id;
    String status;

    public ComplainModel(String name,String father,String location,String category,String description,String number,String id,String status){
        this.name=name;
        this.father=father;
        this.location=location;
        this.category=category;
        this.description=description;
        this.number=number;
        this.id=id;
        this.status=status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String fatherName) {
        this.father = father;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
