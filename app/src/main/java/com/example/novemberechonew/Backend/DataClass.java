package com.example.novemberechonew.Backend;

public class DataClass {

    private String dataUID;
    private String dataDOB;
    private String dataName;
    private String dataEmail;
    private String dataPhone;
    private String dataMiles;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataUID() {
        return dataUID;
    }

    public String getDataDOB() {
        return dataDOB;
    }

    public String getName() {
        return dataName;
    }

    public String getDataEmail() {
        return dataEmail;
    }

    public String getDataMiles() {
        return dataMiles;
    }
    public String getDataPhone() {
        return dataPhone;
    }

    public DataClass(String dataUID, String dataName, String dob, String dataEmail, String dataPhone, String dataMiles) {
        this.dataUID = dataUID;
        this.dataName = dataName;
        this.dataDOB = dob;
        this.dataEmail = dataEmail;
        this.dataPhone = dataPhone;
        this.dataMiles = dataMiles;
    }

    public DataClass() {

    }
}
