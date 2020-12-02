package com.example.e_fir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class registerFIR extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText etName, etFather, etLocation, etDescription;
    Spinner spinnerDistrict, spinnerCategory;
    Button btnRegister;
    String name, father, number, location, district, category, caseDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_f_i_r);

        etName = (EditText) findViewById(R.id.etName);
        etLocation = (EditText) findViewById(R.id.etLocation);
        etFather = (EditText) findViewById(R.id.etFather);
        etDescription = (EditText) findViewById(R.id.etDescription);
        spinnerDistrict = (Spinner) findViewById(R.id.spinnerDistrict);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        number = getIntent().getStringExtra("mobile");

        ArrayAdapter<CharSequence> districtAdapter = ArrayAdapter.createFromResource(this,
                R.array.district_list, R.layout.support_simple_spinner_dropdown_item);
        districtAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinnerDistrict.setAdapter(districtAdapter);
        spinnerDistrict.setOnItemSelectedListener(registerFIR.this);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.category_list, R.layout.support_simple_spinner_dropdown_item);
        categoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(categoryAdapter);
        spinnerCategory.setOnItemSelectedListener(registerFIR.this);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(etName.getText().toString().trim().isEmpty() || etFather.getText().toString().trim().isEmpty() ||
                        etLocation.getText().toString().trim().isEmpty() || etDescription.getText().toString().trim().isEmpty() ||
                        district.equals("Select District") || category.equals("Select Category"))
                {
                    Toast.makeText(registerFIR.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    name = etName.getText().toString().trim();
                    father = etFather.getText().toString().trim();
                    location = etLocation.getText().toString().trim();
                    caseDescription = etDescription.getText().toString().trim();
                    SaveFunction(name,father,location,district,category,caseDescription,number);

                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId() == R.id.spinnerDistrict)
            district = parent.getItemAtPosition(position).toString();

        if(parent.getId() == R.id.spinnerCategory)
            category = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void SaveFunction(String name,String father,String location,String district,String category,String caseDescription,String number){
        String url = "https://dry-anchorage-43299.herokuapp.com/firs";
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(registerFIR.this));
        JSONObject o = new JSONObject();
        try{
            o.put("name",name);
            o.put("father",father);
            o.put("location",location);
            o.put("district",district);
            o.put("category",category);
            o.put("description",caseDescription);
            o.put("number",number);
            o.put("status","Pending");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, o, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(registerFIR.this,"Your FIR has been registered",Toast.LENGTH_SHORT).show();
                Intent i1 = new Intent(registerFIR.this,registerFIR.class);
                startActivity(i1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);}


}