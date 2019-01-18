package com.example.user.bozdurbozdurharca

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_shared_page.*
import kotlinx.android.synthetic.main.currency_item.*
import org.jsoup.Jsoup
import java.text.SimpleDateFormat

class Row {
    var date: String = ""
    var price: String = ""
}


class SharedPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_page)


        val currencyName = intent.getStringExtra("currencyName")
        val currencyCode = intent.getStringExtra("currencyCode")
        val currencyBuy = intent.getStringExtra("currencyBuy")
        val currencySell = intent.getStringExtra("currencySell")

        detailName.text = currencyName
        detailCode.text = currencyCode
        detailBuying.text = currencyBuy
        detailSelling.text = currencySell

        rb_toTl.text = "${currencyCode} -> TL"
        rb_fromTl.text = "TL -> ${currencyCode}"

        val currencySellingDouble = currencySell.replace(",", ".").toDouble()

        convertNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (convertNumber.text != null && convertNumber.text.isNotEmpty()) {
                    if (rb_toTl.isChecked) {

                        convertResult.text = (p0.toString().toDouble() * currencySellingDouble).toString()
                    } else {
                        convertResult.text= (p0.toString().toDouble()/currencySellingDouble).toString()
                    }
                } else {
                    convertResult.text = "-"
                }


            }

        })

        graphBtn.setOnClickListener{
            val intent= Intent(this,GraphActivity::class.java)
            intent.putExtra("currencyCode",currencyCode)
            startActivity(intent)
        }
    }
}
