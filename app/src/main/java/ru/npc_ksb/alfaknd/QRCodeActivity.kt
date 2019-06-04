package ru.npc_ksb.alfaknd

import android.os.Build
import android.os.Bundle
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.widget.Toast
import com.google.zxing.Result

import kotlinx.android.synthetic.main.activity_qrcode.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import me.dm7.barcodescanner.zxing.ZXingScannerView.ResultHandler

class QRCodeActivity : AppCompatActivity(), ResultHandler {
    companion object {
        private var REQUEST_CAMERA_CODE : Int = 1
    }

    private var scannerView: ZXingScannerView? = null

    fun checkPermissions() : Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_CODE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.scannerView = ZXingScannerView(this)

        // setContentView(R.layout.activity_qrcode)
        setContentView(this.scannerView)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkPermissions()) {
            } else {
                this.requestPermissions()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        /*if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Не разрешения для камеры", Toast.LENGTH_LONG).show()
            return
        }*/
        when (requestCode) {
            REQUEST_CAMERA_CODE -> {
                if (grantResults.count() > 0) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                            this.requestPermissions()
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (checkPermissions()) {
            this.scannerView?.setResultHandler(this)
            this.scannerView?.startCamera()
        }
    }

    override fun onDestroy() {
        this.scannerView?.stopCamera()
        super.onDestroy()
    }

    override fun handleResult(result: Result) {
        Log.d("qwerty", result.text)
        this.scannerView?.resumeCameraPreview(this)
    }
}