package isep.fr.moneytracker.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import isep.fr.moneytracker.R;
import isep.fr.moneytracker.databinding.FragmentPreviewBinding;

public class PreviewFragment extends Fragment {

    private FragmentPreviewBinding binding;

    TextView tvUltimatePurpose, tvPassion, tvPleasure, tvBoring, tvSadness, tvHell;
    PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        tvUltimatePurpose = binding.tvUltimatePurpose;
//        tvPassion = binding.tvPassion;
//        tvPleasure = binding.tvPleasure;
//        tvBoring = binding.tvBoring;
//        tvSadness = binding.tvSadness;
//        tvHell = binding.tvBoring;
//        pieChart = binding.piechart;
//        setData();

        binding = FragmentPreviewBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    private void setData() {

        int counterUltimatePurpose = 50;
        int counterPassion = 25;
        int counterPleasure = 25;
        int counterBoring = 25;
        int counterSadness = 25;
        int counterHell = 25;

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
        setData();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}