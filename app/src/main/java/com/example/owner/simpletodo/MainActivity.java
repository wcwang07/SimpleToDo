package com.example.owner.simpletodo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//every class extend from the same base class
public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    EditText etEditText;
    private final int REQUEST_CODE=20;
    int position;
            //adapter.getCursor().getPosition();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //add applicaton logic here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //..super, setContentView, define lvItems
        super.onCreate(savedInstanceState);
        //xml layout is applied onCreate()
        setContentView(R.layout.activity_main);
        populateArrayItems();
        //get handle to ListView
        lvItems = (ListView) findViewById(R.id.lvitems);

        lvItems.setAdapter(itemsAdapter);
        itemsAdapter.notifyDataSetChanged();
        etEditText = (EditText) findViewById(R.id.etEditText);
        setupListViewListener();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void populateArrayItems() {
        readItems();

        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
    }

    public void onAddItem(View v) {

        itemsAdapter.add(etEditText.getText().toString());

        etEditText.setText("");
        writeItems();
    }

    public void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;

            }

        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id){

                Intent i = new Intent(MainActivity.this, EditItemActivity.class);//position=pos;

                position=pos;
                i.putExtra("pos",pos);
                i.putExtra("item",items.get(pos));
                startActivityForResult(i, REQUEST_CODE);
                return;
            }

        });
    }

    private void readItems() {
        //items = new ArrayList<String>();
        File filesDir = getFilesDir();
        File toDoFile = new File(filesDir, "todolist.txt");

        try {
            items = new ArrayList<String>(FileUtils.readLines(toDoFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File toDoFile = new File(filesDir, "todolist.txt");

        try {
            //arraylist will be serialized and add to disk
            FileUtils.writeLines(toDoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(resultCode==RESULT_OK  && requestCode==REQUEST_CODE){
            String item= data.getExtras().getString("item");
            int code = data.getExtras().getInt("code",0);
            items.set(position, item);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
            // Toast the name to display temporarily on screen
            //Toast.makeText(this, "Udapte: "+item, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.owner.simpletodo/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.owner.simpletodo/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}