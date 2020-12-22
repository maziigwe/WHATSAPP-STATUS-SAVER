package com.example.whatsapp1.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.whatsapp1.Adapter.image_adapter;
import com.example.whatsapp1.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class Image extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<File> selected_imageList = new ArrayList<>();
    Context context;
     SwipeRefreshLayout mSwipeRefreshLayout =null;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private final String WHATSAPP_DIR_LOCATION = "/WhatsApp/Media/.Statuses";
    private int STORAGE_PERMISSION_CODE = 1;
    public Image() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Image.
     */
    // TODO: Rename and change types and number of parameters
    public static Image newInstance(String param1, String param2) {
        Image fragment = new Image();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_image, container, false);

        recyclerView = v.findViewById(R.id.Image_recyclerview);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new GridLayoutManager(getContext(),2);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Permission GRANTED", Toast.LENGTH_SHORT).show();


            }
        });

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "You have already granted this permission!", Toast.LENGTH_SHORT).show();
        } else {
            requestStoragePermission();
        }

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "You have already granted this permission!", Toast.LENGTH_SHORT).show();
        } else {
            requestStoragePermission1();
        }
        //File file = new File(Environment.getExternalStorageDirectory().toString()+WHATSAPP_DIR_LOCATION);
        // specify an adapter (see also next example)
       // mAdapter = new image_adapter(getImageFiles(file),getContext());
       // recyclerView.setAdapter(mAdapter);

        // SwipeRefreshLayout
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                // Fetching data from server
                //loadRecyclerViewData();
                restart();
            }
        });
        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void requestStoragePermission(){
       ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.INTERNET", "android.permission.READ_PHONE_STATE"}, 1);
//    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
//            Manifest.permission.READ_EXTERNAL_STORAGE)) {
//        new AlertDialog.Builder(getContext())
//                .setTitle("Permission needed")
//                .setMessage("Permission is needed to access your WhatsApp status Media files")
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ActivityCompat.requestPermissions(getActivity(),
//                                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .create().show();
//
//    } else {
//        ActivityCompat.requestPermissions(getActivity(),
//                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
//    }


}
    private void requestStoragePermission1(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Permission needed")
                    .setMessage("Permission is needed to access your WhatsApp status Media files")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }


    }


   public static ArrayList<File> getImageFiles (File parentDir){
        ArrayList<File> inFiles = new ArrayList<>();
        File[] files;
        files = parentDir.listFiles();

        final Map<File,Long> constantLastModifiedtime = new HashMap<>();
        if(files!=null){
            for (File file:files){

                if(file.getName().endsWith(".jpg")){
                    constantLastModifiedtime.put(file,file.lastModified());//store lastmodified so it can be sorted

                    if(!inFiles.contains(file)){
                        inFiles.add(file);
                    }
                    Collections.sort(inFiles, new Comparator<File>() {//sorting infiles
                        @Override
                        public int compare(File file, File t1) {
                            return constantLastModifiedtime.get(t1).compareTo(constantLastModifiedtime.get(file));
                        }
                    });
                }
            }
        }
        return inFiles;//sorted infiles
   }
    @Override
    public void onRefresh() {
        restart();
    }

    private void restart(){

        File file = new File(Environment.getExternalStorageDirectory().toString()+WHATSAPP_DIR_LOCATION);
        // specify an adapter (see also next example)
        mAdapter = new image_adapter(getImageFiles(file),getContext(),selected_imageList);
        recyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setRefreshing(false);

    }
}
