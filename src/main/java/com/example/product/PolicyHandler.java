package com.example.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.cloud.stream.messaging.Processor;

@Service
public class PolicyHandler {
	
	@Autowired
	ProductRepository productRepository;
	
	@StreamListener(Processor.INPUT)
	public void onEventByString1(@Payload OrderCancelled orderCancelled){
		// json data 를 파싱하는 작업 - > 객체 
		if( orderCancelled.getEventType().equals("OrderCancelled") ) {
			// 주문 성공시 재고량 변경
			Optional<Product> productById = productRepository.findById(orderCancelled.getProductId());
			Product p = productById.get();
			
			System.out.println("======== start =======");
//			Product product = new Product();
//			product.setId(orderPlaced.getProductId());
			p.setName(orderCancelled.getProductName());
		
			p.setStock( p.getStock() + orderCancelled.getQty());
			productRepository.save(p);
			
			System.out.println("======= end ========");
		}
	}

	@StreamListener(Processor.INPUT)
	public void onEventByString(@Payload OrderPlaced orderPlaced){
		
		// 주문 성공시 재고량 변경 
		
		
		if( orderPlaced.getEventType().equals("OrderPlaced") ) {
			// 주문 성공시 재고량 변경
			Optional<Product> productById = productRepository.findById(orderPlaced.getProductId());
			Product p = productById.get();
			
			System.out.println("======== start =======");
//			Product product = new Product();
//			product.setId(orderPlaced.getProductId());
			p.setName(orderPlaced.getProductName());
		
			p.setStock( p.getStock() - orderPlaced.getQty());
			productRepository.save(p);
			
			System.out.println("======= end ========");
			
//			productRepository.findById(orderPlaced.getProductId())
//            .ifPresent(
//                    product -> {
//                        product.setStock(product.getStock() - orderPlaced.getQty());
//                        productRepository.save(product);
//                    }
//            );
    
			
		}
		
		
		
	}
	
}
