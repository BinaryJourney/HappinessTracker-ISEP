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

import isep.fr.moneytracker.Fragments.HistoryFragment;
import isep.fr.moneytracker.Objects.Day;
import isep.fr.moneytracker.Objects.Task;
import isep.fr.moneytracker.Objects.User;
import isep.fr.moneytracker.R;

public class TasksListAdapter extends RecyclerView.Adapter<TasksListAdapter.MyView> {
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

    public TasksListAdapter(List<Task> taskList, Activity activity) {
        this.taskList = taskList;
        this.activity = activity;
        System.out.println(taskList);
    }

    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);

        this.parent = parent;

        // return itemView
        return new MyView(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {

        holder.taskName.setText(taskList.get(position).getName());
        holder.taskDate.setText(taskList.get(position).getDuTime());
        holder.taskDone.setChecked(taskList.get(position).isDone());
        holder.taskDone.setEnabled(false);

        holder.taskContainer.setOnClickListener(item -> {
            displayDialogBox(parent, taskList.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void displayDialogBox(ViewGroup view, Task task){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Your Task");

        View viewInflated = LayoutInflater.from(activity).inflate(R.layout.task_description_box, view, false);

        EditText taskName = viewInflated.findViewById(R.id.taskName);
        EditText taskDuTime = viewInflated.findViewById(R.id.taskDuTime);
        EditText taskDescription = viewInflated.findViewById(R.id.taskDescription);

        taskName.setText(task.getName());
        taskDuTime.setText(task.getDuTime());
        taskDescription.setText(task.getDescription());

        builder.setView(viewInflated);

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

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


}
