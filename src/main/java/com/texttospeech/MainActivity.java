package com.texttospeech;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.EditText;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Toast;
import java.util.Locale;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener
{
	private TextToSpeech tts;
	private EditText text;
	private Button button;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout layout = new LinearLayout(getApplicationContext());
		text = new EditText(getApplicationContext());
		button = new Button(getApplicationContext());
		ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		ViewGroup.LayoutParams childLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(layoutParams);
		text.setLayoutParams(childLayoutParams);
		button.setLayoutParams(childLayoutParams);
		text.setHint("Enter text here..");
		button.setText("SPEAK");
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.addView(text);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				TextSpeech();
			}
		});
		layout.addView(button);
		setContentView(layout);
		tts = new TextToSpeech(this, this);
	}

	private void TextSpeech() {
		CharSequence textChar = text.getText();
		tts.speak(textChar, TextToSpeech.QUEUE_FLUSH, null,"id1");
	}

	@Override
	public void onInit(int status) {
		if(status == TextToSpeech.SUCCESS) {
			int result = tts.setLanguage(Locale.US);
			//you can change the language to any built in language.
			float i = 50;
			if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Toast.makeText(getApplicationContext(), "Language IS not supported!", Toast.LENGTH_SHORT).show();
			}
			else {
				TextSpeech();
			}
		}
		else {
			Toast.makeText(getApplicationContext(), "Initialization failed!", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onDestroy() {
		if(tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}
}
