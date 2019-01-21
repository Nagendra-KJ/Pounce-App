package rvquizcorp.com.pounce_app;
/*This class provides the error messages that occur when anything goes wrong. It opens an alert dialog box with a title
and an error message. The class has only one function showError(Context context which shows the error message in the
given context.
@TODO Add more customised error messages
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

class Error {
    void showError(Context context) {
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
