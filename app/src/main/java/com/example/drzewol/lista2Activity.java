package com.example.drzewol;

        import androidx.appcompat.app.AppCompatActivity;
//        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.io.BufferedReader;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStreamReader;

public class lista2Activity extends AppCompatActivity {
    private static final String FILE_NAME = "lastReport.txt";

    TextView mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista2);

        mEditText = findViewById(R.id.textViewOut);
        load(null);
    }

    /*public void save(View v) {
        String text = mEditText.getText().toString();
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());

            mEditText.getText().clear();
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    public void load(View v) {
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text, LAT, LONG;

            if((text = br.readLine()) != null) {
                sb.append("\n\tTytuł:\n");
                sb.append(text).append("\n");

                LAT = br.readLine();
                LONG = br.readLine();

                sb.append("\n\tOpis:\n");
                text = br.readLine();
                sb.append(text).append("\n");
                while ((text = br.readLine()) != null)
                    sb.append(text).append("\n");

                sb.append("\n\tSzerokość geograficzna:\n");
                sb.append(LAT);
                sb.append("\n\tDługość geograficzna:\n");
                sb.append(LONG);
            } else {
                sb.append("\n\tNie masz jeszcze żadnego zgłoszenia!\n");
            }

            mEditText.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
