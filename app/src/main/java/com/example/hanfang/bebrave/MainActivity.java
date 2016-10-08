package com.example.hanfang.bebrave;

import android.app.Activity;
import android.app.PendingIntent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {

    private Button mButton1;
    private Button mButton2;
    private EditText mEditText1;
    private EditText mEditText2;
    private EditText mEditText3;
    private EditText mEditText4;

    private final static int key = 1;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public static String Encryption (String strIn)
    {
        StringBuffer output = new StringBuffer("");
        char b[];
        char ch;
        b = strIn.toCharArray();
        for(int i=0;i< strIn.length();i++){
            if(b[i] >= 'a' && b[i] <= 'z' || b[i] >= 'A' && b[i] <= 'Z' || b[i] >= '0' && b[i] <= '9'){
                ch = (char)(b[i] + key);
                output.append(ch);
            }
            else{
                output.append(b[i]);
            }
        }
        return output.toString();
    }
    public static  String Decryption (String input)
    {
        StringBuffer output = new StringBuffer("");
        char temp[] = input.toCharArray();
        char ch;
        for(int i = 0; i < input.length(); i++){
            if(temp[i] >= 'b' && temp[i] <= '{' || temp[i] >= 'B' && temp[i] <= '[' || temp[i] >= '1' && temp[i] <= ':'){
                ch = (char)(temp[i] - key);
                output.append(ch);
            }
            else{
                output.append(temp[i]);
            }
        }
        return output.toString();
    }















    @Override
    protected void onCreate(Bundle savedInstanceState) {
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText1 = (EditText) (EditText)
                this.findViewById(R.id.mEditText1);
        mEditText2 = (EditText) (EditText)
                this.findViewById(R.id.mEditText2);
        mEditText3 = (EditText) (EditText)
                this.findViewById(R.id.mEditText3);
       mEditText4 = (EditText) (EditText)
               this.findViewById(R.id.mEditText4);
        mButton1 = (Button) (Button) this.findViewById(R.id.mButton1);
        mButton2 = (Button) (Button) this.findViewById(R.id.mButton2);
        mEditText1.setText("Please enter the cellphone number:");
        mEditText2.setText("Please enter the content: ");
        mEditText3.setText("Please enter the encrypted code:");
        mEditText4.setText("The decrypted text will be shown here.");
        mEditText1.setOnClickListener(new EditText.OnClickListener() {
            public void onClick(View v) {
                mEditText1.setText("");
            }
        });
        mEditText2.setOnClickListener(new EditText.OnClickListener() {
            public void onClick(View v) {
                mEditText2.setText("");
            }
        });
        mEditText3.setOnClickListener(new EditText.OnClickListener() {
            public void onClick(View v){
                mEditText3.setText("");
            }
        });
        mButton1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String strDestAddress = mEditText1.getText().toString();
                String strMessage = mEditText2.getText().toString();
                SmsManager smsManager = SmsManager.getDefault();
                if (isPhoneNumberValid(strDestAddress) == true && iswithin70(strMessage) == true) {
                    try {
                        PendingIntent mPI = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(), 0);
                        //smsManager.sendTextMessage(strDestAddress, null, strMessage, mPI, null);
                        smsManager.sendTextMessage(strDestAddress, null, Encryption(strMessage),mPI,null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(MainActivity.this, "Successfully sent!", Toast.LENGTH_SHORT).show();

                } else {
                    if (isPhoneNumberValid(strDestAddress) == false) {
                        if (iswithin70(strMessage) == false) {
                            Toast.makeText(MainActivity.this, "Wrong format and text ecceeds 70 words!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MainActivity.this, "Wrong format!", Toast.LENGTH_SHORT).show();
                        }
                    } else if (iswithin70(strMessage) == false) {
                        Toast.makeText(MainActivity.this, "Text exceeds 70 word!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

        mButton2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String EncryptedCode = mEditText3.getText().toString();
                String DecryptedCode = EncryptedCode;
               mEditText4.setText(Decryption(DecryptedCode));
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "1[0-9]{10}";
        String expression2 = "1[0-9]{10}";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        Pattern pattern2 = Pattern.compile(expression2);
        Matcher matcher2 = pattern2.matcher(inputStr);
        if (matcher.matches() || matcher2.matches()) {
            isValid = true;

        }
       // return isValid;
        return true;
    }

    public static boolean iswithin70(String text) {
        if (text.length() <= 70) {
            return true;
        } else
            return false;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}


