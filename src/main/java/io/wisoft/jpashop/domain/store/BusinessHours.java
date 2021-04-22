package io.wisoft.jpashop.domain.store;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class BusinessHours {

    @Column(name = "off_day")
    private int offDay;

    @Column(name = "is_run_24")
    private boolean isRun24;

    @Column(name = "open_time")
    private int openTime;

    @Column(name = "close_time")
    private int closeTime;

    public BusinessHours() {}
}
