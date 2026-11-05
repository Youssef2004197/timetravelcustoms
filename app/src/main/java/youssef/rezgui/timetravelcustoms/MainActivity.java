package youssef.rezgui.timetravelcustoms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
//
public class MainActivity extends AppCompatActivity {
    //3 text input ta3 formulaire loula esm thenya 3am lwelada theltha 3am li y7b ymchilo
    private EditText etName;
    private EditText etBirth;
    private EditText etDest;
    //3radio bch yakhtar sbab li yhb yssefer 3la khatro
    private RadioButton rbTourism;
    private RadioButton rbModifyPast;
    private RadioButton rbEscape;
    // oncreate heya method truni awl ma l'acitvité tet7al
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ligne hedha dawro eno yaffichi layout mta3 activity_main.xml 3al ecran
        setContentView(R.layout.activity_main);
        //norbto kol variable b view mte3o fi fichier xml en utilisant id
        etName = findViewById(R.id.etName);
        etBirth = findViewById(R.id.etBirth);
        etDest = findViewById(R.id.etDest);
        rbTourism = findViewById(R.id.rbTourism);
        rbModifyPast = findViewById(R.id.rbModifyPast);
        rbEscape = findViewById(R.id.rbEscape);
    }

    //ysir appel ll method hedhi wakt user yenzl 3al button inspect profile
    //method hedhi marbouta fl xml b est3mel android:onClick="fctInspect"
    public void fctInspect(View v) {
        String name = etName.getText().toString();
        String birthStr = etBirth.getText().toString();
        String destStr = etDest.getText().toString();
        //na3mlo test idha fama text input fergha
        if (name.isEmpty() || birthStr.isEmpty() || destStr.isEmpty()) {
            Toast.makeText(this, "Fill in all the fields", Toast.LENGTH_LONG).show();
            return;
        }
        //variable purpose bch tekho valeur ml les choix l mawjoudin w idha makhtar 7ata wa7da error message yodhher
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
        //na3mlo convert ll years w nrodohom integer
        int birth = Integer.parseInt(birthStr);
        int dest = Integer.parseInt(destStr);


        Toast.makeText(this, name + " " + birth + " -> " + dest + " (" + purpose + ")", Toast.LENGTH_LONG).show();
    }
}