package com.example.lab_week_10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lab_week_10.viewmodels.TotalViewModel

class FirstFragment : Fragment() {

    private lateinit var viewModel: TotalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Shared ViewModel dengan Activity
        viewModel = ViewModelProvider(requireActivity())[TotalViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewModel(view)
    }

    private fun updateText(rootView: View, total: Int) {
        rootView.findViewById<TextView>(R.id.text_total).text =
            getString(R.string.text_total, total)
    }

    private fun prepareViewModel(rootView: View) {
        // Observe LiveData dari ViewModel
        viewModel.total.observe(viewLifecycleOwner) { total ->
            updateText(rootView, total)
        }
    }

    companion object {
        fun newInstance(): FirstFragment = FirstFragment()
    }
}
