package com.example.CarProject;

import java.io.Serializable;

public class Users implements Serializable {
    String id,fname,lname,email,password,is_manager,is_sales;

    public Users(){

    }
    public Users(String fname,String lname,String email,String password){
        this.fname=fname;
        this.lname=lname;
        this.email=email;
        this.password=password;
        this.is_manager="0";
        this.is_sales="0";
    }
}
