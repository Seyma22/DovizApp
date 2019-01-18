package com.example.user.bozdurbozdurharca

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_graph.*
import kotlinx.android.synthetic.main.activity_shared_page.*
import org.jsoup.Jsoup
import java.text.SimpleDateFormat

class GraphActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        val degisken = intent.getStringExtra("currencyCode")

        val obj = GetSourcecode()       //onCerate içinde source kodu aldım
        obj.execute(createUrl(degisken))
    }
    private inner class GetSourcecode : AsyncTask<String, Void, ArrayList<Row>>() {
        override fun doInBackground(vararg urls: String?): ArrayList<Row> {
            var rowList = ArrayList<Row>()
            try {
                val doc = Jsoup.connect(urls[0]).get()
                val tres = doc.select("table")[0].select("tbody").select("tr")


                tres.map { tr ->
                    val date = tr.select("td")[0].text()
                    val price = tr.select("td")[1].text()
                    val row = Row()
                    row.date = date.toString()

                    val s = price.replace(",", ".")
                    row.price = s
                    rowList.add(row)
                    Log.d("anahtar", row.date)

                }


                val series = LineGraphSeries<DataPoint>()
                var i = 0.0
                rowList.reverse()
                rowList.map { row ->
                    val date1 = SimpleDateFormat("dd.MM.yyyy").parse(row.date)
                    Log.d("anakta", date1.toString())
                    series.appendData(DataPoint(date1, row.price.toDouble()), true, rowList.size)

                }


                graph.addSeries(series)
                graph.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(this@GraphActivity);
                graph.gridLabelRenderer.numHorizontalLabels = 3     //ekrana daha fazla sığmıyor 3 yerine 5 tercih ettim


                val d1= SimpleDateFormat("dd.MM.yyyy").parse(rowList[0].date)
                val d3= SimpleDateFormat("dd.MM.yyyy").parse(rowList[rowList.size-1].date)   //grafik date formatında istiyordu string->date
                graph.viewport.setMinX(d1.time.toDouble());
                graph.viewport.setMaxX(d3.time.toDouble());
                graph.viewport.isXAxisBoundsManual = true;              //grafiği sıfırdan başlattı
                //graph.gridLabelRenderer.setHumanRounding(false);


            } catch (e: Exception) {
                Log.d("error", e.toString())

            }

            return rowList
        }

        override fun onPostExecute(result: ArrayList<Row>) {
            val dateValues = ArrayList<String>()
            val priceValues = ArrayList<String>()

            result.map {
                dateValues.add(it.date)
                priceValues.add(it.price)

            }
            dateValues.map {
                Log.e("anakta", it)
            }
            priceValues.map {       //iterate over list<Int>
                Log.w("anakda", it)
            }


        }


    }

    private fun createUrl(currencyCode:String):String{
        return "https://tr.investing.com/currencies/${currencyCode.toLowerCase()}-try-historical-data"       //sitede bu şekilde değişiyor url'ler

    }
}
