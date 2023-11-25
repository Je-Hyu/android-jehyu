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
    private EditText studentNumField;
    private String studentNum;
    private EditText passwordField;

    private Button loginBtn;
    private String password;

    private RetrofitInterface retrofitInterface;

    private SharedPreferences preferences;

    private FirebaseFirestore database;

    private String imgUrl = "https://images.unsplash.com/photo-1671716784499-a3d26826d844?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        studentNumField = binding.loginStudentNumEditText;
        passwordField = binding.loginPasswordEditText;
        loginBtn = binding.loginBtn;
        retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        database = FirebaseFirestore.getInstance();

        loginBtn.setOnClickListener(view -> {

            studentNum = String.valueOf(studentNumField.getText());
            password = String.valueOf(passwordField.getText());
//            Log.d("LoginActivity", studentNum);
//            Log.d("LoginActivity", password);
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
                            ArrayList<String> memberList = new ArrayList<>();

                            database.collection("Member")
                                    .whereIn("studentNum", Arrays.asList(studentNum))
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.getResult().getDocuments().size()==0){
                                                Member member = new Member(studentNum, department, name, major);
                                                Log.d("LoginActivity", "파이어 스토어접근");
                                                database.collection("Member").add(member).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                                        Log.d("LoginActivity", "파이어스토어 저장");
                                                        SharedPreferences.Editor editor = preferences.edit();
                                                        editor.putString("studentNum", studentNum);
                                                        editor.putString("depart", department);
                                                        editor.putString("name", name);
                                                        editor.putString("major", major);
                                                        editor.putString("img",imgUrl);
                                                        //항상 commit & apply 를 해주어야 저장이 된다.
                                                        editor.commit();
                                                        editor.apply();
                                                        Log.d("LoginActivity", preferences.getString("studentNum", ""));
                                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                                    }
                                                });

                                            }else{

                                                Log.d("LoginActivity", task.getResult().getDocuments().toString());
                                                Log.d("LoginActivity", "파이어 스토어접근");
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.putString("studentNum", studentNum);
                                                editor.putString("depart", department);
                                                editor.putString("name", name);
                                                editor.putString("major", major);
                                                editor.putString("img",imgUrl);
                                                //항상 commit & apply 를 해주어야 저장이 된다.
                                                editor.commit();
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

    public void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}