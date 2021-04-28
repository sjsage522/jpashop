package io.wisoft.jpashop.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
public class Cancel {

    @Column(columnDefinition = "varchar(512)",
            name = "cancel_message")
    private String message;

    @Column(name = "canceled_at")
    private LocalDateTime time;

    public Cancel(String message, LocalDateTime time) {
        this.message = message;
        this.time = time;
    }

    protected Cancel() {}
}
