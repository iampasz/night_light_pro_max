package com.appsforkids.pasz.nightlightpromax.Adapters;

import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsforkids.pasz.nightlightpromax.Interfaces.DoThis;
import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.Light;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by pasz on 30.10.2016.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>  {

    public static ArrayList<Light> mylights;
    private static ArrayList<Light> newList;

    private DoThis doThis;

    public MyAdapter(ArrayList<Light> lights, DoThis doThis) {

        this.doThis = doThis;
        mylights = lights;
        if(lights!=null && lights.size()>0){
            this.newList = generateNewList(lights);
        }
    }

    private ArrayList<Light> generateNewList(ArrayList<Light> originalList) {

            Light firstSample = originalList.get(0);
            Light lastSample = originalList.get(originalList.size() - 1);
            newList = new ArrayList<>(originalList.size() + 2);
            newList.add(lastSample);
            newList.addAll(originalList);
            newList.add(firstSample);

        return newList;
    }


    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {


        if(mylights.size()>0){
            Light myLight = mylights.get(holder.getAbsoluteAdapterPosition());

            if (myLight.isValid()) {
                int myPicture = myLight.getMypic();
                String myInternetLink = myLight.getInternetLink();

                if(myPicture==-1){
                    Picasso.get().load(myInternetLink).into(holder.imageView);
                }else{
                    holder.imageView.setImageResource(myPicture);
                }
            }
        }



        int value = getBrightsPreference(holder.imageView.getContext());
        holder.imageView.setColorFilter(brightIt(value));

        final ArrayList<Techniques> techniques = new ArrayList<>();
        techniques.add(Techniques.Bounce);
        techniques.add(Techniques.BounceIn);
        techniques.add(Techniques.FadeIn);
        techniques.add(Techniques.DropOut);
        techniques.add(Techniques.Shake);
        techniques.add(Techniques.Flash);
        techniques.add(Techniques.SlideInLeft);
        techniques.add(Techniques.Swing);
        techniques.add(Techniques.FlipInY);


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doThis.doThis(holder.getAbsoluteAdapterPosition());

                Random random = new Random();
                int i = random.nextInt(techniques.size());

                YoYo.with(techniques.get(i))
                        .duration(700)
                        .playOn(holder.itemView);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mylights.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public static ColorMatrixColorFilter brightIt(int fb) {
        ColorMatrix cmB = new ColorMatrix();
        cmB.set(new float[] {
                1, 0, 0, 0, fb,
                0, 1, 0, 0, fb,
                0, 0, 1, 0, fb,
                0, 0, 0, 1, 0   });

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(cmB);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(colorMatrix);

        return f;
    }


    private int getBrightsPreference(Context ctx){
        SharedPreferences sharedPref = ctx.getSharedPreferences("MYPREFS", 0);
        int brights = sharedPref.getInt("BRIGHT", 0);

        Log.i("BRIGHTS", brights+" ADAPTER");

        return brights;

    }

    public int getItemPosition(Object item) {
        return POSITION_NONE;
    }

}



