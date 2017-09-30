package charusat.vrund;

import android.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

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
    TextView barcodeInfo;
    CameraSource cms;
    BarcodeDetector barcodeDetector;

    public Scanner_Fragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_barcode_scan, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cameraView = (SurfaceView) v.findViewById(R.id.camera_view);
        barcodeInfo = (TextView) v.findViewById(R.id.code_info);

        setUp();
    }

    private void setUp() {
        if (checkPermission()) {
            setUpOthers();
        } else {
            Toast.makeText(getContext(), "Please grant Camera Permission", Toast.LENGTH_SHORT).show();
            getPermissionAndStart();
        }

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

                            //cms.stop();
                            //barcodeDetector.release();
                        }
                    });
                    /*barcodeInfo.postDelayed(new Runnable() {
                        @Override
                        public void run() {
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
                                setProcess();
                                cms.start(cameraView.getHolder());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }, 50);*/
                }
            }
        });
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
}
