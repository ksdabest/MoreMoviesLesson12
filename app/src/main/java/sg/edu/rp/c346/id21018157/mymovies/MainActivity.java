package sg.edu.rp.c346.id21018157.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    //test push

    Spinner spinner;
    String rating;
    TextView tvID, tvTitle, tvGenre, tvYear;
    EditText etID, etTitle, etGenre, etYear;
    Button btnInsert, btnShowList;
    ArrayList<Movie> al;
    ArrayAdapter<Movie> aa;

    Movie data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvID = findViewById(R.id.tvID);
        tvTitle = findViewById(R.id.tvTitle);
        tvGenre = findViewById(R.id.tvGenre);
        tvYear = findViewById(R.id.tvYear);
        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);
        spinner = findViewById(R.id.ratingSpinner);

        al = new ArrayList<Movie>();
        //changed for CA
        aa = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, al);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        rating = "G";
                        break;

                    case 1:
                        rating = "PG";
                        break;

                    case 2:
                        rating = "PG13";
                        break;

                    case 3:
                        rating = "NC16";
                        break;

                    case 4:
                        rating = "M18";
                        break;

                    case 5:
                        rating = "R21";
                        break;

                    default:
                        rating = " ";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String genre = etGenre.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());

                DBHelper db = new DBHelper(MainActivity.this);
                long inserted_id = db.insertMovies(title, genre, year, rating);

                if (inserted_id != -1) {
                    al.clear();
                    al.addAll(db.getAllMovies()); //to add the songs retrieved from the database using dbh.getAllSongs() into the arraylist.
                    aa.notifyDataSetChanged();

                }
                Toast.makeText(MainActivity.this, "Insert successful", Toast.LENGTH_SHORT).show();
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,
                        ShowMovies.class);
                startActivity(i);
            }
        });
    }
}
//new