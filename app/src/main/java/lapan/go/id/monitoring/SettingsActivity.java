package lapan.go.id.monitoring;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "lapan.go.id.monitoring";

    public static final String KEY_PREF_EXAMPLE_SWITCH = "example_switch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

//        mPreferences = PreferenceManager.getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
//        Preference preference = this.findPreference("example_switch");
//        preference.setSummary(mPreferences.getString("change_theme",
//                getString(R.string.option_on)));

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean switchPref = sharedPref.getBoolean(SettingsActivity.KEY_PREF_EXAMPLE_SWITCH, false);
        Toast.makeText(this, switchPref.toString(), Toast.LENGTH_SHORT).show();

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startupActivity = new Intent(SettingsActivity.this, StartupActivity.class);
        startActivity(startupActivity);
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
        finish();
    }
}