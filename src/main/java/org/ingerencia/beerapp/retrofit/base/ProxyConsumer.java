package org.ingerencia.beerapp.retrofit.base;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ingerencia.beerapp.dao.entity.LogEntity;
import org.ingerencia.beerapp.dao.repository.LogRepository;
import org.ingerencia.beerapp.exception.RetrofitCallException;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class ProxyConsumer {

    private static LogRepository logRepository;
    public static LogEntity LogEntityG;

    public static <T> T consume(Call<T> proxyCaller){
        log.info("[ProxyConsumer] - consume {}", proxyCaller.request());
        try {
            Response<T> proxyResponse = proxyCaller.execute();
            String method = proxyCaller.request().method();
            String url = proxyCaller.request().url().toString();
            Long tx = proxyResponse.raw().receivedResponseAtMillis() - proxyResponse.raw().sentRequestAtMillis();
            LogEntity logEntity = new LogEntity();
            logEntity.setMethod(method);
            logEntity.setUrlExterno(url);
            logEntity.setTime(tx);
            LogEntityG = logEntity;
            return proxyResponse.body();
        }catch (IOException ex){
            log.error(ex.getMessage(), ex);
            throw new RetrofitCallException();
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RetrofitCallException();
        }
    }
}
