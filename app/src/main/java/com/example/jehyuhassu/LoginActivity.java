package com.example.jehyuhassu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jehyuhassu.databinding.FragmentLoginBinding;
import com.example.jehyuhassu.firebase_model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private FragmentLoginBinding binding;

    //firebase variable
    private FirebaseAuth mFirebaseAuth; //firebase 인증
    private DatabaseReference mDatabase;    //실시간 데이터 베이스 인스턴스

    //signup user info
    private String id, pw, studentId, name, college, department;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentLoginBinding.inflate(getLayoutInflater()); // 뷰 바인딩
        setContentView(binding.getRoot());

        //firebase setting
        mFirebaseAuth = FirebaseAuth.getInstance();   //파이어베이스 인증
        mDatabase = FirebaseDatabase.getInstance().getReference();   //인스턴스 초기화

        //signup user info
        pw = "user1pw";
        studentId = "20201853";
        name = "서민비";
        college = "IT대학";
        department = "컴퓨터학부";

        //button click listener
        btn = findViewById(R.id.u_SAINT_login_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Firebase 인증을 사용하여 사용자 등록
                mFirebaseAuth.createUserWithEmailAndPassword("seominb@naver.com", pw)    //email, pw
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //접속 성공
                                if (task.isSuccessful()) {
                                    // 사용자 등록 성공
                                    FirebaseUser firebaseuser = mFirebaseAuth.getCurrentUser();
                                    saveUserInfo(studentId, pw, name, college, department);
                                    Toast.makeText(LoginActivity.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                                } else {
                                    // 사용자 등록 실패
                                    Exception e = task.getException();
                                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                        // 잘못된 자격 증명 예외 처리
                                        Log.e("FirebaseAuth", "잘못된 자격 증명: " + e.getMessage());
                                    } else if (e instanceof FirebaseAuthUserCollisionException) {
                                        // 사용자 충돌 예외 처리 (이미 존재하는 사용자)
                                        Log.e("FirebaseAuth", "사용자 충돌: " + e.getMessage());
                                    } else {
                                        // 기타 예외 처리
                                        Log.e("FirebaseAuth", "기타 등록 실패: " + e.getMessage());
                                    }
                                    Toast.makeText(LoginActivity.this, "회원가입 실패. 다시 시도하세요.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }

    private void saveUserInfo(String studentId, String pw, String name, String college, String department) {
        // Firebase Realtime Database를 사용하여 사용자 정보 저장
        User user = new User(studentId, pw, name, college, department);
        mDatabase.child("Users").child(studentId).setValue(user);
    }
}

