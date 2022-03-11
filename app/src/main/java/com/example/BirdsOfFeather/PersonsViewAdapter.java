package com.example.BirdsOfFeather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.BirdsOfFeather.database.AppDatabase;
import com.example.BirdsOfFeather.database.ProfileEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonsViewAdapter extends RecyclerView.Adapter<PersonsViewAdapter.ViewHolder> {
    //private final List<Person> persons;
    //private final Map<Person, ProfileInfo> profileInformationList;
    private static final String TAG = PersonsViewAdapter.class.getSimpleName();
    // 0=default, 1=recent, 2=class size
    private int sortType;
    private final List<ProfileInfo> profileInfos;
    private final boolean isFavoriteSession;


    public PersonsViewAdapter(List<ProfileInfo> persons, boolean isFavoriteSession) {
        super();
        //this.persons = persons;
        //this.profileInformationList = new HashMap<>();
        this.profileInfos = persons;
        this.isFavoriteSession = isFavoriteSession;
    }

    public PersonsViewAdapter(List<ProfileInfo> persons) {
        super();
        //this.persons = persons;
        //this.profileInformationList = new HashMap<>();
        this.isFavoriteSession = false;
        this.profileInfos = persons;

    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public void clearAdapter() {
        this.profileInfos.clear();
        //persons.clear();
        //profileInformationList.clear();
        notifyDataSetChanged();
    }
    //for testing purposes

    public List<ProfileInfo> getProfileInfos() {
        return this.profileInfos;
    }
    //for testing purposes
    /*
    public Map<Person, ProfileInfo> getPeopleInfoMap() {
        return profileInformationList;
    } */

//    public void addPerson(Person person, ProfileInfo newProfileInfo, boolean testing){
//        boolean alreadyContained = false;
//        for (Person existingPerson : persons) {
//            if (person.toString().equals(existingPerson.toString())) {
//                alreadyContained = true;
//                break;
//            }
//        }
//        //Checks if a potential BoF has already been stored in the list
//        if (!alreadyContained) {
//            int addPosition = 0;
//            for (Person listPerson : persons) {
//                ProfileInfo listProfileInfo = profileInformationList.get(listPerson);
//                //Sorts according to match amount
//                if (listProfileInfo != null) {
//                    int newMatches = newProfileInfo.getCommonCourses().size();
//                    int comparingMatches = listProfileInfo.getCommonCourses().size();
//                    if (newMatches >= comparingMatches) {
//                        break;
//                    }
//                    addPosition++;
//                }
//            }
//            this.persons.add(addPosition, person);
//            profileInformationList.put(person, newProfileInfo);
//            if (!testing) {
//                this.notifyItemInserted(addPosition);//this.persons.size()-1);
//
//                // notifyDataSetChanged();
//                Log.i(TAG, "Added list item and notified view adapter of new person");
//            }
//        }
//    }

    public void resort() {
        if (sortType == 0) {
            sortByMatches();
        } else if (sortType == 1) {
            sortByRecent();
        } else {
            sortBySize();
        }
    }
    // new addPerson that allows different types of sorting
    public boolean addPerson(ProfileInfo newProfileInfo, boolean testing){
        boolean alreadyContained = false;
        if (newProfileInfo == null) {
            return false;
        }
        for (ProfileInfo existingProfile : profileInfos) {
            if (newProfileInfo.toString().equals(existingProfile.toString())) {
                Log.i(TAG, newProfileInfo.toString() + " " + existingProfile.toString());
                alreadyContained = true;
                System.out.println("already contained");
                return false;
            }
        }
        //Checks if a potential BoF has already been stored in the list
        FilterScoreCalculation helper = new FilterScoreCalculation();
        newProfileInfo.setScoreRecent(helper.score_recent(newProfileInfo));
        newProfileInfo.setScoreClassSize(helper.score_size(newProfileInfo));
        this.profileInfos.add(newProfileInfo);
        //profileInformationList.put(person, newProfileInfo);
        // sort persons
        if (sortType == 0) {
            sortByMatches();
        } else if (sortType == 1) {
            sortByRecent();
        } else {
            sortBySize();
        }

        return true;
    }

    // different sorting methods and their corresponding comparators
    public Comparator<ProfileInfo> SortByMatchesComparator = new Comparator<ProfileInfo>() {
        @Override
        public int compare(ProfileInfo p1, ProfileInfo p2) {
            if (p1.getIsWaving() == p2.getIsWaving()) {
                int first = p1.getCommonCourses().size();
                int second = p2.getCommonCourses().size();
                return second-first;
            } //else
            //converts boolean to 1 and 0 and compares
            return (p2.getIsWaving() ? 1 : 0) - (p1.getIsWaving() ? 1 : 0);

        }
    };

    public void sortByMatches() {
        Collections.sort(profileInfos, SortByMatchesComparator);
        this.notifyDataSetChanged();
    }

    public Comparator<ProfileInfo> SortByRecentComparator = new Comparator<ProfileInfo>() {
        @Override
        public int compare(ProfileInfo p1, ProfileInfo p2) {
            if (p1.getIsWaving() == p2.getIsWaving()) {
                return p2.getScoreRecent() - p1.getScoreRecent();
            } //else
            //converts boolean to 1 and 0 and compares
            return (p2.getIsWaving() ? 1 : 0) - (p1.getIsWaving() ? 1 : 0);
        }
    };

    public void sortByRecent() {
        Collections.sort(profileInfos, SortByRecentComparator);
        this.notifyDataSetChanged();
    }

    public Comparator<ProfileInfo> SortByCourseSizeComparator = new Comparator<ProfileInfo>() {
        @Override
        public int compare(ProfileInfo p1, ProfileInfo p2) {
            if (p1.getIsWaving() == p2.getIsWaving()) { //this allows for float comparison to return int
                if (p1.getScoreClassSize() > p2.getScoreClassSize()) {
                    return -1;
                }
                else if (p1.getScoreClassSize() < p2.getScoreClassSize()) {
                    return 1;
                }
                else {
                    return 0;
                }
            } //else
            //converts boolean to 1 and 0 and compares
            return (p2.getIsWaving() ? 1 : 0) - (p1.getIsWaving() ? 1 : 0);
        }
    };
    public void sortBySize() {
        Collections.sort(profileInfos, SortByCourseSizeComparator);
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PersonsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.person_row, parent, false);
        //if (!mocking) {
        //}
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonsViewAdapter.ViewHolder holder, int position) {
        //Person holderPerson = persons.get(position);
        //holder.setPerson(holderPerson);
        System.out.println("bound");
        ProfileInfo holderProfile = profileInfos.get(position);
        holder.setProfileInfo(holderProfile);
        //holder.setProfileInfo(profileInformationList.get(holderPerson));
        holder.indicateWave(holderProfile.getIsWaving());
        holder.indicateFavorited(holderProfile.getIsFavorited());
        if (this.isFavoriteSession) {
            holder.setFavoriteSession(true);
            holder.indicateFavorited(true);
        }
    }

    @Override
    public int getItemCount() {
        return this.profileInfos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView personNameView;
        private final ImageView profilePictureView;
        private final TextView matchCountView;
        private final ImageView waveIndicator;
        private final ImageButton favoriteButton;
        private String personName;
        private ProfileInfo profileInfo;
        private boolean isFavorited;
        private long entityId;
        ProfileEntity profileEntity;
        ProfileInfo infoToCopy;
        AppDatabase db;
        private boolean isInFavoriteSession;

        ViewHolder(View itemView) {
            super(itemView);
            isFavorited = false;
            this.personNameView = itemView.findViewById(R.id.person_row_name);
            this.profilePictureView = itemView.findViewById(R.id.profile_thumbnail_view);
            this.matchCountView = itemView.findViewById(R.id.course_match_count_view);
            this.waveIndicator = itemView.findViewById(R.id.person_row_waved_indicator);
            this.favoriteButton = itemView.findViewById(R.id.favorite_person_row_button);
            //check if mocking
            this.db = AppDatabase.singleton(favoriteButton.getContext());
            itemView.setOnClickListener(this);

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    isFavorited = !isFavorited;
                    if (isFavorited) {
                        Log.i(TAG, "favoriting");
                        entityId = db.profilesDao().insert(profileEntity);
                        Log.i(TAG, " done adding");

                        favoriteButton.setImageResource(android.R.drawable.btn_star_big_on);
                    }
                    else {
                        Log.i(TAG, "Deleting");
                        //favorite session id is always 1.
                        ProfileEntity entityToDelete = db.profilesDao().searchFavorite(profileInfo.getUniqueId(), 1);
                        //profileInfo.getUniqueId()
                        //ProfileEntity entityToDelete = db.profilesDao().getEntity(entityId);
                        db.profilesDao().delete(entityToDelete);
                        Log.i(TAG, entityToDelete.profileName + " deleted from favorites");
                        favoriteButton.setImageResource(android.R.drawable.btn_star_big_off);
                        if (isInFavoriteSession) {
                            
                        }
                    }
                }
            });
        }

        public void setFavoriteSession(boolean setType) {
            this.isInFavoriteSession = setType;
        }

        /*
        public void setPerson(ProfileInfo person) {
            this.profileInfo = person;
            this.personName = person.getName();
            this.personNameView.setText(personName);

            //ProfileInfo infoToCopy = db.profilesDao().get(profileInfo.getProfileId());
            this.profileEntity = new ProfileEntity(person.getName(), person.getURL(), 1,
                    person.getCommonCourses(), person.getUniqueId(), person.getIsWaving());
            Log.i(TAG, "Set the profile entity in setPerson");
            //default image check
            if (!person.getURL().equals("")) {
                URLDownload downloadClass = new URLDownload(this.profilePictureView);
                System.out.println(person.getURL());
                downloadClass.execute(person.getURL());
            }
        }

         */

        public void indicateWave(boolean isWaving) {
            if (isWaving) {
                //this.waveIndicator.setBackgroundResource(R.drawable.googleg_standard_color_18);
                this.waveIndicator.setBackgroundResource(R.mipmap.filled_wave_foreground);
            }
            else {
                //this.waveIndicator.setBackgroundResource(R.drawable.googleg_disabled_color_18);
                this.waveIndicator.setBackgroundResource(R.mipmap.empty_wave_foreground);
            }
        }

        public void indicateFavorited (boolean favorited) {
            this.isFavorited = favorited;
            if (favorited) {
                favoriteButton.setImageResource(android.R.drawable.btn_star_big_on);
            }
            else {
                favoriteButton.setImageResource(android.R.drawable.btn_star_big_off);
            }
        }

        public void setProfileInfo (ProfileInfo profInf) {
            this.profileInfo = profInf;
            String matchCount = String.valueOf(this.profileInfo.getCommonCourses().size());
            this.matchCountView.setText(matchCount);
            this.personName = profInf.getName();
            this.personNameView.setText(personName);
            this.profileEntity = new ProfileEntity(profInf.getName(), profInf.getURL(), 1,
                    profInf.getCommonCourses(), profInf.getUniqueId(), profInf.getIsWaving());
            //default image check
            if (!profInf.getURL().equals("")) {
                URLDownload downloadClass = new URLDownload(this.profilePictureView);
                System.out.println(profInf.getURL());
                downloadClass.execute(profInf.getURL());
            }
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

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
            String selfName = preferences.getString("unique_identifer", "elmer");
            intent.putExtra("name", this.profileInfo.getName());
            intent.putExtra("courses", concatenatedStr);
            intent.putExtra("URL", this.profileInfo.getURL());
            intent.putExtra("uniqueId", this.profileInfo.getUniqueId());
            intent.putExtra("selfId", selfName);
            intent.putExtra("favorited", isFavorited);
            intent.putExtra("profileId", this.profileInfo.getProfileId());
            Log.i(TAG, "Going to Profile Activity");
            context.startActivity(intent);
        }
    }
}