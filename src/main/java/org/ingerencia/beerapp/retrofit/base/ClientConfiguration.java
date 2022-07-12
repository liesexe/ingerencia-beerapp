package org.ingerencia.beerapp.retrofit.base;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.ingerencia.beerapp.exception.RetrofitCallException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Configuration
public abstract class ClientConfiguration {

    private static final String DEFAULT_ERROR_MESSAGE = "Lo sentimos, estamos trabajando para solucionar el inconveniente.";
    private static final String DEFAULT_ERROR_CONSUME_MESSAGE = "Lo sentimos, se ha producido un error consumiendo un recurso interno";
    private static final String ERROR_MESSAGE_OBJECT = "errorMessage";
    private static final String ERROR_MESSAGE_BODY = "message";
    private static final String ERROR_MESSAGE_UNAUTHORIZED = "User Unauthorized";
    private static final String ERROR_MESSAGE_BAD_REQUEST = "Bad Request";

    protected OkHttpClient getOkHttpClient(){
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        this.addErrorHandlerInterceptor(okHttpClientBuilder);
        return okHttpClientBuilder.build();
    }


    protected void addErrorHandlerInterceptor(OkHttpClient.Builder builder){
        Interceptor interceptor = (chain) ->{
            Request request = chain.request();
            Response response = chain.proceed(request);

            if(!response.isSuccessful()){
                if(response.code() == 401){
                    throw new RetrofitCallException(ERROR_MESSAGE_UNAUTHORIZED, HttpStatus.resolve(response.code()));
                }
                if(response.code() == 400){
                    throw new RetrofitCallException(ERROR_MESSAGE_BAD_REQUEST, HttpStatus.resolve(response.code()));
                }
                if(response.code() == 500){
                    throw new RetrofitCallException(DEFAULT_ERROR_MESSAGE, HttpStatus.resolve(response.code()));
                }
                throw new RetrofitCallException(extractErrorMessage(response), HttpStatus.resolve(response.code()));
            }
            return response;
        };
        builder.addInterceptor(interceptor);
    }

    protected Gson getGsonConverter(){
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, getLocalDateJsonDeserializer())
                .registerTypeAdapter(LocalDateTime.class, getLocalDateTimeJsonDeserializer())
                .setLenient()
                .create();
    }

    protected JsonDeserializer<LocalDate> getLocalDateJsonDeserializer(){
        return (json, typeOfT, context) -> {
            final String localDateStringValue = json.getAsString();
            if(!DatePattern.DEFAULT_DATE_REGEX.matcher(localDateStringValue).matches()){
                return null;
            }
            return LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern(DatePattern.DEFAULT_DATE_FORMATTER_PATTERN));
        };
    }

    protected JsonDeserializer<LocalDate> getLocalDateTimeJsonDeserializer(){
        return (json, typeOfT, context) -> {
            final String localDateTimeStringValue = json.getAsString();
            if(!DatePattern.DEFAULT_DATETIME_REGEX.matcher(localDateTimeStringValue).matches()){
                return null;
            }
            return LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern(DatePattern.DEFAULT_DATETIME_FORMATTER_PATTERN));
        };
    }

    private static String extractErrorMessage(final Response proxyResponse) throws IOException {
        log.info("[ClienteConfiguration] - extractErrorMessage {}", proxyResponse);
        try{
            final String jsonValue = Optional
                    .ofNullable(proxyResponse.body())
                    .orElseThrow(() -> new RetrofitCallException(DEFAULT_ERROR_CONSUME_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR))
                    .string();
            log.info("ErrorMessage {} ", jsonValue);
            if(StringUtils.isEmpty(jsonValue))
                return Optional.ofNullable(HttpStatus.resolve(proxyResponse.code()))
                        .orElse(HttpStatus.INTERNAL_SERVER_ERROR)
                        .getReasonPhrase();

            JsonObject jsonObject = new JsonParser().parse(jsonValue).getAsJsonObject();
            Optional<Object> errorMessageOpt = Optional.ofNullable(jsonObject.get(ERROR_MESSAGE_OBJECT));

            Object messageObject = errorMessageOpt;
            if(jsonObject.get(ERROR_MESSAGE_BODY) != null){
                messageObject = errorMessageOpt
                        .orElse(
                            Optional.ofNullable(jsonObject.get(ERROR_MESSAGE_BODY).toString()).orElse(DEFAULT_ERROR_MESSAGE)
                        );
            }
            return String.valueOf(messageObject).replaceAll("\"", "");
        }catch (JsonSyntaxException ex){
            log.error(ex.getMessage(), ex);
            throw new RetrofitCallException(ex.getMessage(), HttpStatus.resolve(proxyResponse.code()));
        }
    }
}
