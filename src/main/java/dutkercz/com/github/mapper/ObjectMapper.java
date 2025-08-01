package dutkercz.com.github.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectMapper {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    // Entidade <> DTO
    public static <O, D> D parseObject(O origin, Class<D> destination){
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> originList, Class<D> destination){
        return originList != null ?
                originList.stream().map(x -> mapper.map(x, destination)).collect(Collectors.toList())
                : Collections.emptyList();
    }
}
