package lapan.go.id.monitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class StartupActivity extends AppCompatActivity {

    TextView tvTempOrbital, tvHumOrbital, tvJamOrbital, tvTglOrbital,
            tvTempViasat, tvHumViasat, tvJamViasat, tvTglViasat,
            tvTempZodiac, tvHumZodiac, tvJamZodiac, tvTglZodiac,
            tvTempServer, tvHumServer, tvJamServer, tvTglServer;
    View btnAntenaOrbital, btnAntenaViasat, btnAntenaZodiac, btnServer;
    SimpleDateFormat jam = new SimpleDateFormat("HH:mm", Locale.getDefault());
    SimpleDateFormat tgl = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

    private static final String ID = "Monitoring";
    private static final String NAME_ORBITAL = "Antena Orbital";
    private static final String DESC_ORBITAL = "Suhu Antena Orbital";
    private static final String TEXT_ORBITAL = "Suhu Antena Orbital Melebihi Batas Maksimum !";


    private static final String NAME_VIASAT = "Antena Viasat";
    private static final String DESC_VIASAT = "Suhu Antena Viasat";
    private static final String TEXT_VIASAT = "Suhu Antena Viasat Melebihi Batas Maksimum !";

    private static final String NAME_ZODIAC = "Antena Zodiac";
    private static final String DESC_ZODIAC = "Suhu Antena Zodiac";
    private static final String TEXT_ZODIAC = "Suhu Antena Zodiac Melebihi Batas Maksimum !";

    private static final String NAME_SERVER = "Ruang Server";
    private static final String DESC_SERVER = "Suhu Ruang Server";
    private static final String TEXT_SERVER = "Suhu Ruang Server Melebihi Batas Maksimum !";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        init();
        onClick();

        orbital();
        viasat();
        zodiac();
        server();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.info_app: {
                Intent info_app = new Intent(StartupActivity.this, InfoApp.class);
                startActivity(info_app);
                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
                break;
            }
//            case R.id.change_theme: {
//                linear_layout_startup.setBackground(getResources().getDrawable(R.drawable.bg_gradient_blue_dark));
//                savePrefdata();
//                break;
//            }
//            case R.id.settings: {
//                Intent settings = new Intent(StartupActivity.this, SettingsActivity.class);
//                startActivity(settings);
//                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
//                finish();
//                break;
//            }
        }
        return true;
    }

    private void init() {
        btnAntenaOrbital = findViewById(R.id.btnAntenaOrbital);
        btnAntenaViasat = findViewById(R.id.btnAntenaViasat);
        btnAntenaZodiac = findViewById(R.id.btnAntenaZodiac);
        btnServer = findViewById(R.id.btnServer);

        tvHumOrbital = findViewById(R.id.tvHumOrbital);
        tvTempOrbital = findViewById(R.id.tvTempOrbital);
        tvJamOrbital = findViewById(R.id.tvJamOrbital);
        tvTglOrbital = findViewById(R.id.tvTglOrbital);

        tvHumViasat = findViewById(R.id.tvHumViasat);
        tvTempViasat = findViewById(R.id.tvTempViasat);
        tvJamViasat = findViewById(R.id.tvJamViasat);
        tvTglViasat = findViewById(R.id.tvTglViasat);

        tvHumZodiac = findViewById(R.id.tvHumZodiac);
        tvTempZodiac = findViewById(R.id.tvTempZodiac);
        tvJamZodiac = findViewById(R.id.tvJamZodiac);
        tvTglZodiac = findViewById(R.id.tvTglZodiac);

        tvHumServer = findViewById(R.id.tvHumServer);
        tvTempServer = findViewById(R.id.tvTempServer);
        tvJamServer = findViewById(R.id.tvJamServer);
        tvTglServer = findViewById(R.id.tvTglServer);
    }

    private void onClick() {
        btnAntenaOrbital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartupActivity.this, DetailOrbital.class));
                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            }
        });
        btnAntenaViasat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartupActivity.this, DetailViasat.class));
                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            }
        });
        btnAntenaZodiac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartupActivity.this, DetailZodiac.class));
                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            }
        });
        btnServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartupActivity.this, DetailServer.class));
                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            }
        });
    }

    private void orbital() {
        DataSuhu("Antena Orbital",1,tvTempOrbital,tvHumOrbital,tvJamOrbital,tvTglOrbital);
    }
    private void viasat() {
        DataSuhu("Antena Viasat",1,tvTempViasat,tvHumViasat,tvJamViasat,tvTglViasat);
    }
    private void zodiac() {
        DataSuhu("Antena Zodiac",1,tvTempZodiac,tvHumZodiac,tvJamZodiac,tvTglZodiac);
    }
    private void server() {
        DataSuhu("Ruang Server",1,tvTempServer,tvHumServer,tvJamServer,tvTglServer);
    }

    public void notif_orbital() {
        notif(DetailOrbital.class, NAME_ORBITAL, DESC_ORBITAL, TEXT_ORBITAL);
    }
    public void notif_viasat() {
        notif(DetailViasat.class, NAME_VIASAT, DESC_VIASAT, TEXT_VIASAT);
    }
    public void notif_zodiac() {
        notif(DetailZodiac.class, NAME_ZODIAC, DESC_ZODIAC, TEXT_ZODIAC);
    }
    public void notif_server() {
        notif(DetailServer.class, NAME_SERVER, DESC_SERVER, TEXT_SERVER);
    }

    public void DataSuhu(final String path, int limit, final TextView tvTemp,
                         final TextView tvHum, final TextView tvJam, final TextView tvTgl) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(path);
        Query query = dbRef.orderByKey().limitToLast(limit);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    String datatime = child.child("time").getValue().toString();
                    String datatemp = child.child("temperature").getValue().toString();
                    String datahum = child.child("humidity").getValue().toString();
                    long time = Long.parseLong(datatime);
                    int temp = Integer.parseInt(datatemp);
                    if (temp > 35){
                        if (path == "Antena Orbital"){
                            notif_orbital();
                        } else if (path == "Antena Viasat"){
                            notif_viasat();
                        } else if (path == "Antena Zodiac"){
                            notif_zodiac();
                        } else if (path == "Ruang Server"){
                            notif_server();
                        }
                        tvTemp.setTextColor(Color.rgb(255,60,0));
                    }
                    else {
                        tvTemp.setTextColor(Color.WHITE);
                    }
                    String dataJam = jam.format(time);
                    String dataTgl= tgl.format(time);
                    tvTemp.setText(datatemp);
                    tvHum.setText(datahum);
                    tvJam.setText(dataJam);
                    tvTgl.setText(dataTgl);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void notif(Class cls, String name, String desc, String text){
        Intent intent = new Intent(this,cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    ID, name,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(desc);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        long[] v = {500,1000};
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, ID)
                        .setSmallIcon(R.drawable.temp)
                        .setContentTitle(name)
                        .setContentText(text)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setVibrate(v)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, mBuilder.build());
    }
}
