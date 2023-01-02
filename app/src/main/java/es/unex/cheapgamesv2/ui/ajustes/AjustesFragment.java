package es.unex.cheapgamesv2.ui.ajustes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.unex.cheapgamesv2.AppExecutors;
import es.unex.cheapgamesv2.MenuInicialActivity;
import es.unex.cheapgamesv2.R;
import es.unex.cheapgamesv2.data.model.Usuario;
import es.unex.cheapgamesv2.data.room.CheapGamesDB;
import es.unex.cheapgamesv2.data.room.UsuarioDao;
import es.unex.cheapgamesv2.databinding.FragmentAjustesBinding;
import es.unex.cheapgamesv2.ui.home.HomeViewModel;

public class AjustesFragment extends Fragment {

    private FragmentAjustesBinding binding;
    Button editarUsuario, borrarUsuario, cerrarSesion;
    private Usuario mUsuario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentAjustesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final EditText cambiarNomUsuario = binding.cambiarNomUsuario;
        final EditText cambiarEmail = binding.cambiarEmail;
        final EditText cambiarPassword = binding.cambiarPassword;

        MenuInicialActivity activity = (MenuInicialActivity) getActivity();
        mUsuario = activity.GetDataFromIntent();

        Log.v("fragment ajustes", mUsuario.getNomUsuario());

        cambiarNomUsuario.setText(mUsuario.getNomUsuario());
        cambiarEmail.setText(mUsuario.getEmail());
        cambiarPassword.setText(mUsuario.getPassword());

        editarUsuario=binding.bAplicarCambios;
        editarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomUsuarioText = cambiarNomUsuario.getText().toString();
                String emailText = cambiarEmail.getText().toString();
                String passwordText = cambiarPassword.getText().toString();
                if(nomUsuarioText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                }else{
                    Usuario usuario = new Usuario(mUsuario.getID(), nomUsuarioText, emailText, passwordText);
                    AppExecutors.getInstance().diskIO().execute(()-> CheapGamesDB.getInstance(getActivity().getApplicationContext()).usuarioDao().update(usuario));
                    mUsuario.setNomUsuario(nomUsuarioText);
                    mUsuario.setEmail(emailText);
                    mUsuario.setPassword(passwordText);
                    Intent intent = new Intent(getActivity(), MenuInicialActivity.class);
                    usuario.packageIntent(intent, mUsuario.getNomUsuario(), mUsuario.getEmail(), mUsuario.getPassword());
                    startActivity(intent);
                }
            }
        });

        borrarUsuario = binding.bBorrarCuenta;
        borrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = new Usuario(mUsuario.getID(),mUsuario.getNomUsuario(),mUsuario.getEmail(),mUsuario.getPassword());
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.v("Usuario", mUsuario.getNomUsuario());
                        CheapGamesDB cheapGamesDB = CheapGamesDB.getInstance(getActivity().getApplicationContext());
                        UsuarioDao usuarioDao = cheapGamesDB.usuarioDao();
                        usuarioDao.delete(usuario);
                    }
                });
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}