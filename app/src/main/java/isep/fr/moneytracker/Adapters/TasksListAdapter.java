package isep.fr.moneytracker.Adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import isep.fr.moneytracker.Fragments.DayDescriptionFragment;
import isep.fr.moneytracker.Fragments.HistoryFragment;
import isep.fr.moneytracker.Objects.Day;
import isep.fr.moneytracker.Objects.Task;
import isep.fr.moneytracker.Objects.User;
import isep.fr.moneytracker.R;
import isep.fr.moneytracker.Tools.DialogBox;

public class TasksListAdapter extends RecyclerView.Adapter<TasksListAdapter.MyView> {
    private final boolean newDay;
    private final DayDescriptionFragment binding;
    private List<Task> taskList;
    private Activity activity;
    private ViewGroup parent;


    public class MyView extends RecyclerView.ViewHolder {

        // Text View
        TextView taskName;
        TextView taskDate;
        CheckBox taskDone;
        ConstraintLayout taskContainer;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);
            taskName = itemView.findViewById(R.id.taskName);
            taskDate = itemView.findViewById(R.id.taskDate);
            taskDone = itemView.findViewById(R.id.taskDone);
            taskContainer = itemView.findViewById(R.id.taskContainer);
        }
    }

    public TasksListAdapter(List<Task> taskList, Activity activity, DayDescriptionFragment binding, boolean newDay) {
        this.taskList = taskList;
        this.activity = activity;
        this.newDay = newDay;
        this.binding = binding;
        //System.out.println(taskList);
    }

    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);

        this.parent = parent;

        // return itemView
        return new MyView(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyView holder, final int position) {

        holder.taskName.setText(taskList.get(position).getName());
        holder.taskDate.setText(taskList.get(position).getDuTime());
        holder.taskDone.setChecked(taskList.get(position).isDone());
        holder.taskDone.setEnabled(newDay);

        holder.taskContainer.setOnClickListener(item -> {
            DialogBox dialogBox = new DialogBox();
            dialogBox.displayDialogBox(activity, parent, taskList.get(position), false);
        });

        holder.taskContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(binding.getActivity());
                builder.setTitle("Delete this task ?");
                builder.setMessage("Are you sure that you want to delete this task ? You won't be able to get it back.");

                // Set up the buttons
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        binding.deleteTaskInList(position);
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }




}
