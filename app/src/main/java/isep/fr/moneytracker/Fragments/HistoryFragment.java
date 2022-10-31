package isep.fr.moneytracker.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import isep.fr.moneytracker.Adapters.ExpenseHistoryAdapter;
import isep.fr.moneytracker.Objects.Day;
import isep.fr.moneytracker.Objects.Task;
import isep.fr.moneytracker.Objects.User;
import isep.fr.moneytracker.R;
import isep.fr.moneytracker.databinding.FragmentHistoryBinding;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager VerticalLayout;
    private ExpenseHistoryAdapter expenseHistoryAdapter;
    private ArrayList<Day> dayList;
    private User user;


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

        dayList = new ArrayList<>();
        generateFalseDay();

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
            expenseHistoryAdapter = new ExpenseHistoryAdapter(dayList, binding, getActivity(), user);

            // Set Horizontal Layout Manager
            // for Recycler view
            VerticalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(VerticalLayout);

            // Set adapter on recycler view
            recyclerView.setAdapter(expenseHistoryAdapter);

        }

    }

    public void generateFalseDay(){
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(true, "test de task", "27/10/2022"));
        taskList.add(new Task(true, "test de task", "27/10/2022"));
        taskList.add(new Task(true, "test de task", "27/10/2022"));

        Day day1 = new Day(true, 49, "26/10/2022", taskList, "test d'implémentation de l'history adapter");

        Day day2= new Day(true, 34, "26/10/2022", taskList, "test d'implémentation de l'history adapter");

        Day day3 = new Day(true, 56, "26/10/2022", taskList, "test d'implémentation de l'history adapter");

        dayList.add(day1);
        dayList.add(day2);
        dayList.add(day3);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}