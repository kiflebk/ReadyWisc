package com.example.kiflebk.readywisc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
<<<<<<< HEAD
import android.util.Log;
=======
>>>>>>> pr/2
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private static MyDatabaseHelper mDatabaseHelper;
    private Button DisplayButton, UpdateButton, getDBButton;
    static Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabaseHelper = new MyDatabaseHelper(this);
        UpdateButton = (Button) findViewById(R.id.UpdateButton);
        DisplayButton = (Button) findViewById(R.id.DisplayButton);
        getDBButton = (Button) findViewById(R.id.getDBButton);
        final EditText name = (EditText) findViewById(R.id.editText);
        final EditText email = (EditText) findViewById(R.id.activity_checklist_item_create_qty_edittext);

        ctx = this;

        addUser(null, null, 0);

        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameData = name.getText().toString();
                String emailData = email.getText().toString();
                addUser(nameData, emailData, 0);
                name.setText("");
                email.setText("");
            }
        });

        DisplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = mDatabaseHelper.query(MyDatabaseHelper.TABLE_USERS, MyDatabaseHelper.COL_NAME);

                String[] from = new String[]{MyDatabaseHelper.COL_NAME, MyDatabaseHelper.COL_EMAIL};

                int[] to = { android.R.id.text1, android.R.id.text2 };

                final SimpleCursorAdapter adapter = new SimpleCursorAdapter(ctx, android.R.layout.simple_list_item_2, c, from, to, 0);

                final ListView listView = (ListView) findViewById(R.id.listView);

                listView.setAdapter(adapter);
            }
        });

        //click listener for the new button to update from the web database
        getDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //this string can be dynamically allocated later, I hardcoded just for the proof of concept
                //String[][] webNames = new String[3][3];

                /*new runnable object used to pull the data from the web.
                android will only let you do json calls from a thread other
                than the main
                 */

                DBUpdateFromWeb foo = new DBUpdateFromWeb();
                Thread t = new Thread(foo);
                t.start();
                Toast.makeText(ctx, "Local DB Updated", Toast.LENGTH_SHORT);
            }
        });
    }


    protected static void addUser(String name, String email, long dateOfBirthMillis) {

        ContentValues values = new ContentValues();

        values.put(MyDatabaseHelper.COL_NAME, name);

        if (email != null) {

            values.put(MyDatabaseHelper.COL_EMAIL, email);

        }

        if (dateOfBirthMillis != 0) {

            values.put(MyDatabaseHelper.COL_DOB, dateOfBirthMillis);

        }

        try {

            mDatabaseHelper.insert(MyDatabaseHelper.TABLE_USERS, values);

        } catch (MyDatabaseHelper.NotValidException e) {

            Log.e("DB Error:", "Unable to insert into DB.");

        }

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
}
