package vn.gmi.workzen.ui.authentication.signup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.gmi.workzen.R
import vn.gmi.workzen.databinding.FragmentInputOTPBinding


class InputOTPFragment : Fragment() {
    private lateinit var _binding: FragmentInputOTPBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInputOTPBinding.inflate(inflater,container,false)

        setListener()
        return binding.root
    }

    private fun setListener(){
        binding.header.toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}