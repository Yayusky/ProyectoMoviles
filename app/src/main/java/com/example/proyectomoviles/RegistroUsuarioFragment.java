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
import android.widget.Toast;

import com.example.proyectomoviles.api.RetrofitCliente;
import com.example.proyectomoviles.api.UsuarioApiServicio;
import com.example.proyectomoviles.modelos.Usuario;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistroUsuarioFragment extends Fragment {
    private EditText editName, editEmail, editPassword;
    private Button btnSignUp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro_usuario, container, false);

        editName = view.findViewById(R.id.editName);
        editEmail = view.findViewById(R.id.editEmail);
        editPassword = view.findViewById(R.id.editPassword);
        btnSignUp = view.findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(v -> {
            String name = editName.getText().toString();
            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();

            Usuario usuario = new Usuario();
            usuario.setName(name);
            usuario.setEmail(email);
            usuario.setPa$$(password);

            UsuarioApiServicio api = RetrofitCliente.getUsuarioService();
            Call<Map<String, Object>> call = api.registrarUsuario(usuario);

            call.enqueue(new Callback<Map<String, Object>>() {
                @Override
                public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                        // Aquí navega a LoginFragment, Home o donde quieras
                        NavHostFragment.findNavController(RegistroUsuarioFragment.this)
                                .navigate(R.id.action_registroUsuarioFragment_to_loginFragment);


                    } else {
                        Toast.makeText(getContext(), "Registro fallido: " + response.message(), Toast.LENGTH_SHORT).show();
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
