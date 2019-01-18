package com.example.user.bozdurbozdurharca

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class ParseApplications {
    private val TAG = "ParseApplications"
    val currencies = ArrayList<Currency>()
    var status = true

    fun parse(xmlData: String): Boolean {

        var inCurrency = false
        var textValue = ""

        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            xpp.setInput(xmlData.reader())
            var eventType = xpp.eventType
            var currentRecord = Currency()
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = xpp.name
                var currencyCode = ""
                when (eventType) {

                    XmlPullParser.START_TAG -> {
                        Log.d(TAG, "parse : Starting tag for $tagName")
                        if (tagName == "Currency") {
                            inCurrency = true
                            currencyCode = xpp.getAttributeValue(null, "CurrencyCode")
                            currentRecord.name = currencyCode
                        }

                    }

                    XmlPullParser.TEXT -> textValue = xpp.text

                    XmlPullParser.END_TAG -> {
                        Log.d(TAG, "parse : Ending tag for $tagName")
                        if (inCurrency) {

                            when (tagName) {
                                "Currency" -> {
                                    currencies.add(currentRecord)
                                    currentRecord = Currency()
                                    inCurrency = false
                                }
                                "Unit" -> {
                                    currentRecord.untname = textValue
                                }
                                "Isim" -> {
                                    currentRecord.ismname = textValue
                                }
                                "ForexBuying" -> {
                                    currentRecord.buy = textValue
                                }
                                "ForexSelling" -> {
                                    currentRecord.sell = textValue
                                }
                            }
                        }
                    }
                }
                eventType = xpp.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
        return status
    }


    fun getCurrencyList(): ArrayList<Currency>? {

        if (status) {
            return currencies
        } else {
            return null
        }
    }
}