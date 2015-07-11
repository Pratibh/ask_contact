package com.finalyearproject.askcontact.askcontact;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by pratibh on 7/1/15.
 */
public class EnterCode extends Activity {
    EditText code;
    Button click;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_code);
        code =(EditText)findViewById(R.id.editText);
        click = (Button)findViewById(R.id.btn_click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Loading Contacts ..... ", Toast.LENGTH_SHORT).show();

                    Intent intent= new Intent(EnterCode.this,DisplayContact.class);
                    startActivity(intent);
                }

        });
    }
}
