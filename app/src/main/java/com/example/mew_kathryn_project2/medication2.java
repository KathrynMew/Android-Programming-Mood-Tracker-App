package com.example.mew_kathryn_project2;

import android.content.Context;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.*;
import android.widget.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class medication2 extends Fragment {

    private OnMedicationTodayCheckedListener medListener;

    public medication2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_medication2, container, false);

        RadioGroup meds = rootView.findViewById(R.id.medicationToday);
        // Uncheck or reset the radio buttons initially
        meds.clearCheck();
        meds.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                String option = rb.getText().toString();

                if (checkedId == R.id.yesToday) {
                    medListener.onMedTodayClick(1);
                    Log.d("medsToday.Yes", "===== " + option);
                } else if (checkedId == R.id.noToday) {
                    medListener.onMedTodayClick(0);
                    Log.d("medsToday.No", "===== " + option);
                } else {
                    medListener.onMedTodayClick(-1);
                    Log.d("medsToday.NA", "===== " + option);
                }
            }
        });

        return rootView;
    }

    public interface OnMedicationTodayCheckedListener {
        void onMedTodayClick(int val);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof medication2.OnMedicationTodayCheckedListener) {
            medListener = (medication2.OnMedicationTodayCheckedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement medication2.OnMedicationTodayCheckedListener");
        }
    }
}