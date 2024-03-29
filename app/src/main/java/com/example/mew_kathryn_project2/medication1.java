package com.example.mew_kathryn_project2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.*;
import android.widget.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class medication1 extends Fragment {

    private OnMedicationCheckedListener clickListener;

    public medication1() {
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
        View rootView = inflater.inflate(R.layout.fragment_medication1, container, false);

        RadioGroup meds = rootView.findViewById(R.id.medicationsGroup);
        // Uncheck or reset the radio buttons initially
        meds.clearCheck();
        meds.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                /*SharedPreferences sp = getContext().getSharedPreferences("meds_today",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();*/
                String option = rb.getText().toString();

                if (checkedId == R.id.yesMeds) {
                    clickListener.onMedicationClick(1);
                    Log.d("meds.Yes", "===== " + option);
                } else if (checkedId == R.id.noMeds) {
                    clickListener.onMedicationClick(0);
                    Log.d("meds.No", "===== " + option);
                } else {
                    Log.d("meds.error", "===== crash");
                }
            }
        });

        return rootView;
    }

    public interface OnMedicationCheckedListener {
        void onMedicationClick(int val);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMedicationCheckedListener) {
            clickListener = (OnMedicationCheckedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement medication1.OnMedicationCheckedListener");
        }
    }
}