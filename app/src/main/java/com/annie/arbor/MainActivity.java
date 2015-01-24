package com.annie.arbor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    private Button addButton;
    private EditText addTask;
    private RelativeLayout mainLayout;

    final ArrayList<String> taskArray = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    String task = "";

    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = (RelativeLayout)findViewById(R.id.arbor);
        final InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        addButton = (Button)findViewById(R.id.addBtn);
        addTask = (EditText)findViewById(R.id.addTask);
        ListView myListView = (ListView)findViewById(R.id.tasksView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, taskArray);
        myListView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**Toast.makeText(getApplicationContext(),
                    addTask.getText(), Toast.LENGTH_LONG).show();*/
                task = addTask.getText().toString();
                if (! task.equals("")) {
                    taskArray.add(0, task);
                    adapter.notifyDataSetChanged();
                    imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
                    addTask.setText("");
                }
            }
        });

        double lat = 37.51;
        double lon = -120.85;
        new Weather(this).execute(lat, lon);
        /**Toast.makeText(this, "iasdkjfnasdkfjna", Toast.LENGTH_SHORT).show();*/

    }

    public void map(View v){
        Intent maps = new Intent(this, map.class);
        startActivity(maps);
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
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
