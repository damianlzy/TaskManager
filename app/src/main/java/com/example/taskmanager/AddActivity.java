package com.example.taskmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;


/**
 * Created by 15031759 on 26/5/2017.
 */

public class AddActivity extends AppCompatActivity{
    int reqCode = 12345;
    Button btnAdd, btnCancel;
    EditText etName, etDesc, etRemind;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        etName = (EditText)findViewById(R.id.etName);
        etDesc = (EditText)findViewById(R.id.etDescription);
        etRemind = (EditText)findViewById(R.id.etRemind);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String desc = etDesc.getText().toString();

                DBHelper dbh = new DBHelper(AddActivity.this);
                dbh.insertTask(name, desc);
                dbh.close();

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, Integer.parseInt(etRemind.getText().toString()));

                Intent intent = new Intent(AddActivity.this,ScheduleNotificationReceiver.class);

                intent.putExtra("name", name);
                intent.putExtra("desc", desc);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddActivity.this, reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);


                setResult(RESULT_OK);
                finish();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
