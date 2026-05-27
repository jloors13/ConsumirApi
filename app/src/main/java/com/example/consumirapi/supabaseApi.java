package com.example.consumirapi;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class supabaseApi extends AppCompatActivity {

    TextView txtListasupabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_supabase_api);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtListasupabase = findViewById(R.id.txtListasupabase);

        RequestQueue queue = Volley.newRequestQueue(this);

        // URL DE TU TABLA SUPABASE
        String url = "https://lfjyyzuiqpffituyvxnz.supabase.co/rest/v1/alumnos?select=*";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,

                response -> {

                    try {

                        StringBuilder texto = new StringBuilder();

                        for (int i = 0; i < response.length(); i++) {

                            texto.append(
                                    agregarAlumnosALista(
                                            response.getJSONObject(i),
                                            i + 1
                                    )
                            );
                        }

                        txtListasupabase.setText(texto.toString());

                    } catch (Exception e) {

                        txtListasupabase.setText(
                                "Error procesando datos:\n" + e.getMessage()
                        );
                    }
                },

                error -> txtListasupabase.setText(
                        "Error API:\n" + error.toString()
                )

        ) {

            @Override
            public Map<String, String> getHeaders() {

                Map<String, String> headers = new HashMap<>();

                headers.put("apikey", "xxxxx");
                headers.put("Authorization", "Bearer xxxxx");

                return headers;
            }
        };

        queue.add(request);
    }

    private String agregarAlumnosALista(JSONObject jsonAlumno, int i)
            throws JSONException {

        StringBuilder strAlumno = new StringBuilder();

        strAlumno.append(i)
                .append(".- ")
                .append(jsonAlumno.optString("apellidosNombres", ""))
                .append("\n");

        strAlumno.append("Cédula: ")
                .append(jsonAlumno.optString("cedula", ""))
                .append("\n");

        strAlumno.append("Correo Institucional: ")
                .append(jsonAlumno.optString("correoinstitucional", ""))
                .append("\n");

        strAlumno.append("Correo Microsoft: ")
                .append(jsonAlumno.optString("correoinstitucionalMicrosoft", ""))
                .append("\n\n");

        return strAlumno.toString();
    }
}