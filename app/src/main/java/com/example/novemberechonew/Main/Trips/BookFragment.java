package com.example.novemberechonew.Main.Trips;


import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novemberechonew.Backend.DBHelper;
import com.example.novemberechonew.Backend.DBManager;
import com.example.novemberechonew.Backend.VariableManager;
import com.example.novemberechonew.R;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.errorprone.annotations.Var;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BookFragment extends Fragment {

    Button adult_ic, adult_dec, child_ic, child_dec, btn_dateFrom, btn_dateTo, search;
    TextView adult_counter_view, child_counter_view, departFrom_view, arriveIn_view;
    ImageView counterImage;
    RadioGroup radioGroup;


    private int adultCount = 1;
    private int childCount = 0;
    private String setDateFrom, setDateTo;
    private String cityFrom, cityTo;
    ArrayList<String> from_city_list;
    ArrayList<String> to_city_list;
    Dialog dCity_dialog, aCity_dialog;
    private static final int DELAY_MILLIS = 2000; // 5 seconds

    DBManager dbManager;
    AlertDialog alertDialog;

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
        departFrom_view = view.findViewById(R.id.book_depart_from);
        arriveIn_view = view.findViewById(R.id.book_arrive_in);
        search = view.findViewById(R.id.book_search_button);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                builder.setView(R.layout.progress_layout);
                AlertDialog dialog = builder.create();
                dialog.show();
                Log.e(TAG, "Cities " + cityFrom + " " + cityTo);
                if (cityFrom == null || cityTo == null) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Where do you wanna fly to?", Toast.LENGTH_SHORT).show();
                } else if (setDateFrom == null) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "When do you wanna fly to?", Toast.LENGTH_SHORT).show();
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Cursor cursor2 = dbManager.DB_fetchFlightByDestinationOrigin(cityFrom, cityTo);
                            Log.e(TAG, "Number of rows in the cursor: " + cursor2.getCount());
                            if (cursor2.getCount() == 0) {
                                dialog.dismiss();
                                alertDialog = new AlertDialog.Builder(getContext())
                                        .setTitle("No direct flights found!")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                alertDialog.dismiss();
                                            }
                                        })
                                        .create();
                                alertDialog.show();
                            } else {
                                dialog.dismiss();
                                VariableManager.setDB_cityFrom(cityFrom);
                                VariableManager.setDB_cityTo(cityTo);
                                VariableManager.setDB_adults(String.valueOf(adult_counter_view.getText()));
                                VariableManager.setDB_child((String) child_counter_view.getText());
                                VariableManager.setDB_dateFrom(setDateFrom);
                                VariableManager.setDB_dateTo(setDateTo);

                                SearchFragment searchFragment = new SearchFragment();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.accounts_framelayout, searchFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        }
                    }, DELAY_MILLIS);
                }
            }
        });


        dbManager = new DBManager(getContext());
        try {
            dbManager.open();
        } catch (
                Exception e) {
            e.printStackTrace();
        }

        from_city_list = new ArrayList<>();
        to_city_list = new ArrayList<>();

        Cursor cursor = dbManager.DB_fetchAirport();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String ICAO = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.AIRPORT_ICAO));
                String city = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.AIRPORT_CITY));
                //from_city_list.add("[" + ICAO + "] " + city);
                //to_city_list.add("[" + ICAO + "] " + city);
                from_city_list.add(city);
                to_city_list.add(city);
            } while (cursor.moveToNext());
        }


            /*from_city_list.add("HKG Hong Kong");
            from_city_list.add("NYC Ney York");
            from_city_list.add("TKH Tokyo");
            from_city_list.add("SGP Singapore");


            to_city_list.add("HKG Hong Kong");
            to_city_list.add("NYC Ney York");
            to_city_list.add("TKH Tokyo");
            to_city_list.add("SGP Singapore");*/

        departFrom_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dCity_dialog = new Dialog(getContext());
                dCity_dialog.setContentView(R.layout.z_spinner_from);
                dCity_dialog.getWindow().setLayout(650, 800);
                dCity_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dCity_dialog.show();

                EditText editText = dCity_dialog.findViewById(R.id.edit_text);
                ListView listView = dCity_dialog.findViewById(R.id.list_view);

                ArrayAdapter<String> d_adapter = new ArrayAdapter<>(getContext(), R.layout.z_list_item, from_city_list);
                listView.setAdapter(d_adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        d_adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (d_adapter.getItem(position).equalsIgnoreCase(cityTo)) {
                            Toast.makeText(getContext(), "Cannot be same as arrival city!",
                                    Toast.LENGTH_SHORT).show();
                            dCity_dialog.dismiss();
                        } else {
                            cityFrom = d_adapter.getItem(position);
                            departFrom_view.setText(cityFrom);
                            dCity_dialog.dismiss();
                        }

                    }
                });
            }
        });

        arriveIn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aCity_dialog = new Dialog(getContext());
                aCity_dialog.setContentView(R.layout.z_spinner_to);
                aCity_dialog.getWindow().setLayout(650, 800);
                aCity_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                aCity_dialog.show();

                EditText editText = aCity_dialog.findViewById(R.id.edit_text);
                ListView listView = aCity_dialog.findViewById(R.id.list_view);

                ArrayAdapter<String> a_adapter = new ArrayAdapter<>(getContext(), R.layout.z_list_item, from_city_list);
                listView.setAdapter(a_adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        a_adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (a_adapter.getItem(position).equalsIgnoreCase(cityFrom)) {
                            Toast.makeText(getContext(), "Cannot be same as departure city!",
                                    Toast.LENGTH_SHORT).show();
                            aCity_dialog.dismiss();
                        } else {
                            cityTo = a_adapter.getItem(position);
                            arriveIn_view.setText(cityTo);
                            aCity_dialog.dismiss();
                        }

                    }
                });
            }
        });

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
        btn_dateTo.setTextColor(ContextCompat.getColor(

                requireContext(), R.color.PiperRed));
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
