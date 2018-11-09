package com.itche.gibranhr.encriptacion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editTexto;
    EditText editValor;
    TextView Texto;
    TextView Texto2;
    Button Button;
    Button Button3;
    String mensaje = " ";
    String signo = "$";
    String busca="";
    int valor;
    int posicion;
    String tabla = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String tablaInvertida = "ZYXWVUTSRQPONMLKJIHGFEDCBA";
    String resultado = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button = findViewById(R.id.button);
        Button3 = findViewById(R.id.button3);

        editTexto = (EditText) findViewById(R.id.editTexto);
        editValor = (EditText) findViewById(R.id.editValor);
        Texto = (TextView) findViewById(R.id.edMensaje);
        Texto2 = (TextView) findViewById(R.id.edMensaje2);

        //Verificación de campos para encriptar
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTexto.getText().toString().length() == 0 && editValor.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Ingresa los datos", Toast.LENGTH_SHORT).show();
                    Texto.setText(" ");
                    editTexto.setError(editTexto.getResources().getString(R.string.CampoObligatorio));
                    editValor.setError(editValor.getResources().getString(R.string.CampoObligatorio));

                }else if (editTexto.getText().toString().length() == 0 && editValor.getText().toString().length() != 0) {
                    Toast.makeText(getApplicationContext(), "Ingresa mensaje", Toast.LENGTH_SHORT).show();
                    Texto.setText(" ");
                    editTexto.setError(editTexto.getResources().getString(R.string.CampoObligatorio));

                }else if (editValor.getText().toString().length() == 0 && editTexto.getText().toString().length() != 0 ) {
                    Toast.makeText(getApplicationContext(), "Ingresa la clave", Toast.LENGTH_SHORT).show();
                    Texto.setText(" ");
                    editValor.setError(editValor.getResources().getString(R.string.CampoObligatorio));

                }else{
                    generarEncriptacion(view);
                    editTexto.setError(null);
                    editValor.setError(null);
                }
            }
        });

        //Verificación de campos para desencriptar
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTexto.getText().toString().length() == 0 && editValor.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Ingresa los datos", Toast.LENGTH_SHORT).show();
                    Texto.setText(" ");
                    editTexto.setError(editTexto.getResources().getString(R.string.CampoObligatorio));
                    editValor.setError(editValor.getResources().getString(R.string.CampoObligatorio));

                }else if (editTexto.getText().toString().length() == 0 && editValor.getText().toString().length() != 0) {
                    Toast.makeText(getApplicationContext(), "Ingresa mensaje encriptado", Toast.LENGTH_SHORT).show();
                    Texto.setText(" ");
                    editTexto.setError(editTexto.getResources().getString(R.string.CampoObligatorio));

                }else if (editValor.getText().toString().length() == 0 && editTexto.getText().toString().length() != 0 ) {
                    Toast.makeText(getApplicationContext(), "Ingresa la clave", Toast.LENGTH_SHORT).show();
                    Texto.setText(" ");
                    editValor.setError(editValor.getResources().getString(R.string.CampoObligatorio));

                }else{
                    desencriptar(view);
                    editTexto.setError(null);
                    editValor.setError(null);
                }
            }
        });
    }

    //Cambia letras acentuadas
    private static final String ORIGINAL
            = "ÁÀÄáàäÉÈËéèëÍÌÏíìïÓÒÖóòöÚÙÜúùüÑñ";
    private static final String REPLACEMENT
            = "AAAaaaEEEeeeIIIiiiOOOoooUUUuuuNn";

    public static String stripAccents(String str){
        if (str == null){
            return null;
        }
        char[] array = str.toCharArray();
        for (int index = 0; index < array.length; index++){
            int pos = ORIGINAL.indexOf(array[index]);
            if (pos > -1){
                array[index] = REPLACEMENT.charAt(pos);
            }
        }
        return new String(array);
    }

    //Método que genera la encriptación
    public void generarEncriptacion(View v){
        //Variables
        mensaje = (editTexto.getText().toString().toUpperCase());
        valor = Integer.parseInt(editValor.getText().toString());
        busca = mensaje.substring(mensaje.length() - 1);

        //Verifica clave 0
        if (signo.equalsIgnoreCase(busca)){
            if(valor == 0){
                mensaje.replace("$", " ");
                Toast.makeText(getApplicationContext(), "No hay clave de encriptación", Toast.LENGTH_SHORT).show();
                Texto.setText("Mensaje sin encriptación: " + "\n" + mensaje.toUpperCase());
                resultado = " ";
            }

        //verifica clave positiva
            else if(valor > 0) {
                mensaje.replace("$", " ");
                for(int i = 0; i < mensaje.length(); i++){
                    posicion = tabla.indexOf(stripAccents(mensaje).charAt(i));
                    if((posicion + valor) < tabla.length()){
                        resultado += tabla.charAt(posicion + valor);
                    }else{
                        resultado += tabla.charAt((posicion + valor) - tabla.length());
                    }
                    }
                    Texto.setText("Mensaje encriptado: " + "\n" + resultado.toUpperCase());
                    resultado = " ";
                }

        //verifica clave negativa
            else if(valor < 0){
                mensaje.replace("$", " ");
                //Convierte el valor a positivo (hice trampa lo se, pero solo asi pude resolverlo)
                valor *= (-1);
                for(int i = 0; i < mensaje.length(); i++){
                    posicion = tablaInvertida.indexOf(stripAccents(mensaje).charAt(i));
                    if((posicion + valor) < tablaInvertida.length()){
                        resultado += tablaInvertida.charAt(posicion + valor);
                    }else{
                        resultado += tablaInvertida.charAt((posicion + valor) - tablaInvertida.length());
                    }
                }
                Texto.setText("Mensaje encriptado: " + "\n" + resultado.toUpperCase());
                resultado = " ";
            }
        }else{
            Toast.makeText(getApplicationContext(), "Ingresa $", Toast.LENGTH_SHORT).show();
            Texto.setText("");
        }

    }

    //Método que genera la encriptación
    public void desencriptar(View v){
        //Variables
        mensaje = (editTexto.getText().toString().toUpperCase());
        valor = Integer.parseInt(editValor.getText().toString());
        busca = mensaje.substring(mensaje.length() - 1);

        //Verifica clave 0
            if(valor == 0){
                mensaje.replace(" ", "$");
                Toast.makeText(getApplicationContext(), "No hay clave de encriptación", Toast.LENGTH_SHORT).show();
                Texto.setText("Mensaje sin encriptación: " + "\n" + mensaje.toUpperCase());
                resultado = " ";
            }

            //verifica clave positiva
            else if(valor > 0) {
                mensaje.replace(" ", "$");
                for(int i = 0; i < mensaje.length(); i++){
                    posicion = tabla.indexOf(stripAccents(mensaje).charAt(i));
                    if((posicion - valor) < 0){
                        resultado += tabla.charAt((posicion - valor) + tabla.length());
                    }else{
                        resultado += tabla.charAt(posicion - valor);
                    }
                }
                Texto.setText("Mensaje encriptado: " + "\n" + resultado.toUpperCase());
                resultado = " ";
            }

            //verifica clave negativa
            else if(valor < 0){
                mensaje.replace(" ", "$");
                //Convierte el valor a positivo (hice trampa de nuevo lo se)
                valor *= (-1);
                for(int i = 0; i < mensaje.length(); i++){
                    posicion = tablaInvertida.indexOf(stripAccents(mensaje).charAt(i));
                    if((posicion - valor) < 0){
                        resultado += tablaInvertida.charAt((posicion - valor) + tabla.length());
                    }else{
                        resultado += tablaInvertida.charAt(posicion - valor);
                    }
                }
                Texto.setText("Mensaje encriptado: " + "\n" + resultado.toUpperCase());
                resultado = " ";
            }
    }

    //Elimina los datos de los campos
    public void LimpiarCampos(View V){
        editTexto.setText(null);
        editValor.setText(null);
        Texto.setText(null);
        Texto2.setText(null);
        Toast.makeText(this, "Datos eliminados", Toast.LENGTH_SHORT).show();
    }
}
