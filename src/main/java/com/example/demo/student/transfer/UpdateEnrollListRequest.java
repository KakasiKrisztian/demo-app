package com.example.demo.student.transfer;

import com.sun.istack.NotNull;

public class UpdateEnrollListRequest {
    @NotNull
    private String listName;

    @NotNull
    private String description;

    public UpdateEnrollListRequest(String listName, String description) {
        this.listName = listName;
        this.description = description;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UpdateEnrollListRequest{" +
                "listName='" + listName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
