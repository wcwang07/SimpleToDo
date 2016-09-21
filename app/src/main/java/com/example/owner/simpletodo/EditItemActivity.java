package com.example.owner.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    EditText editText;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        editText = (EditText) findViewById(R.id.et2ndEditText);
        String strItem = getIntent().getStringExtra("item");
        editText.setText(strItem);
        editText.setSelection(editText.getText().length());
        pos = getIntent().getIntExtra("pos",-1);

    }
    public void onSaveItem(View v) {
        EditText etName = (EditText) findViewById(R.id.et2ndEditText);

        Intent data = new Intent();
        data.putExtra("item", etName.getText().toString());
        data.putExtra("code", 200);
        setResult(RESULT_OK, data);
        finish();

    }
}
