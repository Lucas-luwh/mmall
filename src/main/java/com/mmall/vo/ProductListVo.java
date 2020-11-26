package com.mmall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @package: com.mmall.vo
 * @author: luweihong
 * @description:
 * @create: 2020/11/25:22:36
 * @version: 1.0
 */
@Data
public class ProductListVo {
	private Integer id;
	private Integer categoryId;

	private String name;
	private String subtitle;
	private String mainImage;
	private BigDecimal price;

	private Integer status;

	private String imageHost;
}
