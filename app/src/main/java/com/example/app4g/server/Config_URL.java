package com.example.app4g.server;

//This class is for storing all URLs as a model of URLs

public class Config_URL
{
    public static String base_URL           = "http://172.32.1.20:8400/api";
//    public static String base_URL           = "http://192.168.43.156/az-zahra-api/public";

    public static String fotoProfilUrl      = "http://172.32.1.20:8400/potopropil/";
    //users
    public static String login              = base_URL + "/loginpetani";
    public static String registrasi         = base_URL + "/registrasipetani";
    public static String uploadFoto         = base_URL + "/potopropil";

    //petani
    public static String cekPetani           = base_URL + "/petani/";

    //data anak
    public static String dataAnak           = base_URL + "/cekanak/";
    public static String inputAnak          = base_URL + "/anak";
}