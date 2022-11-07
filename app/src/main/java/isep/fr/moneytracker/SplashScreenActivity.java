package isep.fr.moneytracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import org.json.JSONException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import isep.fr.moneytracker.Objects.Day;
import isep.fr.moneytracker.Objects.History;
import isep.fr.moneytracker.Objects.User;


/**
 * This activity is used to do tasks before displaying the main activity.
 * We used it to display the main icon, the logo of the app to the user but also to call the auto-saving algorithm.
 * This will detect if there is a saved day in the user profile. If yes, it will try to add it to the history or update a previously saved version of it.
 */
@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends Activity {

    /**
     * This method will setup the splash screen,
     * use the auto-saving system then call the mainActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();

        User user = null;
        try {
            user = new User(this); //Fetch the user's data from the json file
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        if(user != null){
            System.out.println(user.getCurrentDay().getDate());
            @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // define the date format
            String currentDate = formatter.format(Calendar.getInstance().getTime());
            if(!user.getCurrentDay().getDate().equals(currentDate)){ //see if the saved day date is different from the current one. If yes, add the day to the history and reset the user currentDay.
                if(user.getCurrentDay().getDaySummary() != null){
                    System.out.println("save previous day in history");
                    History history = new History();
                    try {
                        history.getHistory(this); //retrieve all the saved days from history
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                    boolean dayAlreadySaved = false;
                    int indexOfSavedDay =0;
                    for(Day previousDay:history.getDayList()){ //Check if the day was previously added. Does it need to be created or updated.
                        if(previousDay.getDate().equals(user.getCurrentDay().getDate())){
                            indexOfSavedDay = history.getDayList().indexOf(previousDay);
                            dayAlreadySaved = true;
                        }
                    }

                    if(!dayAlreadySaved){
                        history.addDay(user.getCurrentDay());
                    } else {
                        history.getDayList().set(indexOfSavedDay, user.getCurrentDay());
                    }

                    try {
                        history.saveHistory(this); //Save the new history list in a json file
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("Reset user current day");
                user.setCurrentDay(new Day()); //reset the user current day, before displaying the main activity
                try {
                    user.saveUser(this);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //Launch main activity after 1.5s, the time for the user to the logo and for the app to setup itself. could be remove but we wanted to show the main icon.
        handler.postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
        }, 1500);

    }

}
