package org.cljmapdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import neko.App;

import org.cljmapdemo.R;

public class SplashActivity extends Activity {

    private static boolean firstLaunch = true;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        if (firstLaunch) {
            firstLaunch = false;
            setupSplash();
            App.loadAsynchronously("org.cljmapdemo.MainActivity",
                                   new Runnable() {
                                       @Override
                                       public void run() {
                                           proceed();
                                       }});
        } else {
            proceed();
        }
    }

    public void setupSplash() {
        setContentView(R.layout.splashscreen);

        TextView appNameView = (TextView)findViewById(R.id.splash_app_name);
        if (appNameView != null) {
          appNameView.setText(R.string.app_name);
        }
    }

    public void proceed() {
        startActivity(new Intent("org.cljmapdemo.MAIN"));
        finish();
    }

}
