package isep.fr.moneytracker.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import isep.fr.moneytracker.Fragments.DayDescriptionFragment;
import isep.fr.moneytracker.Fragments.HistoryFragment;
import isep.fr.moneytracker.Objects.Day;
import isep.fr.moneytracker.Objects.User;
import isep.fr.moneytracker.R;

public class DayHistoryAdapter extends RecyclerView.Adapter<DayHistoryAdapter.MyView> {

    private List<Day> dayList;
    private HistoryFragment binding;
    private DayDescriptionFragment dayDescriptionFragment = new DayDescriptionFragment();

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView extends RecyclerView.ViewHolder {

        // Text View
        ConstraintLayout dayContainer;
        TextView daydate;
        TextView numberOfTasks;
        TextView happinessLevelText;
        //ProgressBar happinessLevel;
        ImageView happinessLevel;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);
            dayContainer = itemView.findViewById(R.id.dayContainer);
            daydate = itemView.findViewById(R.id.dayDate);
            numberOfTasks = itemView.findViewById(R.id.numberOfTasks);
            happinessLevelText = itemView.findViewById(R.id.happinessLevelText);
            happinessLevel = itemView.findViewById(R.id.happinessLevel);
        }
    }

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
                                 final int position)
    {

        holder.daydate.setText(String.valueOf(dayList.get(position).getDate()));
        holder.numberOfTasks.setText("Tasks : "+String.valueOf(dayList.get(position).getTaskList().size()));
        holder.happinessLevelText.setText(String.valueOf(dayList.get(position).getHappiness()));

        //holder.happinessLevel.setMax(60);
        //holder.happinessLevel.setProgress((int) dayList.get(position).getHappiness());

        int[][] happinessLevelsValues = {
                {0, 10, R.drawable.hell},
                {10, 20, R.drawable.sadness},
                {20, 30, R.drawable.boring},
                {30, 40, R.drawable.pleasure},
                {40, 50, R.drawable.passion},
                {50, 60, R.drawable.ultimate_purpose}
        };

        for(int[] levelsValues:happinessLevelsValues){
            if(levelsValues[0] < dayList.get(position).getHappiness() && dayList.get(position).getHappiness() <= levelsValues[1]){
                holder.happinessLevel.setImageResource(levelsValues[2]);
            }
            else if(dayList.get(position).getHappiness() == 0){
                holder.happinessLevel.setImageResource(R.drawable.hell);
            }
        }

        holder.dayContainer.setOnClickListener(item -> {
            binding.displayDayInfos(position);
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
