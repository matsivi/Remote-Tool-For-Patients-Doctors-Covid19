package com.e.covid19new;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link rantevou#newInstance} factory method to
 * create an instance of this fragment.
 */
public class rantevou extends Fragment {
    protected RecyclerView mRecyclerView;
    protected Adapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ArrayList<String> mDataset;
    Timer clock ;
    TimerTask clockTask;
    final Handler hand = new Handler();
    Context context;

    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public rantevou() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment rantevou.
     */
    // TODO: Rename and change types and number of parameters
    public static rantevou newInstance(String param1, String param2) {
        rantevou fragment = new rantevou();
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
        View rootview=inflater.inflate(R.layout.fragment_rantevou,container,false);
        initDataset();
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new Adapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);
        return rootview;
    }

    private void initDataset() {
        mDataset = new ArrayList<>();
        String s =((MainActivity)getActivity()).getDataReceived();

        String[] t= s.split("\\)");

        Log.e("Msg", String.valueOf(t.length));

        //String t= "[[('tslk','tlkk','12,26/05/2022')],[(tsjddkdklk,tlkjddjkk,12,26/15/2022)]]";
        try{
            s=s.replace("'","")
                    .replace("[","")
                    .replace("]","")
                    .replace("(","")
                    .replace(" ","");

            mDataset =   new ArrayList<>(Arrays.asList(s.split("\\),")));
        }catch (Exception e){
            mDataset.add("");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startClock();
    }

    public void startClock(){
        clock = new Timer();
        init();
        clock.schedule(clockTask,10000);
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