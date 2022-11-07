package isep.fr.moneytracker.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import isep.fr.moneytracker.Objects.Day;
import isep.fr.moneytracker.Objects.History;
import isep.fr.moneytracker.R;
import isep.fr.moneytracker.databinding.FragmentPreviewBinding;

/**
 * The Preview fragment is the main screen of the app. It will be the summary of the user's usage of the application. It is here that is define the circle chart pie and the textual breakdown of the user's happiness.
 */
public class PreviewFragment extends Fragment {

    private FragmentPreviewBinding binding;
    private List<Day> dayList;
    private String selectedDateFilter = "All Time";

    /**
     * The TextView ultimate purpose.
     */
    TextView tvUltimatePurpose,
    /**
     * The TextView passion.
     */
    tvPassion,
    /**
     * The TextView pleasure.
     */
    tvPleasure,
    /**
     * The TextView boring.
     */
    tvBoring,
    /**
     * The TextView sadness.
     */
    tvSadness,
    /**
     * The TextView hell.
     */
    tvHell;
    /**
     * The Pie chart.
     */
    PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPreviewBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    /**
     * This method is used to define the basic data of the fragment.
     * The app will retrieve the data from the history object and adapt it before displaying it.
     * @throws ParseException
     */
    private void setData() throws ParseException {

        int counterUltimatePurpose = 0;
        int counterPassion = 0;
        int counterPleasure = 0;
        int counterBoring = 0;
        int counterSadness = 0;
        int counterHell = 0;

        History history = new History();
        try {
            history.getHistory(getActivity()); //Retrieve all the Day object from history
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dayList = history.getDayList(); //add history to dayList Object = List<Day>

        String[] happinessLevels =  {"Hell", "Sadness", "Boring", "Pleasure", "Passion", "Ultimate Purpose"}; //Define every level of happiness

        /*
        This section is really important to define the date format of the Day but also to allow the user to add some filter on top of the displayed data.
         */
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //Set the wanted format of date.
        Date currentDate = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        Date minWeekDate = cal.getTime();
        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date minMonthDate = cal.getTime();
        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        Date minYearDate = cal.getTime();
        Date dateToProcess;
        List<Day> datesToRemove = new ArrayList<>(); //Define a list of Day that will be removed afterwards


        switch(selectedDateFilter) { //Apply the correct function depending of the selected filter : week, month, year, all time
            case "Week":
                for(Day day:dayList){ //check if each Day validates the condition, if not it will be removed afterwards.
                    dateToProcess = new SimpleDateFormat("dd/MM/yyyy").parse(day.getDate());
                    int result1 = dateToProcess.compareTo(currentDate);
                    int result2 = dateToProcess.compareTo(minWeekDate);
                    if(!(result1 <= 0 && result2 >= 0))
                    {
                        datesToRemove.add(day);
                    }
                }
                for(Day day:datesToRemove)
                {
                    dayList.remove(day);
                }
                break;
            case "Month":
                for(Day day:dayList) { //check if each Day validates the condition, if not it will be removed afterwards.
                    dateToProcess = new SimpleDateFormat("dd/MM/yyyy").parse(day.getDate());
                    int result1 = dateToProcess.compareTo(currentDate);
                    int result2 = dateToProcess.compareTo(minMonthDate);
                    if(!(result1 <= 0 && result2 >= 0))
                    {
                        datesToRemove.add(day);
                    }
                }
                for(Day day:datesToRemove)
                {
                    dayList.remove(day);
                }
                break;
            case "Year":
                for(Day day:dayList) { //check if each Day validates the condition, if not it will be removed afterwards.
                    dateToProcess = new SimpleDateFormat("dd/MM/yyyy").parse(day.getDate());
                    int result1 = dateToProcess.compareTo(currentDate);
                    int result2 = dateToProcess.compareTo(minYearDate);
                    if(!(result1 <= 0 && result2 >= 0))
                    {
                        datesToRemove.add(day);
                    }
                }
                for(Day day:datesToRemove)
                {
                    dayList.remove(day);
                }
                break;
            default:
                //If all-time filter is selected the app should be doing not adaptation on the list.
                break;
        }

        if(dayList != null){ //Check if the DayList is null, it could happen if the user didn't add any day at all.
            for(Day day:dayList){ //Count the number of occurrence of each happiness level
                int happinessLevelValue = ((int) Math.round(day.getHappiness())/10);
                if(happinessLevelValue == 6)
                    happinessLevelValue--;

                switch (happinessLevels[happinessLevelValue]){
                    case "Hell":
                        counterHell++;
                        break;

                    case "Sadness":
                        counterSadness++;
                        break;

                    case "Boring":
                        counterBoring++;
                        break;

                    case "Pleasure":
                        counterPleasure++;
                        break;

                    case "Passion":
                        counterPassion++;
                        break;

                    case "Ultimate Purpose":
                        counterUltimatePurpose++;
                        break;

                    default:
                        break;
                }
            }
        }

        /*
        Set the TextView value based of the number of occurrence of each level of happiness.
         */
        tvUltimatePurpose.setText(Integer.toString(counterUltimatePurpose));
        tvPassion.setText(Integer.toString(counterPassion));
        tvPleasure.setText(Integer.toString(counterPleasure));
        tvBoring.setText(Integer.toString(counterBoring));
        tvSadness.setText(Integer.toString(counterSadness));
        tvHell.setText(Integer.toString(counterHell));

        //Add the values to the pieChart to convert the numerical data to a beautiful circle chart
        pieChart.clearChart(); //Clear the chart of the previous data before changing it.
        pieChart.addPieSlice(
                new PieModel(
                        getResources().getString(R.string.pie_string_1),
                        counterUltimatePurpose,
                        getResources().getColor(R.color.red_neutral_5)));
        pieChart.addPieSlice(
                new PieModel(
                        getResources().getString(R.string.pie_string_2),
                        counterPassion,
                        getResources().getColor(R.color.red_neutral_4)));
        pieChart.addPieSlice(
                new PieModel(
                        getResources().getString(R.string.pie_string_3),
                        counterPleasure,
                        getResources().getColor(R.color.red_neutral_3)));
        pieChart.addPieSlice(
                new PieModel(
                        getResources().getString(R.string.pie_string_4),
                        counterBoring,
                        getResources().getColor(R.color.red_neutral_2)));
        pieChart.addPieSlice(
                new PieModel(
                        getResources().getString(R.string.pie_string_5),
                        counterSadness,
                        getResources().getColor(R.color.red_neutral_1)));
        pieChart.addPieSlice(
                new PieModel(
                        getResources().getString(R.string.pie_string_6),
                        counterHell,
                        getResources().getColor(R.color.red)));

        pieChart.startAnimation();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        Define all the useful TextView in the fragment
         */
        tvUltimatePurpose = binding.tvUltimatePurpose;
        tvPassion = binding.tvPassion;
        tvPleasure = binding.tvPleasure;
        tvBoring = binding.tvBoring;
        tvSadness = binding.tvSadness;
        tvHell = binding.tvHell;
        pieChart = binding.piechart;

        //Define the filter spinner, to allow the user to filter his data.
        Spinner spinnerHappinessStat = (Spinner)binding.spinnerHappinessStat;
        ArrayAdapter<String> spinnerTypeArrayAdapter = new ArrayAdapter<String>(
                getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.happinessDateFilterType));
        spinnerHappinessStat.setAdapter(spinnerTypeArrayAdapter);

        /*
        Create behavior based of the selection of a filter.
         */
        spinnerHappinessStat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                selectedDateFilter = parent.getItemAtPosition(position).toString(); //Retrieve the selected filter value.
                binding.happinessDataTitle.setText("Your " + selectedDateFilter + " Happiness Breakdown");
                try {
                    setData(); //Update the data based of the selected filter.
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        try {
            setData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}