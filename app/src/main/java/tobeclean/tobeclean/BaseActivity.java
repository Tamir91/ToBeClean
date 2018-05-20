package tobeclean.tobeclean;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import butterknife.BindView;
import places.mvp.PlacesFragment;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    private static final String ENGLISH = "ENGLISH";
    private static final String HEBREW = "HEBREW";


    @BindView(R.id.toolBar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate::in");
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
                Toast.makeText(this, "exit_form_app_pressed", Toast.LENGTH_SHORT).show();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void startPlacesFragment() {
        Log.d(TAG, "startPlacesFragment::in");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, new PlacesFragment())
                .commit();
    }

    /*This method create dialog. Tamir 19/03/18*/
    protected void startLanguageDialog() {
    Log.d(TAG, "startLanguageDialog::in");

        new AlertDialog.Builder(this)
                .setTitle("CHANGE LANGUAGE")
                .setMessage("Choose your language")
                .setPositiveButton(ENGLISH, dialogClickListener)
                .setNegativeButton(HEBREW, dialogClickListener)
                .create()
                .show();
    }

    /**
     * This interface set up buttons in dialog. Tamir 19/03/18
     */
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()

    {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            switch (i) {

                //English
                case Dialog.BUTTON_POSITIVE: {
                    //Preferences.setLanguageApp( ENGLISH);
                    Toast.makeText(getApplicationContext(), "Language app was changed to " + ENGLISH, Toast.LENGTH_SHORT).show();
                    break;
                }

                //Hebrew
                case Dialog.BUTTON_NEGATIVE: {
                    // Preferences.setLanguageApp(getApplicationContext(), HEBREW);
                    Toast.makeText(getApplicationContext(), "Language app was changed to " + HEBREW, Toast.LENGTH_SHORT).show();
                    break;
                }

            }
        }
    };
}
