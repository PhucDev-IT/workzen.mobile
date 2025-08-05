package vn.gmi.workzen.ui.account.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.gmi.workzen.R
import vn.gmi.workzen.databinding.FragmentTransactionBinding

class TransactionFragment : Fragment() {
    private lateinit var _binding: FragmentTransactionBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)

        return binding.root
    }
}