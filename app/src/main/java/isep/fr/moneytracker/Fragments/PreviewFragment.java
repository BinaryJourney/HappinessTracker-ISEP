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
        tvUltimatePurpose.setText(Integer.toString(5));
        tvPassion.setText(Integer.toString(20));
        tvPleasure.setText(Integer.toString(30));
        tvBoring.setText(Integer.toString(20));
        tvSadness.setText(Integer.toString(20));
        tvHell.setText(Integer.toString(5));

        pieChart.addPieSlice(
                new PieModel(
                        getResources().getString(R.string.pie_string_1),
                        Integer.parseInt(tvUltimatePurpose.getText().toString()),
                        getResources().getColor(R.color.red)));
        pieChart.addPieSlice(
                new PieModel(
                        getResources().getString(R.string.pie_string_2),
                        Integer.parseInt(tvPassion.getText().toString()),
                        getResources().getColor(R.color.red_neutral_1)));
        pieChart.addPieSlice(
                new PieModel(
                        getResources().getString(R.string.pie_string_3),
                        Integer.parseInt(tvPleasure.getText().toString()),
                        getResources().getColor(R.color.red_neutral_2)));
        pieChart.addPieSlice(
                new PieModel(
                        getResources().getString(R.string.pie_string_4),
                        Integer.parseInt(tvBoring.getText().toString()),
                        getResources().getColor(R.color.red_neutral_3)));
        pieChart.addPieSlice(
                new PieModel(
                        getResources().getString(R.string.pie_string_5),
                        Integer.parseInt(tvSadness.getText().toString()),
                        getResources().getColor(R.color.red_neutral_4)));
        pieChart.addPieSlice(
                new PieModel(
                        getResources().getString(R.string.pie_string_6),
                        Integer.parseInt(tvHell.getText().toString()),
                        getResources().getColor(R.color.red_neutral_5)));
        pieChart.startAnimation();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}