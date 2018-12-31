package rvquizcorp.com.pounce_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

class Error {
    void showError(Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.errorTitle)
               .setMessage(R.string.errorMessage);
        builder.setPositiveButton(R.string.OkButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
