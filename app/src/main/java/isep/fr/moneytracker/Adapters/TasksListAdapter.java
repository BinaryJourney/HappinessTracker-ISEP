package isep.fr.moneytracker.Adapters;

import android.annotation.SuppressLint;
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

/**
 * This adapter is used to define the design and the behavior of the recyclerView element
 */
public class TasksListAdapter extends RecyclerView.Adapter<TasksListAdapter.MyView> {
    private final boolean newDay;
    private final DayDescriptionFragment binding;
    private List<Task> taskList;
    private Activity activity;
    private ViewGroup parent;

    /**
     * The class MyView is used to define the element of the xml layout item.
     */
    public class MyView extends RecyclerView.ViewHolder {

        /**
         * The Task name.
         */
        TextView taskName;
        /**
         * The Task date.
         */
        TextView taskDate;
        /**
         * The Task is done.
         */
        CheckBox taskDone;
        /**
         * The Task container.
         */
        ConstraintLayout taskContainer;

        /**
         * Instantiates a new My view.
         *
         * @param view the view
         */
    // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);
            //define the important xml elements
            taskName = itemView.findViewById(R.id.taskName);
            taskDate = itemView.findViewById(R.id.taskDate);
            taskDone = itemView.findViewById(R.id.taskDone);
            taskContainer = itemView.findViewById(R.id.taskContainer);
        }
    }

    /**
     * Instantiates a new Tasks list adapter.
     *
     * @param taskList the task list
     * @param activity the activity
     * @param binding  the binding
     * @param newDay   the new day
     */
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
    public void onBindViewHolder(@NonNull MyView holder, @SuppressLint("RecyclerView") final int position) {

        //replace the static text value with the data from the selected task.
        holder.taskName.setText(taskList.get(position).getName());
        holder.taskDate.setText(taskList.get(position).getDuTime());
        holder.taskDone.setChecked(taskList.get(position).isDone());
        holder.taskDone.setEnabled(newDay);

        //add a listener to the task Container. This define the behavior of the app if the user click on a task Item in the recyclerView
        holder.taskContainer.setOnClickListener(item -> {
            DialogBox dialogBox = new DialogBox();
            dialogBox.displayDialogBox(activity, parent, taskList.get(position), false);
        });

        //Define a specific behavior if the user decide to longPress on an item of the recyclerView
        holder.taskContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //This alert box will ask user for confirmation before deleting the task
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
