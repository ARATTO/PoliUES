package poliues.eisi.fia.edu.sv.poliues;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class verSolicitudActivity extends AppCompatActivity {
    ControlBDPoliUES helper;
    EditText actividadET;
    EditText areaET;
    EditText motivoET;
    EditText FI;
    EditText FF;
    EditText CT;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_solicitud);

        Bundle bundle = getIntent().getExtras();

        helper = new ControlBDPoliUES(this);

        actividadET = (EditText) findViewById(R.id.editActividad);
        areaET = (EditText) findViewById(R.id.editArea);
        motivoET= (EditText) findViewById(R.id.editMotivo);
        FI= (EditText) findViewById(R.id.editFechaInicio);
        FF= (EditText) findViewById(R.id.editFechaFin);
        CT= (EditText) findViewById(R.id.editCobro);

        verDatos(bundle.getString("motivo"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.opcionessolicitante,menu);

        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.opcionesMenu) {
            Intent intent = new Intent(this,SolicitudConsultarActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void verDatos(String motivo) {
        helper = new ControlBDPoliUES(this);
        helper.leer();
        Cursor cursor = helper.consultarSolicitud();
        //helper.cerrar();

        Cursor cursor2 = helper.consultarDetalleSolicitud();

        Solicitud solicitud = null;
        DetalleSolicitud DS = null;

        solicitud = helper.buscarSolicitud(cursor,motivo);

        DS = helper.buscarDetalleSolicitud(cursor2,solicitud.getIdSolicitud());


        if(solicitud == null && DS == null){
            Toast.makeText(this,"no encontrado", Toast.LENGTH_LONG).show();
        }
        else{
            actividadET.setText(String.valueOf(solicitud.getActividad()));
            motivoET.setText(solicitud.getMotivoSolicitud());
            areaET.setText(String.valueOf(DS.getArea()));
            FI.setText(DS.getFechaInicio());
            FF.setText(DS.getFechaFinal());
            CT.setText(String.valueOf(DS.getCobroTotal()));
        }
    }
}