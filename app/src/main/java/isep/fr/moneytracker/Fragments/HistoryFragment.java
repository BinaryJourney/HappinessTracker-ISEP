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
import java.util.Objects;

import isep.fr.moneytracker.Adapters.ExpenseHistoryAdapter;
import isep.fr.moneytracker.Objects.Day;
import isep.fr.moneytracker.Objects.User;
import isep.fr.moneytracker.R;
import isep.fr.moneytracker.databinding.FragmentHistoryBinding;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
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
            HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(HorizontalLayout);

            // Set adapter on recycler view
            recyclerView.setAdapter(expenseHistoryAdapter);

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}