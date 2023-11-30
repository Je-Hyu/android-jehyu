package com.example.jehyuhassu;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jehyuhassu.databinding.FragmentLoginBinding;
import com.example.jehyuhassu.firebase_model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivityTest extends AppCompatActivity {
    //firebase test code
    private FragmentLoginBinding binding;

    //firebase variable
    private DatabaseReference mDatabase;    //실시간 데이터 베이스 인스턴스

    //signup user info
    private String id, pw, studentId, name, college, department;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = FragmentLoginBinding.inflate(getLayoutInflater()); // 뷰 바인딩
        setContentView(binding.getRoot());

        //firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();   //데이터 베이스 인스턴스 초기화

        //user info
        //signup user info
        id = "user2id";
        pw = "user2pw";
        studentId = "20201854";
        name = "김민비";
        college = "사회과학대학";
        department = "사회학부";

        btn = findViewById(R.id.u_SAINT_login_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입
                saveUserInfo(id, pw, studentId, name, college, department);
            }
        });
    }

    private void saveUserInfo(String id, String pw, String studentId, String name, String college, String department) {
        // Firebase Realtime Database를 사용하여 사용자 정보 저장
        String token = mDatabase.push().getKey();   //Realtime Database에 고유한 키를 생성
        Log.d("minb", token);
        User user = new User(id, pw, studentId, name, college, department);
        mDatabase.child("Users").child(studentId).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // 데이터 저장 성공
                            Toast.makeText(LoginActivityTest.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                        } else {
                            // 데이터 저장 실패
                            Toast.makeText(LoginActivityTest.this, "회원가입 실패. 다시 시도하세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
