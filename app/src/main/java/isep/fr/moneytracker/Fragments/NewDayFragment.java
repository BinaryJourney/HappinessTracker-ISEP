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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;

import isep.fr.moneytracker.Adapters.CreateTasksListAdapter;
import isep.fr.moneytracker.Objects.Day;
import isep.fr.moneytracker.Objects.History;
import isep.fr.moneytracker.Objects.Task;
import isep.fr.moneytracker.Objects.User;
import isep.fr.moneytracker.R;
import isep.fr.moneytracker.databinding.FragmentNewDayBinding;

/**
 * The NewDayFragment is the fragment used by the user to input his current day, his happiness level, his tasks and a summary of his day.
 */
public class NewDayFragment extends Fragment {
    private FragmentNewDayBinding binding;
    private User user;
    private RecyclerView recyclerView;
    /**
     * The Recycler view layout manager.
     * We use this layout manager to create a recyclerView that will be use to contains the tasks of the user as a vertical list.
     */
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    /**
     * The Vertical layout.
     */
    LinearLayoutManager VerticalLayout;
    /**
     * The adapter linked to the recycler view
     */
    private CreateTasksListAdapter tasksListAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewDayBinding.inflate(inflater, container, false);

        try {
            user = new User(getActivity()); //retrieve user profile from .json file
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

        TextView happinessLevelText = binding.happinessLevelText;
        SeekBar happinessLevel = binding.happinessLevel;

        /*
        Display the user's current day information on the fragment
         */
        displayUserDay();

        //Set a behavior to the seekbar depending of the value of itself.
        happinessLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String[] happinessLevels =  {"Hell", "Sadness", "Boring", "Pleasure", "Passion", "Ultimate Purpose"}; //Define every happiness level

                happinessLevelText.setText("Happiness Level : "+happinessLevels[(i-1)/10]);
                user.getCurrentDay().setHappiness(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        //call dialog box to input a new task.
        binding.addTask.setOnClickListener(item -> {
            displayDialogBox(getActivity(), (ViewGroup) view);

        });

        //If the user want to manually add his day to history
        binding.validateDay.setOnClickListener(item -> {
            History history = new History();
            //Set the currentDay data based on the input of the user.
            user.getCurrentDay().setDate(binding.editTextDate.getText().toString());
            user.getCurrentDay().setDaySummary(binding.DayDescription.getText().toString());
            user.getCurrentDay().setDayDone(true);

            try {
                history.getHistory(getActivity());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*
            Check if a day already exist with the same date, if yes the day is updated if not the day is added to the list.
             */
            boolean dayAlreadySaved = false;
            int indexOfSavedDay =0;
            for(Day previousDay:history.getDayList()){
                if(previousDay.getDate().equals(user.getCurrentDay().getDate())){
                    indexOfSavedDay = history.getDayList().indexOf(previousDay);
                    dayAlreadySaved = true;
                }
            }

            if(!dayAlreadySaved){
                history.addDay(user.getCurrentDay());
                Toast.makeText(getContext(),"Your day was added to the history",Toast.LENGTH_SHORT).show();
            } else {
                history.getDayList().set(indexOfSavedDay, user.getCurrentDay());
                Toast.makeText(getContext(),"Your day was updated in the history",Toast.LENGTH_SHORT).show();
            }

            try {
                history.saveHistory(getActivity());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Update data displayed of fragment after saving the day.
            displayUserDay();

        });

    }

    /**
     * Display user day on the fragment element.
     */
    public void displayUserDay(){

        TextView dayDate = binding.editTextDate;
        TextView dayDescription = binding.DayDescription;
        TextView dayStatus = binding.dayStatus;
        TextView happinessLevelText = binding.happinessLevelText;
        SeekBar happinessLevel = binding.happinessLevel;

        dayStatus.setText("Day Status : "+(user.getCurrentDay().isDayDone() ? "Saved" : "Not Saved"));

        happinessLevel.incrementProgressBy(10);

        dayDate.setText(user.getCurrentDay().getDate());
        dayDescription.setText(user.getCurrentDay().getDaySummary());

        String[] happinessLevels =  {"Hell", "Sadness", "Boring", "Pleasure", "Passion", "Ultimate Purpose"};
        int happinessLevelValue = ((int) Math.round(user.getCurrentDay().getHappiness())/10);
        if(happinessLevelValue == 6)
            happinessLevelValue--;
        happinessLevelText.setText("Happiness Level : "+happinessLevels[happinessLevelValue]);
        happinessLevel.setProgress((int) user.getCurrentDay().getHappiness());

        //Set the recycler view adapter and link it with the user TaskList. This allow the app to refresh the list after a new task is added to it.
        recyclerView = binding.tasksList;
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());

        // Set LayoutManager on Recycler View
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        // calling constructor of adapter
        // with source list as a parameter
        tasksListAdapter = new CreateTasksListAdapter(user.getCurrentDay().getTaskList(), getActivity(), true, this);

        // Set Vertical Layout Manager
        // for Recycler view
        VerticalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(VerticalLayout);

        // Set adapter on recycler view
        recyclerView.setAdapter(tasksListAdapter);
    }

    /**
     * Display dialog box.
     *
     * @param activity the activity
     * @param view     the view
     */
    public void displayDialogBox(Activity activity, ViewGroup view){
        Task task = new Task();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Your Task");

        View viewInflated = LayoutInflater.from(activity).inflate(R.layout.task_description_box, view, false);//Link the dialog box with the corresponding xml template

        /*
        Retrieve the useful EditText to later create the new task
         */
        EditText taskName = viewInflated.findViewById(R.id.taskName);
        EditText taskDuTime = viewInflated.findViewById(R.id.taskDuTime);
        EditText taskDescription = viewInflated.findViewById(R.id.taskDescription);

        //set the define xml layout on the dialog box
        builder.setView(viewInflated);

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                //retrieve the data from the EditTexts and add them to the task object
                task.setName(taskName.getText().toString());
                task.setDuTime(taskDuTime.getText().toString());
                task.setDescription(taskDescription.getText().toString());
                task.setDone(false);

                user.getCurrentDay().addTask(task);
                //notify the recyclerView's adapter that the parameter list was changed.
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

    /**
     * Set task done.
     *
     * @param position the position
     * @param taskDone the task done
     */
    public void setTaskDone(int position, boolean taskDone){
        user.getCurrentDay().getTask(position).setDone(taskDone);
    }

    /**
     * Delete task in list.
     *
     * @param position the position
     */
    public void deleteTaskInList(int position){
        user.getCurrentDay().getTaskList().remove(position);
        tasksListAdapter.notifyDataSetChanged();
    }

    /**
     * This method is called when the fragment is destroyed, it is to automatically save the current inside the user profile .json file before quitting fragment.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        user.getCurrentDay().setDate(binding.editTextDate.getText().toString());
        user.getCurrentDay().setDaySummary(binding.DayDescription.getText().toString());
        try {
            user.saveUser(getActivity()); //save user to userProfile.json
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        binding = null;
    }
}
