package com.example.inputclasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonsViewAdapter extends RecyclerView.Adapter<PersonsViewAdapter.ViewHolder> {
    private final List<String> persons;

    public PersonsViewAdapter(List<String> persons) {
        super();
        this.persons = persons;
    }

    public void addPerson(String person){
        if (!this.persons.contains(person)) {
            this.persons.add(person);
            this.notifyItemInserted(this.persons.size()-1);
        }
    }


    @NonNull
    @Override
    public PersonsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.person_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonsViewAdapter.ViewHolder holder, int position) {
        holder.setPerson(persons.get(position));
    }

    @Override
    public int getItemCount() {
        return this.persons.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener
            {
        private final TextView personNameView;
        private String person;

        ViewHolder(View itemView) {
            super(itemView);
            this.personNameView = itemView.findViewById(R.id.person_row_name);
            //itemView.setOnClickListener(this);
        }

        public void setPerson(String person) {
            this.person = person;
            this.personNameView.setText(person);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra("person_id", this.person.getId());
            context.startActivity(intent);
        }
    }
}
