package com.example.app4g.data;

public class DataPupuk {
    public String idPupuk,namaPupuk, jenisPupuk, jmlhPupuk, komoDitas;


   public DataPupuk(){}

    public DataPupuk(String idPupuk,String namaPupuk, String jenisPupuk, String jmlhPupuk, String komoDitas){
        this.idPupuk = idPupuk;
        this.namaPupuk = namaPupuk;
        this.jenisPupuk = jenisPupuk;
        this.jmlhPupuk = jmlhPupuk;
        this.komoDitas = komoDitas;
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

    public String getKomoDitas() {
        return komoDitas;
    }

    public void setKomoDitas(String komoDitas) {
        this.komoDitas = komoDitas;
    }
}
