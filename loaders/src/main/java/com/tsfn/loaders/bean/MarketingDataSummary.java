package com.tsfn.loaders.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketingDataSummary {
	private Long accountId;
	private Integer maxLikes;
	private Integer minLikes;
	private String postMax;
	private String postMin;
}
