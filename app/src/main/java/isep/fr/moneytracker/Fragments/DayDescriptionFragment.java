package isep.fr.moneytracker.Fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import isep.fr.moneytracker.Adapters.DayHistoryAdapter;
import isep.fr.moneytracker.Adapters.TasksListAdapter;
import isep.fr.moneytracker.Objects.Day;
import isep.fr.moneytracker.Objects.User;
import isep.fr.moneytracker.databinding.FragmentDayDescriptionBinding;


public class DayDescriptionFragment extends Fragment {
    private FragmentDayDescriptionBinding binding;
    private Day day;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager VerticalLayout;
    private TasksListAdapter tasksListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDayDescriptionBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView dayDate = binding.editTextDate;
        TextView dayDescription = binding.DayDescription;
        TextView happinessLevelText = binding.happinessLevelText;
        SeekBar happinessLevel = binding.happinessLevel;
        happinessLevel.incrementProgressBy(10);

        dayDate.setText(day.getDate());
        dayDescription.setText(day.getDaySummary());

        String[] happinessLevels =  {"Hell", "Sadness", "Boring", "Pleasure", "Passion", "Ultimate Purpose"};
        int happinessLevelValue = ((int) Math.round(day.getHappiness())/10);
        if(happinessLevelValue == 6)
            happinessLevelValue--;
        happinessLevelText.setText("Happiness Level : "+happinessLevels[happinessLevelValue]);
        happinessLevel.setProgress((int) day.getHappiness());
        happinessLevel.setEnabled(false);

        // Usefull to modify the levelOfHappiness later on
        /*
        happinessLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String[] happinessLevels =  {"Hell", "Sadness", "Boring", "Pleasure", "Passion", "Ultimate Purpose"};

                happinessLevelText.setText("Happiness Level : "+happinessLevels[(i/10)-1]);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
         */

        recyclerView = binding.tasksList;
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());

        // Set LayoutManager on Recycler View
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        // calling constructor of adapter
        // with source list as a parameter
        tasksListAdapter = new TasksListAdapter(day.getTaskList(), getActivity(), false);

        // Set Horizontal Layout Manager
        // for Recycler view
        VerticalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(VerticalLayout);

        // Set adapter on recycler view
        recyclerView.setAdapter(tasksListAdapter);


    }

    public void setDay(Day day) {
        this.day = day;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
