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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
import com.example.jehyuhassu.firebase_model.User;

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

    //minb: 리얼타임 데이터 베이스 인스턴스
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //minb: 리얼타임 데이터 베이스 인스턴스 초기화
        mDatabase = FirebaseDatabase.getInstance().getReference();
        retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);

        binding.loginBtn.setOnClickListener(view -> {
            Log.d("seo", "로그인 버튼 클릭");
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

                            //minb: 회원가입 함수 호출
                            Log.d("seo", "회원가입 함수 호출 전");
                            saveUserInfo(Integer.parseInt(studentNum), password, name, department, major);
                            Log.d("seo", "회원가입 함수 호출 후");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }

                });
            }
        });
    }

    //minb: 회원가입 함수
    private void saveUserInfo(int studentId, String pw, String name, String college, String department) {
        // Firebase Realtime Database를 사용하여 사용자 정보 저장
        User user = new User(studentId, pw, name, college, department);
        Log.d("seo", "회원가입 함수");
        mDatabase.child("Users").child(String.valueOf(studentId)).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // 데이터 저장 성공
                            Toast.makeText(LoginActivity.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                            Log.d("minb", "데이터 저장 성공!");

                        } else {
                            // 데이터 저장 실패
                            Toast.makeText(LoginActivity.this, "회원가입 실패. 다시 시도하세요.", Toast.LENGTH_SHORT).show();
                            Log.e("minb", "데이터 저장 실패", task.getException());
                        }
                    }
                });
    }

    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}