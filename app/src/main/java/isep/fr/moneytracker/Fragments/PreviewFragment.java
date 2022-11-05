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
import java.util.List;

import isep.fr.moneytracker.Objects.Day;
import isep.fr.moneytracker.Objects.History;
import isep.fr.moneytracker.R;
import isep.fr.moneytracker.databinding.FragmentPreviewBinding;

public class PreviewFragment extends Fragment {

    private FragmentPreviewBinding binding;
    private List<Day> dayList;
    private String selectedDateFilter = "All Time";

    TextView tvUltimatePurpose, tvPassion, tvPleasure, tvBoring, tvSadness, tvHell;
    PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPreviewBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    private void setData() {

        int counterUltimatePurpose = 0;
        int counterPassion = 0;
        int counterPleasure = 0;
        int counterBoring = 0;
        int counterSadness = 0;
        int counterHell = 0;

        History history = new History();
        try {
            history.getHistory(getActivity());
            dayList = history.getDayList();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] happinessLevels =  {"Hell", "Sadness", "Boring", "Pleasure", "Passion", "Ultimate Purpose"};

        if(dayList != null){
            for(Day day:dayList){
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

        tvUltimatePurpose.setText(Integer.toString(counterUltimatePurpose));
        tvPassion.setText(Integer.toString(counterPassion));
        tvPleasure.setText(Integer.toString(counterPleasure));
        tvBoring.setText(Integer.toString(counterBoring));
        tvSadness.setText(Integer.toString(counterSadness));
        tvHell.setText(Integer.toString(counterHell));

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

        tvUltimatePurpose = binding.tvUltimatePurpose;
        tvPassion = binding.tvPassion;
        tvPleasure = binding.tvPleasure;
        tvBoring = binding.tvBoring;
        tvSadness = binding.tvSadness;
        tvHell = binding.tvHell;
        pieChart = binding.piechart;

        Spinner spinnerHappinessStat = (Spinner)binding.spinnerHappinessStat;
        ArrayAdapter<String> spinnerTypeArrayAdapter = new ArrayAdapter<String>(
                getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.happinessDateFilterType));
        spinnerHappinessStat.setAdapter(spinnerTypeArrayAdapter);

        spinnerHappinessStat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                selectedDateFilter = parent.getItemAtPosition(position).toString();
                binding.happinessDataTitle.setText("Your " + selectedDateFilter + " Happiness Breakdown");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        setData();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}