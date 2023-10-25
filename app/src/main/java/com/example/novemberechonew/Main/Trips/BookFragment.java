package com.example.novemberechonew.Main.Trips;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.novemberechonew.R;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.nio.file.attribute.AclEntry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookFragment extends Fragment {

    Button adult_ic, adult_dec, child_ic, child_dec, btn_dateFrom, btn_dateTo;
    TextView adult_counter_view, child_counter_view;
    ImageView counterImage;
    RadioGroup radioGroup;


    private int adultCount = 1;
    private int childCount = 0;
    private String setDateFrom, setDateTo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        adult_ic = view.findViewById(R.id.book_adult_add);
        adult_counter_view = view.findViewById(R.id.book_adult_counter);
        adult_dec = view.findViewById(R.id.book_adult_dec);
        child_ic = view.findViewById(R.id.book_child_add);
        child_counter_view = view.findViewById(R.id.book_child_counter);
        child_dec = view.findViewById(R.id.book_child_dec);
        counterImage = view.findViewById(R.id.book_card2_image);
        radioGroup = view.findViewById(R.id.book_radio_group);
        btn_dateFrom = view.findViewById(R.id.book_from);
        btn_dateTo = view.findViewById(R.id.book_to);

        // >>>> COUNTER <<<<
        updateCountersAndButtons();
        updateCounterImage();
        adult_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adultCount < 5) {
                    adultCount++;
                    updateCountersAndButtons();
                    updateCounterImage();
                }
            }
        });
        adult_dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adultCount > 1) {
                    adultCount--;
                    updateCountersAndButtons();
                    updateCounterImage();
                }
            }
        });
        child_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childCount < 4) {
                    childCount++;
                    updateCountersAndButtons();
                    updateCounterImage();
                }
            }
        });

        child_dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childCount > 0) {
                    childCount--;
                    updateCountersAndButtons();
                    updateCounterImage();
                }
            }
        });

        // >>>> RADIO <<<<
        btn_dateTo.setEnabled(false);
        btn_dateTo.setTextColor(ContextCompat.getColor(requireContext(), R.color.PiperRed));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = view.findViewById(checkedId);
                if (selectedRadioButton != null) {
                    if (selectedRadioButton.getId() == R.id.book_radio_round) {
                        btn_dateTo.setEnabled(true);
                        btn_dateTo.setTextColor(ContextCompat.getColor(requireContext(), R.color.PiperWhite));
                    } else {
                        btn_dateTo.setEnabled(false);
                        btn_dateTo.setTextColor(ContextCompat.getColor(requireContext(), R.color.PiperRed));
                    }
                }
            }
        });

        btn_dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointForward.now());
                MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("DEPARTURE DATE")
                        .setTheme(R.style.MaterialCalendarTheme)
                        .setCalendarConstraints(constraintsBuilder.build())
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        setDateFrom = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date(selection));
                        btn_dateFrom.setText(setDateFrom);
                        //DateTimeHandler.setDate(setDate);
                    }
                });
                materialDatePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        btn_dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointForward.now());

                MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("ARRIVAL DATE")
                        .setTheme(R.style.MaterialCalendarTheme)
                        .setCalendarConstraints(constraintsBuilder.build())
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        setDateTo = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date(selection));
                        btn_dateTo.setText(setDateTo);
                        //DateTimeHandler.setDate(setDate);
                    }
                });
                materialDatePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });


        return view;
    }

    private void updateCountersAndButtons() {
        adult_counter_view.setText(String.valueOf(adultCount));
        child_counter_view.setText(String.valueOf(childCount));

        if (adultCount == 1) {
            adult_dec.setEnabled(false);
            adult_dec.setTextColor(ContextCompat.getColor(requireContext(), R.color.PiperRed));
        } else {
            adult_dec.setEnabled(true);
            adult_dec.setTextColor(ContextCompat.getColor(requireContext(), R.color.PiperWhite));
        }

        if (adultCount == 5) {
            adult_ic.setEnabled(false);
            adult_ic.setTextColor(ContextCompat.getColor(requireContext(), R.color.PiperRed));
        } else {
            adult_ic.setEnabled(true);
            adult_ic.setTextColor(ContextCompat.getColor(requireContext(), R.color.PiperWhite));
        }

        if (childCount > 0) {
            //adult_ic.setEnabled(adultCount <= 4);
            child_dec.setEnabled(true);
            child_dec.setTextColor(ContextCompat.getColor(requireContext(), R.color.PiperWhite));
        } else {
            //adult_ic.setEnabled(adultCount <= 5);
            child_dec.setEnabled(false);
            child_dec.setTextColor(ContextCompat.getColor(requireContext(), R.color.PiperRed));
        }

        if (childCount == 4) {
            //adult_ic.setEnabled(adultCount <= 4);
            child_ic.setEnabled(false);
            child_ic.setTextColor(ContextCompat.getColor(requireContext(), R.color.PiperRed));

        } else {
            //adult_ic.setEnabled(adultCount <= 5);
            child_ic.setEnabled(true);
            child_ic.setTextColor(ContextCompat.getColor(requireContext(), R.color.PiperWhite));
        }

        adult_ic.setEnabled(adultCount < 5);
        child_ic.setEnabled(childCount < 4);
    }

    private void updateCounterImage() {
        if (adultCount == 1 && childCount == 0) {
            counterImage.setImageResource(R.drawable.x1a0c);
        } else if (adultCount == 1 && childCount == 1) {
            counterImage.setImageResource(R.drawable.x1a1c);
        } else if (adultCount == 1 && childCount == 2) {
            counterImage.setImageResource(R.drawable.x1a2c);
        } else if (adultCount == 1 && childCount == 3) {
            counterImage.setImageResource(R.drawable.x1a3c);
        } else if (adultCount == 1 && childCount == 4) {
            counterImage.setImageResource(R.drawable.x1a4c);
        } else if (adultCount == 1 && childCount == 5) {
            counterImage.setImageResource(R.drawable.x1a5c);
        } else if (adultCount == 2 && childCount == 0) {
            counterImage.setImageResource(R.drawable.x2a0c);
        } else if (adultCount == 2 && childCount == 1) {
            counterImage.setImageResource(R.drawable.x2a1c);
        } else if (adultCount == 2 && childCount == 2) {
            counterImage.setImageResource(R.drawable.x2a2c);
        } else if (adultCount == 2 && childCount == 3) {
            counterImage.setImageResource(R.drawable.x2a3c);
        } else if (adultCount == 2 && childCount == 4) {
            counterImage.setImageResource(R.drawable.x2a4c);
        } else if (adultCount == 2 && childCount == 5) {
            counterImage.setImageResource(R.drawable.x2a5c);
        } else if (adultCount == 3 && childCount == 0) {
            counterImage.setImageResource(R.drawable.x3a0c);
        } else if (adultCount == 3 && childCount == 1) {
            counterImage.setImageResource(R.drawable.x3a1c);
        } else if (adultCount == 3 && childCount == 2) {
            counterImage.setImageResource(R.drawable.x3a2c);
        } else if (adultCount == 3 && childCount == 3) {
            counterImage.setImageResource(R.drawable.x3a3c);
        } else if (adultCount == 3 && childCount == 4) {
            counterImage.setImageResource(R.drawable.x3a4c);
        } else if (adultCount == 3 && childCount == 5) {
            counterImage.setImageResource(R.drawable.x3a5c);
        } else if (adultCount == 4 && childCount == 0) {
            counterImage.setImageResource(R.drawable.x4a0c);
        } else if (adultCount == 4 && childCount == 1) {
            counterImage.setImageResource(R.drawable.x4a1c);
        } else if (adultCount == 4 && childCount == 2) {
            counterImage.setImageResource(R.drawable.x4a2c);
        } else if (adultCount == 4 && childCount == 3) {
            counterImage.setImageResource(R.drawable.x4a3c);
        } else if (adultCount == 4 && childCount == 4) {
            counterImage.setImageResource(R.drawable.x4a4c);
        } else if (adultCount == 4 && childCount == 5) {
            counterImage.setImageResource(R.drawable.x4a5c);
        } else if (adultCount == 5 && childCount == 0) {
            counterImage.setImageResource(R.drawable.x5a0c);
        } else if (adultCount == 5 && childCount == 1) {
            counterImage.setImageResource(R.drawable.x5a1c);
        } else if (adultCount == 5 && childCount == 2) {
            counterImage.setImageResource(R.drawable.x5a2c);
        } else if (adultCount == 5 && childCount == 3) {
            counterImage.setImageResource(R.drawable.x5a3c);
        } else if (adultCount == 5 && childCount == 4) {
            counterImage.setImageResource(R.drawable.x5a4c);
        } else {
            counterImage.setImageResource(R.drawable.x5a4c);
        }
    }
}
