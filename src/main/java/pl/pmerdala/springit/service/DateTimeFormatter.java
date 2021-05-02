package pl.pmerdala.springit.service;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class DateTimeFormatter {
    private final PrettyTime prettyTime;

    public DateTimeFormatter(PrettyTime prettyTime) {
        this.prettyTime = prettyTime;
    }

    public String format(LocalDateTime dateTime) {
        return prettyTime.format(dateTime, ZoneId.systemDefault());
    }
}
