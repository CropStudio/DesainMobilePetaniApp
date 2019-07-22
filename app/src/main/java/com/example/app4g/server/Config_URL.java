package com.example.app4g.server;

//This class is for storing all URLs as a model of URLs

public class Config_URL
{
    public static String base_URL           = "http://192.168.43.156:8400/api";
//    public static String base_URL           = "http://192.168.43.156/az-zahra-api/public";

    //users
    public static String login              = base_URL + "/loginpetani";
    public static String registrasi         = base_URL + "/registrasipetani";

    //petani
    public static String cekPetani           = base_URL + "/petani/";
}