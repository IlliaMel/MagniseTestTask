package com.infinity.apps.magnisetesttask.data.model.response

import com.infinity.apps.magnisetesttask.domain.model.instrument.response.InstrumentDataResponse


data class InstrumentDataModel(
    val id: String,
    val symbol: String,
    val kind: String,
    val exchange: String?,
    val description: String,
    val tickSize: Double,
    val currency: String,
    val baseCurrency: String?,
    val providerList: List<String>
) {
    companion object {
        fun fromInstrumentDataList(instrumentDataList: InstrumentDataResponse): List<InstrumentDataModel> {
            return instrumentDataList.data.map { instrumentData ->
                InstrumentDataModel(
                    id = instrumentData.id,
                    symbol = instrumentData.symbol,
                    kind = instrumentData.kind,
                    exchange = instrumentData.exchange,
                    description = instrumentData.description,
                    tickSize = instrumentData.tickSize,
                    currency = instrumentData.currency,
                    baseCurrency = instrumentData.baseCurrency,
                    providerList = instrumentData.getTopLevelMappingsKeys()
                )
            }
        }
    }
}