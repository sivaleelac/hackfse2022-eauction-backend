package com.cognizant.hackfse.buyer.model;

import java.util.Arrays;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class Product {
	private String id;
	private Person person;
	
	@NotBlank(message = "error.client.productName.blank")
	@Pattern(regexp = "^.{5,30}", message = "error.client.productName.invalid")
	private String productName;
	private String shortDescription;
	private String detailedDescription;
	private String category;
	
	@NotBlank(message = "error.client.startingPrice.blank")
	@Pattern(regexp = "^[0-9]{10}", message = "error.client.startingPrice.invalid")
	private String startingPrice;
	private String bidEndDate;
	
	public static enum Category{
		PAINTING("Painting"),
		SCULPTOR("Sculptor"),
		ORNAMENT("Ornament");
		
		private String name;
		
		private Category(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
		
		public static boolean isValidCategoryName(String categoryName) {
			return Arrays.stream(Category.values()).anyMatch(category -> category.name.equals(categoryName));
		}
	}
}
