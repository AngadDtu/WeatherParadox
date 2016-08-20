package com.example.angad.forecastio.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.angad.forecastio.R;

public class AlertDialogFragment extends android.app.DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context=getActivity();
        AlertDialog.Builder builder=new AlertDialog.Builder(context)
                .setTitle(R.string.alert_title)
                .setMessage(R.string.alertDialog_message)
                .setPositiveButton(R.string.ok_button_text,null);

AlertDialog dialog=builder.create();
        return dialog;

    }
}
