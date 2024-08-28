package com.cryptoassignment.ui.demo.compose

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.cryptoassignment.R
import com.cryptoassignment.base.BaseFragment
import com.cryptoassignment.base.observe
import com.cryptoassignment.databinding.FragmentDemoControlPanel2Binding
import com.cryptoassignment.ui.demo.DemoActivity
import com.cryptoassignment.ui.demo.DemoControlPanelViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DemoControlPanel2Fragment : BaseFragment<FragmentDemoControlPanel2Binding>() {
    private val viewModel: DemoControlPanelViewModel by viewModels()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDemoControlPanel2Binding
        get() = FragmentDemoControlPanel2Binding::inflate

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigation.observe(viewLifecycleOwner) {
            (activity as DemoActivity).findNavController(R.id.nav_host_fragment).navigate(
                R.id.action_demoPanelFragment_to_currencyListFragment2,
                args = bundleOf(
                    "params" to it
                )
            )
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    uiState.userMessage?.let {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                        viewModel.userMessageShown()
                    }
                }
            }
        }
        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                MaterialTheme {
                    DemoControlPanelScreen()
                }
            }
        }
    }
}