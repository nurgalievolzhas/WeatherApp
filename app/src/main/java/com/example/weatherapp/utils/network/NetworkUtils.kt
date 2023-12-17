package com.example.weatherapp.utils.network

import com.example.weatherapp.common.constants.CommonConstants.EMPTY
import com.example.weatherapp.common.constants.NetworkConstants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.EOFException
import java.io.IOException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLHandshakeException

sealed class ResultApi<out T : Any> {

    data class Success<out T : Any>(val data: T?) : ResultApi<T>()
    data class HttpError<T>(val error: T?, val code: Int = 0) : ResultApi<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is HttpError<*> -> "Error[exception=${error}]"
        }
    }
}

suspend fun <T : Any> safeApiCall(
    call: suspend () -> T
): ResultApi<T> {
    return try {
        ResultApi.Success(call.invoke())
    } catch (e: Exception) {
        handleException(e, ErrorHttpResponse())
    }
}

fun <T : Any, V> handleException(
    e: Exception,
    customResponseError: NetworkErrorHttpPrinter<V>?
): ResultApi<T> =
    when (e) {
        is HttpException -> handleHttpException(e, customResponseError)
        is SocketTimeoutException -> ResultApi.HttpError("Сервер не отвечает",
            HttpURLConnection.HTTP_GATEWAY_TIMEOUT
        )
        is SSLHandshakeException -> ResultApi.HttpError("Возникли проблемы с сертификатом",
            HttpURLConnection.HTTP_GATEWAY_TIMEOUT
        )
        is JsonParseException -> ResultApi.HttpError("Ошибка обработки запроса")
        is EOFException -> ResultApi.HttpError("Ошибка загрузки, попробуйте еще раз")
        is ConnectException,
        is UnknownHostException -> ResultApi.HttpError("Возникли проблемы с интернетом",
            HttpURLConnection.HTTP_GATEWAY_TIMEOUT
        )
        is IOException ->  ResultApi.HttpError(e.message, HttpURLConnection.HTTP_INTERNAL_ERROR)
        else -> ResultApi.HttpError("Ошибка : ${e.javaClass.simpleName} ${e.localizedMessage}")
    }

private fun <T : Any, V> handleHttpException(
    e: HttpException,
    customResponseError: NetworkErrorHttpPrinter<V>?
): ResultApi<T> {
    val errorBody = e.response()?.errorBody()?.string().orEmpty()
    return when (e.code()) {
        HttpURLConnection.HTTP_UNAUTHORIZED -> {
            val reason = "Срок действия сессии истек"
            ResultApi.HttpError(customResponseError?.print(errorBody, reason), e.code())
        }
        else -> {
            ResultApi.HttpError(customResponseError?.print(errorBody, "Ошибка"), e.code())
        }
    }
}

class ErrorHttpResponse : NetworkErrorHttpPrinter<String> {
    override fun print(response: String?, default: String?): String {
        return parseJson<DefaultError>(response)?.getErrorMessage()  ?: default ?: EMPTY
    }
}
class DefaultError(val error: String? = null, private val message: String? = null){
    fun getErrorMessage() = message ?: error ?: EMPTY
}

inline fun <reified T> parseJson(json: String?): T? = try {
    Gson().fromJson(json, T::class.java)
} catch (ex: Exception) {
    null
}

interface NetworkErrorHttpPrinter<T> {

    fun print(response: String?, default: String?): T
}

inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    baseUrl: String
): T = Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create(createGson()))
    .build()
    .create(T::class.java)

fun createGson(): Gson = GsonBuilder().setLenient().create()

fun createRetrofitOkHttpClient(): OkHttpClient {
    val okHttpBuilder = OkHttpClient.Builder()
        .connectTimeout(NetworkConstants.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
        .readTimeout(NetworkConstants.READ_TIMEOUT, TimeUnit.MILLISECONDS)
    return okHttpBuilder.build()
}