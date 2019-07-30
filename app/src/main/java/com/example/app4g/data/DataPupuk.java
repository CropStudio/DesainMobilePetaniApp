package com.example.app4g.data;

public class DataPupuk {
    public String idPupuk,namaPupuk, jenisPupuk, jmlhPupuk;


   public DataPupuk(){}

    public DataPupuk(String idPupuk,String namaPupuk, String jenisPupuk, String jmlhPupuk){
        this.idPupuk = idPupuk;
        this.namaPupuk = namaPupuk;
        this.jenisPupuk = jenisPupuk;
        this.jmlhPupuk = jmlhPupuk;
    }

    public String getIdPupuk() {
        return idPupuk;
    }

    public void setIdPupuk(String idPupuk) {
        this.idPupuk = idPupuk;
    }

    public String getNamaPupuk() {
        return namaPupuk;
    }

    public void setNamaPupuk(String namaPupuk) {
        this.namaPupuk = namaPupuk;
    }

    public String getJenisPupuk() {
        return jenisPupuk;
    }

    public void setJenisPupuk(String jenisPupuk) {
        this.jenisPupuk = jenisPupuk;
    }

    public String getJmlhPupuk() {
        return jmlhPupuk;
    }

    public void setJmlhPupuk(String jmlhPupuk) {
        this.jmlhPupuk = jmlhPupuk;
    }
}
