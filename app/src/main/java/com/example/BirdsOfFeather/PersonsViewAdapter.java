package com.example.BirdsOfFeather;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonsViewAdapter extends RecyclerView.Adapter<PersonsViewAdapter.ViewHolder> {
    private final List<Person> persons;
    private final Map<Person, ProfileInfo> profileInformationList;
    private static final String TAG = PersonsViewAdapter.class.getSimpleName();

    public PersonsViewAdapter(List<Person> persons) {
        super();
        this.persons = persons;
        this.profileInformationList = new HashMap<>();
    }

    //for testing purposes
    public List<Person> getPeople() {
        return this.persons;
    }
    //for testing purposes
    public Map<Person, ProfileInfo> getPeopleInfoMap() {
        return profileInformationList;
    }

    public void addPerson(Person person, ProfileInfo newProfileInfo, boolean testing){
        boolean alreadyContained = false;
        for (Person existingPerson : persons) {
            if (person.toString().equals(existingPerson.toString())) {
                alreadyContained = true;
                break;
            }
        }
        //Checks if a potential BoF has already been stored in the list
        if (!alreadyContained) {
            int addPosition = 0;
            for (Person listPerson : persons) {
                ProfileInfo listProfileInfo = profileInformationList.get(listPerson);
                //Sorts according to match amount
                if (listProfileInfo != null) {
                    int newMatches = newProfileInfo.getCommonCourses().size();
                    int comparingMatches = listProfileInfo.getCommonCourses().size();
                    if (newMatches >= comparingMatches) {
                        break;
                    }
                    addPosition++;
                }
            }
            this.persons.add(addPosition, person);
            profileInformationList.put(person, newProfileInfo);
            Log.i(TAG, "Added list item");
            if (!testing) {
                this.notifyItemInserted(addPosition);//this.persons.size()-1);
                Log.i(TAG, "Notified view adapter of new person");
            }
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
        System.out.println("bound");
        holder.setProfileInfo(profileInformationList.get(persons.get(position)));
    }

    @Override
    public int getItemCount() {
        return this.persons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView personNameView;
        private final ImageView profilePictureView;
        private final TextView matchCountView;
        private String personName;
        private ProfileInfo profileInfo;

        ViewHolder(View itemView) {
            super(itemView);
            this.personNameView = itemView.findViewById(R.id.person_row_name);
            this.profilePictureView = itemView.findViewById(R.id.profile_thumbnail_view);
            this.matchCountView = itemView.findViewById(R.id.course_match_count_view);
            itemView.setOnClickListener(this);
        }

        public void setPerson(Person person) {
            this.personName = person.getName();
            this.personNameView.setText(personName);
            //default image check
            if (!person.getURL().equals("")) {
                URLDownload downloadClass = new URLDownload(this.profilePictureView);
                System.out.println(person.getURL());
                downloadClass.execute(person.getURL());
            }
        }

        public void setProfileInfo (ProfileInfo profInf) {
            this.profileInfo = profInf;
            String matchCount = String.valueOf(this.profileInfo.getCommonCourses().size());
            this.matchCountView.setText(matchCount);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, ProfileActivity.class);
            String concatenatedStr = "";
            Collections.sort(this.profileInfo.getCommonCourses(), new ChronologicalComparator());
            //Listing courses in chronological order
            for (Course course : this.profileInfo.getCommonCourses()) {
                concatenatedStr += course.toString() + '\n';
            }
            intent.putExtra("name", this.profileInfo.getName());
            intent.putExtra("courses", concatenatedStr);
            intent.putExtra("URL", this.profileInfo.getURL());
            Log.i(TAG, "Going to Profile Activity");
            context.startActivity(intent);
        }
    }
}
