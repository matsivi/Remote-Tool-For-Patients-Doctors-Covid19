package com.e.covid19new;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import java.net.ServerSocket;
import java.util.Locale;


public class MainActivity extends AppCompatActivity  {
    private int counter=0;
    Context context;
    private String dataReceived;
    public String getDataReceived(){
        return dataReceived;
    }
    private Priority priority;
    private buttons butt;
    private draseis dras;
    private rantevou rant;
    private embolio emvolio;
    private qrScan qrscan;
    private worldMap worldmap;

    TextToSpeech txs;
    String pat = "Επόμενος ασθενής";
    String dras1 = "Ο κόβιντ 19 είναι μια μολυσματική ασθένεια. Η ασθένεια καταγράφηκε για πρώτη φορά στην πόλη Γουχάν της Κίνας στα τέλη του 2019 και έγινε γνωστή στον Παγκόσμιο οργανισμό υγείας στις 31 Δεκεμβρίου 2019. Από τότε έχει διασπαρεί σε όλον τον πλανήτη και έχει εξελιχθεί σε πανδημία, η οποία βρίσκεται εν εξελίξει μέχρι και σήμερα.";
    String dras2 = "Αυτη ειναι η δευτερη ενημέρωση";
    String dras3 = "Αυτη ειναι η τριτη ενημέρωση";
    String dras4 = "Αυτη ειναι η τέταρτη ενημέρωση";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.

        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        context=this;
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcast,new IntentFilter("customEvent"));
        priority = new Priority();
        dras= new draseis();
        butt = new buttons();
        rant = new rantevou();
        emvolio = new embolio();
        qrscan = new qrScan();
        worldmap = new worldMap();
        getSupportFragmentManager().beginTransaction().add(R.id.container, priority).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.btn_container, butt).commit();

        txs = new TextToSpeech(getApplicationContext(), i -> {

            // if No error is found then only it will run
            if(i!=TextToSpeech.ERROR){
                // To Choose language of speech
                txs.setLanguage(Locale.ROOT);
            }
        });

    }

    private BroadcastReceiver broadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String tx = intent.getStringExtra("action");

            if (tx.equals("next")){
                counter+=1;
                try{
                    TextView tx2 = (TextView) findViewById(R.id.textView3);
                    tx2.setText(String.valueOf(counter));
                }catch (Exception e){

                }


                txs.speak(pat,TextToSpeech.QUEUE_FLUSH,null,"txtTest");

            }else {
                dataReceived=tx;
            }
        }
    };


    public void sayTxtDrasi(){
        txs.speak(dras1,TextToSpeech.QUEUE_FLUSH,null,"txtDras1");
    }

    public void sayTxtDrasi2(){
        txs.speak(dras2,TextToSpeech.QUEUE_FLUSH,null,"txtDras2");
    }

    public void sayTxtDrasi3(){
        txs.speak(dras3,TextToSpeech.QUEUE_FLUSH,null,"txtDras3");
    }

    public void sayTxtDrasi4(){
        txs.speak(dras4,TextToSpeech.QUEUE_FLUSH,null,"txtDras3");
    }


    public void fragmentChange(String frag){
        Thread th = new Thread(() -> {
            if(frag.equals("fragment_buttons")){
                getSupportFragmentManager().beginTransaction().replace(R.id.btn_container,butt).commit();
            }
            else if(frag.equals("fragment_draseis")){
                getSupportFragmentManager().beginTransaction().replace(R.id.btn_container,dras).commit();
            }
            else if(frag.equals("fragment_rantevou")){
                getSupportFragmentManager().beginTransaction().replace(R.id.btn_container,rant).commit();
            }
            else if(frag.equals("fragment_emvolio")){
                getSupportFragmentManager().beginTransaction().replace(R.id.btn_container,emvolio).commit();
            }
            else if(frag.equals("fragment_qr")){
                getSupportFragmentManager().beginTransaction().replace(R.id.btn_container,qrscan).commit();
            }
            else if(frag.equals("fragment_world_map")){
                getSupportFragmentManager().beginTransaction().replace(R.id.btn_container,worldmap).commit();
            }
        });
        th.start();
    }




@Override
protected void onStart(){
        super.onStart();
    Intent startServer = new Intent(MainActivity.this, Server.class);
    startServer.setAction(Server.START_SERVER);
    startService(startServer);

}

    @Override
    protected void onDestroy() {
        // Unregister the RobotLifecycleCallbacks for this Activity.

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcast);
        super.onDestroy();
    }
}
