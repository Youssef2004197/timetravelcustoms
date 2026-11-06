package youssef.rezgui.timetravelcustoms;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    //hedhi matricule li tlabeha fl contrainte
    public static final String MATRICULE = "9898";
    //3 text input ta3 formulaire loula esm thenya 3am lwelada theltha 3am li y7b ymchilo
    private EditText etName;
    private EditText etBirth;
    private EditText etDest;
    //3 radio bch yakhtar sbab li yhb yssefer 3la khatro
    private RadioButton rbTourism;
    private RadioButton rbModifyPast;
    private RadioButton rbEscape;
    //apercu ta3 taswira w el uri mte3ha (null ken el user ma khtarch taswira)
    private ImageView ivPhotoPreview;
    private Uri photoUri = null;

    //kifeh n7lo galerie mta3 tlf bch nakhtaro image n7otoha
    private final ActivityResultLauncher<PickVisualMediaRequest> photoPicker =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                //idha el user khtar taswira nsajlou el uri w naffichiw l'apercu
                if (uri != null) {
                    photoUri = uri;
                    ivPhotoPreview.setPadding(0, 0, 0, 0);
                    ivPhotoPreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    ivPhotoPreview.setImageURI(uri);
                }
            });

    //les mthode static li bch yasn3o les key mta3 putextra

    public static String keyName(String matricule) {
        return matricule + "_NOM_VOYAGEUR";
    }

    public static String keyBirth(String matricule) {
        return matricule + "_ANNEE_NAISSANCE";
    }

    public static String keyDest(String matricule) {
        return matricule + "_ANNEE_DESTINATION";
    }

    public static String keyPurpose(String matricule) {
        return matricule + "_MOTIF";
    }

    public static String keyPhoto(String matricule) {
        return matricule + "_PHOTO";
    }
    // oncreate heya method truni awl ma l'acitvité tet7al
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ligne hedha dawro eno yaffichi layout mta3 activity_main.xml 3al ecran
        setContentView(R.layout.activity_main);
        //norbto kol variable b view mte3o fi fichier xml en utilisant id
        etName = findViewById(R.id.et_name_yr);
        etBirth = findViewById(R.id.et_birth_yr);
        etDest = findViewById(R.id.et_dest_yr);
        rbTourism = findViewById(R.id.rb_tourism_yr);
        rbModifyPast = findViewById(R.id.rb_modify_past_yr);
        rbEscape = findViewById(R.id.rb_escape_yr);
        ivPhotoPreview = findViewById(R.id.iv_photo_preview_yr);
    }

    //ysir appel ll method hedhi wakt user yenzl 3al button upload photo (onClick fl xml)
    public void fctPickPhoto(View v) {
        photoPicker.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }

    //ysir appel ll method hedhi wakt user yenzl 3al button inspect profile
    //method hedhi marbouta fl xml b est3mel android:onClick="fctInspect"
    public void fctInspect(View v) {
        String name = etName.getText().toString().trim();
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
        //3am lwsoul lazem ykoun akbar men 3am lwelada, sinon l'age temporel ykoun 0 wala negatif
        if (dest <= birth) {
            Toast.makeText(this, getString(R.string.error_years_order), Toast.LENGTH_LONG).show();
            return;
        }
        //na9raw 3am lyoum men sa3a ta3 téléphone (Calendar)
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        //ken el motif "Modification du passé" w 3am lwsoul mouch fl madhi (>= 3am lyoum) nwa9fou w nhrou message chnowa yبدّel
        if (purpose.equals("Modifying the Past") && dest >= currentYear) {
            Toast.makeText(this, getString(R.string.error_past_future, currentYear), Toast.LENGTH_LONG).show();
            return;
        }
        //bch n7elo l'acivité li ba3deha bl expicit intent w zid nab3tho data bl les method static with matricule
        Intent i = new Intent(this, VerificationActivity.class);
        i.putExtra(keyName(MATRICULE), name);
        i.putExtra(keyBirth(MATRICULE), birth);
        i.putExtra(keyDest(MATRICULE), dest);
        i.putExtra(keyPurpose(MATRICULE), purpose);
        //ken el user khtar taswira nab3thouha  lil activity li ba3deha
        if (photoUri != null) {
            i.putExtra(keyPhoto(MATRICULE), photoUri);
            i.setClipData(ClipData.newRawUri("photo", photoUri));
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivity(i);

        Toast.makeText(this, name + " " + birth + " -> " + dest + " (" + purpose + ")", Toast.LENGTH_LONG).show();
    }
}