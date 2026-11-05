package youssef.rezgui.timetravelcustoms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etBirth;
    private EditText etDest;
    private RadioButton rbTourism;
    private RadioButton rbModifyPast;
    private RadioButton rbEscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etBirth = findViewById(R.id.etBirth);
        etDest = findViewById(R.id.etDest);
        rbTourism = findViewById(R.id.rbTourism);
        rbModifyPast = findViewById(R.id.rbModifyPast);
        rbEscape = findViewById(R.id.rbEscape);
    }

    // Called from the button (android:onClick="fctInspect" in the XML)
    public void fctInspect(View v) {
        String name = etName.getText().toString();
        String birthStr = etBirth.getText().toString();
        String destStr = etDest.getText().toString();

        if (name.isEmpty() || birthStr.isEmpty() || destStr.isEmpty()) {
            Toast.makeText(this, "Fill in all the fields", Toast.LENGTH_LONG).show();
            return;
        }

        String purpose;
        if (rbTourism.isChecked()) {
            purpose = "Tourism";
        } else if (rbModifyPast.isChecked()) {
            purpose = "Modifying the Past";
        } else if (rbEscape.isChecked()) {
            purpose = "Escape";
        } else {
            Toast.makeText(this, "Select a purpose of travel", Toast.LENGTH_LONG).show();
            return;
        }

        int birth = Integer.parseInt(birthStr);
        int dest = Integer.parseInt(destStr);


        Toast.makeText(this, name + " " + birth + " -> " + dest + " (" + purpose + ")", Toast.LENGTH_LONG).show();
    }
}