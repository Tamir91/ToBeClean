package tobeclean.tobeclean;

import android.app.AlertDialog;
import android.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;


import java.util.List;

import javax.inject.Inject;

import app.App;
import base.mvp.BaseFragment;
import butterknife.BindView;
import helpers.CleanConstants;
import helpers.TinyDB;
import utils.AppViewModel;
import utils.LocaleHelper;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    @BindView(R.id.toolBar)
    Toolbar toolbar;

    @Inject
    List<BaseFragment> baseFragmentList;

    @Inject
    TinyDB tinyDB;

    //Views
    protected AppViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate::in");


        //DI Dagger
        App.getApp(this).getAppComponent().injectMain(this);

        //ViewModel
        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        if (savedInstanceState != null) {
           // baseFragmentList.set(0, (BaseFragment) getSupportFragmentManager().getFragment(savedInstanceState, "map_key"));
           // baseFragmentList.set(1, (BaseFragment) getSupportFragmentManager().getFragment(savedInstanceState, "map_key"));
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //This crash app. Why?
        //getSupportFragmentManager().putFragment(outState, "map_key", baseFragmentList.get(0));
        //getSupportFragmentManager().putFragment(outState, "place_key", baseFragmentList.get(1));
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
                startFragment(R.id.fragPortraitContainer, baseFragmentList.get(1));
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

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(idContainer, fragment)
                .commit();

        Log.d(TAG, "startFragment::" + fragment.getClass().getSimpleName() + "::was replaced");
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
        LocaleHelper.setLocale(this, lang);
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

