package com.e.covid19new;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;


import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link buttons#newInstance} factory method to
 * create an instance of this fragment.
 */
public class buttons extends Fragment  {
    private MainActivity main ;
    TextToSpeech textToSpeech;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public buttons() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment buttons.
     */
    // TODO: Rename and change types and number of parameters
    public static buttons newInstance(String param1, String param2) {
        buttons fragment = new buttons();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_buttons,container,false);
        this.main=(MainActivity) getActivity();

        Button button = (Button) rootview.findViewById(R.id.button7);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                main.fragmentChange("fragment_rantevou");
            }
        });

        Button button2 = (Button) rootview.findViewById(R.id.button10);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               main.fragmentChange("fragment_draseis");
            }
        });

        Button button3 = (Button) rootview.findViewById(R.id.button9);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                main.fragmentChange("fragment_emvolio");
            }
        });

        Button button4 = (Button) rootview.findViewById(R.id.button5);
        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                main.fragmentChange("fragment_qr");
            }
        });

        Button button5 = (Button) rootview.findViewById(R.id.button8);
        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                main.fragmentChange("fragment_world_map");
            }
        });

        textToSpeech = new TextToSpeech(main.getApplicationContext(), i -> {

            // if No error is found then only it will run
            if(i!=TextToSpeech.ERROR){
                // To Choose language of speech
                textToSpeech.setLanguage(Locale.ROOT);
            }
        });


        return rootview;
    }



}