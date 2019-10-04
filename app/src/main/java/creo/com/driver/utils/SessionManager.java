package creo.com.driver.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {


    private SharedPreferences sharedPreferences;


    public SessionManager(Context  context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getUser_pcc() {
        String pcc = sharedPreferences.getString("pcc","");

        return pcc;
    }

    public void setUser_pcc(String user_pcc) {
    sharedPreferences.edit().putString("pcc",user_pcc).commit();
    }

    public void setId(String id){
        sharedPreferences.edit().putString("id",id).commit();
    }

    public String getId(){
        return  sharedPreferences.getString("id","");
    }
    public void setLicence(String lic){
        sharedPreferences.edit().putString("license",lic).commit();
    }
    public String getLicense(){
        return  sharedPreferences.getString("license","");
    }

    public void setMake(String make){
        sharedPreferences.edit().putString("make",make).commit();
    }
    public String getMake(){

            return  sharedPreferences.getString("make","");
    }
    public void setModel(String model){
        sharedPreferences.edit().putString("model",model).commit();
    }
    public String getModel(){

        return  sharedPreferences.getString("model","");
    }
    public void setYear(String year){
        sharedPreferences.edit().putString("year",year).commit();
    }
    public String getYear(){

        return  sharedPreferences.getString("year","");
    }
    public void setPlate(String plate){
        sharedPreferences.edit().putString("plate",plate).commit();
    }
    public String getPlate(){

        return  sharedPreferences.getString("plate","");
    }
    public void setColor(String color){
        sharedPreferences.edit().putString("color",color).commit();
    }
    public String getColor(){

        return  sharedPreferences.getString("color","");
    }
    public void setRC(String rcbook){
        sharedPreferences.edit().putString("rcbook",rcbook).commit();
    }
    public String getRC(){

        return  sharedPreferences.getString("rcbook","");
    }
    public void setPermit(String taxi_permit){
        sharedPreferences.edit().putString("taxi_permit",taxi_permit).commit();
    }
    public String getPermit(){

        return  sharedPreferences.getString("taxi_permit","");
    }
    public void setInsurance(String vehicle_insurance){
        sharedPreferences.edit().putString("vehicle_insurance",vehicle_insurance).commit();
    }
    public String getInsurance(){

        return  sharedPreferences.getString("vehicle_insurance","");
    }
    public void setTourist(String tourist_permit){
        sharedPreferences.edit().putString("tourist_permit",tourist_permit).commit();
    }
    public String getTourist(){

        return  sharedPreferences.getString("tourist_permit","");
    }
    public void setFitness(String vehicle_fitness){
        sharedPreferences.edit().putString("vehicle_fitness",vehicle_fitness).commit();
    }
    public String getFitness(){

        return  sharedPreferences.getString("vehicle_fitness","");
    }
    public void setNOC(String noc){
        sharedPreferences.edit().putString("noc",noc).commit();
    }
    public String getNOC(){

        return  sharedPreferences.getString("noc","");
    }
    public void setPho(String img){
        sharedPreferences.edit().putString("img",img).commit();
    }
    public String getPho(){

        return  sharedPreferences.getString("img","");
    }




}
