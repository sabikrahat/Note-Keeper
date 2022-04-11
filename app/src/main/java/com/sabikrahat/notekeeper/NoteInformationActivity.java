package com.sabikrahat.notekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sabikrahat.notekeeper.db_management.Util;

public class NoteInformationActivity extends AppCompatActivity {

    private EditText courseId, topicId, dateTimeId, descriptionId;

    // declare existingKey variable
    private String existingKey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_information);

        findViewById(R.id.btnCancel).setOnClickListener(view -> finish());

        courseId = findViewById(R.id.courseId);
        topicId = findViewById(R.id.topicId);
        dateTimeId = findViewById(R.id.dateTimeId);
        descriptionId = findViewById(R.id.descriptionId);

        findViewById(R.id.btnSave).setOnClickListener(view -> saveOrUpdateNote());

        // check in intent if there is any key set in putExtra
        Intent i = getIntent();
        existingKey = i.getStringExtra("NoteKey");
        if (existingKey != null && !existingKey.isEmpty()) {
            initializeFormWithExistingData(existingKey);
        }
    }

    private void initializeFormWithExistingData(String eventKey) {

        String value = Util.getInstance().getValueByKey(this, eventKey);
        System.out.println("Key: " + eventKey + "; Value: " + value);

        if (value != null) {
            String[] fieldValues = value.split("-::-");
            String getCourseId = fieldValues[0];
            String getNoteTopic = fieldValues[1];
            String getDateTime = fieldValues[2];
            String getDescription = fieldValues[3];

            courseId.setText(getCourseId);
            topicId.setText(getNoteTopic);
            dateTimeId.setText(getDateTime);
            descriptionId.setText(getDescription);

            TextView tv = findViewById(R.id.titleId);
            tv.setText(getNoteTopic);

            Button btnSave = findViewById(R.id.btnSave);
            btnSave.setText("Update");
        }
    }

    private void saveOrUpdateNote() {
        String course = courseId.getText().toString().trim();
        String topic = topicId.getText().toString().trim();
        String dateTime = dateTimeId.getText().toString().trim();
        String description = descriptionId.getText().toString().trim();

        if (course.isEmpty()) {
            courseId.setError("Course is required");
            courseId.requestFocus();
            return;
        }
        if (topic.isEmpty()) {
            topicId.setError("Topic is required");
            topicId.requestFocus();
            return;
        }
        if (dateTime.isEmpty()) {
            dateTimeId.setError("DateTime is required");
            dateTimeId.requestFocus();
            return;
        }
        if (description.isEmpty()) {
            descriptionId.setError("Description is required");
            descriptionId.requestFocus();
            return;
        }

        System.out.println("Course: " + course);
        System.out.println("Topic: " + topic);
        System.out.println("Description: " + description);
        System.out.println("DateTime: " + dateTime);

        String key = course + "-::-" + dateTime;
        String value = course + "-::-" + topic + "-::-" + dateTime + "-::-" + description;

        if (existingKey != null) {
            key = existingKey;

            Util.getInstance().setKeyValue(this, key, value);
            Toast.makeText(this, "Data updated successfully.", Toast.LENGTH_LONG).show();
        } else {
            Util.getInstance().setKeyValue(this, key, value);
            Toast.makeText(this, "Data saved successfully.", Toast.LENGTH_LONG).show();
        }

        finish();
    }
}