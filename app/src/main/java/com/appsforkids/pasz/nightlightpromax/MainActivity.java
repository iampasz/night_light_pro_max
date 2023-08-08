package com.appsforkids.pasz.nightlightpromax;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.billingclient.api.Purchase;
import com.appsforkids.pasz.nightlightpromax.Billing.BillingClientWrapper;
import com.appsforkids.pasz.nightlightpromax.Fragments.ImageListFragment;
import com.appsforkids.pasz.nightlightpromax.Fragments.MainFragment;
import com.appsforkids.pasz.nightlightpromax.GSON.MyGson;
import com.appsforkids.pasz.nightlightpromax.Interfaces.ChekProductList;
import com.appsforkids.pasz.nightlightpromax.Interfaces.GetJson;
import com.appsforkids.pasz.nightlightpromax.Interfaces.MyCallback;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.Light;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


public class MainActivity extends AppCompatActivity {
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        Realm.init(this);


        realm = Realm.getDefaultInstance();


        if (realm.where(Light.class).findFirst() == null) {

            addToRealm();
        }



        Button open_fr = findViewById(R.id.open_fr);
        Button open_pager = findViewById(R.id.open_pager);

        Button read_json = findViewById(R.id.read_json);

        read_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJson("https://koko-oko.com//images/ng/images.json");
            }
        });



        open_pager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.my_container, new MainFragment()).commit();
            }
        });

        Log.i("LONGPROCESS", "5");


        open_fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.my_container, new ImageListFragment()).commit();
            }
        });

        //

        Log.i("LONGPROCESS", "6");

        BillingClientWrapper billingClientWrapper = new BillingClientWrapper(this);

        Log.i("LONGPROCESS", "7");

        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        TextView purchaseStatus = (TextView) findViewById(R.id.purchase_status);

        Log.i("LONGPROCESS", "8");

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                billingClientWrapper.connectToGooglePlayBilling(new MyCallback() {
                    @Override
                    public void isShown(boolean shown) {

                        Log.i("LEARNBILLING", shown + " result");
                        billingClientWrapper.getProductDetails();
                    }
                });
            }
        });

        Log.i("LONGPROCESS", "9");

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billingClientWrapper.connectToGooglePlayBilling(new MyCallback() {
                    @Override
                    public void isShown(boolean shown) {

                        Log.i("LEARNBILLING", shown + " result");

                        billingClientWrapper.getPurchaseList(new ChekProductList() {
                            @Override
                            public void setList(List<Purchase> list) {

//                                purchaseStatus.setText(
//                                                list.get(0).getPurchaseState() + "\n " +
//                                                list.get(0).getPackageName() + "\n " +
//                                                list.get(0).getProducts().get(0)+ "\n " +
//                                                list.get(0).getOrderId() + "\n "   );

                                if (list.size() > 0) {


//                                    getSupportFragmentManager().beginTransaction()
//                                            .add(R.id.my_container, new MainFragment())
//                                            .commit();

                                    // getSupportFragmentManager().beginTransaction().add(R.id.my_container, new MainFragment()).commit();
                                }


                            }
                        });
                    }
                });
            }
        });
    }

    private void addToRealm() {

        Light ant = new Light();
        ant.setMypic(R.drawable.an_ant);
        ant.setMytext(R.string.animal0);

        Light cow = new Light();
        cow.setMypic(R.drawable.an_cow);
        cow.setMytext(R.string.animal0);

        Light banny = new Light();
        banny.setMypic(R.drawable.an_banny);
        banny.setMytext(R.string.animal0);

        Light croco = new Light();
        croco.setMypic(R.drawable.an_croco);
        croco.setMytext(R.string.animal0);

        Light bear = new Light();
        bear.setMypic(R.drawable.an_bear);
        bear.setMytext(R.string.animal0);

        Light butterfly = new Light();
        butterfly.setMypic(R.drawable.an_butterfly);
        butterfly.setMytext(R.string.animal0);

        Light cat = new Light();
        cat.setMypic(R.drawable.an_cat);
        cat.setMytext(R.string.animal0);

        Light chiken = new Light();
        chiken.setMypic(R.drawable.an_chiken);
        chiken.setMytext(R.string.animal0);

        Light dog = new Light();
        dog.setMypic(R.drawable.an_dog);
        dog.setMytext(R.string.animal0);

        Light elephant = new Light();
        elephant.setMypic(R.drawable.an_elephant);
        elephant.setMytext(R.string.animal0);

        Light frog = new Light();
        frog.setMypic(R.drawable.an_frog);
        frog.setMytext(R.string.animal0);

        Light giraffe = new Light();
        giraffe.setMypic(R.drawable.an_giraffe);
        giraffe.setMytext(R.string.animal0);

        Light hipopotam = new Light();
        hipopotam.setMypic(R.drawable.an_hipopotam);
        hipopotam.setMytext(R.string.animal0);

        Light kaktus = new Light();
        kaktus.setMypic(R.drawable.an_kaktus);
        kaktus.setMytext(R.string.animal0);

        Light lion = new Light();
        lion.setMypic(R.drawable.an_lion);
        lion.setMytext(R.string.animal0);

        Light monkey = new Light();
        monkey.setMypic(R.drawable.an_monkey);
        monkey.setMytext(R.string.animal0);

        Light moose = new Light();
        moose.setMypic(R.drawable.an_moose);
        moose.setMytext(R.string.animal0);

        Light mouse = new Light();
        mouse.setMypic(R.drawable.an_mouse);
        mouse.setMytext(R.string.animal0);


        Light fox = new Light();
        fox.setInternetLink("https://koko-oko.com/images/ng/an_fox.png");
        fox.setMypic(-1);
        fox.setMytext(R.string.animal0);

        Light squirrel = new Light();
        squirrel.setInternetLink("https://koko-oko.com/images/ng/an_squirrel.png");
        squirrel.setMypic(-1);
        squirrel.setMytext(R.string.animal0);


        Light sheep = new Light();
        sheep.setInternetLink("https://koko-oko.com/images/ng/animal7.png");
        sheep.setMypic(-1);
        sheep.setMytext(R.string.animal0);







        realm.beginTransaction();

        realm.copyToRealm(ant);
        realm.copyToRealm(cow);
        realm.copyToRealm(banny);
        realm.copyToRealm(croco);
        realm.copyToRealm(bear);
        realm.copyToRealm(butterfly);
        realm.copyToRealm(cat);
        realm.copyToRealm(chiken);
        realm.copyToRealm(dog);
        realm.copyToRealm(elephant);
        realm.copyToRealm(frog);
        realm.copyToRealm(giraffe);
        realm.copyToRealm(hipopotam);
        realm.copyToRealm(kaktus);
        realm.copyToRealm(lion);
        realm.copyToRealm(monkey);
        realm.copyToRealm(moose);
        realm.copyToRealm(mouse);


        realm.copyToRealm(fox);
        realm.copyToRealm(squirrel);

        realm.copyToRealm(sheep);

        realm.commitTransaction();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.close();
    }


    public void getJson(String url) {

        ReadJson readJson = new ReadJson(new GetJson() {
            @Override
            public void getJson(String result) {


                ArrayList<Light> nightersArray = new ArrayList<>();
                MyGson myGson = new Gson().fromJson(result, MyGson.class);
                Log.i("GSON", myGson.getImages().get(0).getInternet_link());

//                for(int i=0; i<pack.getPack().length; i++){
//                    Log.i("GSON", pack.getPack()[i]+" ");
//                }

            }

            @Override
            public void noAnswer(Boolean answer) {
            }
        });
        readJson.execute(url);
    }
}