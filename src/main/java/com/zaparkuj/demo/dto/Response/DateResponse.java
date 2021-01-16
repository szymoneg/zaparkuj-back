package com.zaparkuj.demo.dto.Response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DateResponse {

    private String dateBegin;
    private String dateEnd;

    public DateResponse(String dateBegin, String dateEnd) {
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }
}
