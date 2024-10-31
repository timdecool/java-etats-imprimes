package introduction.stream;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        {
            List<Integer> list = List.of(0, 1, 2, 3);
            Stream<Integer> stream = list.stream();
            Function<Integer, Integer> integerIntegerFunction = e -> e + 1;
            Stream<Integer> integerStream = stream.map(integerIntegerFunction);
            integerStream.collect(Collectors.toList());
        }
        {
            List<Integer> list = List.of(0, 1, 2, 3);
            Stream<Integer> stream = list.stream();
            stream.forEach(e -> System.out.println(e));
        }
    }
}
