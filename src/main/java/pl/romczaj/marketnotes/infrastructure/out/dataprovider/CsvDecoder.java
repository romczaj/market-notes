package pl.romczaj.marketnotes.infrastructure.out.dataprovider;

import com.opencsv.bean.CsvToBeanBuilder;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Slf4j
class CsvDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type colletionType) throws IOException, DecodeException, FeignException {

        log.info("Decoding response from {}", response.request().url());
        InputStream inputStream = response.body().asInputStream();
        Type nestedType = retriveNestedType(colletionType);

        List<?> parsedObjets = new CsvToBeanBuilder(new InputStreamReader(inputStream))
                .withType((Class<?>) nestedType)
                .build()
                .parse();

        if (parsedObjets.isEmpty()) {
            log.error("Failed request to {}, status code: {}", response.request().url(), response.status());
            throw new DecodeException(response.status(), "Failed request", response.request());
        }

        return parsedObjets;
    }

    private Type retriveNestedType(Type collectionType) {

        if (collectionType instanceof ParameterizedType parameterizedType) {
            if (parameterizedType.getRawType() == List.class) {
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if(actualTypeArguments.length == 1){
                    return actualTypeArguments[0];
                }
            } else {
                return parameterizedType;
            }
        }
        return collectionType;
    }
}
