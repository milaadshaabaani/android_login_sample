package com.example.login_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login_android.model.Login;
import com.example.login_android.model.User;
import com.example.login_android.service.UserClient;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://192.168.1.224:8000/")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);

    private static  String token = "Bearer ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         EditText emailInput  = findViewById(R.id.emailInput);
         EditText passwordInput  = findViewById(R.id.passwordInput);
         Button loginButton = findViewById(R.id.loginButton);
         Button getDataButton = findViewById(R.id.getDateButton);
         Pattern pattern = Pattern.compile("^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$",Pattern.CASE_INSENSITIVE);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailInput.getText().toString();
                if(pattern.matcher(email).matches())
                {

                    login(email,passwordInput.getText().toString());
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Email Not Correct :(",Toast.LENGTH_LONG).show();
                }
            }
        });



        getDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(token.equals("Bearer "))
                {
                    Toast.makeText(MainActivity.this,"You first Login!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    getData();
                }

            }
        });
    }


    private void login(String email,String password)
    {
        Login login = new Login(email,password);
        Call<User> call = userClient.login(login);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    token += response.body().getToken();
                    Toast.makeText(getApplicationContext(),"Login Successfully :)",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Login failed, Check your password!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }



    private void getData()
    {
        Call<ResponseBody> call = userClient.getSecretInfo(token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Toast.makeText(getApplicationContext(),"Data Recived", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,SecActivity.class);
                        intent.putExtra("Data",response.body().string());
                        startActivity(intent);
                    }catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Your are logout, please login!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}