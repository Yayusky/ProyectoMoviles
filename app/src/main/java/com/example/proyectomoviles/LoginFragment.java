package com.example.proyectomoviles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomoviles.api.RetrofitCliente;
import com.example.proyectomoviles.api.UsuarioApiServicio;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {
    private EditText editEmail, editPassword;
    private Button btnLogin;

    private TextView btnRegistro;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        editEmail = view.findViewById(R.id.editEmail);
        editPassword = view.findViewById(R.id.editPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnRegistro = view.findViewById(R.id.textSignUp);

        btnRegistro.setOnClickListener(v -> {
            NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(R.id.action_loginFragment_to_registroUsuarioFragment);
        });


        btnLogin.setOnClickListener(v -> {
            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();

            UsuarioApiServicio api = RetrofitCliente.getUsuarioService();
            Map<String, String> body = new HashMap<>();
            body.put("email", email);
            body.put("pa$$", password);

            Call<Map<String, Object>> call = api.loginUsuario(body);
            call.enqueue(new Callback<Map<String, Object>>() {
                @Override
                public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "Login correcto", Toast.LENGTH_SHORT).show();
                        // Puedes navegar al home u otra pantalla aquí
                        NavHostFragment.findNavController(LoginFragment.this)
                                .navigate(R.id.action_loginFragment_to_homeFragment);

                    } else {
                        Toast.makeText(getContext(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });



        return view;
    }
}
