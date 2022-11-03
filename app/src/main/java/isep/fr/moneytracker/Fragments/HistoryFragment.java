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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import isep.fr.moneytracker.Adapters.DayHistoryAdapter;
import isep.fr.moneytracker.Objects.Day;
import isep.fr.moneytracker.Objects.History;
import isep.fr.moneytracker.Objects.Task;
import isep.fr.moneytracker.Objects.User;
import isep.fr.moneytracker.R;
import isep.fr.moneytracker.databinding.FragmentHistoryBinding;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager VerticalLayout;
    private DayHistoryAdapter dayHistoryAdapter;
    private List<Day> dayList;
    private List<Day> dayListCopy = new ArrayList<>();
    private User user;
    private DayDescriptionFragment dayDescriptionFragment;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        try {
            user = new User(getActivity());
        } catch (Exception e){
            e.printStackTrace();
        }

        if(user == null){
            user = new User();
        }

        History history = new History();
        try {
            history.getHistory(getActivity());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dayList = history.getDayList();

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(dayList != null){
            // initialisation with id's
            recyclerView = binding.expensesList;
            RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());

            // Set LayoutManager on Recycler View
            recyclerView.setLayoutManager(RecyclerViewLayoutManager);

            // calling constructor of adapter
            // with source list as a parameter
            dayHistoryAdapter = new DayHistoryAdapter(dayList, this);

            // Set Horizontal Layout Manager
            // for Recycler view
            VerticalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(VerticalLayout);

            // Set adapter on recycler view
            recyclerView.setAdapter(dayHistoryAdapter);


        SearchView searchView = binding.searchBar;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                System.out.println(query);
                String searchValue = query.toLowerCase();

                restartListView();
                List<Day> newDayList = new ArrayList<>(dayList);
                for (Day day:newDayList) {
                    if(!day.getDaySummary().toLowerCase().contains(searchValue) && !day.getDate().toLowerCase().contains(searchValue)){
                        dayList.remove(day);
                        dayListCopy.add(day);
                        System.out.println(day);
                    }
                }
                dayHistoryAdapter.notifyDataSetChanged();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //adapter.getFilter().filter(newText);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                restartListView();
                return false;
            }
        });


        Spinner spinnerTypeFilter = (Spinner)binding.spinnerType;
        ArrayAdapter<String> spinnerTypeArrayAdapter = new ArrayAdapter<String>(
                getContext(),
                androidx.transition.R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.happinessLevel));
        spinnerTypeFilter.setAdapter(spinnerTypeArrayAdapter);

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
                            if(selectedItem.toLowerCase().contains(key)){
                                if( !(happinessLevelsValues.get(key)[0] < day.getHappiness() && day.getHappiness() <= happinessLevelsValues.get(key)[1]) ){
                                    if(day.getHappiness() == 0 && selectedItem.toLowerCase().contains("hell")){
                                    //TODO delete this empty condition -> not really appropriate
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
                dayHistoryAdapter.notifyDataSetChanged();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        }

    }

    public void restartListView(){
        if(dayListCopy != null){
            for (Day day:dayListCopy) {
                dayList.add(day);
            }
            dayListCopy.clear();
            dayHistoryAdapter.notifyDataSetChanged();
        }
    }

    public void displayDayInfos(int position){
        dayDescriptionFragment = new DayDescriptionFragment();
        dayDescriptionFragment.setDay(dayList.get(position));
        dayDescriptionFragment.setHistoryFragment(this);
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("historyList").replace(R.id.container, dayDescriptionFragment).commit();
    }

    public void deleteDayInList(int position){
        dayList.remove(position);
        dayHistoryAdapter.notifyDataSetChanged();
    }

    public void updateDay(Day newDay){
        for(int i=0; i<dayList.size(); i++){
            if(dayList.get(i).getDate().equals(newDay.getDate())){
                dayList.get(i).setTaskList(newDay.getTaskList());
            }
        }
        saveHistory();
    }

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