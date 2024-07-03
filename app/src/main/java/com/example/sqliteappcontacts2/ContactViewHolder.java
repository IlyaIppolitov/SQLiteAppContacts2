package com.example.sqliteappcontacts2;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactViewHolder extends RecyclerView.ViewHolder {
    public TextView nameTextView;
    public TextView emailTextView;

    public ContactViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.contactName);
        emailTextView = itemView.findViewById(R.id.contactEmail);
    }
}