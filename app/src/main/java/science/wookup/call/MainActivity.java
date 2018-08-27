package science.wookup.call;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void makeCall(final View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Call Permission")
                            .setMessage("How am I supposed to make call without call permission? Please give the call permission.")
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
                                }
                            })
                            .setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(MainActivity.this, "Please grant the permission!", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                } else {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
                }
            } else {
                call();
            }
        } else {
            call();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                call();
            } else {
                Toast.makeText(this, "Please grant the permission!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void call() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + "12345678"));
        startActivity(intent);
    }
}
