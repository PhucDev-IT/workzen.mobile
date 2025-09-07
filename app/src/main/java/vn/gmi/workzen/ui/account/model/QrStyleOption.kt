package vn.gmi.workzen.ui.account.model

import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBallShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorFrameShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorPixelShape

data class QrStyleOption(
    val name: String,
    val pixelShape: QrVectorPixelShape,
    val ballShape: QrVectorBallShape,
    val frameShape: QrVectorFrameShape
)
