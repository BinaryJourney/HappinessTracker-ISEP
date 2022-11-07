package isep.fr.moneytracker;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import isep.fr.moneytracker.Fragments.HistoryFragment;
import isep.fr.moneytracker.Fragments.NewDayFragment;
import isep.fr.moneytracker.Fragments.PreviewFragment;
import isep.fr.moneytracker.databinding.ActivityMainBinding;
import android.view.Menu;
import android.view.MenuItem;


/**
 * The main activity is used to define the global UI of the app but also to setup the bottom navigation view. We chose to use a bottom navigation bar to navigate between the different fragments.
 * We only have 3 different fragments, so it was well suited for our app.
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    /*
    We define all the fragment we need at the top of the activity.
    It will be easier to add them to the navigation bar system afterwards.
     */
    private PreviewFragment previewFragment = new PreviewFragment();
    private HistoryFragment historyFragment = new HistoryFragment();
    private NewDayFragment newDayFragment = new NewDayFragment();

    /**
     * This method will define the basic behavior of the bottom navigation view and display the first fragment. The first fragment displayed can be define manually.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();

        hideSystemUI(decorView);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation); //get the bottom navigation view from the xml file.
        getSupportFragmentManager().beginTransaction().replace(R.id.container, previewFragment).commit(); //define the first displayed fragment
        bottomNavigationView.setSelectedItemId(R.id.Preview);

        /*
        In ths method, we will listen to the user touch on the bottom navigation view and define a behavior based on the user selection. On each button, the app will display the corresponding fragment.
         */
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.Preview:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, previewFragment).commit();
                    return true;

                case R.id.History:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, historyFragment).commit();
                    return true;

                case R.id.Profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, newDayFragment).commit();
                    return true;

            }

            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method setup the basic UI of the app. It will define the behavior of the status and navigation bar during the app usage.
     */
    private void hideSystemUI(View decorView) {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        decorView.setSystemUiVisibility(
                //View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                //| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                //| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        //| View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}