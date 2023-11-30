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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivityTest extends AppCompatActivity {
    //firebase test code
    private FragmentLoginBinding binding;

    //firebase variable
    private DatabaseReference mDatabase;    //실시간 데이터 베이스 인스턴스

    //signup user info
    private String studentId, pw, name, college, department;
    private Button signup_btn, login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = FragmentLoginBinding.inflate(getLayoutInflater()); // 뷰 바인딩
        setContentView(binding.getRoot());

        //firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();   //데이터 베이스 인스턴스 초기화

        //user info
        //signup user info
        studentId = "20201855";
        pw = "user3pw";
        name = "박민비";
        college = "IT대학";
        department = "컴퓨터학부";

        signup_btn = findViewById(R.id.u_SAINT_login_btn);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입
                saveUserInfo(studentId, pw, name, college, department);
            }
        });

        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                deleteUserInfo("20201850");

            }
        });
    }

    private void saveUserInfo(String studentId, String pw, String name, String college, String department) {
        // Firebase Realtime Database를 사용하여 사용자 정보 저장
//        String token = mDatabase.push().getKey();   //Realtime Database에 고유한 키를 생성
//        Log.d("minb", token);
        User user = new User(studentId, pw, name, college, department);
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

    private void deleteUserInfo(String studentId){
        mDatabase.child("Users").child(studentId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // 데이터 삭제 성공
                    Log.d("FirebaseExample", "데이터 삭제 성공!");
                } else {
                    // 데이터 삭제 실패
                    Log.e("FirebaseExample", "데이터 삭제 실패", task.getException());
                }
            }
        });

    }

    private void checkUserInfo(String studentId){
        // 데이터베이스에서 해당 학번의 사용자 정보 조회
//        mDatabase.child("Users").child(studentId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    // 사용자 정보가 존재함
//                    Toast.makeText(LoginActivityTest.this, "사용자 정보가 존재합니다.", Toast.LENGTH_SHORT).show();
//                    // 데이터가 존재할 때의 처리
//                    String pw = snapshot.child("password").getValue(String.class);
//                    String name = snapshot.child("name").getValue(String.class);
//                    String college = snapshot.child("college").getValue(String.class);
//                    String department = snapshot.child("department").getValue(String.class);
//                    Log.d("minb", "Name: " + name + ", college: " + college + ", department: " + department + ", pw" + pw);
//                } else {
//                    // 사용자 정보가 존재하지 않음
//                    Toast.makeText(LoginActivityTest.this, "사용자 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // 조회 중 오류 발생
//                Toast.makeText(LoginActivityTest.this, "데이터베이스 조회 오류", Toast.LENGTH_SHORT).show();
//            }
//        });

        mDatabase.child("Users").child(studentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // 사용자 정보가 존재함
                    Toast.makeText(LoginActivityTest.this, "사용자 정보가 존재합니다.", Toast.LENGTH_SHORT).show();
                    // 데이터가 존재할 때의 처리
                    String pw = snapshot.child("password").getValue(String.class);
                    String name = snapshot.child("name").getValue(String.class);
                    String college = snapshot.child("college").getValue(String.class);
                    String department = snapshot.child("department").getValue(String.class);
                    Log.d("minb", "Name: " + name + ", college: " + college + ", department: " + department + ", pw" + pw);
                } else {
                    // 사용자 정보가 존재하지 않음
                    Toast.makeText(LoginActivityTest.this, "사용자 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                    Log.d("minb", "사용자 정보 없음");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 조회 중 오류 발생
                Toast.makeText(LoginActivityTest.this, "데이터베이스 조회 오류", Toast.LENGTH_SHORT).show();
                Log.d("minb", "데이터베이스 조회 오류");
            }
        });

    }
}
