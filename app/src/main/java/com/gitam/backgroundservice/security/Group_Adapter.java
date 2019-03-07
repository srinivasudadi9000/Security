package com.gitam.backgroundservice.security;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gitam.backgroundservice.R;

import java.util.ArrayList;

public class Group_Adapter extends RecyclerView.Adapter<Group_Adapter.MyHolder> {
    Context context;
    ArrayList<Icons> icons;
    int Rowlayout;


    public Group_Adapter(Trail context, ArrayList<Icons> icons, int appdetails_single) {
        this.context = context;
        this.icons = icons;
        this.Rowlayout = appdetails_single;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(Rowlayout, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        // System.out.println("Appname "+groups.get(position).getApplabel() +"  apppermission "+groups.get(position).getPermissions());
      /*  holder.group_title_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               *//* if (holder.app_permissions.getVisibility() == View.GONE){
                    holder.app_permissions.setVisibility(View.VISIBLE);
                  //  holder.app_permissions.setText(groups.get(position).getPermissions());
                }else {
                    holder.app_permissions.setVisibility(View.GONE);
                }*//*
                getPermissionsByPackageName(holder.group_title_tv.getText().toString());
            }
        });*/

        holder.applabel_tv.setText(icons.get(position).getLable());
        holder.icon_img.setImageDrawable(icons.get(position).getIcon());
        holder.applabel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent permisson = new Intent(context,Permissions_view.class);
                permisson.putExtra("packagename",icons.get(position).getPackagename());
                context.startActivity(permisson);
            }
        });
    }

    @Override
    public int getItemCount() {
        return icons.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        TextView applabel_tv;
        ImageView icon_img;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            icon_img = itemView.findViewById(R.id.icon_img);
            applabel_tv = itemView.findViewById(R.id.applabel_tv);
        }
    }


}
