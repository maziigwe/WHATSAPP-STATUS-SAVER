package com.example.whatsapp1.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.io.File;
import java.util.ArrayList;

public class Image_swipe_adapter extends PagerAdapter {
Context context;
int page;
ArrayList<File> images;


    public Image_swipe_adapter(Context context, ArrayList<File> images, int page){
        this.context=context;
        this.images = images;
        this.page = page;
    }

    public Image_swipe_adapter() {
        super();
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        //imageView.setImageResource(images.get(position));


        //((ViewPager) container).setCurrentItem(page);

       // position=page;
        Toast.makeText(context, "page "+page, Toast.LENGTH_SHORT).show();
        File currentFile = images.get(position);
        // holder.imageView.setImageBitmap(imageList[position]);
        Bitmap bitmap = BitmapFactory.decodeFile(currentFile.getAbsolutePath());
        imageView.setImageBitmap(bitmap);
        ((ViewPager) container).addView(imageView, 0);
        imageView.setPadding(1, 1, 1, 1);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        return imageView;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ImageView) object);
    }
// see methods for swip actions below


}
