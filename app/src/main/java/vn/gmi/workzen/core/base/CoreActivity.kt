package vn.gmi.workzen.core.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

abstract class CoreActivity: AppCompatActivity() {
    protected fun requestPermission(permission:ArrayList<String>,runnable: Runnable){
        Dexter.withContext(this).withPermissions(permission)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport) {
                    if (p0.areAllPermissionsGranted()){
                        runnable.run()
                    }else{
                        Toast.makeText(this@CoreActivity,"Vui lòng cấp quyền", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    list: List<PermissionRequest>,
                    permissionToken: PermissionToken
                ) {
                    permissionToken.continuePermissionRequest()
                }
            }).check()
    }
}