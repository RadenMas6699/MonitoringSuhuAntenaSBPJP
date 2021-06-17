package lapan.go.id.monitoring;

import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetailSuhu {
    SimpleDateFormat jam = new SimpleDateFormat("HH:mm", Locale.getDefault());
    SimpleDateFormat tgl = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

    DatabaseReference dbRef;
    String path;
    Query query;

    int limit;
    TextView tvTemp, tvHum, tvJam, tvTgl, tvKondisi;

    public DetailSuhu() {
    }

    public DetailSuhu(String path, int limit, final TextView tvTemp, final TextView tvHum,
                      final TextView tvJam, final TextView tvTgl, final TextView tvKondisi) {
        this.path = path;
        this.query = query;
        this.limit = limit;
        this.tvTemp = tvTemp;
        this.tvHum = tvHum;
        this.tvJam = tvJam;
        this.tvTgl = tvTgl;
        this.tvKondisi = tvKondisi;

        dbRef = FirebaseDatabase.getInstance().getReference(path);
        query = dbRef.orderByKey().limitToLast(limit);
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
                        tvKondisi.setText(R.string.hot);
                        tvTemp.setTextColor(Color.rgb(255,60,0));
                        tvKondisi.setTextColor(Color.rgb(255,60,0));
                    }
                    else if (temp >= 20 && temp <= 35){
                        tvKondisi.setText(R.string.normal);
                        tvTemp.setTextColor(Color.WHITE);
                        tvKondisi.setTextColor(Color.WHITE);
                    }
                    else if (temp < 20){
                        tvKondisi.setText(R.string.cold);
                        tvTemp.setTextColor(Color.rgb(41,98,255));
                        tvKondisi.setTextColor(Color.rgb(41,98,255));
                    }
                    else {
                        tvKondisi.setText(R.string.strip);
                        tvKondisi.setTextColor(Color.WHITE);
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

    public String getPath() {
        return path;
    }

    public Query getQuery() {
        return query;
    }

    public int getLimit() {
        return limit;
    }

    public TextView getTvTemp() {
        return tvTemp;
    }

    public void setTvTemp(TextView tvTemp) {
        this.tvTemp = tvTemp;
    }

    public TextView getTvHum() {
        return tvHum;
    }

    public void setTvHum(TextView tvHum) {
        this.tvHum = tvHum;
    }

    public TextView getTvJam() {
        return tvJam;
    }

    public void setTvJam(TextView tvJam) {
        this.tvJam = tvJam;
    }

    public TextView getTvTgl() {
        return tvTgl;
    }

    public void setTvTgl(TextView tvTgl) {
        this.tvTgl = tvTgl;
    }

    public TextView getTvKondisi() {
        return tvKondisi;
    }

    public void setTvKondisi(TextView tvKondisi) {
        this.tvKondisi = tvKondisi;
    }
}
