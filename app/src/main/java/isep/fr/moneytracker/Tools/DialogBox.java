package isep.fr.moneytracker.Tools;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import isep.fr.moneytracker.Objects.Task;
import isep.fr.moneytracker.R;

/**
 * This class prompt a dialog box to the user.
 */
public class DialogBox {

    /**
     * Display dialog box task to ask the user for confirmation.
     * This is also use to create a new task by addind EditText element into the layout.
     *
     * @param activity the activity
     * @param view     the view
     * @param task     the task
     * @param newDay   the new day
     * @return the task
     */
    public Task displayDialogBox(Activity activity, ViewGroup view, Task task, Boolean newDay){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Your Task");

        View viewInflated = LayoutInflater.from(activity).inflate(R.layout.task_description_box, view, false); //Link the dialog box with the corresponding xml template

        /*
        Retrieve the useful edittext to later create the new task
         */
        EditText taskName = viewInflated.findViewById(R.id.taskName);
        EditText taskDuTime = viewInflated.findViewById(R.id.taskDuTime);
        EditText taskDescription = viewInflated.findViewById(R.id.taskDescription);

        if(!newDay){ //check if the dialog box is called from history or not
            taskName.setEnabled(false);
            taskDuTime.setEnabled(false);
            taskDescription.setEnabled(false);
        }

        if(task != null){ //add the previously created inputs if they exists. (new task or update one)
            taskName.setText(task.getName());
            taskDuTime.setText(task.getDuTime());
            taskDescription.setText(task.getDescription());
        }


        builder.setView(viewInflated);

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                if(newDay){ //retrieve the data from the EditTexts and add them to the task object
                    task.setName(taskName.getText().toString());
                    task.setDuTime(taskDuTime.getText().toString());
                    task.setDescription(taskDescription.getText().toString());
                    task.setDone(false);
                }

            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

        return null;
    }
}
