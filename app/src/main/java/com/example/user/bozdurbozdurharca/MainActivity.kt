package com.example.user.bozdurbozdurharca

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import android.support.v4.content.ContextCompat.startActivity
import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import kotlinx.android.synthetic.main.currency_item.view.*
import lecho.lib.hellocharts.view.LineChartView




class Currency {
    var untname:String=""
    var ismname:String=""
    var name: String = ""
    var buy: String = ""
    var sell: String = ""
}

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Log.d(TAG, "OnCreateCalled")

        val downloadData = DownloadData()
        downloadData.execute("http://tcmb.gov.tr/kurlar/today.xml")     //indireceğim url verdim
        Log.d(TAG, "onCreate: Done")




    }
    private inner class DownloadData : AsyncTask<String, Void, String>() {
        private val TAG = "DownloadData"

        override fun doInBackground(vararg url: String?): String {
            Log.d(TAG, "onDoInBackground : starts with ${url[0]}")

            val rssFeed = downloadXML(url[0])
            if (rssFeed.isEmpty())
                Log.e(TAG, "doInBackground : Error Downloading")

            return rssFeed
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)

            val parseApplications = ParseApplications()
            parseApplications.parse(result)
            val currencyList = parseApplications.getCurrencyList() as ArrayList<Currency>
            val adapter:CurrencyAdapter = CurrencyAdapter(this@MainActivity, R.layout.currency_item,currencyList)
            currencyListView.adapter = adapter

            currencyListView.onItemClickListener= AdapterView.OnItemClickListener{ parent, view, position, id ->
                val intent=Intent(this@MainActivity,SharedPage::class.java)
                val currencyCode=view.currencyCode.text
                val currencyName=view.currencyIsim.text
                val currencyBuy=view.currencyBuying.text
                val currencySell=view.currencySelling.text

                intent.putExtra("currencyCode",currencyCode)
                intent.putExtra("currencyName",currencyName)
                intent.putExtra("currencyBuy",currencyBuy)
                intent.putExtra("currencySell",currencySell)
                startActivity(intent)


            }


        }

        private fun downloadXML(urlPath: String?): String {

            return URL(urlPath).readText()
        }
    }

}

