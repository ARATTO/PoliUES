package poliues.eisi.fia.edu.sv.poliues;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SolicitudConsultarActivity extends AppCompatActivity {

    private ListView list;
    String[] solicitudes;
    AlertDialog alertDialog;
    AlertDialog alertDialogE;
    ControlBDPoliUES helper;
    Cursor cursor;
    Cursor cursor2;
    List<String> item = null;
    String datoAbuscar;
    Solicitante soli=null;
    int creador=0;
    String esAdmin=null;
    Bundle usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_consultar);

        usuario = getIntent().getExtras();


        esAdmin = usuario.getString("EnvioAdministradorIDENTIFICADOR");
        if(esAdmin==null){
            esAdmin="noEsAdmin";
            creador = usuario.getInt("IDUSUARIO");
            System.out.println(esAdmin);
        }
        else {
           // creador = usuario.getInt("EnvioAdministradorID");
            System.out.println(esAdmin);
        }

        soli = new Solicitante();
        soli.setIdSolicitante(creador);


        System.out.println("variable int: " +creador);
        System.out.println("OBJETO SOLICITUD " +soli.getIdSolicitante());


        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Que Accion desea Realizar");
        alertDialog.setButton("Consultar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                llamarConsultar();
            }
        });

        if (!(esAdmin.equals("admin"))){
            alertDialog.setButton2("Actualizar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    llamarActualizar();
                }
            });
            alertDialog.setButton3("Eliminar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    llamarEliminar();
                }
            });

        }



        alertDialogE = new AlertDialog.Builder(this).create();
        alertDialogE.setTitle("Seguro que quiere eliminar");
        alertDialogE.setButton("SI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                    eliminarSolicitud();
            }
        });
        alertDialogE.setButton2("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        list = (ListView) findViewById(R.id.listado);

        try {
            llenarSolicitudes();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "error de llenado", Toast.LENGTH_SHORT).show();
        }


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "Ha pulsado el item " + position, Toast.LENGTH_SHORT).show();
                datoAbuscar = (String)(list.getItemAtPosition(position));

                System.out.println(datoAbuscar);
                alertDialog.show();


            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if (esAdmin.equals("admin"))
            getMenuInflater().inflate(R.menu.principal,menu);
        else
            getMenuInflater().inflate(R.menu.opcionessolicitante,menu);

        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent;

        switch (id){
            case R.id.consultarSolicitud:
                intent = new Intent(this,SolicitudConsultarActivity.class);
                if (esAdmin.equals("admin")){
                    intent.putExtra("IDUSUARIO",usuario.getInt("EnvioAdministradorID"));
                }
                else {
                    intent.putExtra("IDUSUARIO",soli.getIdSolicitante());
                }
                intent.putExtra("EnvioAdministradorID",usuario.getInt("EnvioAdministradorID"));
                intent.putExtra("EnvioAdministradorNOMBRE",usuario.getString("EnvioAdministradorNOMBRE"));
                intent.putExtra("EnvioAdministradorPASS",usuario.getString("EnvioAdministradorPASS"));
                intent.putExtra("EnvioAdministradorCORREO",usuario.getString("EnvioAdministradorCORREO"));
                intent.putExtra("EnvioAdministradorIDENTIFICADOR",usuario.getString("EnvioAdministradorIDENTIFICADOR"));
                startActivity(intent);
                break;
            case R.id.actInsertar:
                intent = new Intent(this,SolicitudInsertarActivity.class);
                intent.putExtra("IDUSUARIO",soli.getIdSolicitante());
                startActivity(intent);
                break;
            case R.id.actPrincipalUsuario:
                intent = new Intent(this,PrincipalUsuario.class);
                intent.putExtra("EnvioSolicitanteID",soli.getIdSolicitante());
                startActivity(intent);
                break;
            case R.id.action_settings:
                intent = new Intent(this,principal.class);
                Administrador administrador = new Administrador();
                if (esAdmin.equals("admin")){
                    intent.putExtra("IDUSUARIO",usuario.getInt("EnvioAdministradorID"));
                }
                else {
                    intent.putExtra("IDUSUARIO",soli.getIdSolicitante());
                }

                intent.putExtra("EnvioAdministradorID",usuario.getInt("EnvioAdministradorID"));
                intent.putExtra("EnvioAdministradorNOMBRE",usuario.getString("EnvioAdministradorNOMBRE"));
                intent.putExtra("EnvioAdministradorPASS",usuario.getString("EnvioAdministradorPASS"));
                intent.putExtra("EnvioAdministradorCORREO",usuario.getString("EnvioAdministradorCORREO"));
                intent.putExtra("EnvioAdministradorIDENTIFICADOR",usuario.getString("EnvioAdministradorIDENTIFICADOR"));
                startActivity(intent);
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    public void llamarActualizar(){
        Intent o = new Intent(this,SolicitudActualizarActivity.class);
        o.putExtra("IDUSUARIO",soli.getIdSolicitante());
        o.putExtra("motivo",datoAbuscar);
        startActivity(o);
    }

    public void llamarEliminar(){
        alertDialogE.show();

    }

    public void llamarConsultar(){
        Intent o = new Intent(this, verSolicitudActivity.class);
        if (esAdmin.equals("admin")){
            o.putExtra("IDUSUARIO", usuario.getInt("EnvioAdministradorID"));
        }
        else {
            o.putExtra("IDUSUARIO", soli.getIdSolicitante());
        }
        o.putExtra("motivo",datoAbuscar);

        o.putExtra("EnvioAdministradorID", usuario.getInt("EnvioAdministradorID"));
        o.putExtra("EnvioAdministradorNOMBRE",usuario.getString("EnvioAdministradorNOMBRE"));
        o.putExtra("EnvioAdministradorPASS",usuario.getString("EnvioAdministradorPASS"));
        o.putExtra("EnvioAdministradorCORREO",usuario.getString("EnvioAdministradorCORREO"));
        o.putExtra("EnvioAdministradorIDENTIFICADOR",usuario.getString("EnvioAdministradorIDENTIFICADOR"));
        startActivity(o);
    }

    public void eliminarSolicitud(){
        helper = new ControlBDPoliUES(this);
        helper.leer();
        cursor = helper.consultarSolicitud();
        cursor2 = helper.consultarDetalleSolicitud();

        Solicitud s = new Solicitud();
        DetalleSolicitud d = new DetalleSolicitud();
        System.out.println(this.datoAbuscar);

        s = helper.buscarSolicitud(cursor,this.datoAbuscar);

        d = helper.buscarDetalleSolicitud(cursor2,s.getIdSolicitud());

        if (s ==null || d == null || !(s.getMotivoSolicitud().equals(this.datoAbuscar)) ){
            Toast.makeText(getApplicationContext(), "No se puede eliminar la solicitud ", Toast.LENGTH_SHORT).show();
        }
        else {
            System.out.println(s.getMotivoSolicitud());
            helper.eliminar(d);
            Toast.makeText(getApplicationContext(), "Se elimino correctamente ", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this,SolicitudConsultarActivity.class);
            intent.putExtra("IDUSUARIO",soli.getIdSolicitante());
            startActivity(intent);
        }


    }


    public void llenarSolicitudes(){
        helper = new ControlBDPoliUES(this);
        helper.abrir();
        cursor = helper.consultarSolicitud();
        Cursor cursor2 = helper.consultarDetalleSolicitud();
        int esta =0;
        //helper.cerrar();

        item = new ArrayList<String>();


        if (cursor.moveToFirst() && cursor2.moveToFirst()){
            do {


                    do {
                        if(cursor.getInt(0)== cursor2.getInt(1)){
                            esta+=1;
                        }

                    }while (cursor2.moveToNext());



                if (esta==0){
                    cursor2.moveToFirst();
                    helper.eliminarSolicitud(cursor.getInt(0));
                    esta=0;
                }else {
                    cursor2.moveToFirst();
                    esta=0;
                }

            }while (cursor.moveToNext());
        }


        Cursor cursor3 = helper.consultarSolicitud();

        if(cursor3.moveToFirst()){

            do {

                Solicitud solicitud = new Solicitud();


                solicitud.setIdSolicitud(cursor3.getInt(0));

                solicitud.setSolicitante(cursor3.getInt(4));

                solicitud.setMotivoSolicitud(cursor3.getString(5));

                if (soli.getIdSolicitante() == solicitud.getSolicitante() || esAdmin.equals("admin")){

                    item.add(solicitud.getMotivoSolicitud());

                }




            }while(cursor3.moveToNext());
        }


        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item);
        list.setAdapter(adaptador);
    }

}
