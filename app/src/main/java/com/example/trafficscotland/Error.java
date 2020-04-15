package com.example.trafficscotland;

public class Error {
    public String  Error(Exception e){
        System.out.println("\033[0;31m"+e.getMessage()+"\033");
       return e.getMessage();
    }
}
