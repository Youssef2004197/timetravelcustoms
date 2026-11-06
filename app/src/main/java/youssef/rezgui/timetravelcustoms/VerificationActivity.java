package youssef.rezgui.timetravelcustoms;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class VerificationActivity extends AppCompatActivity {

    //"Modification du passé" lazem ykoun nafs el texte li yab3athou MainActivity
    private static final String PURPOSE_MODIFY_PAST = "Modifying the Past";

    //method static li tsan3 el key ta3 el verdict matricule + esm w tet3ada fl ResultActivity
    public static String keyVerdict(String matricule) {
        return matricule + "_VERDICT";
    }

    //les vues li bch nest3mlouhom
    private ImageView ivPhoto;
    private TextView tvStatus;
    private MaterialButton btnApprove;
    //el esm ta3 voyageur ne7tajouh bch nab3thouh lil activity 3
    private String name;

    //ken ma5tarch taswira fl activité loula ynjm yaawd yakhtar fl activité thenya
    private final ActivityResultLauncher<PickVisualMediaRequest> photoPicker =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                //idha el user khtar taswira (uri mch null) nbadlou biha
                if (uri != null) {
                    showPhoto(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //n'affichiw layout ta3 activity_verification.xml
        setContentView(R.layout.activity_verification);

        //norbto les vues bl id mte3hom
        ivPhoto = findViewById(R.id.iv_photo_yr);
        tvStatus = findViewById(R.id.tv_status_yr);
        btnApprove = findViewById(R.id.btn_approve_yr);
        TextView tvName = findViewById(R.id.tv_name_value_yr);
        TextView tvBirth = findViewById(R.id.tv_birth_value_yr);
        TextView tvDest = findViewById(R.id.tv_dest_value_yr);
        TextView tvAge = findViewById(R.id.tv_age_value_yr);
        TextView tvPurpose = findViewById(R.id.tv_purpose_value_yr);

        //na9raw les données li jew men MainActivity, les keys yetbnew bl method static w el matricule
        Intent in = getIntent();
        String mat = MainActivity.MATRICULE;
        name = in.getStringExtra(MainActivity.keyName(mat));
        int birth = in.getIntExtra(MainActivity.keyBirth(mat), 0);
        int dest = in.getIntExtra(MainActivity.keyDest(mat), 0);
        String purpose = in.getStringExtra(MainActivity.keyPurpose(mat));

        //na9raw taswira (ken el user khtarha fl MainActivity) w naffichiwha, sinon nab9aw 3al silhouette
        Uri photo;
        if (Build.VERSION.SDK_INT >= 33) {
            photo = in.getParcelableExtra(MainActivity.keyPhoto(mat), Uri.class);
        } else {
            photo = in.getParcelableExtra(MainActivity.keyPhoto(mat));
        }
        if (photo != null) {
            showPhoto(photo);
        }

        //l'age temporel = 3am destination - 3am lwelada
        int age = dest - birth;

        //n'affichiw el résumé fl passport
        tvName.setText(name);
        tvBirth.setText(String.valueOf(birth));
        tvDest.setText(String.valueOf(dest));
        tvAge.setText(getString(R.string.age_years, age));
        tvPurpose.setText(purpose);

        //el contrainte: motif "Modification du passé" ET el farq 3adad premier  nsakrou button Approuver mel awel
        boolean paradox = PURPOSE_MODIFY_PAST.equals(purpose) && isPrime(age);
        if (paradox) {
            btnApprove.setEnabled(false);
            tvStatus.setText(R.string.status_paradox);
            tvStatus.setTextColor(getColor(R.color.red_light));
            tvStatus.setBackgroundResource(R.drawable.bg_stamp_red);
        } else {
            tvStatus.setText(R.string.status_clear);
            tvStatus.setTextColor(getColor(R.color.green_light));
            tvStatus.setBackgroundResource(R.drawable.bg_stamp_green);
        }

        //kif yenzel 3al taswira yet7al el galerie
        ivPhoto.setOnClickListener(v -> photoPicker.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build()));
    }

    //ta3mel affichage ll taswira li khtarha el user
    private void showPhoto(Uri uri) {
        ivPhoto.setPadding(0, 0, 0, 0);
        ivPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ivPhoto.setImageURI(uri);
    }

    //method test: el 3adad premier = ykoun akbar men 1 w ma yetqasemch ela 3la 1 w 3la rou7ou
    private boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; (long) i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    //button "Approuver" (onClick fl xml)
    public void fctApprove(View v) {
        goToResult(true);
    }

    //button "Refuser" (onClick fl xml)
    public void fctRefuse(View v) {
        goToResult(false);
    }

    //n7elo ResultActivity b intent explicite w nab3thou el verdict (booléen) w esm voyageur
    private void goToResult(boolean approved) {
        Intent i = new Intent(this, ResultActivity.class);
        i.putExtra(keyVerdict(MainActivity.MATRICULE), approved);
        i.putExtra(MainActivity.keyName(MainActivity.MATRICULE), name);
        startActivity(i);
    }
}