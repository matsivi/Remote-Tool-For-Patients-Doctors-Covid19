package com.e.covid19new;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link qrScan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class qrScan extends Fragment {

    WebView wb ;
    private MainActivity main ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public qrScan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment qrScan.
     */
    // TODO: Rename and change types and number of parameters
    public static qrScan newInstance(String param1, String param2) {
        qrScan fragment = new qrScan();
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
        View rootview = inflater.inflate(R.layout.fragment_qr_scan,container,false);
        this.main=(MainActivity) getActivity();
        wb = (WebView) rootview.findViewById(R.id.webProg2);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setDomStorageEnabled(true);
        wb.getSettings().setAllowContentAccess(true);
        wb.getSettings().setMediaPlaybackRequiresUserGesture(false);
        wb.setWebChromeClient(new WebChromeClient(){
            public void onPermissionRequest(final PermissionRequest request) {
                request.grant(request.getResources());
            }
        });
        wb.loadUrl("https://app.digitalcovidcertchecker.gov.ie/");
        this.wb.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                wb.loadUrl(url);
                return true;
            }
        });

        Button button = (Button) rootview.findViewById(R.id.button12);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                main.fragmentChange("fragment_buttons");
            }
        });

        return rootview;
    }
}