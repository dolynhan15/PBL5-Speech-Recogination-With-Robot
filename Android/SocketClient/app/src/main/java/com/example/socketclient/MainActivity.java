package com.example.socketclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    public String message;
    public String line_rcv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText inputText = (EditText)findViewById(R.id.editText3);
        final Button clickable = (Button)findViewById(R.id.button);
        clickable.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                send sendcode = new send();
                message = inputText.getText().toString();
                //inputText.setText(line_rcv);
                sendcode.execute();
            }
        });
    }
    class send extends AsyncTask<Void,Void,Void>
    {
        Socket s;
        PrintWriter pw;

        @Override
        protected Void doInBackground(Void... params)
        {
            try{
                s = new Socket("10.10.42.144",8000);
                pw = new PrintWriter(s.getOutputStream());
                pw.write(message);
                pw.flush();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                line_rcv = bufferedReader.readLine();
                System.out.println("Python Says : " + line_rcv);
                pw.close();
                s.close();
            } catch (UnknownHostException e){
                System.out.println("Fail");
                e.printStackTrace();
            }   catch (IOException e){
                System.out.println("Fail");
                e.printStackTrace();
            }
            return null;


        }
    }
}