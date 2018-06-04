package tobeclean.tobeclean;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;


import com.google.android.gms.maps.StreetViewPanoramaFragment;

import java.util.Locale;

import javax.inject.Inject;

import app.App;
import base.mvp.BaseFragment;
import butterknife.BindView;
import helpers.CleanConstants;
import helpers.TinyDB;
import map.mvp.MapFragment;
import places.mvp.PlacesFragment;
import utils.AppViewModel;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    @BindView(R.id.toolBar)
    Toolbar toolbar;

    @Inject
    PlacesFragment placesFragment;

    @Inject
    MapFragment mapFragment;

    @Inject
    TinyDB tinyDB;


    //Views
    // protected PlacesFragment placesFragment;
    protected AppViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        Log.d(TAG, "onCreate::in");


        //DI Dagger
        App.getApp(this).getAppComponent().injectMain(this);

        //ViewModel
        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu::in");

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected::in");

        switch (item.getItemId()) {

            case R.id.my_places: {
                startFragment(R.id.fragPortraitContainer, placesFragment);
                break;
            }


            case R.id.settings: {
                startLanguageDialog();
                break;
            }

            case R.id.exit_form_app: {
                finish();// added by michael- 25.05.18
                //TODO save all relevant data.
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_90 ||
                getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_270) {
            menu.getItem(0).setVisible(false);//hide problem and useless menu item in landscape mode
        } else {
            menu.getItem(0).setVisible(true);//make it visible
        }

        return true;
    }

    protected <T extends BaseFragment> void startFragment(int idContainer, T fragment) {
        Log.d(TAG, "startFragment::" + fragment.getClass().getSimpleName() + "::was replaced");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(idContainer, fragment)
                .commit();
    }

    //Create dialog Method.
    // added by Michael- 25.05.18
    protected void startLanguageDialog() {

        final String[] listItems = {"עברית", "English", "Русский", "km", "miles"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(BaseActivity.this);
        mBuilder.setTitle("Choose Language....");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    //Hebrew
                    recreate();
                    setLocale("iw");
                } else if (i == 1) {
                    //English
                    recreate();
                    setLocale("en");
                } else if (i == 2) {
                    //Russian
                    recreate();
                    setLocale("ru");
                } else if (i == 3) {
                    setDistanceMetricSystem(CleanConstants.DISTANCE_KILOMETER);

                } else if (i == 4) {
                    setDistanceMetricSystem(CleanConstants.DISTANCE_MILE);
                }

                //dismiss alert dialog when language selected
                dialogInterface.dismiss();
            }
        });


        AlertDialog mDialog = mBuilder.create();
        //show alert dialog
        mDialog.getListView().setBackgroundResource(R.drawable.white_border);
        mDialog.show();
    }

    //Set checked Language from the dialog to default.
    // added by michael- 25.05.18
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        //save data to shared preferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    //load language saved in shared preferences
    // added by michael- 25.05.18
    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "en");
        setLocale(language);

    }

    /**
     * Set metric system
     *
     * @param value {@link String}
     */
    public void setDistanceMetricSystem(String value) {
        Log.d(TAG, "setDistanceMetricSystem::" + value);
        tinyDB.putString(CleanConstants.DISTANCE_METRIC_SYSTEM, value);
    }

    /**
     * Get metric system
     *
     * @return String
     */
    public String getDistanceMetricSystem() {
        String value = tinyDB.getString(CleanConstants.DISTANCE_METRIC_SYSTEM);

        if (value.equals("")) {
            Log.d(TAG, "getDistanceMetricSystem::" + CleanConstants.DISTANCE_KILOMETER);
            return CleanConstants.DISTANCE_KILOMETER;
        }
        Log.d(TAG, "getDistanceMetricSystem::" + value);
        return value;
    }

}

