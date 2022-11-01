package isep.fr.moneytracker.Fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;

import isep.fr.moneytracker.Adapters.TasksListAdapter;
import isep.fr.moneytracker.Objects.Day;
import isep.fr.moneytracker.Objects.Task;
import isep.fr.moneytracker.Objects.User;
import isep.fr.moneytracker.R;
import isep.fr.moneytracker.Tools.DialogBox;
import isep.fr.moneytracker.databinding.FragmentNewDayBinding;

public class NewDayFragment extends Fragment {
    private FragmentNewDayBinding binding;
    private User user;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager VerticalLayout;
    private TasksListAdapter tasksListAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewDayBinding.inflate(inflater, container, false);

        try {
            user = new User(getActivity());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(user == null){
            user = new User();
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView dayDate = binding.editTextDate;
        TextView dayDescription = binding.DayDescription;
        TextView happinessLevelText = binding.happinessLevelText;
        SeekBar happinessLevel = binding.happinessLevel;
        happinessLevel.incrementProgressBy(10);

        dayDate.setText(user.getCurrentDay().getDate());
        dayDescription.setText(user.getCurrentDay().getDaySummary());

        String[] happinessLevels =  {"Hell", "Sadness", "Boring", "Pleasure", "Passion", "Ultimate Purpose"};
        happinessLevelText.setText("Happiness Level : "+happinessLevels[((int) Math.round(user.getCurrentDay().getHappiness())/10)]);
        happinessLevel.setProgress((int) user.getCurrentDay().getHappiness());

        happinessLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String[] happinessLevels =  {"Hell", "Sadness", "Boring", "Pleasure", "Passion", "Ultimate Purpose"};

                happinessLevelText.setText("Happiness Level : "+happinessLevels[(i-1)/10]);
                user.getCurrentDay().setHappiness(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        recyclerView = binding.tasksList;
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());

        // Set LayoutManager on Recycler View
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        // calling constructor of adapter
        // with source list as a parameter
        tasksListAdapter = new TasksListAdapter(user.getCurrentDay().getTaskList(), getActivity());

        // Set Horizontal Layout Manager
        // for Recycler view
        VerticalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(VerticalLayout);

        // Set adapter on recycler view
        recyclerView.setAdapter(tasksListAdapter);

        binding.addTask.setOnClickListener(item -> {
            displayDialogBox(getActivity(), (ViewGroup) view);

        });

    }

    public void displayDialogBox(Activity activity, ViewGroup view){
        Task task = new Task();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Your Task");

        View viewInflated = LayoutInflater.from(activity).inflate(R.layout.task_description_box, view, false);

        EditText taskName = viewInflated.findViewById(R.id.taskName);
        EditText taskDuTime = viewInflated.findViewById(R.id.taskDuTime);
        EditText taskDescription = viewInflated.findViewById(R.id.taskDescription);

        builder.setView(viewInflated);

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                task.setName(taskName.getText().toString());
                task.setDuTime(taskDuTime.getText().toString());
                task.setDescription(taskDescription.getText().toString());
                task.setDone(false);

                user.getCurrentDay().addTask(task);
                tasksListAdapter.notifyDataSetChanged();

            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        user.getCurrentDay().setDate(binding.editTextDate.getText().toString());
        user.getCurrentDay().setDaySummary(binding.DayDescription.getText().toString());
        try {
            user.saveUser(getActivity());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        binding = null;
    }
}