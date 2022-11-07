package isep.fr.moneytracker.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import isep.fr.moneytracker.Adapters.DayHistoryAdapter;
import isep.fr.moneytracker.Objects.Day;
import isep.fr.moneytracker.Objects.History;
import isep.fr.moneytracker.Objects.User;
import isep.fr.moneytracker.R;
import isep.fr.moneytracker.databinding.FragmentHistoryBinding;

/**
 * The History fragment is used to display the list of the previously saved days.
 */
public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private RecyclerView recyclerView;
    /**
     * The Recycler view layout manager.
     */
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    /**
     * The Vertical layout.
     */
    LinearLayoutManager VerticalLayout;

    private DayHistoryAdapter dayHistoryAdapter;
    private List<Day> dayList;
    private List<Day> dayListCopy = new ArrayList<>(); //this list is necessary to create a simple search bar like system. It is used to stored the Day that doesn't contain the search element.
    private User user;
    private DayDescriptionFragment dayDescriptionFragment;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        try {
            user = new User(getActivity()); //Retrieve user profile from jsonfile.
        } catch (Exception e){
            e.printStackTrace();
        }

        if(user == null){
            user = new User();
        }

        History history = new History();
        try {
            history.getHistory(getActivity()); //retrieve Day lsit from history
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dayList = history.getDayList(); //set current dayList with the list of days fetch from history

        /*
        Sort the dayList array to display the most recent day first
         */
        Collections.sort(dayList, new Comparator<Day>() {
            DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            @Override
            public int compare(Day lhs, Day rhs) {
                try {
                    return f.parse(rhs.getDate()).compareTo(f.parse(lhs.getDate())); //parse the string to date while comparing it.
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(dayList != null){ //check is dayList is null before trying to display the recyclerView -> keep resources
            // initialisation with id's
            recyclerView = binding.historyList;
            RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());

            // Set LayoutManager on Recycler View
            recyclerView.setLayoutManager(RecyclerViewLayoutManager);

            // calling constructor of adapter
            // with source list as a parameter
            dayHistoryAdapter = new DayHistoryAdapter(dayList, this);

            // Set Vertical Layout Manager
            // for Recycler view
            VerticalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(VerticalLayout);

            // Set adapter on recycler view
            recyclerView.setAdapter(dayHistoryAdapter);

            //Define the search bar and listen to the user inputs
            SearchView searchView = binding.searchBar;
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    //Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                    //System.out.println(query);
                    String searchValue = query.toLowerCase(); //retrieve the input value as string

                    restartListView(); //reset dayList and reyclerView before applying this new research
                    List<Day> newDayList = new ArrayList<>(dayList);
                    for (Day day:newDayList) {
                        if(!day.getDaySummary().toLowerCase().contains(searchValue) && !day.getDate().toLowerCase().contains(searchValue)){ //check if the search input is contain in the daySummary or if it is a date
                            dayList.remove(day);
                            dayListCopy.add(day);
                            //System.out.println(day);
                        }
                    }
                    dayHistoryAdapter.notifyDataSetChanged(); //Notified the adapter that the dataset changed

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    //adapter.getFilter().filter(newText);
                    return false;
                }
            });

            //Reset the dayList and recyclerView
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    restartListView();
                    return false;
                }
            });

            //Define the dropdown filter spinner
            Spinner spinnerTypeFilter = (Spinner)binding.spinnerType;
            ArrayAdapter<String> spinnerTypeArrayAdapter = new ArrayAdapter<String>(
                    getContext(),
                    androidx.transition.R.layout.support_simple_spinner_dropdown_item,
                    getResources().getStringArray(R.array.happinessLevel));
            spinnerTypeFilter.setAdapter(spinnerTypeArrayAdapter); //Set the spinner's attributes

            //Add a listener to the filter and define a behavior based on the selected item
            spinnerTypeFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    String selectedItem = parent.getItemAtPosition(position).toString(); //this is your selected item
                    System.out.println(selectedItem);

                    HashMap<String, int[]> happinessLevelsValues = new HashMap<>();
                    happinessLevelsValues.put("hell", new int[]{0, 10});
                    happinessLevelsValues.put("sadness", new int[]{10, 20});
                    happinessLevelsValues.put("boring", new int[]{20, 30});
                    happinessLevelsValues.put("pleasure", new int[]{30, 40});
                    happinessLevelsValues.put("passion", new int[]{40, 50});
                    happinessLevelsValues.put("ultimate purpose", new int[]{50, 60});

                    if(!selectedItem.equals("FILTERS")){
                        restartListView();
                        List<Day> newDayList = new ArrayList<>(dayList);
                        for (Day day:newDayList) {
                            for(String key:happinessLevelsValues.keySet()){
                                if(selectedItem.toLowerCase().contains(key)){ //Check if the selected level of happiness is equal to the stored level of happiness
                                    if( !(happinessLevelsValues.get(key)[0] < day.getHappiness() && day.getHappiness() <= happinessLevelsValues.get(key)[1]) ){ //check if the happiness value is between the array of each happiness level
                                        if(day.getHappiness() == 0 && selectedItem.toLowerCase().contains("hell")){
                                            //Do nothing
                                        }else {
                                            dayList.remove(day);
                                            dayListCopy.add(day);
                                            System.out.println(day);
                                        }

                                    }
                                }

                            }
                        }
                    }else {
                        restartListView();
                    }
                    dayHistoryAdapter.notifyDataSetChanged(); //Notified the adapter that the dataset changed
                }
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });

        }

    }

    /**
     * Restart list view  and dayList array.
     */
    public void restartListView(){
        if(dayListCopy != null){
            dayList.addAll(dayListCopy);
            dayListCopy.clear();
            dayHistoryAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Display day info by switching to the Day Description fragment
     *
     * @param position the position
     */
    public void displayDayInfos(int position){
        dayDescriptionFragment = new DayDescriptionFragment(); // define the new fragment
        dayDescriptionFragment.setDay(dayList.get(position)); //set the selected day
        dayDescriptionFragment.setHistoryFragment(this);
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("historyList").replace(R.id.container, dayDescriptionFragment).commit(); // replace the current fragment with the DayDescription fragment + add a trace to the BackStack, allow the user to goBack easily with the backButton on android.
    }

    /**
     * Delete day in list.
     *
     * @param position the position
     */
    public void deleteDayInList(int position){
        dayList.remove(position);
        dayHistoryAdapter.notifyDataSetChanged();
    }

    /**
     * Update day.
     *
     * @param newDay the new day
     */
    public void updateDay(Day newDay){
        for(int i=0; i<dayList.size(); i++){
            if(dayList.get(i).getDate().equals(newDay.getDate())){
                dayList.get(i).setTaskList(newDay.getTaskList());
            }
        }
        saveHistory();
    }

    /**
     * Save history.
     */
    public void saveHistory(){
        History history = new History();
        history.setDayList(dayList);
        try {
            history.saveHistory(getActivity());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        saveHistory();
    }

}