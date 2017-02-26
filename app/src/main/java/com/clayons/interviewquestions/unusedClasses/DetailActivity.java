package com.clayons.interviewquestions.unusedClasses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.clayons.interviewquestions.Constants;
import com.clayons.interviewquestions.R;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvNameValue;
    private TextView tvAgeValue;
    private TextView tvPhoneValue;

    private ImageView ivLikedIcon;

    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = this.getIntent().getExtras();

        if(bundle == null || !bundle.containsKey(Constants.BUNDLE_KEY_PERSON)) {
            errorOut();
            return;
        }

        this.person = (Person) bundle.get(Constants.BUNDLE_KEY_PERSON);

        if(this.person == null) {
            errorOut();
            return;
        }

        tvNameValue = (TextView) this.findViewById(R.id.tvNameValue);
        tvAgeValue = (TextView) this.findViewById(R.id.tvAgeValue);
        tvPhoneValue = (TextView) this.findViewById(R.id.tvPhoneValue);
        ivLikedIcon = (ImageView) this.findViewById(R.id.ivLikedIcon);

        String fullName = person.getFirstName() + " " + person.getLastName();
        this.setTitle(fullName);

        tvNameValue.setText(fullName);
        tvAgeValue.setText(String.valueOf(this.person.getAge()));
        tvPhoneValue.setText(person.getPhoneNum());

        ivLikedIcon.setClickable(true);
        ivLikedIcon.setOnClickListener(this);

        if(this.person.isLiked()) {
            ivLikedIcon.setImageResource(R.drawable.ic_heart_filled);
        } else {
            ivLikedIcon.setImageResource(R.drawable.ic_heart_outline);
        }
    }

    private void errorOut() {
        Toast.makeText(this, "Invalid action", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    private void likeIconHandler() {
        if(this.person.isLiked()) {
            this.person.setLiked(false);
            ivLikedIcon.setImageResource(R.drawable.ic_heart_filled);
        } else {
            this.person.setLiked(true);
            ivLikedIcon.setImageResource(R.drawable.ic_heart_outline);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivLikedIcon:
                likeIconHandler();
                break;
            default:
                break;
        }
    }
}
