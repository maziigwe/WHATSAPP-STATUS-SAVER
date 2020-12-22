package com.example.whatsapp1.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.whatsapp1.Adapter.Image_swipe_adapter;
import com.example.whatsapp1.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;


public class ViewImage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
   private ArrayList<File> images; int page;
   Button download, share;Snackbar snackbar;

    private final String WHATSAPP_DIR_LOCATION = "/WhatsApp/Media/.Statuses";
    private final String DIR_SAVE = "/WStatus Saver/";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    public ViewImage(int page) {
        this.page =page;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewVideo.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewImage newInstance(String param1, String param2, int page) {
        //this.images = images;
        ViewImage fragment = new ViewImage(page);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // File file = new File(Environment.getExternalStorageDirectory().toString()+WHATSAPP_DIR_LOCATION);
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            //images = Image.getImageFiles(file);

            //ViewPager viewPager = (ViewPager) v.findViewById(R.id.view_pager);
            Image_swipe_adapter adapter = new Image_swipe_adapter(getContext(),images, page); //Here we are defining the Imageadapter object
            // viewPager.setAdapter(adapter); // Here we are passing and setting the adapter for the images

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_image, container, false);// Inflate the layout for this fragment
        final ArrayList<File> images;
        final FrameLayout frameLayout = (FrameLayout) v.findViewById(R.id.viewImage1);
        final File file = new File(Environment.getExternalStorageDirectory().toString()+WHATSAPP_DIR_LOCATION);
        images = Image.getImageFiles(file);
        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.view_pager);
        Image_swipe_adapter adapter = new Image_swipe_adapter(getContext(),images, page); //Here we are defining the Imageadapter object
        viewPager.setAdapter(adapter); // Here we are passing and setting the adapter for the images
        viewPager.setCurrentItem(page);

        download = v.findViewById(R.id.download);
        share = v.findViewById(R.id.share);


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download(images.get(viewPager.getCurrentItem()));
                snackbar =snackbar.make(view.getRootView(), "Saved to Gallery ", snackbar.LENGTH_SHORT);
                snackbar.show();

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return v;
    }


    private void download (final File sourcefile){
        File destfile = new File(Environment.getExternalStorageDirectory().toString()+DIR_SAVE+sourcefile.getName());
        Toast.makeText(getContext(), " "+destfile, Toast.LENGTH_SHORT).show();
        copyFile(sourcefile,destfile);

    }
    private void copyFile(File sourcefile, File destfile) {
        if(!destfile.getParentFile().exists()){
            destfile.getParentFile().mkdirs();
        }

        if(!destfile.exists()){
            try {
                destfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try
        {
            sourceChannel = new FileInputStream(sourcefile).getChannel();
            destChannel = new FileOutputStream(destfile).getChannel();
            destChannel.transferFrom(sourceChannel,0, sourceChannel.size());

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if(sourceChannel!=null){
            try {
                sourceChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(destChannel!=null){
            try {
                destChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    

}
