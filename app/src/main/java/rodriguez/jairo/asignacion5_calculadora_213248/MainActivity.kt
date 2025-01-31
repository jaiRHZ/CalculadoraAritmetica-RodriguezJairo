package rodriguez.jairo.asignacion5_calculadora_213248

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var resultadoTextView: TextView
    private var primerNumero = 0.0
    private var operacionActual = ""
    private var nuevoNumero = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        resultadoTextView = findViewById(R.id.resultadoTextView)

        // Configurar listeners para botones numéricos
        val botonesNumericos = arrayOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        for (id in botonesNumericos) {
            findViewById<Button>(id).setOnClickListener { numeroPresionado(it as Button) }
        }

        // Configurar listeners para botones de operaciones
        findViewById<Button>(R.id.btnSumar).setOnClickListener { operacionPresionada("+") }
        findViewById<Button>(R.id.btnRestar).setOnClickListener { operacionPresionada("-") }
        findViewById<Button>(R.id.btnMultiplicar).setOnClickListener { operacionPresionada("*") }
        findViewById<Button>(R.id.btnDividir).setOnClickListener { operacionPresionada("/") }
        findViewById<Button>(R.id.btnIgual).setOnClickListener { calcularResultado() }
        findViewById<Button>(R.id.btnLimpiar).setOnClickListener { limpiar() }
        findViewById<Button>(R.id.btnPunto).setOnClickListener { agregarPunto() }
    }

    private fun numeroPresionado(button: Button) {
        if (nuevoNumero) {
            resultadoTextView.text = button.text
            nuevoNumero = false
        } else {
            resultadoTextView.append(button.text)
        }
    }

    private fun operacionPresionada(op: String) {
        primerNumero = resultadoTextView.text.toString().toDouble()
        operacionActual = op
        nuevoNumero = true
    }

    private fun calcularResultado() {
        try {
            val segundoNumero = resultadoTextView.text.toString().toDouble()
            val resultado = when (operacionActual) {
                "+" -> primerNumero + segundoNumero
                "-" -> primerNumero - segundoNumero
                "*" -> primerNumero * segundoNumero
                "/" -> if (segundoNumero == 0.0) {
                    resultadoTextView.text = "Error"
                    return
                } else {
                    primerNumero / segundoNumero
                }
                else -> segundoNumero
            }
            resultadoTextView.text = if (resultado.toLong().toDouble() == resultado)
                resultado.toLong().toString()
            else resultado.toString()
        } catch (e: Exception) {
            resultadoTextView.text = "Error"
        }
        nuevoNumero = true
    }

    private fun limpiar() {
        resultadoTextView.text = "0"
        primerNumero = 0.0
        operacionActual = ""
        nuevoNumero = true
    }

    private fun agregarPunto() {
        if (nuevoNumero) {
            resultadoTextView.text = "0."
            nuevoNumero = false
        } else if (!resultadoTextView.text.contains(".")) {
            resultadoTextView.append(".")
        }
    }
}
