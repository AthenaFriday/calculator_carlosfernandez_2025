package com.athenafriday.birdiefragment.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.athenafriday.birdiefragment.R
import com.athenafriday.birdiefragment.databinding.FragmentCalculatorBinding
import com.athenafriday.birdiefragment.viewmodel.CalculatorViewModel

class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() =  _binding!!
    private val viewModel: CalculatorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.input.observe(viewLifecycleOwner, Observer {
            binding.tvCalcInput.text = it
        })

        val buttons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7,
            binding.btn8, binding.btn9, binding.btnClear, binding.btnPlus,
            binding.btnMinus, binding.btnMultiply, binding.btnDivide,
            binding.btnEquals, binding.btnDot

        )

        for(button in buttons) {
            button.setOnClickListener{
                val value = (it as Button).text.toString()
                viewModel.onButtonClick(value)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
