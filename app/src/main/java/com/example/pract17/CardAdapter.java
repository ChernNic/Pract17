package com.example.pract17;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.PersonViewHolder> {

    private ArrayList<User> userList;
    private Context context;
    private Activity activity;

    public CardAdapter(Context context, ArrayList<User> userList, Activity activity) {
        this.context = context;
        this.userList = userList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        User user = userList.get(position);
        holder.nameTextView.setText(user.getName());
        holder.emailTextView.setText(user.getEmail());

        int id = position;

        Picasso.get().load(user.getProfileImageUrl()).placeholder(R.drawable.placeholder_image).into(holder.profileImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id", id);
                activity.startActivity(intent);
            }
        });

        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    private int lastPosition = -1;

    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in);
            view.startAnimation(animation);
            lastPosition = position;
        }
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, emailTextView;
        AppCompatButton editPersonBtn, deletePersonBtn;
        ImageView profileImageView;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            profileImageView = itemView.findViewById(R.id.profileImageView);
        }
    }
}
