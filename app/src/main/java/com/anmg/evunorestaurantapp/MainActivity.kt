package com.anmg.evunorestaurantapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat


class Pedidos{
    fun calcularPrecioComida(precio: Int, cantidad: Int):Int{
        return cantidad * precio
    }
}

class MainActivity : AppCompatActivity() {

    var totalCazuela = 0
    var totalPChoclo = 0
    var subtotal = 0
    var propina = 0.0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Esta es la forma para sacarle los decimales .00
        //String.format("%.2f", variable).removeSuffix(".00")

        //val propinaChoclo = pctPropina * precioPChoclo

        //val propinaCazuela = pctPropina * precioCazuela

        /*
        * -- ACTIVIDADES DE PASTEL DE CHOCLO --
        */
        val totalAPagar = findViewById<TextView>(R.id.total_a_pagar)

        val switchPropina = findViewById<SwitchCompat>(R.id.switch_propina)

        val precioPChoclo = 10000

        val textViewPChoclo = findViewById<TextView>(R.id.dish_price_pastel_choclo)

        val numberPickerPChoclo = findViewById<NumberPicker>(R.id.p_choclo_qty)
        numberPickerPChoclo.minValue = 0
        numberPickerPChoclo.maxValue = 20
        numberPickerPChoclo.wrapSelectorWheel = false

        numberPickerPChoclo.setOnValueChangedListener { _, _, newVal ->
            val precioPlatoChoclo = Pedidos()
            totalPChoclo = precioPlatoChoclo.calcularPrecioComida(precioPChoclo, newVal)
            textViewPChoclo.text = totalPChoclo.toString()

            subtotalComida()
            propinaComida()

            totalAPagar.text = subtotalComida().toString()
        }

        /*
        * -- ACTIVIDADES DE CAZUELA --
        */

        val precioCazuela = 12000

        //val totalCazuela = String.format("%.2f", propinaCazuela).removeSuffix(".00")
        val textViewCazuela = findViewById<TextView>(R.id.dish_price_cazuela)

        val numberPickerCazuela = findViewById<NumberPicker>(R.id.cazuela_qty)
        numberPickerCazuela.minValue = 0
        numberPickerCazuela.maxValue = 20
        numberPickerCazuela.wrapSelectorWheel = false

        numberPickerCazuela.setOnValueChangedListener{ _, _, newVal ->
            val precioPlatoCazuela = Pedidos()

            totalCazuela = precioPlatoCazuela.calcularPrecioComida(precioCazuela, newVal)
            textViewCazuela.text = totalCazuela.toString()
            subtotalComida()
            propinaComida()
            totalAPagar.text = subtotalComida().toString()
        }

        switchPropina.setOnCheckedChangeListener{buttonView, isChecked ->
            if(isChecked){
                val total = propina.toInt() + subtotal
                totalAPagar.text = total.toString()
            } else{
                totalAPagar.text = subtotalComida().toString()
            }
        }
    }
    private fun subtotalComida():Int{
        subtotal = totalCazuela + totalPChoclo
        val textViewSubtotal = findViewById<TextView>(R.id.subtotal)
        textViewSubtotal.text = subtotal.toString()
        return subtotal
    }

    private fun propinaComida(){
        propina = subtotal * 0.1
        val textViewPropina = findViewById<TextView>(R.id.con_propina)
        textViewPropina.text = propina.toString().format("%.f", propina).removeSuffix(".0")
    }
}



