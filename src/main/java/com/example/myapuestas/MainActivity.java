package com.example.myapuestas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etNumero,etNombre,etCantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etNumero=findViewById(R.id.etNumero);
        etNombre=findViewById(R.id.etNombre);
        etCantidad=findViewById(R.id.etCantidad);


    }

    //Registro en la base de datos

    public void Registrar(View view){
        AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase BaseDeDatos=admin.getWritableDatabase();

        String numero= etNumero.getText().toString();
        String nombre = etNombre.getText().toString();
        String cantidad= etCantidad.getText().toString();

        if (!numero.isEmpty() && !nombre.isEmpty() && !cantidad.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("idp", numero);
            registro.put("nombre",nombre);
            registro.put("cantidad",cantidad);

            BaseDeDatos.insert("participantes", null,registro);
            BaseDeDatos.close();

            etNumero.setText("");
            etNombre.setText("");
            etCantidad.setText("");
            Toast.makeText(this, "Registro Exitoso",Toast.LENGTH_SHORT);
        }else{
            Toast.makeText(this,"Debes de ingresar todos los datos",Toast.LENGTH_SHORT);
        }
    }

    //Metodo para consultar un participante

    public void Buscar(View view){
        AdminSQLiteOpenHelper admin =new AdminSQLiteOpenHelper(this, "administracion",null,1);
        SQLiteDatabase BaseDeDatos= admin.getWritableDatabase();

        String numero=etNumero.getText().toString();
        if (!numero.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery("select nombre, cantidad from participantes where idp ="+numero,null);
            if (fila.moveToFirst()){
                etNombre.setText(fila.getString(0));
                etCantidad.setText(fila.getString(1));
                BaseDeDatos.close();
            }else{
                Toast.makeText(this, "El participante no esta Registrado", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "El participante no esta registrado",Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para eliminar o borrar registro

    public void Eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String numero = etNumero.getText().toString();

        if (!numero.isEmpty()){
            int numRegistro= BaseDeDatos.delete("participantes","idp="+numero,null);
            BaseDeDatos.close();

            etCantidad.setText("");
            etNombre.setText("");
            etNumero.setText("");

            if (numRegistro==1){
                Toast.makeText(this, "Participante borrado con exito",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"El participante no esta registrado",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"El participante no esta registrado",Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para Modificar un registro

    public void Modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null,1);
        SQLiteDatabase BaseDeDatos= admin.getWritableDatabase();


        String numero = etNumero.getText().toString();
        String nombre = etNombre.getText().toString();
        String cantidad = etCantidad.getText().toString();
        if (!numero.isEmpty() && !nombre.isEmpty() && !cantidad.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("idp",numero);
            registro.put("nombre",nombre);
            registro.put("cantidad",cantidad);

            int numRegistro = BaseDeDatos.update("participantes",registro,"idp="+numero,null);

            BaseDeDatos.close();
            if(numRegistro==1){
                Toast.makeText(this,"Participante Modificado",Toast.LENGTH_SHORT);
            }else{
                Toast.makeText(this,"El partcipante no esta en el sistema",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Debes de poner todos los campos",Toast.LENGTH_SHORT).show();
        }
    }
}










