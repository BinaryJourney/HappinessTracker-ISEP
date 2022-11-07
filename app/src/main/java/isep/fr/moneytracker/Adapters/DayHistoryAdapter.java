package isep.fr.moneytracker.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import isep.fr.moneytracker.Fragments.DayDescriptionFragment;
import isep.fr.moneytracker.Fragments.HistoryFragment;
import isep.fr.moneytracker.Objects.Day;
import isep.fr.moneytracker.Objects.User;
import isep.fr.moneytracker.R;

/**
 * This adapter is used to define the design and the behavior of the recyclerView element
 */
public class DayHistoryAdapter extends RecyclerView.Adapter<DayHistoryAdapter.MyView> {

    private List<Day> dayList;
    private HistoryFragment binding;
    private DayDescriptionFragment dayDescriptionFragment = new DayDescriptionFragment();

    /**
     * The class MyView is used to define the element of the xml layout item.
     */
// View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView extends RecyclerView.ViewHolder {

        /**
         * The Day container.
         */
        ConstraintLayout dayContainer;
        /**
         * The Day date.
         */
        TextView daydate;
        /**
         * The Number of tasks.
         */
        TextView numberOfTasks;
        /**
         * The Happiness level text.
         */
        TextView happinessLevelText;
        /**
         * The Happiness level.
         */
        ImageView happinessLevel;

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
            dayContainer = itemView.findViewById(R.id.dayContainer);
            daydate = itemView.findViewById(R.id.dayDate);
            numberOfTasks = itemView.findViewById(R.id.numberOfTasks);
            happinessLevelText = itemView.findViewById(R.id.happinessLevelText);
            happinessLevel = itemView.findViewById(R.id.happinessLevel);
        }
    }

    /**
     * Instantiates a new Day history adapter.
     *
     * @param dayList the day list
     * @param binding the binding
     */
// Constructor for adapter class
    // which takes a list of String type
    public DayHistoryAdapter(List<Day> dayList, HistoryFragment binding) {
        this.dayList = dayList;
        this.binding = binding;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType)
    {

        // Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);

        // return itemView
        return new MyView(itemView);
    }

    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @Override
    public void onBindViewHolder(final MyView holder,
                                 @SuppressLint("RecyclerView") final int position)
    {

        //replace the static text value with the data from the selected day.
        holder.daydate.setText(String.valueOf(dayList.get(position).getDate()));
        holder.numberOfTasks.setText("Tasks : "+String.valueOf(dayList.get(position).getTaskList().size()));
        holder.happinessLevelText.setText(String.valueOf((int) dayList.get(position).getHappiness())+"/60");


        //Define the happiness levels values and associated image
        int[][] happinessLevelsValues = {
                {0, 10, R.drawable.hell},
                {10, 20, R.drawable.sadness},
                {20, 30, R.drawable.boring},
                {30, 40, R.drawable.pleasure},
                {40, 50, R.drawable.passion},
                {50, 60, R.drawable.ultimate_purpose}
        };

        //Display the correct source image based on the happiness level of the day.
        for(int[] levelsValues:happinessLevelsValues){
            if(levelsValues[0] < dayList.get(position).getHappiness() && dayList.get(position).getHappiness() <= levelsValues[1]){ //check the level of happiness of the day
                holder.happinessLevel.setImageResource(levelsValues[2]);
            }
            else if(dayList.get(position).getHappiness() == 0){
                holder.happinessLevel.setImageResource(R.drawable.hell);
            }
        }

        //Set listener to switch fragment if the user click on it -> see Historyfragment
        holder.dayContainer.setOnClickListener(item -> {
            binding.displayDayInfos(position);
        });

        //Define a specific behavior if the user decide to longPress on an item of the recyclerView
        holder.dayContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //This alert box will ask user for confirmation before deleting the Day
                AlertDialog.Builder builder = new AlertDialog.Builder(binding.getActivity());
                builder.setTitle("Delete this day ?");
                builder.setMessage("Are you sure that you want to delete this day ? You won't be able to get it back.");

                // Set up the buttons
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        binding.deleteDayInList(position);
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

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount()
    {
        return dayList.size();
    }

}
