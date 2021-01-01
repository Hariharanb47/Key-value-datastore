package Model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Data {
    String key;
    String value;
    int timeToLive;
    long creationDateTimeMillis;
}
