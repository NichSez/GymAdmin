package com.example.nich_sez.gymadmin;

public class User {
    String name,family,address,id,lockerCode,nationalCode,tel,image;

    public User(String name, String family, String address, String id, String lockerCode, String nationalCode, String tel) {
        this.name = name;
        this.family = family;
        this.address = address;
        this.id = id;
        this.lockerCode = lockerCode;
        this.nationalCode = nationalCode;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }

    public String getLockerCode() {
        return lockerCode;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public String getTel() {
        return tel;
    }

    public String getImage() {
        return image;
    }
}
