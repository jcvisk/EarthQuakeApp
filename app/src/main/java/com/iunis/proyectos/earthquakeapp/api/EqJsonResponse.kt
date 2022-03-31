package com.iunis.proyectos.earthquakeapp.api

import com.squareup.moshi.Json

class EqJsonResponse(@Json(name="features") val features:List<Feature>)