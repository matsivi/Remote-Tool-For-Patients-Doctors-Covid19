package com.e.covid19new;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link draseis#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class draseis extends Fragment  {
    private MainActivity maTa ;
    Timer clock ;
    TimerTask clockTask;
    final Handler hand = new Handler();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment draseis.
     */
    // TODO: Rename and change types and number of parameters
    public static draseis newInstance(String param1, String param2) {
        draseis fragment = new draseis();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public draseis() {
        // Required empty public constructor
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

        View rootview = inflater.inflate(R.layout.fragment_drasi,container,false);
        this. maTa=(MainActivity) getActivity();
        Button button1 = (Button) rootview.findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               maTa.sayTxtDrasi();
            }
        });

        Button button2 = (Button) rootview.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               maTa.sayTxtDrasi2();
            }
        });

        Button button3 = (Button) rootview.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               maTa.sayTxtDrasi3();
            }
        });

        Button button4 = (Button) rootview.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                maTa.sayTxtDrasi4();
            }
        });

        // Inflate the layout for this fragment
        return rootview;
    }




   public void onResume() {
        super.onResume();
        startClock();
    }

    public void startClock(){

        clock = new Timer();
        init();
        clock.schedule(clockTask,30000);

    }

    public void stopTimerTask(){
        if(clock!=null){
            clock.cancel();
            clock=null;
        }


    }

    public void init(){
        clockTask = new TimerTask() {
            @Override
            public void run() {
                hand.post(new Runnable() {
                    @Override
                    public void run() {
                        stopTimerTask();
                        buttons mFragment = new buttons();
                        FragmentManager fr=getActivity().getSupportFragmentManager();
                        fr.beginTransaction().replace(R.id.btn_container,mFragment).commit();

                    }
                });
            }
        };

    }




}