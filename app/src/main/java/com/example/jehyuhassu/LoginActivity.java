package com.example.jehyuhassu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jehyuhassu.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.jehyuhassu.MainActivity;
import com.example.jehyuhassu.RetrofitClient;
import com.example.jehyuhassu.RetrofitInterface;
import com.example.jehyuhassu.Member;
import com.example.jehyuhassu.LoginRequest;
import com.example.jehyuhassu.LoginResponse;
import com.example.jehyuhassu.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private String studentNum;
    private String password;

    private RetrofitInterface retrofitInterface;

    private SharedPreferences preferences;

    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        database = FirebaseFirestore.getInstance();

        binding.loginBtn.setOnClickListener(view -> {
            studentNum = binding.loginStudentNumEditText.getText().toString();
            password = binding.loginPasswordEditText.getText().toString();

            if (studentNum.equals("")) {
                showToast("학번을 입력해주세요");
            } else if (password.equals("")) {
                showToast("비밀번호를 입력해주세요");
            } else {
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setStudentNum(studentNum);
                loginRequest.setPassword(password);
                retrofitInterface.postLogin(loginRequest).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            LoginResponse loginResponse = response.body();
                            assert loginResponse != null;

                            String department = loginResponse.getParentDept();
                            String major = loginResponse.getDept();
                            String name = loginResponse.getName();
                            Member member = new Member(studentNum, department, name, major);

                            database.collection("Member")
                                    .whereEqualTo("studentNum", studentNum)
                                    .get()
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            // firestore에 해당 학번의 멤버가 없다면 추가
                                            if (task.getResult().getDocuments().isEmpty()) {
                                                database.collection("Member")
                                                        .add(member)
                                                        .addOnCompleteListener(task1 -> {
                                                            SharedPreferences.Editor editor = preferences.edit();
                                                            editor.putString("studentNum", studentNum);
                                                            editor.putString("depart", department);
                                                            editor.putString("name", name);
                                                            editor.putString("major", major);
                                                            editor.apply();

                                                            Log.d("LoginActivity", preferences.getString("studentNum", ""));
                                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                        });
                                            } else { // firestore에 해당 학번의 멤버가 있다면
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.putString("studentNum", studentNum);
                                                editor.putString("depart", department);
                                                editor.putString("name", name);
                                                editor.putString("major", major);
                                                editor.apply();

                                                Log.d("LoginActivity", preferences.getString("studentNum", ""));
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            }
                                        }
                                    });

                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }

                });
            }
        });
    }

    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}