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

public class DialogBox {

    public Task displayDialogBox(Activity activity, ViewGroup view, Task task, Boolean newDay){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Your Task");

        View viewInflated = LayoutInflater.from(activity).inflate(R.layout.task_description_box, view, false);

        EditText taskName = viewInflated.findViewById(R.id.taskName);
        EditText taskDuTime = viewInflated.findViewById(R.id.taskDuTime);
        EditText taskDescription = viewInflated.findViewById(R.id.taskDescription);

        if(!newDay){
            taskName.setEnabled(false);
            taskDuTime.setEnabled(false);
            taskDescription.setEnabled(false);
        }

        if(task != null){
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

                if(newDay){
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
