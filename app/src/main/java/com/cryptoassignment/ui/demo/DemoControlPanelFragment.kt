package com.cryptoassignment.ui.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.cryptoassignment.R
import com.cryptoassignment.base.BaseFragment
import com.cryptoassignment.base.observe
import com.cryptoassignment.databinding.FragmentDemoControlPanelBinding
import com.cryptoassignment.local.currency.Type
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DemoControlPanelFragment : BaseFragment<FragmentDemoControlPanelBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDemoControlPanelBinding
        get() = FragmentDemoControlPanelBinding::inflate

    private val viewModel: DemoControlPanelViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigation.observe(viewLifecycleOwner) {
            (activity as DemoActivity).findNavController(R.id.nav_host_fragment).navigate(
                R.id.action_demoPanelFragment_to_currencyListFragment,
                args = bundleOf(
                    "params" to it
                )
            )
        }
        binding.createDbBtn.setOnClickListener {
            viewModel.createDBClicked()
        }
        binding.clearDbBtn.setOnClickListener {
            viewModel.clearDBClicked()
        }
        binding.showCryptoBtn.setOnClickListener {
            viewModel.showCurrencyList(Type.CRYPTO)
        }
        binding.showFiatBtn.setOnClickListener {
            viewModel.showCurrencyList(Type.FIAT)
        }
        binding.showAllCurrencyBtn.setOnClickListener {
            viewModel.showCurrencyList(Type.CRYPTO, Type.FIAT)
        }

        viewModel.toast.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
}