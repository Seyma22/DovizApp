package com.example.user.bozdurbozdurharca

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CurrencyAdapter(context: Context, val resource: Int, val currencies: ArrayList<Currency>): ArrayAdapter<Currency>(context, resource, currencies) {
    private val TAG = "FeedAdapter"
    private val inflater = LayoutInflater.from(context)



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        Log.d(TAG,"getViewCalled")
        val view = inflater.inflate(resource, parent, false)


        val currencyIsim:TextView=view.findViewById(R.id.currencyIsim)
        val currencyCode: TextView = view.findViewById(R.id.currencyCode)
        val currencyUnit:TextView=view.findViewById(R.id.currencyUnit)
        val currencyBuying: TextView = view.findViewById(R.id.currencyBuying)
        val currencySelling: TextView = view.findViewById(R.id.currencySelling)

        val currentCurrency = currencies[position]


        currencyIsim.text=currentCurrency.ismname
        currencyCode.text = currentCurrency.name
        currencyUnit.text=currentCurrency.untname
        currencyBuying.text = currentCurrency.buy
        currencySelling.text = currentCurrency.sell


        return view
    }

    override fun getCount(): Int {
        Log.d(TAG, "getCount called")
        return currencies.size
    }
}