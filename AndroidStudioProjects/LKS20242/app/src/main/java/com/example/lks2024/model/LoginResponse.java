package com.example.lks2024.model;

public class LoginResponse {
    private int id;
    private String email,name,phone;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
/*
{
  "id": 1,
  "email": "khuddle0@cbc.ca",
  "name": "Keri",
  "birthday": "2001-02-14T00:00:00",
  "phoneNumber": "+63 474 869 6984"
}
* */
