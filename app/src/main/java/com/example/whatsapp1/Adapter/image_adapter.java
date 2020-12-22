package com.example.whatsapp1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.view.ActionMode;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp1.Fragment.ViewImage;
import com.example.whatsapp1.Fragment.ViewVideo;
import com.example.whatsapp1.R;

import java.io.File;
import java.util.ArrayList;

public class image_adapter extends  RecyclerView.Adapter <image_adapter.MyViewHolder>{
    private Context mContext;
    private ArrayList<File> imageList;
    private ActionMode mActionMode;
    private boolean active = false;
    private ArrayList<File> selected_imageList;
    Menu menu;


    public image_adapter( ArrayList<File> imageList, Context context, ArrayList<File> selected_imageList) {
        this.imageList = imageList;
        this.mContext = context;
        this.selected_imageList =selected_imageList;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_adapter,
                parent, false);

        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        File currentFile = imageList.get(position);
       // holder.imageView.setImageBitmap(imageList[position]);
        Bitmap bitmap = BitmapFactory.decodeFile(currentFile.getAbsolutePath());
        holder.imageView.setImageBitmap(bitmap);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!active) {
                    Fragment frag = new ViewImage(position);
                    FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.drawer_layout, frag, "");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
//               Toast.makeText(mContext, "Permission GRANTED", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mContext, "Selected "+position, Toast.LENGTH_SHORT).show();
                    selected_imageList.add(imageList.get(position));
                   // holder.imageView.setBackgroundColor();
                   // holder.imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.list_item_selected_state));
                   //holder.imageView.setColorFilter(R.color.list_item_selected_state);
                    if(selected_imageList.contains(imageList.get(position))){
                        holder.imageView.setColorFilter(R.color.list_item_selected_state);
                    }

                }


            }
        });
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(mContext, "OnLongClicked position "+position, Toast.LENGTH_SHORT).show();
                active =true;
                if (mActionMode != null) {
                    return false;
                }
                mActionMode =((FragmentActivity)mContext).startActionMode(mActionModeCallback);
               // mActionMode = mContext.startActionMode(mActionModeCallback);
////                        return true;
              // mActionMode = mContext.startActionMode(mActionModeCallback);
                selected_imageList.add(imageList.get(position));


                return true;
            }
        });
    }
    @Override
    public int getItemCount() {
        return imageList.size();
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.custom_main, menu);

            mode.setTitle(" "+selected_imageList.size());

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            active=false;
            switch (item.getItemId()) {
                case R.id.save:
                    Toast.makeText(mContext, "saved", Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
                case R.id.delete:
                    Toast.makeText(mContext, "deleted", Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            active=false;
        }
    };


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView imageView;
        public MyViewHolder(View v) {
            super(v);
            imageView = itemView.findViewById(R.id.image_items);
           // imageView.setImageDrawable();
        }
    }
}
