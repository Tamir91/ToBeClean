package tobeclean.tobeclean;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import javax.inject.Inject;

import app.App;
import butterknife.BindView;
import places.mvp.PlacesFragment;
import places.mvp.PlacesPresenter;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    private PlacesFragment mPlacesFragment = new PlacesFragment();

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
                //startLanguageDialog();
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
                .replace(R.id.fragmentContainer, mPlacesFragment)
                .commit();
    }

}
