package charusat.vrund;

import android.*;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static charusat.vrund.R.id.start;
import static charusat.vrund.R.id.url;

/**
 * A simple {@link Fragment} subclass.
 */


/**
 * Only for Authorized persons
 *
 */
public class Scanner_Fragment extends Fragment {

    View v;
    SurfaceView cameraView;
    EditText barcodeInfo;
    CameraSource cms;
    BarcodeDetector barcodeDetector;

    Button scan;
    Button download_php;
    Button check;
    SharedPreferences sharedpreferences;
    ArrayList<String> al;


    public Scanner_Fragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_barcode_scan, container, false);
        sharedpreferences = getContext().getSharedPreferences("abcd", Context.MODE_PRIVATE);
        loadFromSharedPrefs();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);

        cameraView = (SurfaceView) v.findViewById(R.id.camera_view);
        barcodeInfo = (EditText) v.findViewById(R.id.code_info);
        scan = (Button) v.findViewById(R.id.scan);
        download_php = (Button) v.findViewById(R.id.download_php);
        check = (Button) v.findViewById(R.id.check);
        setUp();
    }

    private void setUp()
    {
        setScanButton();
        setDownloadButton();
        setBarCodeInfo();
        setUpCheck();
        if (checkPermission()) {
            setUpOthers();
        } else {
            Toast.makeText(getContext(), "Please grant Camera Permission", Toast.LENGTH_SHORT).show();
            getPermissionAndStart();
        }

    }

    private void setUpCheck()
    {
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checksss();
            }
        });

    }

    private void checksss()
    {
        String r = barcodeInfo.getText().toString().toUpperCase().trim();

        String message = "Participant NOT FOUND \uD83D\uDE13";

        if(al.contains(r))
        {
            message = "Participant FOUND ☺";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startScanning();
            }
        }).show();
    }

    private void setBarCodeInfo()
    {
        barcodeInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setScanButton()
    {
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScanning();
            }
        });
    }

    private void setDownloadButton()
    {

        download_php.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ProgressTask(getContext()).execute("http://yashsodha.in/vrund/getDetails.php");
            }
        });
        new ProgressTask(getContext()).execute("http://yashsodha.in/vrund/getDetails.php");

    }

    private void getPermissionAndStart() {
        requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 100);
    }

    private void setUpOthers() {
        barcodeDetector =
                new BarcodeDetector.Builder(getContext())
                        .setBarcodeFormats(Barcode.ALL_FORMATS)
                        .build();

        cms = new CameraSource
                .Builder(getContext(), barcodeDetector)
                .setAutoFocusEnabled(true)
                .build();


        setProcess();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    else
                    {
                        cms.start(cameraView.getHolder());
                    }
                }
                catch (Exception ie)
                {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder)
            {
                cms.stop();
            }
        });
    }

    private void setProcess()
    {
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    String val = barcodes.valueAt(0).displayValue;
                    Log.i("Val", val);
                    barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
                        public void run() {

                            barcodeInfo.setText(    // Update the TextView
                                    barcodes.valueAt(0).displayValue
                            );
                            checksss();
                            stopScanning();
                        }
                    });
                }
            }
        });
    }

    private void startScanning()
    {
        barcodeInfo.post(new Runnable() {
            @Override
            public void run() {
                try {
                    barcodeInfo.setText("");
                    if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    setProcess();
                    cms.start(cameraView.getHolder());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void stopScanning()
    {
        cms.stop();
        barcodeDetector.release();
    }

    private Boolean checkPermission()
    {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            Log.i("Camera Permission", "False");
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            setUp();
        }
        else
        {
            getPermissionAndStart();
        }
    }

    private void setInsideSharedPrefs(String ss)
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("allids", ss);
        editor.commit();

    }

    private void loadFromSharedPrefs()
    {
        al = new ArrayList<>();
        try {
            if (!sharedpreferences.getString("allids", "").equals(""))
            {
                JSONArray ja = new JSONArray(sharedpreferences.getString("allids", ""));
                for (int i=0; i<ja.length(); i++)
                {
                    String ele = (String)ja.get(i);
                    al.add(ele);
                }
            }
        }
        catch (JSONException e)
        {
            Toast.makeText(getContext(), "An unexpected error occured.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }



    class ProgressTask extends AsyncTask<String, Void, String>
    {

        /** progress dialog to show user that the backup is processing. */
        private ProgressDialog dialog;
        /** application context. */
        private Context coo;
        public ProgressTask(Context c)
        {
            dialog = new ProgressDialog(c);
            dialog.setTitle("Downloading Offline");
            dialog.setMessage("All data is being saved offline into the database");
            this.coo = c;
        }


        protected void onPreExecute() {
            this.dialog.setMessage("Progress start");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(final String ss)
        {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            if(!ss.equals(""))
            {
                setInsideSharedPrefs(ss);
                loadFromSharedPrefs();
            }

            Toast.makeText(coo, "Data Downloaded!", Toast.LENGTH_SHORT).show();
        }
        private InputStream getStream(String u)
        {

            try {
                URL url = new URL(u);
                URLConnection urlConnection = url.openConnection();
                urlConnection.setConnectTimeout(1000);
                return urlConnection.getInputStream();
            }
            catch (Exception ex)
            {
                return null;
            }
        }
        String convertStreamToString(java.io.InputStream is)
        {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }


        protected String doInBackground(String ...args) {
            try
            {
                InputStream is = getStream(args[0]);

                if(is != null && !is.equals(""))
                {
                    String theString = convertStreamToString(is);
                    Log.i("GOT", theString);
                    return theString;
                }
                else
                {
                    return "";
                }
            }
            catch (Exception e)
            {
                Log.e("tag", "error", e);
                return "";
            }
        }
    }

}