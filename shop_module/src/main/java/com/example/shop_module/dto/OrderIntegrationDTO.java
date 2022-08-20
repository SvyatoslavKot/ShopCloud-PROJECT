package com.example.shop_module.dto;

import com.example.shop_module.domain.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderIntegrationDTO {
    private Long orderId;
    private String username;
    private String address;
    private List<OrderDetailsDTO> details;

    public static class OrderDetailsDTO {
        private String product;
        private Double price;
        private Double amount;
        private Double sum;

        public OrderDetailsDTO(OrderDetails details) {
            this.product = details.getProduct().getTitle();
            this.price = details.getPrice().doubleValue();
            this.amount = details.getAmount().doubleValue();
            this.sum = details.getPrice().multiply(details.getAmount()).doubleValue();
        }
    }

    @Override
    public String toString() {
        return "OrderIntegrationDTO{" +
                "orderId=" + orderId +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", details=" + details +
                '}';
    }
}
