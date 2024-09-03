package ir.mhkapr.webtaxi.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogDTO {
    LogLevel logLevel;
    String message;
    String source;
    @DateTimeFormat(pattern = "yyyy-MM-dd/hh-mm-ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd/hh-mm-ss")
    Date timestamp;
    String objectData;
}
