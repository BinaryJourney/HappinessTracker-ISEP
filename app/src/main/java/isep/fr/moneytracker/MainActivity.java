package isep.fr.moneytracker;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import isep.fr.moneytracker.Fragments.HistoryFragment;
import isep.fr.moneytracker.Fragments.NewDayFragment;
import isep.fr.moneytracker.Fragments.PreviewFragment;
import isep.fr.moneytracker.Objects.Day;
import isep.fr.moneytracker.Objects.History;
import isep.fr.moneytracker.Objects.User;
import isep.fr.moneytracker.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private BottomNavigationView bottomNavigationView;

    private PreviewFragment previewFragment = new PreviewFragment();
    private HistoryFragment historyFragment = new HistoryFragment();
    private NewDayFragment newDayFragment = new NewDayFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();

        hideSystemUI(decorView);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        User user = null;
        try {
            user = new User(this);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(user != null){
            System.out.println(user.getCurrentDay().getDate());
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String currentDate = formatter.format(Calendar.getInstance().getTime());
            if(!user.getCurrentDay().getDate().equals(currentDate)){
                if(user.getCurrentDay().getDaySummary() != null){
                    System.out.println("save previous day in history");
                    History history = new History();
                    try {
                        history.getHistory(this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    history.addDay(user.getCurrentDay());
                    try {
                        history.saveHistory(this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("Reset user current day");
                user.setCurrentDay(new Day());
                try {
                    user.saveUser(this);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        //appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, previewFragment).commit();
        bottomNavigationView.setSelectedItemId(R.id.Preview);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Preview:
                        System.out.println("Preview");
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, previewFragment).commit();
                        return true;

                    case R.id.History:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, historyFragment).commit();
                        return true;

                    case R.id.Profile:
                        System.out.println("Profile");
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, newDayFragment).commit();
                        return true;

                }

                return false;
            }
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