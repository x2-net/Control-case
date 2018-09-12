package com.x2.myteammanager2;

import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.x2.myteammanager2.adapter.HTTP_REQUEST;
import com.x2.myteammanager2.adapter.OnEventListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        Menu menu = (Menu) navigationView.getMenu();


        menu.add("Mein Team");
        menu.add("Kalendar");
        menu.add("Termine");
        menu.add("Mitteilungen");

        // Map<String, String> params = new HashMap<String, String>();
        // params.put("name", "Süleyman");
        // ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        // params.add(new BasicNameValuePair("name", "topaloglu"));
        // params.add(new BasicNameValuePair("surname", "suleyman"));

        HTTP_REQUEST http_request = new HTTP_REQUEST( getApplicationContext(), null, new OnEventListener() {
            @Override
            public void onSuccess( JSONArray object) {


                // Log.e("Returned Data", object.toString() );



                /*for (int i=0; i < object.length(); i++){

                    Log.d("Cikti ", String.valueOf(i));

                }*/


                Toast.makeText( getApplicationContext(), "Success:" + object, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception e) {

                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });
        // HTTP_REQUEST http_request = new HTTP_REQUEST();

        // Add Parameter to Object
        http_request.setHost("http://app.my-team-manager.de/test_team_agent_menu.php");

        http_request.setParameter("name", "topal");
        http_request.setParameter("surname", "suleyman");

        http_request.setController("Menu");
        http_request.setNamespace("!");
        http_request.setMethod("list");
        http_request.execute();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Log.e("Selected Item id", item.getTitle().toString());

        /*
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
