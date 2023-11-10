package nl.aldera.newsapp721447.data.mapper

import android.util.Log
import retrofit2.Response

class ResponseMapper {
    fun <T> map(response: Response<T>): Result<T> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Log.d(
                    "ResponseMapper",
                    "RESPONSEMAPPER WORKS " + response.body().toString(),
                    Exception("Body is null")
                )
                return Result.success(body)
            } else {
                Log.e(
                    "ResponseMapper",
                    "Error fetching products",
                    Exception("Body is null")
                )
                Result.failure(Exception("Error fetching products"))
            }
        } else {
            Log.e(
                "ResponseMapper",
                "Error fetching products",
                Exception((response.errorBody()?.string() ?: "unknown") + " ${response.code()}")
            )
            Result.failure(Exception("Error fetching products"))
        }
    }
}