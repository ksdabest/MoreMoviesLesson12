package sg.edu.rp.c346.id21018157.mymovies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ModifyMovies extends AppCompatActivity {
    EditText etID, etTitle, etGenre, etYear;
    Button btnUpdate, btnDelete, btnCancel;
    Spinner ratingSpinner;
    ArrayAdapter spinnerAdapter;
    String[] ratingArray;

    Movie data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_movies);

        etID = findViewById(R.id.readOnlyID);
        etTitle = findViewById(R.id.modifyTitle);
        etGenre = findViewById(R.id.modifyGenre);
        etYear = findViewById(R.id.modifyYear);
        ratingSpinner = findViewById(R.id.modifyRatingSpinner);

        ratingArray = new String []{"G", "PG", "PG13", "NC16", "M18", "R21"};
        spinnerAdapter = new ArrayAdapter(ModifyMovies.this, android.R.layout.simple_spinner_dropdown_item, ratingArray);
        ratingSpinner.setAdapter(spinnerAdapter);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        //initialise variables
        Intent i = getIntent();
        data = (Movie) i.getSerializableExtra("data");

        for(int j = 0; j < ratingArray.length; j++){
            if(ratingArray[j].equalsIgnoreCase(data.getRating())){
                ratingSpinner.setSelection(j);
            }
        }

        etID.setText(String.valueOf(data.getId()));
        etTitle.setText(data.getTitle());
        etGenre.setText(data.getGenre());
        etYear.setText("" + data.getYear());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(ModifyMovies.this);

                data.setTitle(etTitle.getText().toString());
                data.setGenre(etGenre.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));
                TextView rating = (TextView) ratingSpinner.getSelectedView();
                data.setRating(rating.getText().toString());
                db.updateMovie(data);
                db.close();
                finish();
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(ModifyMovies.this);
                db.deleteMovie(data.getId());

                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}


