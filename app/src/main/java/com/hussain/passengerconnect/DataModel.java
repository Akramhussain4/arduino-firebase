package com.hussain.passengerconnect;

/**
 * Created by Hussain on 30-Sep-17.
 */

public class DataModel {

        private String id;
        private String item_name;


    public DataModel(){
    }
    public DataModel(String id,String item_name){
        this.id=id;
        this.item_name=item_name;
    }
    public String getid() {
        return id;
    }

    public void setid(String rfid) {
        this.id = rfid;
    }

    public String getItem_name() {
        return item_name;

    }
    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

}
