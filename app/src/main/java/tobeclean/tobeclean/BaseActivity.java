package tobeclean.tobeclean;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import java.util.Locale;

import app.App;
import butterknife.BindView;
import places.mvp.PlacesFragment;
import utils.AppViewModel;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    @BindView(R.id.toolBar)
    Toolbar toolbar;

    //Views
    protected PlacesFragment placesFragment;
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
                startPlacesFragment();
                break;
            }

            case R.id.add_to_my_places: {
                Toast.makeText(this, "add_to_my_places_pressed", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.settings: {
                startLanguageDialog();
                break;
            }

            case R.id.exit_form_app: {
                finish();// added by michael- 25.05.18

            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void startPlacesFragment() {
        Log.d(TAG, "startPlacesFragment::in");

        if (placesFragment == null) {
            placesFragment = new PlacesFragment();
            Log.d(TAG, "startPlacesFragment::fragment was created");
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, placesFragment)
                .commit();
    }

     //Create dialog Method.
    // added by Michael- 25.05.18
    protected void startLanguageDialog() {

        final String[] listItems = {"עברית", "English", "Русский"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(BaseActivity.this);
        mBuilder.setTitle("Choose Language....");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    //Hebrew
                    recreate();
                    setLocale("iw");
                }
                else if (i == 1){
                    //English
                    recreate();
                    setLocale("en");
                }
                else if (i == 2){
                    //Russian
                    recreate();
                    setLocale("ru");
                }

                //dismiss alert dialog when language selected
                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        //show alert dialog
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
    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","en");
        setLocale(language);

    }

}

