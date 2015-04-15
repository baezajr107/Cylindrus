package example.project.cylindris;

import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class gameActivity extends FragmentActivity {

         MediaPlayer player =  MediaPlayer.create(gameActivity.this, R.raw.cylindrissong);
    @Override
       protected void onCreate( Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        player.start();
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int backCount = getSupportFragmentManager().getBackStackEntryCount();
                if (backCount == 0)
                {
                    finish();
                }
            }
        });

        if (savedInstanceState == null)
        {

            getSupportFragmentManager().beginTransaction().add(R.id.container, new Fragment())
                    .addToBackStack(null)
                    .commit();
        }
    }


}
