package netlab.fakturk.proximity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    TextView proximityText, lightText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(MainActivity.this, SensorService.class));
        String proximity, light;

        proximityText = (TextView) findViewById(R.id.proximity_textView);
        lightText = (TextView) findViewById(R.id.light_textView);


        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {

                float proximity = intent.getFloatExtra("PROXIMITY",-1);
                float light = intent.getFloatExtra("LIGHT",-1);
                if (proximity!=-1)
                {
                    proximityText.setText(String.valueOf(proximity));
                }
                if (light!=-1)
                {
                    lightText.setText(String.valueOf(light));
                }


            }
        }, new IntentFilter(SensorService.ACTION_SENSOR_BROADCAST));
    }
    @Override
    protected void onDestroy()
    {

        super.onDestroy();
        stopService(new Intent(this,SensorService.class));
    }
    @Override
    protected void onPause() {

        super.onPause();
        stopService(new Intent(this,SensorService.class));


    }

    @Override
    protected void onResume() {

        super.onResume();
        startService(new Intent(this, SensorService.class));
    }
}
