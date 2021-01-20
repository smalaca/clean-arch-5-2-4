package com.smalaca.rentalapplication.domain.apartmentoffer;

import com.smalaca.rentalapplication.domain.money.Money;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@SuppressWarnings("PMD.UnusedPrivateField")
public class ApartmentOffer {
    @Id
    @GeneratedValue
    private UUID id;
    private String apartmentId;
    @Embedded
    private Money money;
    @Embedded
    private ApartmentAvailability availability;

    private ApartmentOffer() {}

    private ApartmentOffer(String apartmentId, Money money, ApartmentAvailability availability) {
        this.apartmentId = apartmentId;
        this.money = money;
        this.availability = availability;
    }

    public UUID id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApartmentOffer that = (ApartmentOffer) o;

        return new EqualsBuilder().append(apartmentId, that.apartmentId).isEquals();
    }

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(apartmentId).toHashCode();
    }

    public static class Builder {
        private String apartmentId;
        private BigDecimal price;
        private LocalDate start;
        private LocalDate end;

        private Builder() {}
        
        public static Builder apartmentOffer() {
            return new Builder();
        }

        public Builder withApartmentId(String apartmentId) {
            this.apartmentId = apartmentId;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder withAvailability(LocalDate start, LocalDate end) {
            this.start = start;
            this.end = end;
            return this;
        }

        public ApartmentOffer build() {
            return new ApartmentOffer(apartmentId, money(), availability());
        }

        private ApartmentAvailability availability() {
            return ApartmentAvailability.of(start, end);
        }

        private Money money() {
            return Money.of(price);
        }
    }
}
