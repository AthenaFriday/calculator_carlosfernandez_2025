package com.athenafriday.birdiefragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.math.BigDecimal

class CalculatorViewModel : ViewModel() {

    private val _input = MutableLiveData<String>("0")
    val input: LiveData<String> = _input

    private var current = ""
    private var operand: BigDecimal? = null
    private var operator: String? = null

    fun onButtonClick(value: String)
    {
        when (value) {
            "C" -> clear()
            "=" -> calculate()
            "+", "-","x", "÷" -> setOperator(value)
            "." -> addDecimal()
            else -> appendNumber(value)
        }
    }

    private fun clear()
    {
        current =""
        operand = null
        operator = null
        _input.value = "0"
    }

    private fun appendNumber(number: String)
    {
        if(current == "0") current = ""
        current += number
        _input.value = current
    }

    private fun addDecimal() {
        if(!current.contains(".")){
            if(current.isEmpty()) current = "0"
            current += "."
            _input.value = current
        }
    }

    private fun setOperator(op : String)
    {
        operand =  current.toBigDecimalOrNull()
        if(operand != null)
        {
            current = ""
            operator = op
        }
    }

    private fun calculate()
    {
        val secondOperand = current.toBigDecimalOrNull() ?: return
        val result =  when (operator) {
            "+" -> operand?.plus(secondOperand)
            "-" -> operand?.minus(secondOperand)
            "x" -> operand?.times(secondOperand)
            "÷" -> if (secondOperand != BigDecimal.ZERO)
            {
                operand?.divide(secondOperand, 10, BigDecimal.ROUND_HALF_UP)
            }  else  {null}
            else -> null
        }
        result?.let {
            current = it.stripTrailingZeros().toPlainString()
            _input.value = current
            operand = null
            operator = null
        }

    }
}