package isep.fr.moneytracker.Adapters;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import isep.fr.moneytracker.Fragments.DayDescriptionFragment;
import isep.fr.moneytracker.Objects.Day;
import isep.fr.moneytracker.Objects.User;
import isep.fr.moneytracker.R;
import isep.fr.moneytracker.databinding.FragmentHistoryBinding;

public class ExpenseHistoryAdapter extends RecyclerView.Adapter<ExpenseHistoryAdapter.MyView> {

    private List<Day> dayList;
    private FragmentHistoryBinding binding;
    private Activity activity;
    private User user;
    private DayDescriptionFragment dayDescriptionFragment = new DayDescriptionFragment();

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView extends RecyclerView.ViewHolder {

        // Text View
        ConstraintLayout dayContainer;
        TextView daydate;
        TextView numberOfTasks;
        TextView happinessLevelText;
        ProgressBar happinessLevel;

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
    public ExpenseHistoryAdapter(ArrayList<Day> dayList, FragmentHistoryBinding binding, FragmentActivity activity, User user) {
        this.dayList = dayList;
        this.binding = binding;
        this.activity = activity;
        this.user = user;
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

        holder.happinessLevel.setMax(60);
        holder.happinessLevel.setProgress((int) dayList.get(position).getHappiness());

        holder.dayContainer.setOnClickListener(item -> {
            activity.getFragmentManager().beginTransaction().replace(R.id.container, dayDescriptionFragment).commit();
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
