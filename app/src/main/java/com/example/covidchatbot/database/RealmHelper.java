package com.example.covidchatbot.database;

import android.util.Log;

import com.example.covidchatbot.model.ChatModel;
import com.example.covidchatbot.model.CountryModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {

    Realm realm;

    public  RealmHelper(Realm realm){
        this.realm = realm;
    }

    //chat
    // add Chat data
    public void addChat(final ChatModel chatModel){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null){
                    Number currentIdNum = realm.where(ChatModel.class).max("id");
                    int nextId;
                    if (currentIdNum == null){
                        nextId = 1;
                    }else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    chatModel.setId(nextId);
                    ChatModel model = realm.copyToRealm(chatModel);
                }else{
                    Log.e("error", "execute: Database not Exist");
                }
            }
        });
    }

    // get all Chat data
    public List<ChatModel> getAllChat(){
        RealmResults<ChatModel> results = realm.where(ChatModel.class).findAll();
        return results;
    }


    //country
    //save country data
    public void addCountry(final CountryModel countryModel){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null){
                    Number currentIdNum = realm.where(CountryModel.class).max("id");
                    int nextId;
                    if (currentIdNum == null){
                        nextId = 1;
                    }else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    countryModel.setId(nextId);
                    CountryModel model = realm.copyToRealm(countryModel);
                }else{
                    Log.e("error", "execute: Database not Exist");
                }
            }
        });
    }

    //getAllCountry Data
    public List<CountryModel> getAllCountry(){
        RealmResults<CountryModel> results = realm.where(CountryModel.class).findAll();
        return results;
    }


    public CountryModel searchCountry(String query)
    {
        CountryModel countryModel = realm.where(CountryModel.class).equalTo("slug",query.toLowerCase()).findFirst();
       return countryModel;
    }

    public boolean hasCountry(String query)
    {
        CountryModel countryModel = realm.where(CountryModel.class).equalTo("slug",query.toLowerCase()).findFirst();
        if (countryModel!=null)
        {
            Log.d("okeeee","sukses");
            return true;
        }
        else
        {
            Log.d("okeeee","gagaal");
            return false;
        }


    }


}
