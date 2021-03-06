package Capstone.MonitoringSystem.domain.Storage;

import Capstone.MonitoringSystem.domain.Stock.Stock;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter @Setter
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storage_id")
    private Long id;

    @Column(name = "storage_name")
    private String name;

    private Integer temperature;

    private Integer humidity;

    private Double maxCapacity;

    @OneToMany(mappedBy = "storage")
    private List<Stock> stocks = new ArrayList<>();

    public Double getTotalStockQuantity() {
        Double total = 0.0;
        for (Stock stock : stocks) {
            total += stock.getQuantity();
        }
        return total;
    }
}
