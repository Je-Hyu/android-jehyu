package com.example.jehyuhassu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jehyuhassu.databinding.FragmentLoginBinding;

public class LoginActivityTest extends AppCompatActivity {
    //firebase test code
    private FragmentLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = FragmentLoginBinding.inflate(getLayoutInflater()); // 뷰 바인딩
        setContentView(binding.getRoot());
    }
}
