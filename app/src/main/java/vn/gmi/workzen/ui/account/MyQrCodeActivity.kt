package vn.gmi.workzen.ui.account

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.github.alexzhirkevich.customqrgenerator.QrData
import com.github.alexzhirkevich.customqrgenerator.style.Color
import com.github.alexzhirkevich.customqrgenerator.vector.QrCodeDrawable
import com.github.alexzhirkevich.customqrgenerator.vector.QrVectorOptions
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBackground
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBallShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorColor
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorColors
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorFrameShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogo
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoPadding
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorPixelShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorShapes
import vn.gmi.workzen.R
import vn.gmi.workzen.databinding.ActivityMyQrCodeBinding
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MyQrCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyQrCodeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMyQrCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setListener()
        drawQrCode()
    }


    private fun setListener(){
        binding.llSelectImage.setOnClickListener{
            openPicker()
        }
    }

    private fun initTelegram(){
        val options = QrVectorOptions.Builder()
            .setPadding(.1f) // Ít padding để QR đầy đặn
            .setLogo(
                QrVectorLogo(
                    drawable = ContextCompat.getDrawable(this, R.drawable.logo),
                    size = .25f,
                    padding = QrVectorLogoPadding.Natural(.2f),
                    shape = QrVectorLogoShape.Circle
                )
            )
            .setBackground(
                QrVectorBackground(
                    drawable = ContextCompat.getDrawable(this, R.drawable.bg_circle) // Là khung xanh bo góc, bạn cần vẽ ảnh này
                )
            )
            .setColors(
                QrVectorColors(
                    dark = QrVectorColor.Solid(0xFF24467C.toInt()), // Xanh đậm
                    ball = QrVectorColor.Solid(0xFF24467C.toInt()), // Cùng màu dark
                    frame = QrVectorColor.LinearGradient(
                        colors = listOf(
                            0f to 0xFF30C7EC.toInt(), // Xanh nhạt
                            1f to 0xFF24467C.toInt(), // Xanh đậm
                        ),
                        orientation = QrVectorColor.LinearGradient.Orientation.LeftDiagonal
                    )
                )
            )
            .setShapes(
                QrVectorShapes(
                    darkPixel = QrVectorPixelShape.RoundCorners(.45f),
                    ball = QrVectorBallShape.RoundCorners(.4f),
                    frame = QrVectorFrameShape.RoundCorners(.4f)
                )
            )
            .build()
        val data = QrData.Url("https://example.com")
        val drawable : Drawable = QrCodeDrawable(data, options)

        binding.imgQrcode.setImageDrawable(drawable)
    }

    private fun drawQrCode(background: BitmapDrawable?=null){
       lifecycleScope.launch {
           val options = QrVectorOptions.Builder()
               .setPadding(.3f)
               .setLogo(
                   QrVectorLogo(
                       drawable = ContextCompat
                           .getDrawable(this@MyQrCodeActivity, R.drawable.logo),
                       size = .25f,
                       padding = QrVectorLogoPadding.Natural(.2f),
                       shape = QrVectorLogoShape
                           .Circle
                   )
               )
               .setBackground(
                   QrVectorBackground(
                       drawable = background ?: ContextCompat
                           .getDrawable(this@MyQrCodeActivity, R.drawable.bg_circle),
                   )
               )
               .setColors(
                   QrVectorColors(
                       dark = QrVectorColor
                           .Solid(Color(0xff345288)),
                       ball = QrVectorColor.Solid(
                           ContextCompat.getColor(this@MyQrCodeActivity, R.color.primary)
                       ),
                       frame = QrVectorColor.LinearGradient(
                           colors = listOf(
                               0f to android.graphics.Color.RED,
                               1f to android.graphics.Color.BLUE,
                           ),
                           orientation = QrVectorColor.LinearGradient
                               .Orientation.LeftDiagonal
                       )
                   )
               )
               .setShapes(
                   QrVectorShapes(
                       darkPixel = QrVectorPixelShape
                           .RoundCorners(.5f),
                       ball = QrVectorBallShape
                           .RoundCorners(.25f),
                       frame = QrVectorFrameShape
                           .RoundCorners(.25f),
                   )
               )
               .build()

           val data = QrData.Phone("0374164756")
           val drawable : Drawable = QrCodeDrawable(data, options)

           withContext(Dispatchers.Main) { binding.imgQrcode.setImageDrawable(drawable) }
       }
    }

    fun openPicker() {
        pickMultipleMedia.launch(arrayOf("image/*"))

    }

    val pickMultipleMedia = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let { handleQrWithBackground(it) }
    }

    private fun handleQrWithBackground(uri: Uri){
        val inputStream = contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val drawable = bitmap.toDrawable(resources)

        drawQrCode(drawable)
    }

}