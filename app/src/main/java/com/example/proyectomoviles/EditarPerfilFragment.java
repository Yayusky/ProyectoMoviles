package com.example.proyectomoviles;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyectomoviles.api.RetrofitCliente;
import com.example.proyectomoviles.api.UsuarioApiServicio;
import com.example.proyectomoviles.modelos.Usuario;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditarPerfilFragment extends Fragment {

    private TextInputEditText editarLogin, editarPass;
    private Button btnGuardar;
    private UsuarioApiServicio usuarioApi;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editar_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editarLogin    = view.findViewById(R.id.editarPerfil);
        editarPass = view.findViewById(R.id.edtPassword);
        btnGuardar  = view.findViewById(R.id.btnGuardar);
        usuarioApi = RetrofitCliente.getUsuarioService();

        SharedPreferences prefs = requireContext()
                .getSharedPreferences("usuarioPrefs", Context.MODE_PRIVATE);
        String usuarioId = prefs.getString("usuarioId", null);
        String loginGuardado = prefs.getString("login", "");

        editarLogin.setText(loginGuardado);
        btnGuardar.setOnClickListener(v -> {
            String nuevoLogin = editarLogin.getText().toString().trim();
            String nuevaPass  = editarPass.getText().toString().trim();

            if (nuevoLogin.isEmpty() || nuevaPass.isEmpty()) {
                Toast.makeText(getContext(),
                        "Login y contraseña son obligatorios",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (usuarioId == null) {
                Toast.makeText(getContext(),
                        "No se encontró el usuario en sesión",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario.UsuarioUpdate body = new Usuario.UsuarioUpdate(nuevoLogin, nuevaPass);
            Call<Usuario.UsuarioRespuesta> call =
                    usuarioApi.actualizarLogin(usuarioId, body);

            call.enqueue(new Callback<Usuario.UsuarioRespuesta>() {
                @Override
                public void onResponse(Call<Usuario.UsuarioRespuesta> call,
                                       Response<Usuario.UsuarioRespuesta> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Usuario u = response.body().getUser();

                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("login", u.getEmail());
                        editor.apply();

                        Toast.makeText(getContext(),
                                "Datos de acceso actualizados",
                                Toast.LENGTH_SHORT).show();
                        NavController navController =
                                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                        navController.navigate(R.id.homeFragment);

                    } else {
                        Toast.makeText(getContext(),
                                "No se pudo actualizar el usuario",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Usuario.UsuarioRespuesta> call, Throwable t) {
                    Toast.makeText(getContext(),
                            "Error de conexión: " + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
