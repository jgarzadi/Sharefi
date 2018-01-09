package com.sharefi.srcit.sharefi.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sharefi.srcit.sharefi.R;
import com.sharefi.srcit.sharefi.app.Connectivity;
import com.sharefi.srcit.sharefi.database.DBHandler;
import com.sharefi.srcit.sharefi.models.Connection;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // ui
    private TextView textViewWifiName;
    private TextView textViewWifiStatus;
    private TextView textViewLinkSpeed;
    private TextView textViewWifiFrequency;
    private ImageButton btnSync;
    private Button btnSave;
    private Button btnEdit;
    private Button btnShare;

    // Database
    DBHandler db;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHandler(this);

        //Enlazar elementos de la vista
        BindUI();

        //Acciones de elementos
        SetActionsToElements();

        LoadActivity();

    }

    private void LoadActivity() {
        Connectivity currentConn = new Connectivity(this);

        if (currentConn.checkConnectivity() && currentConn.ConnWifiInfo() != null) {

            SetTextViewValues(currentConn.getConnName(), "Conectado", currentConn.getConnSpeed().toString() + " Mbps", currentConn.getConnFrequency().toString() + " MHz");

            //Revisa si la red existe en la base de datos
            if (getConnFromDatabase() != null) {
                // Deshabilita boton guardar
                EnableDisableMainButtons(false, true, true);
            } else {
                // Deshabilita boton editar y compartir
                EnableDisableMainButtons(true, false, false);
            }

        } else {
            //SetTextViewValues((String) R.string.dash, R.string.dash, R.string.dash, R.string.dash);
            SetTextViewValues("-", "-", "-", "-");

            EnableDisableMainButtons(false, false, false);

            Toast.makeText(this, "No estas conectado a una red WiFi", Toast.LENGTH_LONG).show();
        }
    }

    private void BindUI() {
        textViewWifiName = (TextView) findViewById(R.id.textView_WifiName);
        textViewWifiStatus = (TextView) findViewById(R.id.textView_WifiStatus);
        textViewLinkSpeed = (TextView) findViewById(R.id.textView_WifiSpeed);
        textViewWifiFrequency = (TextView) findViewById(R.id.textView_WifiFrequency);
        btnSync = (ImageButton) findViewById(R.id.button_sync);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnShare = (Button) findViewById(R.id.btnShare);
    }

    private void SetActionsToElements() {
        // boton sincronizar o recargar
        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                //startActivity(getIntent());
                LoadActivity();
            }
        });

        // boton guardar
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuildAndShowSaveDialog();
            }
        });

        // boton compartir
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection dbConn = getConnFromDatabase();
                if (dbConn != null)
                    ShareConnection(dbConn.getName(), dbConn.getPasscode());
            }
        });
    }

    private void BuildAndShowSaveDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.new_conn_title)
                .content(R.string.new_conn_content)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("Contraseña", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        //TODO: insertar datos de la nueva red a la base de datos
                        db.addConnection(new Connection(textViewWifiName.getText().toString(), input.toString()));
                        LoadActivity();
                    }
                }).show();
    }

    private void ShareConnection(String connName, String connPass) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "WiFi: " + connName + "\nContraseña: " + connPass);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void EnableDisableMainButtons(Boolean isSave, Boolean isEdit, Boolean isShare) {
        btnSave.setEnabled(isSave);
        btnEdit.setEnabled(isEdit);
        btnShare.setEnabled(isShare);
    }

    private void SetTextViewValues(String connName, String connState, String mbps, String mhz) {
        textViewWifiName.setText(connName);
        textViewWifiStatus.setText(connState);
        textViewLinkSpeed.setText(mbps);
        textViewWifiFrequency.setText(mhz);
    }

    private Connection getConnFromDatabase() {
        return db.getConnection(textViewWifiName.getText().toString());
    }

}