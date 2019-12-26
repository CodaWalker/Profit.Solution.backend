package Profit.Solution.backend.util.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Вспомогательные методы для маппинга
 *
 * @author Pavel Zaytsev
 */
public class MapperUtils {

    /** Метод для применении функции (маппинга) к коллекции элементов */
    public static <EntityT, TargetT> List<TargetT> mapList(Function<EntityT, TargetT> mapper, Collection<? extends EntityT> elements) {
        List<TargetT> list = new ArrayList<>();

        for (EntityT element : elements) {
            TargetT target = mapper.apply(element);

            list.add(target);
        }

        return list;
    }

    /** Метод для маппинга ДТО через функцию */
    public static <EntityT, TargetT> Function<EntityT, TargetT> getMapper(Function<EntityT, TargetT> mapper) {
        return mapper;
    }
}
