package tarjetas.dwh.com.tarjetas.fragments.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import tarjetas.dwh.com.tarjetas.R;

/**
 * Created by ricar on 04/01/2017.
 */
public class SinOpcionesDeSeguridadDialog extends DialogFragment {
    private SinOpcionesDeSeguridadListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.MensajeSinOpcionesSeguridad)
                .setPositiveButton(R.string.btnAceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onRegresarFlujo();
                    }
                })
                .setTitle(R.string.TitleMisTarjetas);
        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SinOpcionesDeSeguridadListener) {
            listener = (SinOpcionesDeSeguridadListener) context;
        } else {
            throw new IllegalArgumentException(context.toString() + "debe de implementar en onAttach");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface SinOpcionesDeSeguridadListener{
        void onRegresarFlujo();
    }

}
