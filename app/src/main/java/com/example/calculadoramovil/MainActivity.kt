package com.example.calculadoramovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CalculatorApp()
            }
        }
    }
}

@Composable
fun CalculatorApp() {
    var displayText by remember { mutableStateOf("0") }
    var operand1 by remember { mutableStateOf("") }
    var operand2 by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf<Char?>(null) }

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF6200EE),
            background = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            DisplayScreen(displayText)
            CalculatorButtons(
                onNumberClick = { number ->
                    if (operator == null) {
                        operand1 += number
                        displayText = operand1
                    } else {
                        operand2 += number
                        displayText = operand2
                    }
                },
                onOperatorClick = { op ->
                    if (operand1.isNotEmpty()) {
                        operator = op
                    }
                },
                onCalculate = {
                    if (operand1.isNotEmpty() && operand2.isNotEmpty() && operator != null) {
                        val result = when (operator) {
                            '+' -> operand1.toDouble() + operand2.toDouble()
                            '-' -> operand1.toDouble() - operand2.toDouble()
                            '*' -> operand1.toDouble() * operand2.toDouble()
                            '/' -> if (operand2.toDouble() != 0.0) operand1.toDouble() / operand2.toDouble() else Double.NaN
                            else -> 0.0
                        }
                        displayText = result.toString()
                        operand1 = displayText
                        operand2 = ""
                        operator = null
                    }
                },
                onClear = {
                    operand1 = ""
                    operand2 = ""
                    operator = null
                    displayText = "0"
                }
            )
        }
    }
}

@Composable
fun DisplayScreen(displayText: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = displayText,
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun CalculatorButton(symbol: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(8.dp)
            .size(80.dp)
            .semantics { contentDescription = "Botón $symbol" }
    ) {
        Text(text = symbol, style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun CalculatorButtons(
    onNumberClick: (String) -> Unit,
    onOperatorClick: (Char) -> Unit,
    onCalculate: () -> Unit,
    onClear: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row {
            CalculatorButton("7") { onNumberClick("7") }
            CalculatorButton("8") { onNumberClick("8") }
            CalculatorButton("9") { onNumberClick("9") }
            CalculatorButton("/") { onOperatorClick('/') }
        }
        Row {
            CalculatorButton("4") { onNumberClick("4") }
            CalculatorButton("5") { onNumberClick("5") }
            CalculatorButton("6") { onNumberClick("6") }
            CalculatorButton("*") { onOperatorClick('*') }
        }
        Row {
            CalculatorButton("1") { onNumberClick("1") }
            CalculatorButton("2") { onNumberClick("2") }
            CalculatorButton("3") { onNumberClick("3") }
            CalculatorButton("-") { onOperatorClick('-') }
        }
        Row {
            CalculatorButton("0", modifier = Modifier.weight(2f)) { onNumberClick("0") }
            CalculatorButton(".") { onNumberClick(".") }
            CalculatorButton("+") { onOperatorClick('+') }
        }
        Row {
            CalculatorButton("C", modifier = Modifier.weight(2f)) { onClear() }
            CalculatorButton("=") { onCalculate() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculatorApp() {
    CalculatorApp()
}