package youssef.rezgui.timetravelcustoms;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //n'affichiw layout ta3 activity_result.xml
        setContentView(R.layout.activity_result);

        //na9raw el verdict w esm voyageur
        Intent in = getIntent();
        String mat = MainActivity.MATRICULE;
        boolean approved = in.getBooleanExtra(VerificationActivity.keyVerdict(mat), false);
        String name = in.getStringExtra(MainActivity.keyName(mat));

        //norbto les vues
        View root = findViewById(R.id.root_result_yr);
        TextView tvTitle = findViewById(R.id.tv_verdict_title_yr);
        TextView tvMessage = findViewById(R.id.tv_message_yr);

        //el fond w el message yetbadlou 7asb el verdict: 5adhra ken approuvé, 7amra ken refusé
        if (approved) {
            root.setBackgroundColor(getColor(R.color.green_approve));
            tvTitle.setText(R.string.result_approved_title);
            tvMessage.setText(getString(R.string.result_approved_msg, name));
        } else {
            root.setBackgroundColor(getColor(R.color.red_refuse));
            tvTitle.setText(R.string.result_refused_title);
            tvMessage.setText(getString(R.string.result_refused_msg, name));
        }
    }

    //button "Nouveau voyageur": nerj3ou lil MainActivity w nesakrou kol les activités li9bal
    public void fctNewTraveler(View v) {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
