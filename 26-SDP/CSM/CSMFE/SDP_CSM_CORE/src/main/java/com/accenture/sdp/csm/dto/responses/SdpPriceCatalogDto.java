package com.accenture.sdp.csm.dto.responses;

import java.math.BigDecimal;

public class SdpPriceCatalogDto extends SdpBaseResponseDto {
	private static final long serialVersionUID = 1L;

	private Long priceCatalogId;
	private BigDecimal price;

	public SdpPriceCatalogDto() {
		super();
	}

	public Long getPriceCatalogId() {
		return priceCatalogId;
	}

	public void setPriceCatalogId(Long priceCatalogId) {
		this.priceCatalogId = priceCatalogId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
