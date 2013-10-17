package com.example.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Email extends Activity implements View.OnClickListener {

	EditText personsEmail, intro, personsName;
	String emailAdd, beginning, name;
	Button sendEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email);
		initializeVars();
		sendEmail.setOnClickListener(this);
	}

	private void initializeVars() {
		personsEmail = (EditText) findViewById(R.id.etEmails);
		personsName = (EditText) findViewById(R.id.etName);
		sendEmail = (Button) findViewById(R.id.bSentEmail);
	}

	public void onClick(View v) {

		convertEditTextVarsIntoStringsAndYesThisIsAMethodWeCreated();
		String emailaddress[] = { emailAdd };
		String message = "Hello "
				+ name
				+ " just testing my EmailSenderApp";
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.putExtra(Intent.EXTRA_EMAIL, emailaddress);
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "I hate you");
		emailIntent.setType("plain/text");
		emailIntent.putExtra(Intent.EXTRA_TEXT, message);
		startActivity(emailIntent);
	}

	private void convertEditTextVarsIntoStringsAndYesThisIsAMethodWeCreated() {
		emailAdd = personsEmail.getText().toString();
		name = personsName.getText().toString();
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

}
