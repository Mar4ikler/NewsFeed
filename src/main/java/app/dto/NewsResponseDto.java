package app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewsResponseDto {
    private Long id;
    private String title;
    private String text;
    private Instant date;
    private CategoryDto category;
}
