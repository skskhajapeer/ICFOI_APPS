package com.roomdb.example.model

import com.google.gson.annotations.SerializedName

data class AddDataModel (

	@SerializedName("Sites") val sites : List<Sites>

)