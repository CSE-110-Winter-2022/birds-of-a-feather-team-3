package com.example.inputclasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonsViewAdapter extends RecyclerView.Adapter<PersonsViewAdapter.ViewHolder> {
    private final List<String> persons;
    private final Map<String, ProfileInfo> profileInformationList;

    public PersonsViewAdapter(List<String> persons) {
        super();
        this.persons = persons;
        this.profileInformationList = new HashMap<>();
    }

    public void addPerson(String person, ProfileInfo newProfileInfo){
        if (!this.persons.contains(person)) {
            this.persons.add(person);
            profileInformationList.put(person, newProfileInfo);
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
        holder.setProfileInfo(profileInformationList.get(persons.get(position)));
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
        private ProfileInfo profileInfo;

        ViewHolder(View itemView) {
            super(itemView);
            this.personNameView = itemView.findViewById(R.id.person_row_name);
            //maybe comment out below, it was before.
            itemView.setOnClickListener(this);
        }

        public void setPerson(String person) {
            this.person = person;
            this.personNameView.setText(person);
        }

        public void setProfileInfo (ProfileInfo profInf) {
            this.profileInfo = profInf;
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, ProfileActivity.class);
            String concatenatedStr = "";
            for (Course course : this.profileInfo.getCommonCourses()) {
                concatenatedStr += course.toString() + '\n';
            }
            intent.putExtra("name", this.profileInfo.getName());
            intent.putExtra("courses", concatenatedStr);
            intent.putExtra("URL", this.profileInfo.getURL());
            context.startActivity(intent);
        }
    }
}
