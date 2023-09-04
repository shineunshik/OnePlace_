package oneplace.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void DefaultPayment(View v) {
        Intent intent = new Intent(getApplicationContext(), DefaultPayment.class);
        startActivity(intent);
    }

    public void PassWordPayment(View v) {
        Intent intent = new Intent(getApplicationContext(), PassWordPayment.class);
        startActivity(intent);
    }


}