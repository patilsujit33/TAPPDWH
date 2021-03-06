package tarjetas.dwh.com.tarjetas.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.renderscript.Double2;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import tarjetas.dwh.com.tarjetas.DTO.Categoria;
import tarjetas.dwh.com.tarjetas.R;
import tarjetas.dwh.com.tarjetas.fragments.dialog.CategoriaCambiarImagenDialog;
import tarjetas.dwh.com.tarjetas.model.Categorias;
import tarjetas.dwh.com.tarjetas.utilities.FormatCurrency;
import tarjetas.dwh.com.tarjetas.utilities.RealmAdministrator;

/**
 * Created by ricar on 30/05/2017.
 */

public class CategoriasConfiguracionFragment extends Fragment implements View.OnClickListener{


    CategoriasConfiguracionFragmentListener listener;
    Button btnGuardarModificacionesCategoria;
    FloatingActionButton imgCategoria;
    EditText txtCategoriaNombreConfiguracion,txtCategoriaMontoLimiteConfiguracion;
    Spinner spn_CicloLimiteConfiguracion;
    Categorias categoria;
    Toolbar detail_toolbar;
    int hImage, hImageDrawer;

    int idCategoriaSeleccionada;

    public static final String FRAGMENT_TAG = "CategoriasConfiguracionFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_categorias_configurar_1,container,false);

        btnGuardarModificacionesCategoria = (Button) v.findViewById(R.id.btnGuardarModificacionesCategoria);
        imgCategoria = (FloatingActionButton) v.findViewById(R.id.imbCategoriaConfiguracion);
        txtCategoriaMontoLimiteConfiguracion = (EditText) v.findViewById(R.id.txtCategoriaMontoLimiteConfiguracion);
        txtCategoriaNombreConfiguracion = (EditText) v.findViewById(R.id.txtCategoriaNombreConfiguracion);
        spn_CicloLimiteConfiguracion = (Spinner) v.findViewById(R.id.spn_CicloLimiteConfiguracion);
        detail_toolbar = (Toolbar) v.findViewById(R.id.detail_toolbar);




        btnGuardarModificacionesCategoria.setOnClickListener(this);
        imgCategoria.setOnClickListener(this);

        categoria = RealmAdministrator.getInstance(getContext()).getCategoryById(idCategoriaSeleccionada);
        hImage = categoria.getImagen();
        hImageDrawer = categoria.getImagenDrawer();

        Picasso.with(getContext()).load(categoria.getImagen()).into(imgCategoria);
        txtCategoriaMontoLimiteConfiguracion.setText(Double.toString(categoria.getCapacidad()));
        txtCategoriaNombreConfiguracion.setText(categoria.getNombre());

        spn_CicloLimiteConfiguracion.setSelection(categoria.getRecurrence());

        detail_toolbar.setTitle("Configuración de Categoria");
        detail_toolbar.setTitleTextColor(ContextCompat.getColor(getContext(),R.color.white));

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGuardarModificacionesCategoria:

                Categorias categoria = RealmAdministrator.getInstance(getContext()).getCategoryById(idCategoriaSeleccionada);
                Categoria hCategoria = new Categoria(categoria.getId(),categoria.getNombre(),categoria.getCapacidad(),hImage,hImageDrawer,categoria.getRecurrence());


                hCategoria.setRecurrence(spn_CicloLimiteConfiguracion.getSelectedItemPosition());
                hCategoria.setNombre(txtCategoriaNombreConfiguracion.getText().toString());
                hCategoria.setCapacidad(Double.valueOf(txtCategoriaMontoLimiteConfiguracion.getText().toString()));
                RealmAdministrator.getInstance(getContext()).updateCategoriaById(hCategoria);

                getActivity().onBackPressed();

                break;
            case R.id.imbCategoriaConfiguracion:
                new CategoriaCambiarImagenDialog(getContext()) {
                    @Override
                    public void cambiarImagen(int imagen, int imagenDrawable) {
                        hImage = imagen;
                        hImageDrawer = imagenDrawable;
                        Picasso.with(getContext()).load(imagen).into(imgCategoria);
                    }
                }.show();

                break;
        }
    }



    public interface CategoriasConfiguracionFragmentListener{
        void onCancelar();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CategoriasConfiguracionFragmentListener) {
            listener = (CategoriasConfiguracionFragmentListener) context;
        } else {
            throw new IllegalArgumentException(context.toString() + "debe de implementar en onAttach");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idCategoriaSeleccionada = getArguments().getInt("idCategoria",0);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_configuracion_categoria,menu);
    }
}
